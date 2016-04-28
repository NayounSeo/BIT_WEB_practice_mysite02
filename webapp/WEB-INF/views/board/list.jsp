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
				<form id="search_form" action="${pageContext.request.contextPath}/board/list/${wannaSearch }" method="post">
				<c:choose>
					<c:when test='${wannaSearch == ""}'>
						<input type="text" id="kwd" name="wannaSearch" value="">
					</c:when>
					<c:otherwise>
						<input type="text" id="kwd" name="wannaSearch" value="">
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
					<c:set var="firstIndex"	value="${totalBoards - (currentPage - 1)*rowSize }" />
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>[${firstIndex - status.index + 1 }]</td>
							<td style="text-align:left; padding-left:${vo.depth*20}px">
								<c:if test="${vo.depth >0}">
									<img src="${pageContext.request.contextPath}/assets/images/replyVector.png">
								</c:if>
								<a href="${pageContext.request.contextPath}/board/view/${vo.no }">${vo.title }</a>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.views }</td>
							<td>${vo.regDate }</td>
							<c:choose>
								<c:when test="${ not empty authUser && authUser.no == vo.userNo}">
									<td><a href="${pageContext.request.contextPath}/board/delete/${vo.no }" class="del">삭제</a></td>
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
					<c:if test="${prevPage > 0 }">
						<li><a href="${pageContext.request.contextPath}/board/list">◀</a></li>
					</c:if>
					<c:forEach begin="${firstPage }" end="${lastPage }" var="page" step="1">
							<c:choose>
								<c:when test="${page == currentPage }">
									<li class="selected">${page }</li>
								</c:when>
								<c:otherwise>
								<li><a href="${pageContext.request.contextPath}/board/list">${page }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					<c:if test="${nextPage > 0 }">
						<li><a href="${pageContext.request.contextPath}/board/list">▶</a></li>		
					</c:if>
					</ul>
				</div>
				
				<c:choose>
					<c:when test="${ not empty authUser }">
					<div class="bottom">
						<a href="${pageContext.request.contextPath}/board/writeform" id="new-book">글쓰기</a>
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