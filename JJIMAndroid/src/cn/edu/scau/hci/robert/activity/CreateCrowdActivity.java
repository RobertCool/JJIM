/**
 * 
 */
package cn.edu.scau.hci.robert.activity;

import java.io.FileNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.scau.hci.robert.R;
import cn.edu.scau.hci.robert.common.ImCache;
import cn.edu.scau.hci.robert.common.SettingAttribute;
import cn.edu.scau.hci.robert.entity.Crowd;
import cn.edu.scau.hci.robert.service.CrowdService;
import cn.edu.scau.hci.robert.util.Logger;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author robert 创建群
 */
public class CreateCrowdActivity extends Activity implements OnClickListener {

	private Button backBtn, createBtn;
	private EditText nameEdit, noteEdit;
	private ImageView headIv;
	private Crowd crowd;

	private ProgressDialog proDialog;
	
	private final int CREATE_CROWD = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.create_crowd_layout);
		this.initViews();
		crowd  = new Crowd();
		crowd.setCrowdNote("/JJIM/image/1");
	}

	private void initViews() {
		backBtn = (Button) this.findViewById(R.id.back_btn);
		createBtn = (Button) this.findViewById(R.id.create_btn);

		nameEdit = (EditText) this.findViewById(R.id.name_edit);
		noteEdit = (EditText) this.findViewById(R.id.note_edit);
		headIv = (ImageView) this.findViewById(R.id.head_iv);

		backBtn.setOnClickListener(this);
		createBtn.setOnClickListener(this);
		headIv.setOnClickListener(this);

		proDialog = new ProgressDialog(this);
		proDialog.setTitle("创建群");
		proDialog.setMessage("正在创建……");
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_btn:
			this.finish();
			break;
		case R.id.create_btn:
			crowd.setCrowdName(nameEdit.getText().toString());
			crowd.setCrowdNote(noteEdit.getText().toString());
			proDialog.show();
			new Thread(new Runnable(){
				public void run() {
					Message msg = new Message();
					msg.what = CREATE_CROWD;
					CrowdService cs = new CrowdService();
					cs.createCrowd(crowd, msg);
					mHandler.sendMessage(msg);
				}
			}).start();
			break;
		case R.id.head_iv:
			//TODO:此处需要上传图片
			Intent intent = new Intent();
			/* 开启Pictures画面Type设定为image */
			intent.setType("image/*");
			/* 使用Intent.ACTION_GET_CONTENT这个Action */
			intent.setAction(Intent.ACTION_GET_CONTENT);
			/* 取得相片后返回本画面 */
			startActivityForResult(intent, 1);
			break;
		}
	}
	
	public Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			proDialog.dismiss();
			switch(msg.what){
			case CREATE_CROWD:
				if(msg.obj != null){
					try {
						JSONObject jsObj = new JSONObject((String)msg.obj);
						if(jsObj.getBoolean("success")){
							crowd = Crowd.toCrowd((String)jsObj.getString("result"));
							ImCache.crowds.add(crowd);
							SettingAttribute.getInstance().setCrowdListChanged(true);
							Toast.makeText(CreateCrowdActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(CreateCrowdActivity.this, jsObj.getString("result"), Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
						Toast.makeText(CreateCrowdActivity.this, "创建群错误，请联系开发人员", Toast.LENGTH_SHORT).show();
					}
				}
				else{
					Toast.makeText(CreateCrowdActivity.this, "创建群失败，请重试", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
		
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			Logger.e("uri", uri.toString());
			ContentResolver cr = this.getContentResolver();
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(cr
						.openInputStream(uri));
				/* 将Bitmap设定到ImageView */
				headIv.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
