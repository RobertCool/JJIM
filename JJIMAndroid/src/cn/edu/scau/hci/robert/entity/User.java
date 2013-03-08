package cn.edu.scau.hci.robert.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 */



/**
 * @author robert
 *
 */

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;

	private String userAccount;

	private String password;

	private String userIcon;

	private String userRegisterTime;

	private String userNote;

	private String nickName;

	private int userStatus;
	
	private int sex;
	
	private String userEmail;
	

	public static User toUser(String jsonStr) throws JSONException{
		JSONObject jsObj = new JSONObject(jsonStr);
		User user = new User();
		user.setUserId(jsObj.getLong("UserId"));
		user.setUserAccount(jsObj.getString("UserAccount"));
		user.setPassword(jsObj.getString("Password"));
		user.setNickName(jsObj.getString("NickName"));
		user.setSex(jsObj.getInt("Sex"));
		user.setUserEmail(jsObj.getString("UserEmail"));
		user.setUserRegisterTime(jsObj.getString("UserEmail"));
		user.setUserStatus(jsObj.getInt("UserStatus"));
		user.setUserIcon(jsObj.getString("UserIcon"));
		user.setUserNote(jsObj.getString("UserNote"));
		return user;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getUserRegisterTime() {
		return userRegisterTime;
	}

	public void setUserRegisterTime(String userRegisterTime) {
		this.userRegisterTime = userRegisterTime;
	}

	public String getUserNote() {
		return userNote;
	}

	public void setUserNote(String userNote) {
		this.userNote = userNote;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}



}
