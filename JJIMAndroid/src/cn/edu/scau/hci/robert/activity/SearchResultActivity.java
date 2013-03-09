/**
 * 
 */
package cn.edu.scau.hci.robert.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.scau.hci.robert.R;
import cn.edu.scau.hci.robert.entity.Crowd;
import cn.edu.scau.hci.robert.entity.Friend;
import cn.edu.scau.hci.robert.service.CrowdService;
import cn.edu.scau.hci.robert.service.FriendService;
import cn.edu.scau.hci.robert.util.Logger;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author robert
 * 
 */
public class SearchResultActivity extends Activity implements
		OnItemClickListener {

	public static final int SEARCH_FRIEND = 1, SERACH_CROWD = 2,
			SEARCH_FRIEND_ALL = 3, SEARCH_CROWD_ALL = 4;
	public static final String SEARCH_TYPE_KEY = "search_type_key";
	public static final String SEARCH_KEY = "search_key";

	private ListView listView;
	private Button back_btn;
	private ProgressDialog proDialog;
	private TextView textView;

	private List<Map<String, String>> list;
	private int searchType;

	private List<Friend> friends = new ArrayList<Friend>();
	private List<Crowd> crowds = new ArrayList<Crowd>();

	private SimpleAdapter adapter;
	private int page = 1;
	private boolean hasMore = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.search_result_layout);

		searchType = this.getIntent().getIntExtra(SEARCH_TYPE_KEY, 0);

		this.initViews();
		this.getData();
	}

	/**
	 * 初始化界面
	 * */
	private void initViews() {
		back_btn = (Button) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		listView = (ListView) this.findViewById(R.id.search_result_listView);
		list = new ArrayList<Map<String, String>>();
		adapter = new SimpleAdapter(SearchResultActivity.this, list,
				android.R.layout.simple_list_item_2, new String[] { "name",
						"note" }, new int[] { android.R.id.text1,
						android.R.id.text2 });

		textView = new TextView(SearchResultActivity.this);
		textView.setText("点击加载更多……");
		textView.setGravity(Gravity.CENTER);
		textView.setTextColor(Color.BLACK);
		textView.setTextSize(24.0f);
		listView.addFooterView(textView);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);

		proDialog = new ProgressDialog(SearchResultActivity.this);
		proDialog.setTitle("查询");
		proDialog.setMessage("查找中，请稍候");
	}

	private void getData() {
		switch (searchType) {
		case SEARCH_CROWD_ALL:
			this.findCrowd(page);
			break;
		case SEARCH_FRIEND_ALL:
			this.findFriend(page);
			break;
		}
	}
	
	/**分页查找群*/
	private void findCrowd(final int page){
		if(!hasMore) return;
		proDialog.show();
		new Thread(new Runnable(){
			public void run(){
				Message msg = new Message();
				msg.what = SEARCH_CROWD_ALL;
				CrowdService cs = new CrowdService();
				cs.getCrowdList(page, msg);
				mHandler.sendMessage(msg);
			}
		}).start();
	}

	/** 分页查找好友 */
	private void findFriend(final int page) {
		if(!hasMore) return;
 		proDialog.show();
		new Thread(new Runnable() {
			public void run() {
				Message msg = new Message();
				msg.what = SEARCH_FRIEND_ALL;
				FriendService fs = new FriendService();
				fs.getAllFriends(page, msg);
				mHandler.sendMessage(msg);
			}
		}).start();
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (searchType) {
		case SEARCH_CROWD_ALL:
			if(position == crowds.size()){
				page++;
				this.findCrowd(page);
			}else{
				Intent intent = new Intent(SearchResultActivity.this,InfoActivity.class);
				intent.putExtra(SearchResultActivity.SEARCH_TYPE_KEY, SearchResultActivity.SERACH_CROWD);
				intent.putExtra("crowd", crowds.get(position));
				startActivity(intent);
			}
			break;
		case SEARCH_FRIEND_ALL:
			if(position == friends.size()){
				page++;
				this.findFriend(page);
			}else{
				Intent intent = new Intent(SearchResultActivity.this,InfoActivity.class);
				intent.putExtra(SearchResultActivity.SEARCH_TYPE_KEY, SearchResultActivity.SEARCH_FRIEND);
				intent.putExtra("friend", friends.get(position));
				startActivity(intent);
			}
			break;
		}
	
	}
	
	private void showList(){
		adapter.notifyDataSetChanged();
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			proDialog.dismiss();
			switch (msg.what) {
			case SEARCH_FRIEND_ALL:
				if(msg.obj != null){
					try {
						Logger.i("friend_all_resuilt", (String)msg.obj);
						JSONObject jsObj = new JSONObject((String)msg.obj);
						if(jsObj.getBoolean("success")){
							JSONObject resultObj = jsObj.getJSONObject("result");
							//是否还有更多
							hasMore = page == resultObj.getInt("totalPage");
							if(!hasMore){
								textView.setText("没有更多了");
							}
							JSONArray jsArray = resultObj.getJSONArray("list");
							for(int i = 0 ; i < jsArray.length(); i++){
								Friend friend = Friend.toFriend(jsArray.getString(i));
								friends.add(friend);
								Map<String, String> map = new HashMap<String,String>();
								map.put("name", friend.getFriendNickName());
								map.put("note", friend.getFriendNote());
								list.add(map);
							}
							showList();
						}
					} catch (JSONException e) {
						e.printStackTrace();
						Toast.makeText(SearchResultActivity.this, "查找错误，请联系我们", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(SearchResultActivity.this, "查找失败，请重试", Toast.LENGTH_SHORT).show();
				}
				break;
				
			case SEARCH_CROWD_ALL:
				if(msg.obj != null){
					try {
						Logger.i("crowd_all_resuilt", (String)msg.obj);
						JSONObject jsObj = new JSONObject((String)msg.obj);
						if(jsObj.getBoolean("success")){
							JSONObject resultObj = jsObj.getJSONObject("result");
							//是否还有更多
							hasMore = page == resultObj.getInt("totalPage");
							if(!hasMore){
								textView.setText("没有更多了");
							}
							JSONArray jsArray = resultObj.getJSONArray("list");
							for(int i = 0 ; i < jsArray.length(); i++){
								Crowd crowd = Crowd.toCrowd(jsArray.getString(i));
								crowds.add(crowd);
								Map<String, String> map = new HashMap<String,String>();
								map.put("name", crowd.getCrowdName());
								map.put("note", crowd.getCrowdNote());
								list.add(map);
							}
							showList();
						}
					} catch (JSONException e) {
						e.printStackTrace();
						Toast.makeText(SearchResultActivity.this, "查找错误，请联系我们", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(SearchResultActivity.this, "查找失败，请重试", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}

	};
}
