package cn.edu.scau.hci.robert.common;

import android.os.Environment;

public class FilePath {
	
	private final static String Root = "/program_files/JJIM/";
	private final static String SettingFilePath = Root+"Setting.bin";
	private final static String SDCardPath = "";
	private final static String FriendCachePath = Root +"Cache/";
	private final static String FriendImageCache = Root + "Cache/Head/";
	private final static String GroupFriendFile = Root + "Cache/Group.bin";
	private final static String CrowdFriendFile = Root + "Cache/Crowd.bin";
	private final static String MessageFile = Root + "Cache/Message.bin";
	
	public static String getFriendcachepath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()+FriendCachePath;
	}
	public static String getFriendimagecache() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()+FriendImageCache;
	}
	public static String getRoot() {
		return  Environment.getExternalStorageDirectory().getAbsolutePath()+Root;
	}
	public static String getSettingfilepath() {
		return  Environment.getExternalStorageDirectory().getAbsolutePath()+SettingFilePath;
	}
	public static String getSdcardpath() {
		return  Environment.getExternalStorageDirectory().getAbsolutePath()+SDCardPath;
	}
	
	public static String getGroupPath(){
		return Environment.getExternalStorageDirectory().getAbsolutePath() + GroupFriendFile;
	}
	
	public static  String getCrowdPath(){
		return Environment.getExternalStorageDirectory().getAbsolutePath() + CrowdFriendFile;
	}
	
	public static String getMessagePath(){
		return Environment.getExternalStorageDirectory().getAbsolutePath() + MessageFile;
	}


}
