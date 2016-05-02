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

				<!--  c:when으로 뭣모르고 돌렸다가 안되서 if로 고쳐줌 ㅋㅋㅋㅋ 답글 수정 하나만 나왔어 계속..ㄷㄷ -->
				<div class="bottom">
					<a href="${pageContext.request.contextPath}/board/list">글목록</a>
					<c:if  test="${not empty authUser }">
						<c:if test="${not empty authUser && authUser.no == boardVo.userNo }">
							<a href="${pageContext.request.contextPath}/board/modifyform/${boardVo.no }">글수정</a>
						</c:if>
							<a href="${pageContext.request.contextPath}/board/replyform/${boardVo.no }">답글</a>
					</c:if>
				</div>				
			</div>
		</div>		
		<c:import url="/WEB-INF/views/include/navigation.jsp"/>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>	
	</div>
</body>
</html>