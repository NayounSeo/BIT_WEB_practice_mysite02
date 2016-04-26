<%@page import="com.estsoft.mysite.vo.UserVo"%>
<%@page import="com.estsoft.mysite.vo.BoardVo"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.Object"%>
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
			<div id="board">	
				<form id="search_form" action="${pageContext.request.contextPath}/board" method="post">
				<c:choose>
					<c:when test='${map.wannaSearch == ""}'>
						<input type="text" id="kwd" name="kwd" value="">
					</c:when>
					<c:otherwise>
						<input type="text" id="kwd" name="kwd" value="">
						</c:otherwise>
					</c:choose>
					<input type="submit" value="찾기">
				</form>	
				
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:set var="firstIndex"	value="${map.totalBoards - (map.currentPage - 1)*map.rowSize }" />
					<c:forEach items="${map.list }" var="vo" varStatus="status">
						<tr>
							<td>[${firstIndex - status.index + 1 }]</td>
							<td style="text-align:left; padding-left:${vo.depth*20}px">
								<c:if test="${vo.depth >0}">
									<img src="${pageContext.request.contextPath}/assets/images/replyVector.png">
								</c:if>
								<a href="${pageContext.request.contextPath}/board?a=view&no=${vo.no }">${vo.title }</a>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.views }</td>
							<td>${vo.regDate }</td>
							<c:choose>
								<c:when test="${ not empty authUser && authUser.no == vo.userNo}">
									<td><a href="${pageContext.request.contextPath}/board?a=delete&no=${vo.no }&userNo= ${vo.userNo }" class="del">삭제</a></td>
								</c:when>
								<c:otherwise>
									<td><img src="${pageContext.request.contextPath}/assets/images/recycle_non.png"></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
				
				<div class="pager">
					<ul>
					<c:if test="${map.prevPage > 0 }">
						<li><a href="${pageContext.request.contextPath}/board?page=${map.prevPage }&kwd=${map.wannaSearch }&">◀</a></li>
					</c:if>
					<c:forEach begin="${map.firstPage }" end="${map.lastPage }" var="page" step="1">
							<c:choose>
								<c:when test="${page == map.currentPage }">
									<li class="selected">${page }</li>
								</c:when>
								<c:otherwise>
								<li><a href="${pageContext.request.contextPath}/board?page=${page }&kwd=${map.wannaSearch }">${page }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					<c:if test="${map.nextPage > 0 }">
						<li><a href="${pageContext.request.contextPath}/board?page=${map.nextPage }&kwd=${map.wannaSearch }">▶</a></li>		
					</c:if>
					</ul>
				</div>
				<c:choose>
					<c:when test="${ not empty authUser }">
					<div class="bottom">
	<!-- 				와 개바보였네 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ	
						</a href="/WEB-INF/views/board?a=insert" id="new-book">글쓰기<//a> -->
						<a href="${pageContext.request.contextPath}/board?a=insert" id="new-book">글쓰기</a>
					</div>
					</c:when>	
					</c:choose>			
			</div>
		</div>		
		<c:import url="/WEB-INF/views/include/navigation.jsp"/>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>	
	</div>
</body>
</html>