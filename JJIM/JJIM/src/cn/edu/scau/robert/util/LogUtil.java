package cn.edu.scau.robert.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;

public  class LogUtil {
	
	private static File  logFile = new File("RestLog.txt");
	
	@SuppressWarnings("deprecation")
	public static void info(String info) {
		try{
			FileWriter fw = new FileWriter(logFile,true);
			fw.append(new Date(System.currentTimeMillis()).toLocaleString() +":"+info+"\n");
			fw.flush();
			fw.close();
		}
		catch(IOException exception){
			exception.printStackTrace();
		}
	}
	
	public static void main(String[] argb) throws IOException{
		LogUtil.info("hah");
	}

}
