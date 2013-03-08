/**
 * 
 */
package cn.edu.scau.robert.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * @author robert
 *
 */
public class User extends Model<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final User dao = new User();
	
}
