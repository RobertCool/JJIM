/**
 * 
 */
package cn.edu.scau.robert.util;

import java.io.File;

/**
 * @author robert
 *
 */
public class ConfigUtil {
	private static final String fileDir = "/home/robert/image";
	
	/**分页大小*/
	public static final int PAGE_SIZE = 20;
	
	/**Session User key*/
	public static final String SESSION_USER_KEY = "user";
	
	/**临时文件应存储的目录*/
	public static String getFileDir(){
		File file = new File(fileDir);
		if(!file.exists()){
			file.mkdirs();
		}
		return fileDir;
	}
}
