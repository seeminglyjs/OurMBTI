<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/userHeader.jsp" %>

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



<style type="text/css">
/* loginDiv Css 영역 */
#loginDiv{
	width: 500px;
	margin: 100px auto;
	padding: 50px;
	
	border: 1px solid #ccc;
	border-radius: 30px;
	
}

</style>


<div id="loginDiv">
<form action="/login/login" method="post">
  <div class="form-group">
    <label for="id">아이디</label>
    <input type="text" class="form-control" id="id" name="id" placeholder="아이디를 입력하세요">
  </div>
  <div class="form-group">
    <label for="pw">암호</label>
    <input type="password" class="form-control" id="pw" name="pw" placeholder="비밀번호를 입력하세요">
  </div>
  <div class="checkbox">
    <label>
      <input type="checkbox" id="memoryId" name="memoryId"> 아이디 기억하기
    </label>
  </div>
  <button type="submit" class="btn btn-default btn-sm">로그인</button>
  <!-- 카카오톡로그인 자바스크립트 메소드 호출  -->
  <a href="javascript:loginWithKakao()"><button type="button" class="btn btn-default btn-sm">카카오로그인</button></a>
</form>
</div>
<%@ include file="/WEB-INF/views/layout/userFooter.jsp" %>