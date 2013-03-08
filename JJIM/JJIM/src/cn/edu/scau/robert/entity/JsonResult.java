/**
 * 
 */
package cn.edu.scau.robert.entity;

/**
 * @author robert
 *
 */
public class JsonResult {
	 
	private boolean success;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	private Object result;
}
