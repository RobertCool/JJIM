/**
 * 
 */
package cn.edu.scau.hci.robert.activity;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.GetMethod;

import cn.edu.scau.hci.robert.R;
import cn.edu.scau.hci.robert.util.HttpUtil;
import cn.edu.scau.hci.robert.util.Logger;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author robert
 *
 */
public class RegisteActivity extends Activity implements OnClickListener{
	
	private Button registerBtn,backBtn;
	private Button testBtn1,testBtn2,testBtn3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.registe_layout);
		this.initViews();
	}
	
	private void initViews(){
		registerBtn = (Button)this.findViewById(R.id.register_btn);
		backBtn = (Button)this.findViewById(R.id.back_btn);
		
		testBtn1 = (Button)this.findViewById(R.id.button1);
		testBtn2 = (Button)this.findViewById(R.id.button2);
		testBtn3 = (Button)this.findViewById(R.id.button3);
		
		backBtn.setOnClickListener(this);
		registerBtn.setOnClickListener(this);
		
		testBtn1.setOnClickListener(this);
		testBtn2.setOnClickListener(this);
		testBtn3.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.register_btn:
			break;
		case R.id.back_btn:
			this.finish();
			break;
		case R.id.button1:
			HttpClient client = new HttpClient();
			client.getState().addCookies(HttpUtil.getInstance().getState().getCookies());
			GetMethod get = new GetMethod(HttpUtil.host + "/friend");
			try {
				if(HttpStatus.SC_OK ==client.executeMethod(get)){
					Logger.i("result", get.getResponseBodyAsString());
				}
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.button2:
			break;
		case R.id.button3:
			break;
		}
	}

	
	
}
