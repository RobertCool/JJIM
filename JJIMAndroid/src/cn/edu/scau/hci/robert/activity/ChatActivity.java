/**
 * 
 */
package cn.edu.scau.hci.robert.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.scau.hci.robert.R;
import cn.edu.scau.hci.robert.adapter.ChatMsgAdapter;
import cn.edu.scau.hci.robert.common.Action;
import cn.edu.scau.hci.robert.common.ImCache;
import cn.edu.scau.hci.robert.common.SettingAttribute;
import cn.edu.scau.hci.robert.entity.Crowd;
import cn.edu.scau.hci.robert.entity.Friend;
import cn.edu.scau.hci.robert.service.MessageService;
import cn.edu.scau.hci.robert.util.Logger;
import cn.edu.scau.hci.robert.util.MessageStatus;
import cn.edu.scau.hci.robert.util.MessageType;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author robert
 *聊天窗口
 */
public class ChatActivity extends Activity implements OnClickListener{
	
	private ListView chatListView;
	private ChatMsgAdapter chatAdapter;
	private List<Map<String,Object>> chatMsgList;
	
	private Button backBtn,setBtn,sendBtn,emotionBtn,picBtn;
	private EditText contentEdit;
	private TextView titleView;

	private Friend friend;
	private Crowd crowd;
	
	private MyReciver mReciver = new MyReciver();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_form_layout);
		
		if(this.getIntent().getSerializableExtra("friend") != null){
			friend = (Friend) this.getIntent().getSerializableExtra("friend");
		}else{
			crowd = (Crowd) this.getIntent().getSerializableExtra("crowd");
		}
			
		InitViews();
		InitData();

		IntentFilter intentFilter = new IntentFilter(Action.JJIMService_New_Message);
		this.registerReceiver(mReciver, intentFilter);
	}
	
	private void InitViews() {
		chatListView = (ListView)findViewById(R.id.chat_form_list);
		backBtn = (Button)findViewById(R.id.chat_top_back);
		setBtn = (Button)findViewById(R.id.chat_top_choose);
		sendBtn = (Button)findViewById(R.id.chat_bottom_send);
		picBtn = (Button)findViewById(R.id.chat_bottom_sendpic);
		emotionBtn = (Button)findViewById(R.id.chat_bottom_emotion);
		
		backBtn.setOnClickListener(this);
		sendBtn.setOnClickListener(this);
		setBtn.setOnClickListener(this);
		picBtn.setOnClickListener(this);
		emotionBtn.setOnClickListener(this);
		
		contentEdit = (EditText)findViewById(R.id.chat_content);
		titleView = (TextView)findViewById(R.id.chat_top_title);
		
		if(friend != null){
			titleView.setText(friend.getFriendNickName());
		}else if(crowd != null){
			titleView.setText(crowd.getCrowdName());
		}
	}
	

	private void InitData() {
		chatMsgList = new  ArrayList<Map<String,Object>>();
		
		if(friend != null){
			//显示好友发送的消息
			List<cn.edu.scau.hci.robert.entity.Message> msgs = ImCache.messages.get(friend.getFriendId());
			if(msgs == null){
				msgs = new ArrayList<cn.edu.scau.hci.robert.entity.Message>();
				ImCache.messages.put(friend.getFriendId(), msgs);
			}
			for(int i = 0 ; i < msgs.size(); i++){
				Map<String, Object> map = new HashMap<String, Object>();
				if(msgs.get(i).getUserId().equals(SettingAttribute.getInstance().getUser().getUserId())){
					//我发出去的
					map.put(ChatMsgAdapter.MSG_TYPE, ChatMsgAdapter.MSG_TO);
					map.put(ChatMsgAdapter.MSG_NAME, "我");
				}else if(msgs.get(i).getFriendId().equals(SettingAttribute.getInstance().getUser().getUserId())){
					//发给我的消息
					map.put(ChatMsgAdapter.MSG_TYPE, ChatMsgAdapter.MSG_FROM);
					map.put(ChatMsgAdapter.MSG_NAME, friend.getFriendNickName());
				}else{
					//我发出去的消息
					map.put(ChatMsgAdapter.MSG_TYPE, ChatMsgAdapter.MSG_FROM);
					map.put(ChatMsgAdapter.MSG_NAME, "我");
				}
				map.put(ChatMsgAdapter.MSG_TEXT, msgs.get(i).getMessageContent());
				map.put(ChatMsgAdapter.MSG_TIME, msgs.get(i).getMessageTime());
				chatMsgList.add(map);
			}
		}else{
			//显示群发送的消息
			List<cn.edu.scau.hci.robert.entity.Message> msgs = ImCache.crowdMsgs.get(crowd.getCrowdId());
			if(msgs == null){
				msgs = new ArrayList<cn.edu.scau.hci.robert.entity.Message>();
				ImCache.crowdMsgs.put(crowd.getCrowdId(), msgs);
			}
			for(int i = 0 ; i < msgs.size(); i++){
				Map<String, Object> map = new HashMap<String, Object>();
				if(msgs.get(i).isSend()){
					//我发出去的消息
					map.put(ChatMsgAdapter.MSG_TYPE, ChatMsgAdapter.MSG_TO);
					map.put(ChatMsgAdapter.MSG_NAME, "我");
				}
				else if(msgs.get(i).getFriendId().equals(SettingAttribute.getInstance().getUser().getUserId())){
					//发给我的消息
					map.put(ChatMsgAdapter.MSG_TYPE, ChatMsgAdapter.MSG_FROM);
					map.put(ChatMsgAdapter.MSG_NAME, msgs.get(i).getMessagePic());
				}else{
					//我发出去的消息
					map.put(ChatMsgAdapter.MSG_TYPE, ChatMsgAdapter.MSG_TO);
					map.put(ChatMsgAdapter.MSG_NAME, "我");
				}
				map.put(ChatMsgAdapter.MSG_TEXT, msgs.get(i).getMessageContent());
				map.put(ChatMsgAdapter.MSG_TIME, msgs.get(i).getMessageTime());
				chatMsgList.add(map);
			}
		}
		
		chatAdapter = new ChatMsgAdapter(chatMsgList, this);
		chatListView.setAdapter(chatAdapter);
	}
	
	private String formatDate(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(mReciver);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.chat_bottom_send:
			//TODO:发送消息
			final cn.edu.scau.hci.robert.entity.Message msg = new cn.edu.scau.hci.robert.entity.Message();
			msg.setMessageTime(this.formatDate(new Date()));
			msg.setMessageStatus(MessageStatus.UnRead.ordinal());
			msg.setUserId(SettingAttribute.getInstance().getUser().getUserId());
			msg.setMessageContent(contentEdit.getText().toString());
			msg.setMessagePic(SettingAttribute.getInstance().getUser().getNickName());
			msg.setSend(true);
			
			if(friend !=null){
				msg.setFriendId(friend.getFriendId());
				msg.setMessageType(MessageType.Friend.ordinal());
				ImCache.messages.get(friend.getFriendId()).add(msg);
			}else{
				msg.setFriendId(crowd.getCrowdId());
				msg.setMessageType(MessageType.Crowd.ordinal());
				ImCache.crowdMsgs.get(crowd.getCrowdId()).add(msg);
			}
			new Thread(new Runnable(){
				public void run() {
					MessageService ms = new MessageService();
					ms.sendMessage(msg, new android.os.Message());
				}
			}).start();
			this.InitData();
			contentEdit.setText("");
			break;
		case R.id.chat_top_back:
			this.finish();
			break;
		case R.id.chat_bottom_emotion:
			//选择表情
			break;
		case R.id.chat_bottom_sendpic:
			//选择图片
		case R.id.chat_top_choose:
			//设置
			break;
		}
	}


	public class MyReciver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			Logger.i("MyReciver_2", intent.getAction());
			ChatActivity.this.InitData();
		}
		
	}
}
