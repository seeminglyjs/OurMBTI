<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/userHeader.jsp" %>
<!-- 메인화면 css  -->
<style type="text/css">

#mainpageDiv{
	padding-bottom: 300px;
}

.jumbotron{
	background-image: url("/resources/img/mainBanner2.png");
}

.jumbotron:hover{
	background-size: 130%;	
}

</style>

<div id="mainpageDiv">

<!-- 메인배너  -->
<div class="jumbotron" style="border-radius: 30px;">
  <h1 style="color:white; padding-left: 15px;"><strong>What is MBTI?</strong></h1>
  <p style="color:#e0fbfc; padding-left: 15px;">당신은 어떤 사람인지 궁금한가요?</p>
  <p><a class="btn btn-primary" href="#" role="button" style="margin-left: 20px;">MBTI 검사</a></p>
</div>

<hr>
<!-- 썸네일 배너 -->
<div class="col">
  <div class="col-sm-6 col-md-4">
    <div class="thumbnail">
      <img src="/" alt="...">
      <div class="caption">
        <h3>Thumbnail label</h3>
        <p>...</p>
        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
      </div>
    </div>
  </div>
</div>


<div class="col">
  <div class="col-sm-6 col-md-4">
    <div class="thumbnail">
      <img src="/" alt="...">
      <div class="caption">
        <h3>Thumbnail label</h3>
        <p>...</p>
        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
      </div>
    </div>
  </div>
</div>


<div class="col">
  <div class="col-sm-6 col-md-4">
    <div class="thumbnail">
      <img src="/" alt="...">
      <div class="caption">
        <h3>Thumbnail label</h3>
        <p>...</p>
        <p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
      </div>
    </div>
  </div>
</div>
<!-- 썸네일 배너 끝  -->


<h1>hello world</h1>

</div>

<%@ include file="/WEB-INF/views/layout/userFooter.jsp" %>