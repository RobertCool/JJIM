/**
 * 
 */
package cn.edu.scau.hci.robert.common;

/**
 * @author robert
 * Service发送给广播的Action
 * 和Activity发送给Service的请求
 */
public class Action {
	
	/**访问JJIMService的Action*/
	public static final String JJIMServiceAction = "cn.edu.scau.hci.robert.JJIMService";
	
	/**查看好友分组*/
	public static final String GROUP_LIST = "GROUP_LIST";
	
	/**查看某分组下的好友*/
	public static final String GROUP_FRIEND = "GROUP_FRIEND";
	
	/**查看某组的信息*/
	public static final String GROUP_INFO = "GROUP_INFO";
	
	/**创建分组*/
	public static final String GROUP_ADD = "GROUP_ADD";
	
	/**修改组的名称*/
	public static final String GROUP_UPDATE = "GROUP_UPDATE";
	
	/**查看某个好友的信息*/
	public static final String FRIEND_INFO = "FRIEND_INFO";
	
	/**添加好友*/
	public static final String FRIEND_ADD = "FRIEND_ADD";
	
	/**查找好友*/
	public static final String FRIEND_LIST = "FRIEND_LIST";
	
	/**移动好友*/
	public static final String FRIEND_MOVE = "FRIEND_MOVE";
	
	/**删除好友*/
	public static final String FRIEND_DELETE = "FRIEND_DELETE";
	
	/**用户下线*/
	public static final String USER_UNDERLINE = "USER_UNDERLINE";
	
	/**出错*/
	public static final String GROUP_ERROR = "GROUP_ERROR";
	
}
