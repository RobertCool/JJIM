/**
 * 
 */
package cn.edu.scau.robert.util;

/**
 * @author robert
 *
 */
public class PublicLib {

	/**
	 * 判断是否为空，并返回默认值
	 * @param obj 要判断的值
	 * @param defaultValue 如果为空，返回的值
	 * */
	public static Object ifNull(Object obj, Object defaultValue){
		if(obj == null){
			return defaultValue;
		}else{
			return obj;
		}
	}
	
	/**
	 * 判断是否为空，并且是否能转化为Integer对象
	 * */
	public static int ifNullToInt(Object obj, int defaultValue){
		int value = defaultValue;
		if(obj != null){
			try{
				value = Integer.parseInt((String)obj);
			}catch(NumberFormatException e){
				value = defaultValue;
			}
		}
		return value;
	}
	
	/**
	 * 判断是否为空，并且是否能转化为Long对象
	 * */
	public static long ifNullToLong(Object obj, long defaultValue){
		long value = defaultValue;
		if(obj != null){
			try{
				value = Long.parseLong((String)obj);
			}catch(NumberFormatException e){
				value = defaultValue;
			}
		}
		return value;
	}

}
