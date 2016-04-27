<%@page import="com.estsoft.db.MySQLWebDBConnection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"/>
		<div id="content">
			<div id="board" class="board-form">
			<input type="hidden" name="no" value="${boardVo.no }">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">[${boardVo.no }]</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${boardVo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(boardVo.content, newline, "<br>") }
							</div>
						</td>
					</tr>
				</table>

				<div class="bottom">
					<a href="${pageContext.request.contextPath}/board/list">글목록</a>
					<c:choose>
						<c:when test="${ not empty authUser && authUser.no == boardVo.userNo}">
							<a href="${pageContext.request.contextPath}/board/modifyform/${boardVo.no }">글수정</a>
						</c:when>
						<c:when test="${ not empty authUser }">
							<a href="${pageContext.request.contextPath}/board/replyform/${boardVo.no }">답글</a>
						</c:when>
					</c:choose>
				</div>				
			</div>
		</div>		
		<c:import url="/WEB-INF/views/include/navigation.jsp"/>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>	
	</div>
</body>
</html>