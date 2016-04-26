<%@page import="com.estsoft.mysite.vo.GuestBookVo"%>
<%@page import="java.util.List"%>
<%@page import="com.estsoft.db.MySQLWebDBConnection"%>
<%@page import="com.estsoft.mysite.dao.GuestBookDao"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	List<GuestBookVo> list = (List<GuestBookVo>) request.getAttribute("list");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>방명록</title>
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form action="${pageContext.request. contextPath}/guest/insert"
					method='POST'>
					<table>
						<tr>
							<td>이름</td>
							<td><input type="text" name="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="passwd"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="message" cols=60 rows=5></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul>
					<li>
						<%
							int count = list.size();
							int index = 0;
							for (GuestBookVo vo : list) {
						%>
						<table>
							<tr>
								<td>[<%=count - index%>]
								</td>
								<td><%=vo.getName()%></td>
								<td><%=vo.getRegDate()%></td>
								<td><a href="/Guestbook3/deleteform/<%=vo.getNo()%>">삭제</a></td>
							</tr>
							<tr>
								<td colspan=4><%=vo.getMessage().replaceAll("\r\n", "<br>")%></td>
							</tr>
						</table> 
						<%
 							index++;
 							}
						 %> 
						<br>
					</li>
				</ul>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp"/>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>
	</div>
</body>
</html>