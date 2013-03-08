package cn.edu.scau.hci.robert.common;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class SetupHelper {
	
	private final static String TAG = "SetupHelper";
	
	/**
	 * 程序第一次启动，查看文件是否存在，创建相关文件夹
	 * */
	public static boolean Setup(){
		Log.i(TAG, "Setup Start");
		try{
			File file = new File(FilePath.getRoot());
			if(!file.exists()){
				Log.i(TAG, "create new folder");
				file.mkdirs();
				File friendFile		= new File( FilePath.getFriendcachepath());
				File imageFile 	= new File( FilePath.getFriendimagecache());
				
				friendFile.mkdirs();
				imageFile.mkdirs();
			}
		}
		catch(Exception e){
			Log.e(TAG, "Setup Error:"+e.getCause());
			return false;
		}
		return true;
	}
	
	/**
	 * 检查是否需要更新文件
	 * */
	public static boolean isUpdate(String filePath){
		File file = new File(filePath);
		
		//最新时间
		Calendar nowCal = Calendar.getInstance();
		nowCal.setTime(new Date());
		
		Calendar oldCal = Calendar.getInstance();
		oldCal.setTimeInMillis(file.lastModified());
		oldCal.add(Calendar.DAY_OF_YEAR, 7);
		
		if(nowCal.before(oldCal)){
			return false;
		}else{
			return true;
		}
	}

}
