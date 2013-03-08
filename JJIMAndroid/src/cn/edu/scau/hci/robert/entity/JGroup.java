package cn.edu.scau.hci.robert.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author robert 好友分组
 */
public class JGroup implements Serializable, Parcelable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long groupId;

	private Long userId;

	private String groupName;

	public static JGroup toGroup(String jsonStr) throws JSONException {
		JSONObject jsObj = new JSONObject(jsonStr);
		JGroup group = new JGroup();

		group.setUserId(jsObj.getLong("UserId"));
		group.setGroupName(jsObj.getString("GroupName"));
		group.setGroupId(jsObj.getLong("GroupId"));

		return group;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(this);
	}

	// 添加一个静态成员,名为CREATOR,该对象实现了Parcelable.Creator接口
	public static final Parcelable.Creator<JGroup> CREATOR = new Parcelable.Creator<JGroup>() {

		public JGroup createFromParcel(Parcel source) {
			return (JGroup)source.readSerializable();
		}

		public JGroup[] newArray(int size) {
			return new JGroup[size];
		}

	};

}
