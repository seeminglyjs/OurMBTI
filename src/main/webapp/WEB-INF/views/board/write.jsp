<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/layout/userHeader.jsp" %>

<!--스마트 에디터 스크립트 라이브러리 불러오기  -->
<script type="text/javascript" src="/resources/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>




<script type="text/javascript">

$(document).ready(function(){

	// 글작성 클릭시
	$("#btnSave").click(function(){
		//스마트에디터의 내용을 <textare>에 적용하기
		var check = submitContents( $("#btnSave") );

		if(check){
			console.log(check)
			console.log("등록!!!!!")
			$("form").submit()
		}
		
	})
	
})	

</script>




<style type="text/css">

/* 게시글 상세보기 전체 DIV 설정 */
#writeDiv{
	margin-top: 20px;
	padding: 15px;
	min-height: 800px;
	border: 2px solid #98c1d9;
	border-radius: 30px;
}

#contentDiv{
	padding: 15px;
	min-height: 350px;
}


/* 파일업로드 css 부여하기 */
.filebox label { 
	display: inline-block; 
	padding: .5em .75em; 
	color: white; 
	font-size: inherit; 
	line-height: normal; 
	vertical-align: middle; 
	background-color: #98c1d9; 
	cursor: pointer; 
	border: 1px solid #ebebeb; 
	border-bottom-color: #e2e2e2; 
	border-radius: .25em; 
	} 

.filebox label:hover{
	text-decoration: underline;
}


.filebox input[type="file"] { 
/* 파일 필드 숨기기 */ 
	position: absolute; 
	width: 1px; 
	height: 1px; 
	padding: 0; 
	margin: -1px; 
	overflow: hidden; 
	clip:rect(0,0,0,0); 
	border: 0; 
	}


/* 제목창 테두리 제거 */
#title {
	 border:none;
     border-top: 0;
     border-right: 0;
     border-left: 0;

     -webkit-box-shadow: none;
     box-shadow: none;
     
     font-size: 23px;
     
}

</style>


<hr>
<div id="writeDiv">
<form action="/board/write" method="post" enctype="multipart/form-data" onsubmit="">

	<div class="form-group">
		<select class="form-control input-sm" id="boardCategory" name="boardCategory" style="width: 15%;">
			<option value="F">자유게시판</option>
			<option value="E">경험게시판</option>
			<option value="J">진로게시판</option>
		</select>
	</div>
	
	<!--게시글 제목  -->
	<div class="form-group" style="border-bottom: 1px solid #98c1d9;">
		<input type="text" class="form-control input-lg" id="title" name="title" placeholder="제목을 입력해주세요.">
	<br>
	</div>
	
	<!-- 파일 업로드 -->
	<div class="filebox text-right"> 
		<label for="uploadFiles">이미지 업로드</label> 
		<input multiple="multiple" type="file" id="uploadFiles" accept="image/*"> 
	</div>
	
	
	<!-- 게시글 내용영역  -->
	<div style="margin-top: 5px;" id="contentDiv">
		<textarea class="form-control" id="ir1" name="content" rows="25"></textarea>	
	</div>
	
	
	
	<hr>
		
	<!-- 게시글 페이지 이동 영역 -->
	<div class="form-group text-center">
	<button type="button" class="btn btn-sm btn-primary" id="btnSave">등록</button>
	<button type="button" class="btn btn-sm btn-default" onclick="history.back()">뒤로</button>	
	</div>
		
	
</form>
</div>


<!-- 스마트 에디터 스크립트 코드  -->
<script type="text/javascript">
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
	 oAppRef: oEditors,
	 elPlaceHolder: "ir1",
	 sSkinURI: "/resources/se2/SmartEditor2Skin.html",
	 fCreator: "createSEditor2"
	});
	
	
	// ‘저장’ 버튼을 누르는 등 저장을 위한 액션을 했을 때 submitContents가 호출된다고 가정한다.
function submitContents(elClickedObj) {
	 // 에디터의 내용이 textarea에 적용된다.
	 oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
	
 	// 제목 인풋창 정규식 체크		
	var title = document.getElementById("title").value;
 	if(title == null || title == ""){
			alert("제목을 입력해주세요.")
			return false;
	}else{
		 // 에디터의 내용에 대한 값 검증은 이곳에서
		 // document.getElementById("ir1").value를 이용해서 처리한다.
		 var content = document.getElementById("ir1").value;
		
		 content = content.replace(/&nbsp;/gi,"");
		 content = content.replace(/<br>/gi,"");
		 content = content.replace(/ /gi,"");
		
		 if(content == "<p><\/p>" || Text == ""){
			 alert("내용을 입력해주세요.")
			 return false;
		 }else{
				return true;
		 }	
	}
	 
	 try {
	     elClickedObj.form.submit();
	 } catch(e) {}
}
</script>

<%@include file="/WEB-INF/views/layout/userFooter.jsp" %>