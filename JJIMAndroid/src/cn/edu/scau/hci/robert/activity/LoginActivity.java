package cn.edu.scau.hci.robert.activity;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.scau.hci.robert.R;
import cn.edu.scau.hci.robert.common.Code;
import cn.edu.scau.hci.robert.common.SettingAttribute;
import cn.edu.scau.hci.robert.common.SettingHelper;
import cn.edu.scau.hci.robert.common.SetupHelper;
import cn.edu.scau.hci.robert.entity.User;
import cn.edu.scau.hci.robert.service.CommonService;
import cn.edu.scau.hci.robert.service.ImageService;
import cn.edu.scau.hci.robert.util.Logger;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText loginAccountEdit, loginPwdEdit;
	private Button loginButton, registerButton;
	private CheckBox rememberCheckBox, hidelineCheckBox;
	private ImageView headImageView;

	private ProgressDialog mProgress = null;

	private static final int LOGIN = 1;

	private String account, password;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		SetupHelper.Setup();
		InitViews();
	}

	private void InitViews() {
		loginAccountEdit = (EditText) findViewById(R.id.login_account_edit);
		loginPwdEdit = (EditText) findViewById(R.id.login_pwd_edit);
		loginButton = (Button) findViewById(R.id.login_button);
		registerButton = (Button) findViewById(R.id.login_register_button);
		
		loginAccountEdit.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			public void afterTextChanged(Editable s) {
				headImageView.setImageResource(R.drawable.icon);
			}
		});

		rememberCheckBox = (CheckBox) findViewById(R.id.login_remember_check);
		hidelineCheckBox = (CheckBox) findViewById(R.id.login_hideline_check);

		loginAccountEdit.setText(SettingAttribute.getInstance().getUserName());
		loginPwdEdit.setText(SettingAttribute.getInstance().getPassword());
		rememberCheckBox
				.setChecked(SettingAttribute.getInstance().isRemember());

		loginButton.setOnClickListener(this);
		registerButton.setOnClickListener(this);
		rememberCheckBox.setOnClickListener(this);
		hidelineCheckBox.setOnClickListener(this);

		mProgress = new ProgressDialog(this);
		mProgress.setMessage("正在登录……");
		mProgress.setTitle("登录");

		headImageView = (ImageView) this.findViewById(R.id.login_head);
		ImageService is = new ImageService();
		String fileName = is.tryGetImage(SettingAttribute.getInstance()
				.getUser().getUserIcon());
		if (fileName != null) {
			headImageView.setImageURI(Uri.fromFile(new File(fileName)));
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == loginButton) {
			account = loginAccountEdit.getText().toString();
			password = loginPwdEdit.getText().toString();
			mProgress.show();

			new Thread(new Runnable() {
				public void run() {
					Message msg = new Message();
					msg.what = LOGIN;
					CommonService service = new CommonService();
					service.login(account, password, msg);
					mHandler.sendMessage(msg);
				}
			}).start();
		} else if (v == registerButton) {
			Intent intent = new Intent(LoginActivity.this,
					RegisteActivity.class);
			startActivityForResult(intent, Code.SUCCESS);
		} else if (v == rememberCheckBox) {
			SettingAttribute.getInstance().setRemember(
					rememberCheckBox.isChecked());
		} else if (v == hidelineCheckBox) {
			SettingAttribute.getInstance().setHideLine(
					hidelineCheckBox.isChecked());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("code", "requestCode:" + requestCode + "--resultCode:"
				+ resultCode);
		if (resultCode == Code.EXIT) {
			finish();
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOGIN:
				try {
					Logger.i("LoginActivity_getUserInfo", (String) msg.obj);
					JSONObject jsObj = new JSONObject((String) msg.obj);
					boolean success = jsObj.getBoolean("success");
					if (success) {
						SettingAttribute.getInstance().setUser(
								User.toUser(jsObj.getString("result")));
						if (SettingAttribute.getInstance().isRemember()) {
							SettingAttribute.getInstance().setUserName(account);
							SettingAttribute.getInstance()
									.setPassword(password);
						} else {
							SettingAttribute.getInstance().setUserName(account);
							SettingAttribute.getInstance().setPassword("");
						}
						SettingHelper.writeSettingAttribute(SettingAttribute
								.getInstance());

						mProgress.dismiss();
						// 进入主页面
						Intent intent = new Intent(LoginActivity.this,
								MainActivity.class);
						startActivityForResult(intent, Code.EXIT);
					} else {
						mProgress.dismiss();
						new AlertDialog.Builder(LoginActivity.this)
								.setTitle("登录失败")
								.setMessage(jsObj.getString("result"))
								.setNeutralButton("确定", null).show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
					mProgress.dismiss();
				}
				break;
			}

		}

	};

}