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
	href="${base}/resources/mobile/jquery.mobile-1.3.0.min.css">
	<script type="text/javascript" src="${base}/resources/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${base}/resources/mobile/jquery.mobile-1.3.0.min.js"></script>
<title>好友信息</title>

</head>
<body>

<form>
    <ul data-role="listview" data-inset="true">
        <li data-role="fieldcontain">
        	<img alt="头像" src="${friend.FriendIcon}">
        	<h2>${friend.FriendAccount }</h2>
            <p>账号<p>
        </li>
        <li data-role="fieldcontain">
            <h2>个性签名</h2>
            <p>${friend.FriendNote}</p>
        </li>
        <li data-role="fieldcontain">
            <h2>性别</h2>
            <p>
            <c:choose>
						<c:when test="${friend.FriendSex==0}">男</c:when>
						<c:otherwise>女</c:otherwise>
					</c:choose>
            </p>
        </li>

        <li data-role="fieldcontain">
            <label for="slider2">Slider:</label>
            <input type="range" name="slider2" id="slider2" value="0" min="0" max="100" data-highlight="true">
        </li>
        <li data-role="fieldcontain">
            <label for="select-choice-1" class="select">Choose shipping method:</label>
            <select name="select-choice-1" id="select-choice-1">
                <option value="standard">Standard: 7 day</option>
                <option value="rush">Rush: 3 days</option>
                <option value="express">Express: next day</option>
                <option value="overnight">Overnight</option>
            </select>
        </li>
        <li class="ui-body ui-body-b">
            <fieldset class="ui-grid-a">
                    <div class="ui-block-a"><button type="submit" data-theme="d">Cancel</button></div>
                    <div class="ui-block-b"><button type="submit" data-theme="a">Submit</button></div>
            </fieldset>
        </li>
    </ul>
</form>

</body>
</html>