/**
 * 
 */
package cn.edu.scau.robert.controller;

import java.io.File;


import cn.edu.scau.robert.entity.JsonResult;
import cn.edu.scau.robert.model.Image;
import cn.edu.scau.robert.util.ConfigUtil;
import cn.edu.scau.robert.util.ResourceMessage;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.Restful;
import com.jfinal.upload.UploadFile;

/**
 * @author robert
 *
 */
@Before(Restful.class)
public class ImageController extends Controller {

	
	/**
	 * add  edit 无需处理
	 * 
	 * GET		/user			--->	index
	 * GET		/user/id		--->	show  
	 * POST		/user			--->	save	
	 * PUT		/user/id		--->	update
	 * DELECT	/user/id		--->	delete
	 */
	public void index(){
		this.renderJson(ResourceMessage.unreachable());
	}
	
	/**
	 * 获取图片
	 * */
	public void show(){
		String id = this.getPara(0);
		Image image = Image.dao.findById(id);
		
		if(image == null){
			this.renderError404();
		}else{
			this.renderFile(new File(image.getStr("ImagePath")));
		}
	}
	
	/**
	 * 上传图片
	 * */
	public void save(){
		UploadFile file = this.getFile("imgFile");
		String desFileName =ConfigUtil.getFileDir() + "/" + System.currentTimeMillis() +  file.getFileName().substring(file.getFileName().lastIndexOf("."));
		file.getFile().renameTo(new File(desFileName));
		
		Image image=  new Image().set("ImagePath", desFileName);
		boolean success = image.save();
		
		JsonResult result = new JsonResult();
		result.setSuccess(success);
		result.setResult(image.getInt("ImageId"));
		
		this.renderJson(result);
	}
	
	public void delete(){
		this.renderJson(ResourceMessage.unreachable());
	}
	
	public void update(){
		this.renderJson(ResourceMessage.unreachable());
	}
	
}
