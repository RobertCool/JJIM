//文件上传组件
//参数说明
//url：要长传的路径
//elementId:选择文件的ID
//fileType：“img”图片   “file”文档类型
var upload = function(url, elementId, callback) {
	$("#" + elementId).after(
			"<img src='" + getScriptPath() + "/loading.gif' id='loading'/>");
	ajaxFileUpload(url, elementId, callback);
};

var getScriptPath = function() {
	var result = "";
	var es = document.getElementsByTagName("script");
	for ( var i = 0; i < es.length; i++) {
		if (es[i].src.match("upload.js")) {
			result = es[i].src.substring(0, es[i].src.lastIndexOf("/"));
			break;
		}
	}
	return result;
};

var ajaxFileUpload = function(actionUrl, elementId, callback) {
	$("#" + elementId).ajaxStart(function() {

	})//开始上传文件时显示一个图片
	.ajaxComplete(function() {
		$("#loading").remove();
	});//文件上传完成将图片隐藏起来

	$.ajaxFileUpload({
		url : actionUrl,			//用于文件上传的服务器端请求地址
		secureuri : false,		//一般设置为false
		fileElementId : elementId,	//文件上传空间的id属性  <input type="file" id="file" name="file" />
		dataType : "json",				//返回值类型 一般设置为json
		success : function(data, status) //服务器成功响应处理函数
		{
			callback(data);
		},
		error : function(data, status, e)	//服务器响应失败处理函数
		{
			callback(data);
		}
	});

	return false;

};