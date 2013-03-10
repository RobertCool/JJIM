/**
 * 
 */
package cn.edu.scau.hci.robert.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.scau.hci.robert.R;
import cn.edu.scau.hci.robert.common.ImCache;
import cn.edu.scau.hci.robert.common.SettingAttribute;
import cn.edu.scau.hci.robert.entity.Crowd;
import cn.edu.scau.hci.robert.entity.Friend;
import cn.edu.scau.hci.robert.service.CrowdService;
import cn.edu.scau.hci.robert.service.FriendService;
import cn.edu.scau.hci.robert.service.ImageService;
import cn.edu.scau.hci.robert.util.Logger;
import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author robert
 *
 */
public class InfoActivity extends Activity implements OnClickListener{
	
	private Button backBtn;
	private ImageButton addBtn;
	private ImageView headImage;
	private TextView nameText,numText;
	private Friend friend;
	private Crowd crowd;
	
	private ProgressDialog proDialog;
	private ListView listView;
	private List<Map<String, String>> list = new ArrayList<Map<String,String>>();
	
	private int type;
	private final int IMAGE_GET = 1, FRIEND_ADD = 2, CROWD_ADD = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.info_layout);

		type = this.getIntent().getIntExtra(SearchResultActivity.SEARCH_TYPE_KEY, 0);
		if(type == SearchResultActivity.SEARCH_FRIEND){
			friend = (Friend)this.getIntent().getSerializableExtra("friend");
			this.getImage(R.id.info_head, friend.getFriendIcon());
		}else if(type == SearchResultActivity.SERACH_CROWD){
			crowd = (Crowd)this.getIntent().getSerializableExtra("crowd");
			this.getImage(R.id.info_head, crowd.getCrowdIcon());
		}
	
		this.initViews();
		this.showData();
	}
	
	private void initViews(){
		backBtn = (Button)this.findViewById(R.id.back_btn);
		addBtn = (ImageButton)this.findViewById(R.id.add_btn);
		backBtn.setOnClickListener(this);
		addBtn.setOnClickListener(this);
		
		nameText = (TextView)this.findViewById(R.id.info_name);
		numText = (TextView)this.findViewById(R.id.info_number);
		headImage = (ImageView)this.findViewById(R.id.info_head);
		
		listView = (ListView)this.findViewById(R.id.listView);
		
		if(type == SearchResultActivity.SEARCH_FRIEND){
			nameText.setText(friend.getFriendNickName());
			numText.setText(friend.getFriendAccount());
		}else if(type == SearchResultActivity.SERACH_CROWD){
			nameText.setText(crowd.getCrowdName());
			numText.setText(crowd.getCrowdNote());
		}
		
		proDialog = new ProgressDialog(this);

	}
	
	private void showData(){
		if(type == SearchResultActivity.SEARCH_FRIEND){
			
			Map<String,String> map1 = new HashMap<String, String>();
			map1.put("text1", "个性签名");
			map1.put("text2", friend.getFriendNote());
			
			Map<String,String> map2 = new HashMap<String, String>();
			map2.put("text1", "性别");
			map2.put("text2", friend.getFriendSex()==0?"男":"女");
			
			list.add(map1);
			list.add(map2);
		}else if(type == SearchResultActivity.SERACH_CROWD){
			Map<String,String> map1 = new HashMap<String, String>();
			map1.put("text1", "公告");
			map1.put("text2", crowd.getCrowdNote());
			list.add(map1);
		}
		
		listView.setAdapter(new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, 
				new String[]{"text1","text2"}, new int[]{android.R.id.text1,android.R.id.text2}));
		
	}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.add_btn:
			if(type == SearchResultActivity.SEARCH_FRIEND){
				this.addFriend();
			}else if(type == SearchResultActivity.SERACH_CROWD){
				this.addCrowd();
			}
			break;
		case R.id.back_btn:
			this.finish();
			break;
		}
	}
	
	/**添加好友*/
	private void addFriend(){
		proDialog.setTitle("添加好友");
		proDialog.setMessage("添加好友中……");
		proDialog.show();
		new Thread(new Runnable(){

			public void run() {
				Message msg = new Message();
				msg.what = FRIEND_ADD;
				FriendService fs = new FriendService();
				fs.addFriend(friend.getFriendId().intValue(), ImCache.groups.get(0).getGroupId().intValue(), msg);
				mHandler.sendMessage(msg);
			}
			
		}).start();
	}
	
	/**
	 * 加入指定的群
	 * */
	private void addCrowd(){
		proDialog.setTitle("加入群");
		proDialog.setMessage("正在申请加入群中……");
		proDialog.show();
		new Thread(new Runnable(){
			public void run(){
				Message msg = new Message();
				msg.what = CROWD_ADD;
				CrowdService cs = new CrowdService();
				cs.joinCrowd(crowd.getCrowdId(), SettingAttribute.getInstance().getUser().getUserId(), msg);
				mHandler.sendMessage(msg);
			}
		}).start();
	}
	
	/**获取图片*/
	private void getImage(final int imageViewId, final String url){
		new Thread(new Runnable(){

			public void run() {
				Message msg = new Message();
				msg.what = IMAGE_GET;
				ImageService  is = new ImageService();
				msg.obj = is.getImage(url);
				msg.arg1 = imageViewId;
				mHandler.sendMessage(msg);
			}
			
		}).start();
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			proDialog.dismiss();
			switch(msg.what){
			case IMAGE_GET:
				if(msg.obj != null){
					ImageView iv = (ImageView) InfoActivity.this.findViewById(msg.arg1);
					iv.setImageURI(Uri.fromFile(new File((String)msg.obj)));
				}
				break;
			case FRIEND_ADD:
				proDialog.dismiss();
				if(msg.obj != null){
					Logger.i("addFriend",(String)msg.obj);
					try {
						JSONObject jsObj = new JSONObject((String)msg.obj);
						if(jsObj.getBoolean("success")){
							JSONObject jsObj1 = jsObj.getJSONObject("result");
							ImCache.friends.get(jsObj1.getLong("GroupId")).add(friend);
							SettingAttribute.getInstance().setFriendListChanged(true);
							Toast.makeText(InfoActivity.this, "添加好友成功", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(InfoActivity.this, (String)jsObj.getString("result"), Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
						Toast.makeText(InfoActivity.this, "添加好友失败，请重试", Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case CROWD_ADD:
				Logger.i("addCrowd", (String)msg.obj);
				try {
					JSONObject jsObj = new JSONObject((String)msg.obj);
					if(jsObj.getBoolean("success")){
						JSONObject jsObj1 = jsObj.getJSONObject("result");
						ImCache.crowds.add(crowd);
						SettingAttribute.getInstance().setCrowdListChanged(true);
						Toast.makeText(InfoActivity.this, "加入群成功", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(InfoActivity.this, (String)jsObj.getString("result"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(InfoActivity.this, "加入群失败，请重试", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
		
	};

}
