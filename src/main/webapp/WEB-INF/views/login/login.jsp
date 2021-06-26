<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/userHeader.jsp" %>

<% String noneUser  = (String)request.getAttribute("noneUser");%>

<!-- 쿠키 데이터를 가져온다. -->
<% Cookie[] cookies = request.getCookies();  %>
<!-- id 정보 쿠기와 체크박스 쿠키 -->
<% String idCookie = null;%>
<% String idCheck = null;%>

<!-- 쿠키 배열을 돌면서 쿠키 정보를 확인한다. -->
<%for(Cookie c : cookies) {%>
	<%if(c.getName().equals("idCookie")) {%>
		<% idCookie = c.getValue(); %>
	<%} %>
	<%if(c.getName().equals("idCheck")) {%>
		<% idCheck = c.getValue(); %>
	<%} %>
<%} %>

<!--pageScope에 쿠기 정보 저장  -->
<c:set value="<%=idCookie %>" var="idCookie"></c:set>
<c:set value="<%=idCheck %>" var="idCheck"></c:set>


<!-- 카카오톡 로그인 API 영역  -->
<script>  
     function loginWithKakao(){
    	 Kakao.Auth.login({
 		    scope: 'profile_nickname, account_email', // 가져올 정보
 		    success: function(authObj) { // 로그인 성공시
	 		    console.log(authObj);
	 		   	Kakao.Auth.authorize({ 
	 			  redirectUri: 'http://localhost:8088/login/kakaoLogin',
	 			  scope: 'profile_nickname, account_email'
	 			});
 		    },
 		    fail: function(error) {
 		        console.log(error);
 		    }
 		});
	} 
</script>


<script type="text/javascript">

/* 로그인 실패시 DB에 없는 데이터 일때 알림창이 나타남 */
$(document).ready(function(){	
	var check = "<%=noneUser %>" 	
	if( check == "ok"){
		alert("입력하신 계정 정보를 확인해 주세요.")
	}
})



/* 로그인 폼 전달전 확인 사항  */
function loginDataCheck (){
	var id = /^[A-Za-z\d]{4,12}$/ /* 아이디 정규식 체크  */
		if(!id.test($("#id").val())){
			$("#sp1").text("잘못된 아이디 형식입니다.").addClass("red")
			$("#id").focus()
			return false;
		}else{
			$("#sp1").text("")
			$("#sp1").removeClass("red")
		}
	
	
	var pw = /^[A-Za-z\d]{4,12}$/ 
		if(!pw.test($("#pw").val())){ /*비밀번호 정규식 체크  */
			$("#sp2").text("잘못된 비밀번호 형식입니다.").addClass("red")
			$("#pw").focus()
			return false;
		}else{
			$("#sp2").text("")
			$("#sp2").removeClass("red")
		}
	
}

</script>



<style type="text/css">
/* loginDiv Css 영역 */
#loginDiv{
	width: 600px;
	margin: 100px auto;
	padding: 50px;
	
	border: 1px solid #ccc;
	border-radius: 30px;
	
}

.red{
	font-size: 8px;
	color: red;
}

.green{
	font-size: 8px;
	color: green;
}


</style>


<div id="loginDiv">
<form action="/login/login" method="post" onsubmit="return loginDataCheck()">
  <div class="form-group">
    <label for="id">아이디</label>
    <c:choose>
    	<c:when test="${not empty idCookie }">
    	<input type="text" class="form-control" id="id" name="id" value="${idCookie }">
    	<span id="sp1"></span>
  		</c:when>
  		
  		<c:otherwise>
  		<input type="text" class="form-control" id="id" name="id" placeholder="아이디를 입력하세요">
  		<span id="sp1"></span>
  		</c:otherwise>
  	</c:choose>
  </div>
  <div class="form-group">
    <label for="pw">암호</label>
    <input type="password" class="form-control" id="pw" name="pw" placeholder="비밀번호를 입력하세요">
  	<span id="sp2"></span>
  </div>
  <div class="checkbox">
    <label>
        <c:choose>
    	<c:when test="${not empty idCookie }">
      	<input type="checkbox" id="memoryId" name="memoryId" checked="checked"> 아이디 기억하기
 	  	</c:when>
  		
  		<c:otherwise>
  		<input type="checkbox" id="memoryId" name="memoryId"> 아이디 기억하기
  		</c:otherwise>
  	</c:choose>
    </label>
  </div>
  <button class="btn btn-default btn-sm">로그인</button>
  <!-- 카카오톡로그인 자바스크립트 메소드 호출  -->
  <a href="javascript:loginWithKakao()"><button type="button" class="btn btn-default btn-sm">카카오로그인</button></a>
</form>
</div>
<%@ include file="/WEB-INF/views/layout/userFooter.jsp" %>