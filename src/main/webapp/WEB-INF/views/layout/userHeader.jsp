<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<!--제이쿼리 CDN  -->
<script src="https://code.jquery.com/jquery-2.2.4.min.js" ></script>

<!-- JSTL 태그 라이브러리  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>


<!-- 카카오 api 초기화 -->
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script type="text/javascript">
// SDK를 초기화 합니다. 사용할 앱의 JavaScript 키를 설정해 주세요.
Kakao.init('1edcb61a473aa9342e8cfcb704cd0b80');
// SDK 초기화 여부를 판단합니다. 성공시 true
console.log(Kakao.isInitialized());
</script>

<!-- 카카오 api 연결 끊기 로그아웃  -->
<script type="text/javascript">
$(document).ready(function(){
	$("#logout").click(function(){	
		Kakao.API.request({
			  url: '/v1/user/unlink',
			  success: function(response) {
			    console.log(response);
			    $(location).attr("href","/login/logout") // 로그아웃 링크 연결
			  },
			  fail: function(error) {  
			    console.log(error);
			    $(location).attr("href","/login/logout") // 로그아웃 링크 연결
			  },
			});		
	})	
})
</script>

<style type="text/css">
/* 폰트 설정  */
@font-face {
    font-family: 'NEXON Lv1 Gothic OTF';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

body{
	font-family: 'NEXON Lv1 Gothic OTF';
}

/* 전체컨테이너 설정  */
#containerAll{
	width: 1000px; /*넓이  */
	min-height: 1600px;
	padding-bottom: 300px;
	margin: 0 auto;
	position: relative;
}


/* 헤더컨테이너 설정  */
#containerHeader{
	margin-top: 20px;
	width: 1000px; /*넓이  */
	display: flex;
}


#headerImgDiv{
	width: 300px; /*넓이  */
	height: 100px;
	
}



/* 네비게이션 DIV 설정 */
.headerNavDiv{
	height: 100px;
}

/* 네비게이션 ul */
.headerNav{	
	margin:0px; 
	padding:0px;
	margin-top: 30px;	
}

.headerNav li{
	text-align:center;
	list-style:none; 
}


.headerNav > li{
	float: left;
}

.headerNav ul{
	display: none;
}

.headerNav ul li{
	line-height:30px;
	text-align: center;
	
}

.headerNav li:hover > ul {
	position: absolute;
	text-align:center;
	padding-left: 0px;
	display:block;
}

.headerNav li{
	width: 130px;
	padding: 3px;
}

/* 드롭다운 메뉴 위아래 둥글게 설정 */
.headerNav li:hover > ul > li {
	border: 1px solid #98c1d9;
	background-color: white;
}

.headerNav li:hover > ul > li:first-child {
	margin-top: 10px;
	border-radius: 20px 20px 0px 00px;
}

.headerNav li:hover > ul > li:last-child {
	border-radius: 0px 0px 20px 20px;
}
/* --------------------------------- */

.headerNav a{
	font-weight:bold;
	color:#3d5a80;
	display:block;
	font-size: 15px;
}


</style>
<meta charset="UTF-8">
<title>OurMBTI</title>
</head>
<body>
<div id="containerAll">

<!-- 헤더 Div  -->
<div id="containerHeader">
	
	<div id="headerImgDiv"><!-- 헤더 이미지 Div  -->
		<a href="/"><img src="/resources/img/mainLogo.png" style="width:200px; height: 100px;"></a>
	</div>
	
	<div id="headerNavDiv"><!--네비게이션   -->
		<ul class="headerNav" class="float-left"> 
  			<li><a href="#">MBTI Is</a>
  				<ul>
      	 			<li><a href="#">MBTI 정의</a></li>
       				<li><a href="#">MBTI 유형</a></li>
    			</ul>
  			</li>
			<li><a href="#">MBTI 검사</a>
 				<ul>
      	 			<li><a href="#">sub menu1</a></li>
       				<li><a href="#">sub menu2</a></li>
       				<li><a href="#">sub menu3</a></li>
    			</ul>
 			</li>
  			<li><a href="#">Board</a>
    			<ul>
      	 			<li><a href="/board/list?category=F">자유게시판</a></li>
       				<li><a href="/board/list?category=E">경험게시판</a></li>
       				<li><a href="/board/list?category=J">진로게시판</a></li>
    			</ul>
			</li>
		</ul>
	</div>
	
	<!--float 영향 없애기  -->
	<div class="clearfix"></div>
	
	<div id="headerLoginDiv" style="margin-left: 50px;"><!-- 로그인 영역  -->
		<ul class="headerNav"> 
  			<li style="width: 100px;"><a href="#">
  					<i class="glyphicon glyphicon-user" style="font-size: 20px;"></i>
  				</a>
  				<ul style="border: 1px solid #98c1d9; border-radius: 20px;">
  				<c:if test="${empty login }"><!-- 로그인 여부에 따라 다르게 보이게 설정 로그인하면(true) -->
      	 			<li style="border: 0px; width: 100px; margin-top: 5px"><a href="/login/login" >로그인</a></li>
      	 			<li style="border: 0px; width: 100px;"><a href="/join/join">회원가입</a></li>
      	 		</c:if>	
      	 		<c:if test="${login}">
      	 			<li style="border: 0px; width: 100px;"><a href="#" id="logout">로그아웃</a></li>
      	 			<li style="border: 0px; width: 100px;"><a href="/" >마이페이지</a></li>    
      	 		</c:if>	
    			</ul>
  			</li>
		</ul>
	</div>
	
	<!--float 영향 없애기  -->
	<div class="clearfix"></div>
		
</div>
<!--header div 끝  -->