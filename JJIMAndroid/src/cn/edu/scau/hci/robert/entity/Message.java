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
public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long messageId;
	
	private Long friendId;
	
	private Long userId;
	
	private String messageContent;
	
	private String messagePic;
	
	private String messageTime;
	
	private Integer  messageType;
	
	private Integer messageStatus;
	
	public static Message toMessage(String jsonStr) throws JSONException{
		JSONObject jsObj = new JSONObject(jsonStr);
		Message message = new Message();
		
		message.setMessageId(jsObj.getLong("MessageId"));
		message.setFriendId(jsObj.getLong("FriendId"));
		message.setUserId(jsObj.getLong("UserId"));
		message.setMessageContent(jsObj.getString("MessageContent"));
		message.setMessagePic(jsObj.getString("MessagePic"));
		message.setMessageTime(jsObj.getString("MessageTime"));
		message.setMessageType(jsObj.getInt("MessageType"));
		message.setMessageStatus(jsObj.getInt("MessageStatus"));
		
		return message;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getMessagePic() {
		return messagePic;
	}

	public void setMessagePic(String messagePic) {
		this.messagePic = messagePic;
	}


	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public Integer getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(Integer messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(String messageTime) {
		this.messageTime = messageTime;
	}


}
