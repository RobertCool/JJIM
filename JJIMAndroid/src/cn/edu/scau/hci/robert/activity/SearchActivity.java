/**
 * 
 */
package cn.edu.scau.hci.robert.activity;

import cn.edu.scau.hci.robert.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * @author robert
 *	实现查找的Activity
 * 找好友或找群
 */
public class SearchActivity extends Activity implements OnClickListener{
	
	private EditText searchEdit;
	private RadioButton searchFriendRadio,searchCrowdRadio;
	private Button searchBeginBtn,searchAllBtn;

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
	}

	public void onClick(View v) {
		if(v.getId() == R.id.search_begin_btn){
			//根据号码查找
		}else if(v.getId() == R.id.search_all_btn){
			//查看所有
		}
	}
	
}
