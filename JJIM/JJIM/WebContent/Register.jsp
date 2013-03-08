<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <% 
		String base = request.getContextPath(); 
	    request.setAttribute("base",base);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="${base}/resources/js/jquery-1.4.3.min.js"></script>
<script type="text/javascript"
	src="${base}/resources/js/upload/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="${base}/resources/js/upload/upload.js"></script>
	<link rel="stylesheet" type="text/css"
	href="${base}/resources/css/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${base}/resources/css/icon.css">
<link rel="stylesheet" type="text/css" href="${base}/resources/css/common.css">
<script type="text/javascript"
	src="${base}/resources/js/jquery.easyui.min.js"></script>
<title>用户注册</title>
<script type="text/javascript">
$(document).ready(function(){
	$("#upload").click(function(){
		var url="<%=base%>/image";
		upload(url,"file",function(json){
				if(json.success){
					var path = "<%=base%>/image/" + json.result ;
					$("#icon").val(path);
					$("#iconPath").attr("src", path);
				}else{
					$("#upload").after("<p>上传失败，请重试</p>");
				}
			});
		});
});
</script>
</head>
<body>
	<div style="margin:auto;width:400px;">
	<div class="easyui-panel"  title="JJIM即时通信系统——注册用户">
	<div class="error text-center top-gap bottom-gap" id="message">${message}</div>
	<form action="${base}/common/register" method="post" id="registerForm">
		<table style="margin: auto; width: 320px">
			<tr>
				<td>昵称：</td>
				<td><input name="nickName" type="text"  value="${nickName}" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>
			<tr>
				<td>头像：</td>
				<td>
						<img width="100" height="100" id="iconPath" src="${iconPath}" alt="头像"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="file" id="file" name="imgFile" />
        			<br />
        			<input type="button" value="上传"  id="upload" />
				    <input name="icon" type="hidden" id="icon"/>
				</td>
			</tr>
			<tr>
				<td>性别：</td>
				<td>
					<input type="radio" name="sex" value="0"  checked="checked"/>男
					<input type="radio" name="sex" value="1" /> 女
				</td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input name="password1" type="password" class="easyui-validatebox" data-options="required:true,validType:'length[6,16]'"/></td>
			</tr>
			<tr>
				<td>重复密码：</td>
				<td><input name="password2" type="password" class="easyui-validatebox" data-options="required:true,validType:'length[6,16]'"/></td>
			</tr>
			<tr>
				<td>邮箱：</td>
				<td>
					<input name="email" type="text" value="${email}" class="easyui-validatebox" data-options="required:true,validType:'email'"/>
				</td>
			</tr>
			<tr>
				<td>个性签名：</td>
				<td><input name="note" type="text"  value="${note}" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>
		</table>
	</form>
			<div style="text-align: center; padding: 5px" class="bottom-gap top-gap">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="submitForm('registerForm')">注册</a> <a href="javascript:void(0)"
					class="easyui-linkbutton" onclick="clearForm('registerForm')">重置</a>
			</div>
	</div>
	</div>
	
	<script type="text/javascript">
	function submitForm(formName){
		if($("#icon").val() == ""){
			$("#message").text("请上传头像图片");
			return;
		}
		document.forms[formName].submit();
	}
	function clearForm(){
		document.forms[formName].reset();
	}
	</script>
</body>
</html>