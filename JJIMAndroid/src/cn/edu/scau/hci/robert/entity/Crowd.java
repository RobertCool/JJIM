package cn.edu.scau.hci.robert.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;


public class Crowd implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long crowdId;
	private Long userId;
	private String crowdName;
	private String crowdNote;
	private String crowdIcon;
	
	public static Crowd toCrowd(String jsonStr) throws JSONException{
		JSONObject jsObj = new JSONObject(jsonStr);
		Crowd crowd = new Crowd();
		
		crowd.setCrowdIcon(jsObj.getString("CrowdIcon"));
		crowd.setCrowdId(jsObj.getLong("CrowdId"));
		crowd.setCrowdName(jsObj.getString("CrowdName"));
		crowd.setCrowdNote(jsObj.getString("CrowdNote"));
		crowd.setUserId(jsObj.getLong("UserId"));
		
		return crowd;
	}
	
	public Long getCrowdId() {
		return crowdId;
	}

	public void setCrowdId(Long crowdId) {
		this.crowdId = crowdId;
	}


	public String getCrowdName() {
		return crowdName;
	}

	public void setCrowdName(String crowdName) {
		this.crowdName = crowdName;
	}

	public String getCrowdNote() {
		return crowdNote;
	}

	public void setCrowdNote(String crowdNote) {
		this.crowdNote = crowdNote;
	}

	public String getCrowdIcon() {
		return crowdIcon;
	}

	public void setCrowdIcon(String crowdIcon) {
		this.crowdIcon = crowdIcon;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}



	
	
}
