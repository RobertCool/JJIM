/**
 * 
 */
package cn.edu.scau.hci.robert.activity;

import cn.edu.scau.hci.robert.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * @author robert
 *
 */
public class SearchResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.search_result_layout);
	}
}
