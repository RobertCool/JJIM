package cn.edu.scau.hci.robert.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	//是否为自己发出去的
	private boolean isSend = false;
	
	public static Message toMessage(String jsonStr) throws JSONException{
		JSONObject jsObj = new JSONObject(jsonStr);
		Message message = new Message();
		
		message.setMessageId(jsObj.getLong("MessageId"));
		message.setFriendId(jsObj.isNull("FriendId")?-1L:jsObj.getLong("FriendId"));
		
		message.setUserId(jsObj.isNull("UserId")?-1L:jsObj.getLong("UserId"));
		
		message.setMessageContent(jsObj.getString("MessageContent"));
		message.setMessagePic(jsObj.getString("MessagePic"));
//		message.setMessageTime(timeFormat(jsObj.getString("MessageTime")));
		message.setMessageTime(jsObj.getString("MessageTime"));
		message.setMessageType(jsObj.getInt("MessageType"));
		message.setMessageStatus(jsObj.getInt("MessageStatus"));
		
		return message;
	}
	
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String timeFormat(String sDate){
		Date date = new Date(sDate);
		return format.format(date);
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

	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}


}
