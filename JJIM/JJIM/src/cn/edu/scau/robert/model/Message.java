/**
 * 
 */
package cn.edu.scau.robert.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * @author robert
 *
 */
public class Message extends Model<Message> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Message dao = new Message();
}
