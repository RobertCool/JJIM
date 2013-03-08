/**
 * 
 */
package cn.edu.scau.hci.robert.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.scau.hci.robert.R;
import cn.edu.scau.hci.robert.adapter.ChatMsgAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * @author robert
 *聊天窗口
 */
public class ChatActivity extends Activity {
	
	private ListView chatListView;
	private ChatMsgAdapter chatAdapter;
	private List<Map<String,Object>> chatMsgList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_form_layout);
		InitViews();
		InitData();
		setAdapter();
	}

	private void setAdapter() {
		chatAdapter = new ChatMsgAdapter(chatMsgList, this);
		chatListView.setAdapter(chatAdapter);
	}

	private void InitData() {
		chatMsgList = new  ArrayList<Map<String,Object>>();
		
		int i=0;
		for(; i<5 ;i ++){
			Map<String,Object> map =new  HashMap<String, Object>();
			map.put(ChatMsgAdapter.MSG_TYPE, ChatMsgAdapter.MSG_FROM);
			map.put(ChatMsgAdapter.MSG_TEXT, "如果我的文字很长很长很长会怎么样呢，我是文字"+i);
			map.put(ChatMsgAdapter.MSG_TIME, new Date().toLocaleString());
			chatMsgList.add(map);
		}
		for(; i<10 ;i ++){
			Map<String,Object> map =new  HashMap<String, Object>();
			map.put(ChatMsgAdapter.MSG_TYPE, ChatMsgAdapter.MSG_TO);
			map.put(ChatMsgAdapter.MSG_TEXT, "我是文字"+i);
			map.put(ChatMsgAdapter.MSG_TIME, new Date().toLocaleString());
			chatMsgList.add(map);
		}
		
	}

	private void InitViews() {
		chatListView = (ListView)findViewById(R.id.chat_form_list);
	}
	
	

}
