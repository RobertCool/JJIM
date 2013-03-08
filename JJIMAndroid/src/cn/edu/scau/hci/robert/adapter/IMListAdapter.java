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
public class IMListAdapter extends BaseAdapter {
	
	/**头像*/
	public static final String HEADIMAGE = "head_image";
	/**名称*/
	public static final String NAMETEXT  = "name_text";
	/**个性签名*/
	public static final String NOTETEXT = "note_text";
	
	private List<Map<String,Object>> list = null;
	private Context context;
	
	public IMListAdapter( List<Map<String,Object>> list, Context context){
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
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
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
		
		Bitmap headBitmap =(Bitmap) list.get(position).get(HEADIMAGE);
		if(headBitmap == null){
			headImage.setImageResource(R.drawable.icon);
		}else{
			headImage.setImageBitmap((Bitmap) list.get(position).get(HEADIMAGE));
		}
		nameText.setText((String) list.get(position).get(NAMETEXT)==null?"未知昵称":(String) list.get(position).get(NAMETEXT));
		noteText.setText((String) list.get(position).get(NOTETEXT)==null?"":(String) list.get(position).get(NOTETEXT));
		
		return convertView;
	}

}
