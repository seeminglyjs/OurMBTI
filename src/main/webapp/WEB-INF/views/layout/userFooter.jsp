<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- footer Css 코드 -->
<style type="text/css">

#footerDiv{
	display: flex;
	border: 1px solid #ccc;
    position: absolute;
    min-width: 1000px;
    min-height: 200px;
    left: 0;
    bottom: 0;

}


#footerDiv .col{
	margin-top: 30px;
	padding-left: 25px;
	padding-right: 25px;
}

/* footer img 위치 조정 */
#footerImgDiv{
	margin-top: 30px;
}

</style>


<div id="footerDiv">
	
	<div id="footerImgDiv" class="col">
		<img alt="로그" src="/resources/img/mainLogo.png" style="width: 200px; height: 100px;">
	</div>
	
	<div class="col">
		<h3>Contact</h3>
		<span>a</span><span>b</span><span>c</span>
	</div>
	
	<div class="col">
	    <h3>Services</h3>
	    <ul class="footerlist">
	        <li><a href="/">메인 화면</a></li>
	        <li><a href="/">MBTI 유형</a></li>
	        <li><a href="/">MBTI 게시판</a></li>
	    </ul>
     </div>
     <div class="col">
         <h3>OurMBTI</h3>
         <p>Seeminglyjs의 개인 Toy Project_01[ 프로젝트: OurMBTI ]</p>
         <p>Groups: 장성우 seeminglyjs@naver.com</p>                     
     </div>
</div>


</div>
</body>
</html>