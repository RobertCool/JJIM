<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String base = request.getContextPath();
	request.setAttribute("base", base);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${user.NickName}的个人信息</title>
<link rel="stylesheet" type="text/css"
	href="${base}/resources/css/common.css">
	<link rel="stylesheet" type="text/css"
	href="${base}/resources/css/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${base}/resources/css/icon.css">
<link rel="stylesheet" type="text/css" href="${base}/resources/css/common.css">

</head>
<body>
	<div style="margin: auto; width: 400px">
	<div class="easyui-panel" title="JJIM即时通信系统——个人信息">
		<div class="error text-center bottom-gap top-gap">${message}</div>
		<table style="margin: auto; width: 300px">
			<tr>
				<td>账号：</td>
				<td>${user.UserAccount}</td>
			</tr>
			<tr>
				<td>昵称：</td>
				<td>${user.NickName}</td>
			</tr>
			<tr>
				<td>头像：</td>
				<td>
					<img alt="头像" width="100" height="100" src="${user.UserIcon}"/>
				</td>
			</tr>
			<tr>
				<td>性别：</td>
				<td>
					<c:choose>
						<c:when test="${user.Sex==0}">男</c:when>
						<c:otherwise>女</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>注册时间：</td>
				<td>
					${user.UserRegisterTime}
				</td>
			</tr>
			<tr>
				<td>账号状态：</td>
				<td><c:choose>
						<c:when test="${user.UserStatus == 0}">禁用</c:when>
						<c:when test="${user.UserStatus == 1}">正常</c:when>
						<c:when test="${user.UserStatus == 2}">管理员</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose></td>
			</tr>
			<tr>
				<td>个性签名：</td>
				<td>${user.UserNote}</td>
			</tr>
		</table>
		
		<div style="text-align: center; padding: 5px" class="bottom-gap top-gap">
				<a href="${base}" class="easyui-linkbutton">返回首页</a>
				</div>
		</div>
	</div>
	<script type="text/javascript" 	src="${base}/resources/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript"	src="${base}/resources/js/jquery.easyui.min.js"></script>
</body>
</html>