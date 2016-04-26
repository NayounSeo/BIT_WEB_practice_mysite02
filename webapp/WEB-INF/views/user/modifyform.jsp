<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.estsoft.mysite.vo.UserVo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
		UserVo userVo = (UserVo)request.getAttribute("userVo");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/assets/css/main.css" rel="stylesheet" type="text/css">
<title>회원정보 수정</title>
</head>
<body>
<div id="container"> <!--  div는 페이지를 논리적으로 나누는 태그!!! -->
		<jsp:include page="/WEB-INF/views/include/header.jsp"/>	
		<div id="content">
			<div id="user">
				<form id="modify-form" name="modifyform" method="POST" action="${pageContext.request.contextPath}/user">
				<input type='hidden' name="a" value="modify">
				<input type='hidden' name="no" value="${authUser.no }">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="${authUser.name }">
					<br><br>
					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="${authUser.email }">
					<input type="button" value="id 중복체크">
					<br><br>
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					<br><br>
					<fieldset>
						<legend>성별</legend>
						<% 
								if (userVo.getGender( ).equals("f") )  {
						%>
						<label>여</label> <input type="radio" name="gender" value="f" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="m">
						<% 
								} else {
						%>
						<label>여</label> <input type="radio" name="gender" value="f">
						<label>남</label> <input type="radio" name="gender" value="m"  checked="checked">
						<% 
								} 
						%>
					</fieldset>
										
					<input type="submit" value="수정하기">					
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp"/>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>
	</div>
</body>
</html>