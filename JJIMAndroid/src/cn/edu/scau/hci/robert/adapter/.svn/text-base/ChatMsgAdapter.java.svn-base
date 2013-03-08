/**
 * 
 */
package cn.edu.scau.hci.robert.adapter;

import java.util.List;
import java.util.Map;

import cn.edu.scau.hci.robert.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * @author robert
 *
 */
public class ChatMsgAdapter extends BaseAdapter{
		

		public final static String MSG_TYPE = "msg_type";
		public  static String MSG_FROM = "msg_from";
		public  static String MSG_TO = "msg_to";
		public final static String MSG_TEXT = "msg_text";
		public final static String MSG_HEAD = "msg_head";
		public final static String MSG_TIME = "msg_time";
	
	   private static final String TAG = ChatMsgAdapter.class.getSimpleName();
	   
	   private List<Map<String, Object>> list;
	   private Context context;
	   
	   public ChatMsgAdapter(List<Map<String, Object>> list, Context context){
		   this.list = list;
		   this.context = context;
	   }

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;	

			Log.i("MSG_TYPE", (String) list.get(position).get(MSG_TYPE));
			 if(list.get(position).get(MSG_TYPE).toString().equals(MSG_FROM)){
				 convertView =  LayoutInflater.from(context).inflate(R.layout.chatting_item_msg_text_left, null);
			 }else{
				 convertView =  LayoutInflater.from(context).inflate(R.layout.chatting_item_msg_text_right, null);
			 }
			 
	    	  viewHolder = new ViewHolder();
			  viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			  viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
			  viewHolder.ivHead = (ImageView)convertView.findViewById(R.id.iv_userhead);
			  
			  convertView.setTag(viewHolder);
		
		if(list.get(position).get(MSG_HEAD)!=null){
			viewHolder.ivHead.setImageBitmap((Bitmap)list.get(position).get(MSG_HEAD));
		}
		viewHolder.tvContent.setText(list.get(position).get(MSG_TEXT).toString());
		viewHolder.tvSendTime.setText(list.get(position).get(MSG_TIME).toString());
		
		return convertView;
	}
	
    static class ViewHolder { 
        public TextView tvSendTime;
        public TextView tvContent;
        public ImageView ivHead;
    }

}
