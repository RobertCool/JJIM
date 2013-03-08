/**
 * 定义常用的json消息方法
 */
package cn.edu.scau.robert.util;

import cn.edu.scau.robert.entity.JsonResult;

/**
 * @author robert
 *
 */
public class ResourceMessage {
	
	/**
	 * 不可访问资源
	 * @return JsonResult
	 * */
	public static JsonResult unreachable(){
		JsonResult result = new JsonResult();
		result.setSuccess(false);
		result.setResult("该资源不可访问");
		return result;
	}
	
	/**
	 * 出错提示
	 * @param message 提示消息
	 * @return JsonResult;
	 * */
	public static JsonResult error(Object message){
		JsonResult result = new JsonResult();
		result.setSuccess(false);
		result.setResult(message);
		return result;
	}
	
	/**
	 * 成功的结果消息
	 * */
	public static JsonResult success(Object message){
		JsonResult result = new JsonResult();
		result.setSuccess(true);
		result.setResult(message);
		return result;
	}
	
}
