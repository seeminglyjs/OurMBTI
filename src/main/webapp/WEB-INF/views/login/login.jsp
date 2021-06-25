<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/userHeader.jsp" %>

<style type="text/css">

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
</form>
</div>
<%@ include file="/WEB-INF/views/layout/userFooter.jsp" %>