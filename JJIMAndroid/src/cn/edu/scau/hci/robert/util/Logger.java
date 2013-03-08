/**
 * 
 */
package cn.edu.scau.hci.robert.util;

import android.util.Log;

/**
 * @author robert
 * 自定义的日志输出工具
 */
public class Logger {
	
	public static boolean DEBUG = true;

	public static void i(String tag, String msg){
		if(DEBUG){
			Log.i(tag, msg);
		}
	}

	public static void e(String tag, String msg){
		if(DEBUG){
			Log.e(tag, msg);
		}
	}
	
	public static void w(String tag, String msg){
		if(DEBUG){
			Log.w(tag, msg);
		}
	}
	
	public static void d(String tag, String msg){
		if(DEBUG){
			Log.d(tag, msg);
		}
	}
	
	public static void v(String tag, String msg){
		if(DEBUG){
			Log.v(tag, msg);
		}
	}
}
