/**
 * 
 */
package cn.edu.scau.hci.robert.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.scau.hci.robert.entity.Crowd;
import cn.edu.scau.hci.robert.entity.Friend;
import cn.edu.scau.hci.robert.entity.JGroup;
import cn.edu.scau.hci.robert.entity.Message;

/**
 * @author robert
 *
 */
public class ImCache {
	/**分组信息*/
	public static  List<JGroup> groups = new ArrayList<JGroup>();
	
	/**分组的好友，key:组编号*/
	public static  Map<Long, List<Friend>> friends = new HashMap<Long, List<Friend>>();
	
	/**群组*/
	public static  List<Crowd> crowds = new ArrayList<Crowd>();
	
	
	/**正在聊天的好友*/
	public static List<Friend> chattings = new ArrayList<Friend>();
	
	/**聊天的消息，key:好友编号*/
	public static Map<Long, List<Message>> messages = new HashMap<Long, List<Message>>();
	/**聊天的消息, key:群的编号*/
	public static Map<Long, List<Message>> crowdMsgs = new HashMap<Long, List<Message>>();
	
	/**未读的聊天消息*/
	public static List<Message> unreadMsgs = new ArrayList<Message>();
}
