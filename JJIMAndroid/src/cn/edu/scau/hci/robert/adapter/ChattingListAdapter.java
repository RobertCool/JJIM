/**
 * 
 */
package cn.edu.scau.hci.robert.adapter;

import java.util.List;
import java.util.Map;

import cn.edu.scau.hci.robert.R;

import android.content.Context;
import android.graphics.Bitmap;
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
public class ChattingListAdapter extends BaseAdapter {
	
	private List<Map<String,Object>> list = null;
	private Context context;
	
	
	public ChattingListAdapter( List<Map<String,Object>> list, Context context){
		this.list = list;
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.im_list_adapter, null);
		
		ImageView headImage = (ImageView)convertView.findViewById(R.id.im_list_image);
		TextView nameText = (TextView)convertView.findViewById(R.id.im_list_name_text);
		TextView noteText  = (TextView)convertView.findViewById(R.id.im_list_note_text);
		View linear = (View)convertView.findViewById(R.id.im_list_text_linear);
		
		linear.setBackgroundResource(R.drawable.chatting_bg);
		
		Bitmap headBitmap =(Bitmap) list.get(position).get(IMListAdapter.HEADIMAGE);
		if(headBitmap == null){
			headImage.setImageResource(R.drawable.icon);
		}else{
			headImage.setImageBitmap((Bitmap) list.get(position).get(IMListAdapter.HEADIMAGE));
		}
		nameText.setText((String) list.get(position).get(IMListAdapter.NAMETEXT)==null?"未知昵称":(String) list.get(position).get(IMListAdapter.NAMETEXT));
		noteText.setText((String) list.get(position).get(IMListAdapter.NOTETEXT)==null?"":(String) list.get(position).get(IMListAdapter.NOTETEXT));
		
		return convertView;
	}

}
