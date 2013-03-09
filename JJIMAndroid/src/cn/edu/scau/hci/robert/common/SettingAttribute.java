/**
 * 
 */
package cn.edu.scau.hci.robert.common;

import java.io.Serializable;

import cn.edu.scau.hci.robert.entity.User;

/**
 * @author robert
 *
 */
public class SettingAttribute  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static SettingAttribute instance;
	private static Object obj = new Object();
	
	public static SettingAttribute getInstance(){
		synchronized (obj) {
			if(instance == null){
				instance =SettingHelper.readSettingAttribute();
				if(instance == null){
					instance = new SettingAttribute();
				}
			}
		}
		return instance;
	}
	private User user;
	
	private boolean isRemember = true;
	private String userName = "";
	private String password = "";
	private boolean isHideLine = false;
	
	private boolean friendListChanged = false;
	private boolean crowdListChanged = false;
	
	public boolean isRemember() {
		return isRemember;
	}
	public void setRemember(boolean isRemember) {
		this.isRemember = isRemember;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isHideLine() {
		return isHideLine;
	}
	public void setHideLine(boolean isHideLine) {
		this.isHideLine = isHideLine;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isFriendListChanged() {
		return friendListChanged;
	}
	public void setFriendListChanged(boolean friendListChanged) {
		this.friendListChanged = friendListChanged;
	}
	public boolean isCrowdListChanged() {
		return crowdListChanged;
	}
	public void setCrowdListChanged(boolean crowdListChanged) {
		this.crowdListChanged = crowdListChanged;
	}

}
