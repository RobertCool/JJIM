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
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author robert
 *
 */
public class GroupAdapter extends BaseExpandableListAdapter {
	
	private List<Map<String,Object>> groupData;
	private List<List<Map<String,Object>>> childData;
	
	private Context context;
	
	/**分组名称*/
	public final static String GROUP_TEXT = "group_text";
	/**分组组成员数量*/
	public final static String GROUP_NUMBER = "group_number";
	/**成员名称*/
	public final static String CHILD_TEXT = "child_text";
	/**成员个性签名*/
	public final static String CHILD_NOTE = "child_note";
	/**成员头像*/
	public final static String CHILD_HEAD = "child_head";

	public GroupAdapter(List<Map<String,Object>> groupData, List<List<Map<String,Object>>> childData, Context context){
		super();
		this.childData = childData;
		this.groupData = groupData;
		this.context = context;
	}

	public Object getChild(int groupPosition, int childPosition) {
		return childData.get(groupPosition).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.im_list_adapter, null);
		}
		ImageView headImage = (ImageView)convertView.findViewById(R.id.im_list_image);
		TextView nameText = (TextView)convertView.findViewById(R.id.im_list_name_text);
		TextView noteText  = (TextView)convertView.findViewById(R.id.im_list_note_text);
				
		convertView.setBackgroundResource(R.color.ChatingBG);
		
		Bitmap headBitmap =(Bitmap) childData.get(groupPosition).get(childPosition).get(CHILD_HEAD);
		if(headBitmap == null){
			headImage.setImageResource(R.drawable.icon);
		}else{
			headImage.setImageBitmap((Bitmap) childData.get(groupPosition).get(childPosition).get(CHILD_HEAD));
		}
		nameText.setText((String) childData.get(groupPosition).get(childPosition).get(CHILD_TEXT)==null?"未知昵称":(String) childData.get(groupPosition).get(childPosition).get(CHILD_TEXT));
		noteText.setText((String) childData.get(groupPosition).get(childPosition).get(CHILD_NOTE)==null?"":(String) childData.get(groupPosition).get(childPosition).get(CHILD_NOTE));

		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		return childData.get(groupPosition).size();
	}

	public Object getGroup(int groupPosition) {
		return childData.get(groupPosition);
	}

	public int getGroupCount() {
		return groupData.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.im_group_adapter, null);
		}
		TextView textView = (TextView)convertView.findViewById(R.id.im_group_name);
		ImageView imageView = (ImageView)convertView.findViewById(R.id.im_group_icon);
		
		textView.setText(groupData.get(groupPosition).get(GROUP_TEXT).toString());
		if(isExpanded){
			imageView.setBackgroundResource(R.drawable.group_expanded);
		}else{
			imageView.setBackgroundResource(R.drawable.group_disexpand);
		}
		
		return convertView;
	}

	public boolean hasStableIds() {
		return true;
	}
	
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
