/**
 * 
 */
package cn.edu.scau.hci.robert.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.scau.hci.robert.common.Action;
import cn.edu.scau.hci.robert.entity.JGroup;
import cn.edu.scau.hci.robert.service.GroupService;
import cn.edu.scau.hci.robert.util.Logger;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

/**
 * @author robert
 *	负责网络连接部分，并实时更新前台Activity
 */
public class JJIMService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO 提示离线,并且进行离线操作
		super.onDestroy();
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		return super.onStartCommand(intent, flags, startId);
	}

}
