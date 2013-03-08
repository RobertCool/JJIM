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
<link rel="stylesheet" type="text/css"
	href="${base}/resources/css/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${base}/resources/css/icon.css">
<title>Insert title here</title>

</head>
<body>
<a href="${base }/common/searchFriend">查询</a> 
好友信息
<table>
	<tr>
		<td>
		账号
		</td>
		<td>
		昵称
		</td>
		<td>
		性别
		</td>
		<td>
		头像
		</td>
		<td>
		签名
		</td>
	</tr>
	<c:forEach items="${myFriends }" var="list">
		<tr>
			<td>
			${list.FriendAccount}
			</td>
			<td>
			${list.FriendNickName}
			</td>
			<td>
			<c:choose>
						<c:when test="${list.FriendSex==0}">男</c:when>
						<c:otherwise>女</c:otherwise>
			</c:choose>
			</td>
			<td>
				<img src="${list.FriendIcon }" width="160" height="120"/>
			</td>
			<td>
			${list.FriendNote }
			</td>
		</tr>
	</c:forEach>
</table>
	<div id="p" class="easyui-panel" title="My Panel"   style="width:500px;height:150px;padding:10px;background:#fafafa;"  data-options="iconCls:'icon-save',closable:true,   collapsible:true,minimizable:true,maximizable:true">  
	    <p>panel content.</p>  
	    <p>panel content.</p>  
	</div>  
	<div id="mm" style="padding:10px;">  
    <p>panel content.</p>  
    <p>panel content.</p>  
</div>  
<script type="text/javascript" 	src="${base}/resources/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" 	src="${base}/resources/js/jquery.easyui.min.js"></script>
<script>
$(document).ready(function() {
	$('#mm').panel({  
		  width:500,  
		  height:150,  
		  title: 'Echo Panel',  
		  tools: [{  
		    iconCls:'icon-add',  
		    handler:function(){alert('new');}  
		  },{  
		    iconCls:'icon-save',  
		    handler:function(){alert('save');}  
		  }]  
		});   
});
</script>
</body>
</html>