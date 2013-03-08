/**
 * 
 */
package cn.edu.scau.hci.robert.util;

/**
 * @author robert
 * 消息的类型
 */
public enum MessageType {
	/**来自好友的消息*/
	Friend,
	/**来自群的消息*/
	Crowd,
	/**来自系统的消息*/
	System,
	/**请求加好友的消息*/
	FriendAdd,
	/**请求加入群的消息*/
	CrowdAdd,
	/**好友状态改变*/
	FriendStatus
}
