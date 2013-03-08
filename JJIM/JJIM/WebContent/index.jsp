<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String base = request.getContextPath();
	request.setAttribute("base", base);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JJIM即时通信聊天系统</title>
<link rel="stylesheet" type="text/css"
	href="${base}/resources/css/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${base}/resources/css/icon.css">
<link rel="stylesheet" type="text/css" href="${base}/resources/css/common.css">
<script type="text/javascript"
	src="${base}/resources/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript"
	src="${base}/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${base}/resources/js/common.js">
</script>
</head>
<body>
	<div style="margin: auto; width: 400px">
		<div class="easyui-panel" title="JJIM即时通信系统——登录">
			<div style="padding: 10px 0 10px 60px"></div>
			<div class="error text-center">${message}</div>
			<form id="loginForm" action="${base}/common/login" method="post">
				<table style="margin: auto; width: 300px">
					<tr>
						<td>账号：</td>
						<td><input type="text" name="account"
							class="easyui-validatebox easyui-numberbox"
							data-options="required:true,validType:['length[9,9]']" /></td>
					</tr>
					<tr>
						<td>密码：</td>
						<td><input type="password" name="password"
							class="easyui-validatebox"
							data-options="required:true,validType:['length[6,16]']" /></td>
					</tr>
				</table>
			</form>
			<div style="text-align: center; padding: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="submitForm('loginForm')">登录</a> <a href="javascript:void(0)"
					class="easyui-linkbutton" onclick="clearForm('loginForm')">重置</a>
			</div>
			<div class="text-center bottom-gap top-gap">
				<a href="${base}/Register.jsp">没有账号？注册一个</a>
			</div>
		</div>
	</div>
</body>
</html>