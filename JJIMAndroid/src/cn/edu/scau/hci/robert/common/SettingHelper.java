/**
 * 
 */
package cn.edu.scau.hci.robert.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.util.Log;


/**
 * @author robert
 *
 */
public class SettingHelper {
	
	private final static String TAG="SettingHelper";
	
	/**
	 * 读取设置信息
	 * @author Robert
	 * @return 如果不存在设置信息，返回null;否则返回设置信息
	 * */
	public static SettingAttribute readSettingAttribute(){
		File file = new File(FilePath.getSettingfilepath());
		if(file.exists())
		{
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				SettingAttribute setting = (SettingAttribute)ois.readObject();
				ois.close();
				return setting;
			} catch(Exception e){
				e.printStackTrace();
				Log.e(TAG, e.getCause().getMessage());
				return null;
			}
		}
		else{
			return null;
		}
	}
	/**
	 * 写入设置信息
	 * @author Robert
	 * @return 写入成功，返回true;否则返回false;
	 * */
	public static boolean writeSettingAttribute(SettingAttribute setting){
		File file = new File(FilePath.getSettingfilepath());
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(setting);
			oos.close();
		} catch (Exception e){
			e.printStackTrace();
			Log.e(TAG, e.getCause().toString());
			return false;
		}
		return true;
	}

}
