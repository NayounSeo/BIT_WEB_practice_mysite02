<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript">
$(function ( ) {
	// form id는 여기에 쓰는 거였구나~~
	$("#join-form").submit( function( ) {
		//2016 04 18 오늘의 과제
		//1. 이름 유효성 체크
		if($("#name").val( ) == "") {
			alert("이름은 필수요소입니다.");
			$("#name").focus( );
			return false;
		}
		//2. 이메일 유효성 체크
		 if( $("#img-checkemail").is(":visible")==false) {
			alert("이메일 중복체크를 해야 합니다.");
			return false;
		} 
		//3. 약관 체크 유무
		if( $("#agree-prov").is(":checked") == false ) {
			alert("약관 동의에 체크해주세요.");
			return false;
		}
		//4. 패스워드 유효성 체크
		if( $("#password").val( ) == "") {
			alert("패스워드를 입력하세요.");
			return false;
		}
		//alert("여기가 보이면 폼을 서밋하세요.");
		return true;
	});
	
	$("#email").change(function ( ) {
		$("#btn-checkemail").show( );
		$("#img-checkemail").hide( );
	});

	$("#btn-checkemail").click( function( ) {
		var email = $("#email").val( );
		if(email == "") {
			return;
		}
		////////////////////////////////////////////////////////////////////////////////////
		$.ajax({
			url:"${pageContext.request.contextPath}/user/checkemail?email=" + email, //요청 url  //TH예닮
			type: "GET",						//통신방식 GET
			dataType:"json",
			data:"",				//POST방식인 경우 서버에 전달할 paramater 데이터 ex) a=checkemail&email=kickscar@gmail.com
									//보내는 데이터가 JSON 형식인 경우 "{\"a\":\"checkemail\",....."}
			//contentType:"application/json"
			success:function( response ) { //성공시 callback
				if( response.result != "success" ) {
					return;
				}
			//여기서부터 판단해야 하는 것은 data!
				if( response.data == false ) {
					alert("이미 존재하는 이메일입니다. 다른 이메일을 사용해 주세요.");
					$("#email").val("").focus( );
					return;
				}
			//여기서부터는 사용 가능한 이메일
				$("#btn-checkemail").hide( );
				$("#img-checkemail").show( );
			},
			error:function( jqXHR, status, error ) { //실패시 callback
				console.error(status+":"+error);
			}
		});
	});
});
</script>
</head>
<body>
	<div id="container"> <!--  div는 페이지를 논리적으로 나누는 태그!!! -->
		<c:import url="/WEB-INF/views/include/header.jsp"/>	
		<div id="content">
			<div id="user">
				<!--  뭔가 Vo가 필요할 것만 같은 곳이야.... -->
				<form id="join-form" name="joinForm" method="POST" action="${pageContext.request.contextPath}/user/join">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="">
					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="">
					<input id="btn-checkemail" type="button" value="id 중복체크">
					<img id="img-checkemail" style="display:none" src="${pageContext.request.contextPath}/assets/images/check.png">
					
					<label class="block-label">패스워드</label>
					<input id="password" name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="f" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="m">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
										
					<input type="submit" value="가입하기">					
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp"/>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>
	</div>
</body>
</html>