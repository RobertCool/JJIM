/**
 * 
 */
package cn.edu.scau.hci.robert.activity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.scau.hci.robert.R;
import cn.edu.scau.hci.robert.entity.Friend;
import cn.edu.scau.hci.robert.service.FriendService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * @author robert
 *	实现查找的Activity
 * 找好友或找群
 */
public class SearchActivity extends Activity implements OnClickListener{
	
	private EditText searchEdit;
	private RadioButton searchFriendRadio,searchCrowdRadio;
	private Button searchBeginBtn,searchAllBtn, backBtn;
	private ProgressDialog proDialog;

	public static final int SEARCH_FRIEND = 1, SERACH_CROWD = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_layout);
		this.initViews();
	}
	
	private void initViews(){
		searchEdit = (EditText)this.findViewById(R.id.search_num_edit);
		searchCrowdRadio = (RadioButton)this.findViewById(R.id.search_crowd_radio);
		searchFriendRadio = (RadioButton)this.findViewById(R.id.search_friend_radio);
		searchBeginBtn = (Button)this.findViewById(R.id.search_begin_btn);
		searchAllBtn = (Button)this.findViewById(R.id.search_all_btn);
		backBtn = (Button)this.findViewById(R.id.back_btn);
		
		searchBeginBtn.setOnClickListener(this);
		searchAllBtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);
		
		proDialog = new ProgressDialog(this);
		proDialog.setTitle("查询");
		proDialog.setMessage("查找中，请稍候……");
	}

	String account = "";
	public void onClick(View v) {
		if(v.getId() == R.id.search_begin_btn){
			//根据号码查找
			account = searchEdit.getText().toString();
			proDialog.show();
			new Thread(new Runnable(){
				public void run() {
					Message msg = new Message();
					if(searchCrowdRadio.isChecked()){
						msg.what = SERACH_CROWD;
					}else{
						msg.what = SEARCH_FRIEND;
						FriendService fs = new FriendService();
						fs.findFriend(account, msg);
					}
					mHandler.sendMessage(msg);
				}
			}).start();

		}else if(v.getId() == R.id.search_all_btn){
			//查看所有
			Intent intent  = new Intent(SearchActivity.this, SearchResultActivity.class);
			if(searchFriendRadio.isChecked()){
				intent.putExtra(SearchResultActivity.SEARCH_TYPE_KEY, SearchResultActivity.SEARCH_FRIEND_ALL);
			}else{
				intent.putExtra(SearchResultActivity.SEARCH_TYPE_KEY, SearchResultActivity.SEARCH_CROWD_ALL);
			}
			this.startActivity(intent);
		}
		else if(v.getId() == R.id.back_btn){
			this.finish();
		}
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			proDialog.dismiss();
			switch(msg.what){
			case SEARCH_FRIEND:
				try {
					JSONObject jsObj = new JSONObject((String)msg.obj);
					boolean result = jsObj.getBoolean("success");
					if(result){
						Friend friend = Friend.toFriend(jsObj.getString("result"));
						Intent intent = new Intent(SearchActivity.this, InfoActivity.class);
						intent.putExtra(SearchResultActivity.SEARCH_TYPE_KEY, SearchResultActivity.SEARCH_FRIEND);
						intent.putExtra("friend", friend);
						startActivity(intent);
					}else{
						Toast.makeText(SearchActivity.this, "查找不到该好友", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		
	};
	
}
