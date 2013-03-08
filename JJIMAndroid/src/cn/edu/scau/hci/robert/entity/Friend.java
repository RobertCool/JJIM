package cn.edu.scau.hci.robert.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;


public class Friend implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long friendId;
	private String friendNote;
	private String friendAccount; 
	private int friendSex;
	private String friendIcon;
	private String friendNickName;
	private int friendStatus;
	
	public static Friend toFriend(String jsonStr) throws JSONException{
		JSONObject jsObj = new JSONObject(jsonStr);
		Friend friend = new Friend();
		
		friend.setFriendId(jsObj.getLong("FriendId"));
		friend.setFriendIcon(jsObj.getString("FriendIcon"));
		friend.setFriendSex(jsObj.getInt("FriendSex"));
		friend.setFriendNickName(jsObj.getString("FriendNickName"));
		friend.setFriendAccount(jsObj.getString("FriendAccount"));
		friend.setFriendNote(jsObj.getString("FriendNote"));
		friend.setFriendStatus(jsObj.getInt("FriendStatus"));
		
		return friend;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public String getFriendAccount() {
		return friendAccount;
	}

	public void setFriendAccount(String friendAccount) {
		this.friendAccount = friendAccount;
	}

	public String getFriendIcon() {
		return friendIcon;
	}

	public void setFriendIcon(String friendIcon) {
		this.friendIcon = friendIcon;
	}

	public String getFriendNickName() {
		return friendNickName;
	}

	public void setFriendNickName(String friendNickName) {
		this.friendNickName = friendNickName;
	}

	public String getFriendNote() {
		return friendNote;
	}

	public void setFriendNote(String friendNote) {
		this.friendNote = friendNote;
	}

	public int getFriendSex() {
		return friendSex;
	}

	public void setFriendSex(int friendSex) {
		this.friendSex = friendSex;
	}

	public int getFriendStatus() {
		return friendStatus;
	}

	public void setFriendStatus(int friendStatus) {
		this.friendStatus = friendStatus;
	}



	
	
}
