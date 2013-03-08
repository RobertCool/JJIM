/**
 * 
 */
package cn.edu.scau.hci.robert.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.scau.hci.robert.R;
import cn.edu.scau.hci.robert.adapter.ChattingListAdapter;
import cn.edu.scau.hci.robert.adapter.GroupAdapter;
import cn.edu.scau.hci.robert.adapter.IMListAdapter;
import cn.edu.scau.hci.robert.common.Action;
import cn.edu.scau.hci.robert.common.Code;
import cn.edu.scau.hci.robert.common.SettingAttribute;
import cn.edu.scau.hci.robert.entity.Crowd;
import cn.edu.scau.hci.robert.entity.Friend;
import cn.edu.scau.hci.robert.entity.JGroup;
import cn.edu.scau.hci.robert.service.CrowdService;
import cn.edu.scau.hci.robert.service.FriendService;
import cn.edu.scau.hci.robert.service.GroupService;
import cn.edu.scau.hci.robert.service.ImageService;
import cn.edu.scau.hci.robert.util.HttpUtil;
import cn.edu.scau.hci.robert.util.Logger;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * @author robert
 * 
 */
public class MainActivity extends Activity {

	private ViewPager viewPager;
	private View chattingView, friendsView, crowdView, tempView;
	private ArrayList<View> views = new ArrayList<View>();
	// 显示当前选择的选项卡
	private TextView one_text, two_text, three_text;

	private ListView chattingListView, crowdListView;
	private ExpandableListView friendsListView;

	private List<Map<String, Object>> chattingList = new ArrayList<Map<String, Object>>(),
			groupList = new ArrayList<Map<String, Object>>(),
			crowdList = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> testList;

	private List<List<Map<String, Object>>> friendsList = new ArrayList<List<Map<String, Object>>>();

	private IMListAdapter crowdAdapter;
	private GroupAdapter friendAdapter;
	private ChattingListAdapter chattingAdapter;

	private ImageView userHeadImage, userStatusImage, tabUnderlineImage;
	private TextView userNameText;

	private int bmpW, offset, currentIndex = 0;

	// 好友分组数据
	private List<JGroup> groups = new ArrayList<JGroup>();
	private Map<Long, List<Friend>> friends = new HashMap<Long, List<Friend>>();
	private List<Crowd> crowds = new ArrayList<Crowd>();

	private final int GROUP_LIST = 1, GROUP_ERROR = 2, GROUP_LIST_RESULT = 3;
	private final int CROWD_LIST = 4, CROWD_ERROR = 5;
	private final int IMAGE_GET = 6;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		InitViews();
		InitData();

		chattingAdapter = new ChattingListAdapter(testList, this);
		chattingListView.setAdapter(chattingAdapter);
		
		this.getGroupList();
	}

	/**
	 * 初始化界面元素
	 * */
	private void InitViews() {
		one_text = (TextView) findViewById(R.id.text_one);
		two_text = (TextView) findViewById(R.id.text_two);
		three_text = (TextView) findViewById(R.id.text_three);

		tabUnderlineImage = (ImageView) findViewById(R.id.im_tab_cursor);
		one_text.setTextColor(Color.WHITE);
		InitTabUnderline();

		userHeadImage = (ImageView) findViewById(R.id.im_tab_bar_head);
		this.getImage(R.id.im_tab_bar_head, SettingAttribute.getInstance().getUser().getUserIcon());
		userStatusImage = (ImageView) findViewById(R.id.im_tab_bar_status);
		userNameText = (TextView) findViewById(R.id.im_tab_bar_name);
		userNameText.setText(SettingAttribute.getInstance().getUser()
				.getNickName());

		viewPager = (ViewPager) findViewById(R.id.view_pager);
		views = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();

		chattingView = mInflater.inflate(R.layout.chatting_layout, null);
		friendsView = mInflater.inflate(R.layout.friends_layout, null);
		crowdView = mInflater.inflate(R.layout.crowd_layout, null);

		chattingListView = (ListView) chattingView
				.findViewById(R.id.chatting_listview);
		friendsListView = (ExpandableListView) friendsView
				.findViewById(R.id.friend_listview);
		crowdListView = (ListView) crowdView.findViewById(R.id.crowd_listview);
		
		friendsListView.setGroupIndicator(null);
		friendsListView.setDivider(null);
		chattingListView.setDivider(null);

		views.add(friendsView);
		views.add(crowdView);
		views.add(chattingView);

		viewPager.setAdapter(new MyPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new mOnPageChangeListener());

		one_text.setOnClickListener(new PageOnClickListener(0));
		two_text.setOnClickListener(new PageOnClickListener(1));
		three_text.setOnClickListener(new PageOnClickListener(2));

		crowdListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Log.i("OnItemClick", arg0.getClass() + ":" + arg1.getClass()
						+ ":" + arg2 + ":" + arg3);

				Intent intent = new Intent(MainActivity.this,
						ChatActivity.class);
				startActivity(intent);
			}
		});
	}

	private void InitData() {
		// TODO:获取好友列表

		testList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 15; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(IMListAdapter.NAMETEXT, "用户" + i);
			map.put(IMListAdapter.NOTETEXT, "个性签名" + i);
			testList.add(map);
		}

		for (int i = 0; i < 5; i++) {
			Map<String, Object> curGroupMap = new HashMap<String, Object>();
			curGroupMap.put(GroupAdapter.GROUP_TEXT, "Group " + i);
			groupList.add(curGroupMap);

			List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
			for (int j = 0; j < 5; j++) {
				Map<String, Object> curChildMap = new HashMap<String, Object>();
				curChildMap.put(GroupAdapter.CHILD_TEXT, "Child " + j);
				curChildMap.put(GroupAdapter.CHILD_NOTE, "note " + j);
				children.add(curChildMap);
			}
			friendsList.add(children);
		}

	}

	/** 获取分组及好友 */
	private void getGroupList() {
		new Thread(new Runnable() {

			public void run() {
				// TODO 获取分组信息
				Message msg = new Message();
				msg.what = GROUP_LIST;
				GroupService gs = new GroupService();
				if (gs.getGroup(msg)) {
					try {
						JSONObject jsObj = new JSONObject((String) msg.obj);
						if (jsObj.getBoolean("success")) {
							FriendService fs = new FriendService();
							JSONArray jsArray = jsObj.getJSONArray("result");
							groups = new ArrayList<JGroup>();
							friends = new HashMap<Long, List<Friend>>();
							for (int i = 0; i < jsArray.length(); i++) {
								JGroup j = JGroup.toGroup(jsArray.getString(i));
								groups.add(j);

								Message tempMsg = new Message();
								if (fs.getFriendsByGroupId(j.getGroupId()
										.intValue(), tempMsg)) {
									Logger.i("FriendList_byGroupId",
											(String) msg.obj);
									JSONObject jsObj1 = new JSONObject(
											(String) tempMsg.obj);
									if (jsObj1.getBoolean("success")) {
										JSONArray jsArray1 = jsObj1
												.getJSONArray("result");
										List<Friend> tempFriends = new ArrayList<Friend>();
										for (int t = 0; t < jsArray1.length(); t++) {
											Friend f = Friend.toFriend(jsArray1
													.getString(i));
											tempFriends.add(f);
										}
										friends.put(j.getGroupId(), tempFriends);
									}
								}
							}
						} else {
							msg.what = GROUP_ERROR;
							msg.obj = jsObj.getString("result");
						}
					} catch (JSONException e) {
						e.printStackTrace();
						msg.what = GROUP_ERROR;
					}
				}
				mHandler.sendMessage(msg);
			}

		}).start();
	}

	/** 显示分组 */
	private void initGroups() {
		groupList = new ArrayList<Map<String, Object>>();
		friendsList = new ArrayList<List<Map<String, Object>>>();
		for (int i = 0; i < groups.size(); i++) {
			Map<String, Object> curGroupMap = new HashMap<String, Object>();
			curGroupMap.put(GroupAdapter.GROUP_TEXT, groups.get(i)
					.getGroupName());
			groupList.add(curGroupMap);

			List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
			List<Friend> tempFriends = friends.get(groups.get(i).getGroupId());
			for (int j = 0; j < tempFriends.size(); j++) {
				Map<String, Object> curChildMap = new HashMap<String, Object>();
				curChildMap.put(GroupAdapter.CHILD_TEXT, tempFriends.get(j)
						.getFriendNickName());
				curChildMap.put(GroupAdapter.CHILD_NOTE, tempFriends.get(j)
						.getFriendNote());
				children.add(curChildMap);
			}
			friendsList.add(children);
		}
		friendAdapter = new GroupAdapter(groupList, friendsList, this);
		friendsListView.setAdapter(friendAdapter);
		friendsListView.invalidate();
		//TODO:这个地方要重新看看
//		this.getCrowdList();
	}

	/** 获取群列表 */
	private void getCrowdList() {
		new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = CROWD_LIST;
				CrowdService cs = new CrowdService();
				if (cs.getMyCrowd(msg)) {
					Logger.i("CROWD_LIST", (String) msg.obj);
					try {
						JSONObject jsObj = new JSONObject((String) msg.obj);
						if (jsObj.getBoolean("success")) {
							JSONArray jsArray = jsObj.getJSONArray("result");
							crowds = new ArrayList<Crowd>();
							for (int i = 0; i < jsArray.length(); i++) {
								Crowd crowd = Crowd.toCrowd(jsArray
										.getString(i));
								crowds.add(crowd);
							}
						} else {
							msg.what = CROWD_ERROR;
							msg.obj = jsObj.getString("result");
						}
					} catch (JSONException e) {
						e.printStackTrace();
						msg.what = CROWD_ERROR;
					}

				}
				mHandler.handleMessage(msg);
			}
		}).start();
	}
	
	/** 显示群 */
	private void initCrowds() {	
		crowdList = new ArrayList<Map<String, Object>>();
		Logger.i("Crowd_Size", crowds.size() +"");
		for(int i = 0; i <  crowds.size(); i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(IMListAdapter.NAMETEXT, crowds.get(i).getCrowdName());
			map.put(IMListAdapter.NOTETEXT, crowds.get(i).getCrowdNote());
			crowdList.add(map);
		}
		

		if(crowds.size() <= 0){
			//这里设置没有群的消息
			crowdView.setBackgroundResource(R.drawable.head);
			return;
		}else{
			crowdView.setBackgroundDrawable(null);
			crowdAdapter = new IMListAdapter(crowdList, this);
			crowdListView.setAdapter(crowdAdapter);
			crowdListView.invalidate();
		}
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
	// TODO:处理消息
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			String message = "";
			switch (msg.what) {
			case GROUP_LIST:
				MainActivity.this.initGroups();
				break;
			case GROUP_ERROR:
				message = "获取分组信息失败";
				if (msg != null) {
					message = (String) msg.obj;
				}
				MainActivity.this.showMessageBox("错误", message);
				break;
			case CROWD_LIST:
				MainActivity.this.initCrowds();
				break;
			case CROWD_ERROR:
				message = "获取群信息失败";
				if (msg != null) {
					message = (String) msg.obj;
				}
				MainActivity.this.showMessageBox("错误", message);
				break;
			case IMAGE_GET:
				if(msg.obj != null){
					ImageView iv = (ImageView) MainActivity.this.findViewById(msg.arg1);
					iv.setImageURI(Uri.fromFile(new File((String)msg.obj)));
				}
				break;
			}
		}

	};

	/** 创建菜单 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(1, Menu.FIRST, 1, "退出");
		menu.add(1, Menu.FIRST + 1, 2, "注销");
		menu.add(1, Menu.FIRST + 2, 3, "个人中心");
		menu.add(2, Menu.FIRST + 3, 4, "查找");
		menu.add(2, Menu.FIRST + 4, 5, "创建群");
		menu.add(2, Menu.FIRST + 5, 6, "关于");
		return true;
	}

	/** 响应菜单点击事件 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST:
			// 退出
			this.setResult(Code.EXIT);
			this.finish();
			break;
		case Menu.FIRST + 1:
			// 注销
			this.setResult(Code.SUCCESS);
			this.finish();
			break;
		case Menu.FIRST + 2:
			// TODO:个人中心
			this.finish();
			break;
		case Menu.FIRST + 3:
			// TODO:查找
			Intent intent = new Intent(MainActivity.this, SearchActivity.class);
			this.startActivity(intent);
			break;
		case Menu.FIRST + 4:
			// TODO:创建群
			break;
		case Menu.FIRST + 5:
			// TODO:关于
			break;
		}
		return true;
	}

	/** 按返回键时，询问是否退出 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle("退出")
					.setMessage("你确定要离开吗？")
					.setNegativeButton("取消", null)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									MainActivity.this.setResult(Code.EXIT);
									MainActivity.this.finish();
								}
							}).show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	/** 显示一个消息对话框 */
	private void showMessageBox(String title, String message) {
		new AlertDialog.Builder(MainActivity.this).setTitle(title)
				.setMessage(message).setNegativeButton("确定", null).show();
	}

	private void InitTabUnderline() {
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_underline).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (screenW / 3 - bmpW) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);

		tabUnderlineImage.setImageMatrix(matrix);
	}

	private class PageOnClickListener implements OnClickListener {

		private int index = 0;

		public PageOnClickListener(int i) {
			this.index = i;
		}

		public void onClick(View v) {
			// TODO Auto-generated method stub
			viewPager.setCurrentItem(index);
		}

	}

	private class mOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2; // 页卡1 -> 页卡3 偏移量

		public void onPageScrollStateChanged(int arg0) {
			// PrintHelper.Info(TAG,"onPageScrollStateChanged"+
			// String.valueOf(arg0));
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// PrintHelper.Info(TAG, "onPageScrolled:"+String.valueOf(arg0)
			// +"--" +String.valueOf(arg1) +"--" +String.valueOf(arg2));
		}

		public void onPageSelected(int index) {
			// 根据选中的位置，选项卡中的radioButton设置成相应的选中状态
			Animation anim = null;

			switch (index) {
			case 0:
				if (currentIndex == 1) {
					anim = new TranslateAnimation(one, 0, 0, 0);
				} else if (currentIndex == 2) {
					anim = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				if (currentIndex == 0) {
					anim = new TranslateAnimation(offset, one, 0, 0);
				} else if (currentIndex == 2) {
					anim = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				if (currentIndex == 1) {
					anim = new TranslateAnimation(one, two, 0, 0);
				} else if (currentIndex == 0) {
					anim = new TranslateAnimation(offset, two, 0, 0);
				}
				break;
			}
			currentIndex = index;

			anim.setFillAfter(true);// True:图片停在动画结束位置
			anim.setDuration(600);
			anim.setAnimationListener(new AnimationListener() {

				public void onAnimationStart(Animation animation) {
				}

				public void onAnimationRepeat(Animation animation) {
				}

				public void onAnimationEnd(Animation animation) {
					switch (currentIndex) {
					case 0:
						one_text.setTextColor(Color.WHITE);
						two_text.setTextColor(Color.BLACK);
						three_text.setTextColor(Color.BLACK);
						break;
					case 1:
						one_text.setTextColor(Color.BLACK);
						two_text.setTextColor(Color.WHITE);
						three_text.setTextColor(Color.BLACK);
						break;
					case 2:
						one_text.setTextColor(Color.BLACK);
						two_text.setTextColor(Color.BLACK);
						three_text.setTextColor(Color.WHITE);
						break;
					}
				}
			});
			tabUnderlineImage.startAnimation(anim);

		}

	}

	private class MyPagerAdapter extends PagerAdapter {

		private ArrayList<View> views;

		public MyPagerAdapter(ArrayList<View> views) {
			this.views = views;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO 此处欲实现循环左右移动的效果，以后再实现
			((ViewPager) container).removeView(views.get(position));
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			((ViewPager) container).addView(views.get(position), 0);
			return views.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

}
