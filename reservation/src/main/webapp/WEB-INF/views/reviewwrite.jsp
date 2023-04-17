<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
	<title>전시 예약</title>
	<link rel="shortcut icon" href="#">
	<link href="../css/style.css?ver=3.6" rel="stylesheet" />
</head>

<body>
	<div id="container">
		<div class="header">
				<div class="gnb">
				<h1 class="reservation"><span class="logo">RESERVATION</span>
					<c:choose>
						<c:when test="${(cookie['token']!=null||cookie['kakao_token']!=null)||cookie['naver_token']!=null}">
							<a id="myReserveBtn" href="/myreservation?reservationId=" class="btn_my"> 
								<span id="myReserveBtnStr" title="마이페이지"></span>
							</a>
						</c:when>
						<c:otherwise>
							<a href="./bookinglogin" class="btn_my"> 
								<span title="로그인"> 로그인 </span>
							</a>
							<a href="./register" class="btn_my"> 
								<span title="회원가입"> 회원가입 </span>
							</a>
						</c:otherwise>
					</c:choose>			
				</h1>
	  		</div>
		</div>
		<div class="ct">
		<!-- formdata 단 -->
		<form class="uploadDataForm" enctype="multipart/form-data" method="post" name="uploadDataForm" action="/review/upload">
			<div class="ct_wrap ct_wrap_reviewwrite">
				<div class="top_title review_header">
					<a href="javascript:history.back()" class="btn_back" title="이전 화면으로 이동"> <i class="fn fn-backward1"></i> </a>
					<h2><span class="title">${displayInfoDescription }</span></h2>
				</div>
				<!-- 리뷰 별점 -->
				<div class="write_act">
					<p class="title_star">별점과 이용경험을 남겨주세요.</p>
					<div class="review_rating rating_point">
						<div class="rating">
							<!-- [D] 해당 별점이 선택될 때 그 점수 이하의 input엘리먼트에 checked 클래스 추가 -->
							<!-- 첫번째 input : 별점 score -->
							<span class="chk active" data-value="1"></span>
							<span class="chk" data-value="2"></span>
							<span class="chk" data-value="3"></span>
							<span class="chk" data-value="4"></span>
							<span class="chk" data-value="5"></span>
							<input name="score" class="rating_value" value="1" readonly>
							<!-- [D] 0점일 때 gray_star 추기 -->
						</div>
					</div>
				</div>
				<!-- //리뷰 별점 -->
	
				<!-- 리뷰 입력 -->
				<div class="review_contents write">
					<!-- [D] review_write_info 클릭 시 자신을 숨기고 review_textarea 에 focus를 보낸다. -->
					<a href="#" class="review_write_info">
						<span class="middot">
							실 사용자의 리뷰는 상품명의 더 나은 서비스 제공과 다른 사용자들의 선택에 큰 도움이 됩니다.
						</span><br>
						<span class="middot">
							소중한 리뷰에 대한 감사로 포인트 500원을 적립해드립니다.
						</span>
						<span class="left_space">(단, 리뷰 포인트는 ID 당 1일 최대 5건까지 지급됩니다.)</span>
					</a>
					<textarea cols="30" rows="10" name="comment" class="review_textarea"></textarea>
				</div>
				<!-- //리뷰 입력 -->

				<!-- 리뷰 작성 푸터 -->
				<div class="review_write_footer_wrap">
					<div class="review_write_footer">
						<label class="btn_upload" for="reviewImageFileOpenInput">
							<i class="fn fn-image1" aria-hidden="true"></i>
							<span class="text_add_photo">사진 추가</span>
						</label>
						<input type="file" class="hidden_input" name="attachedImage" id="reviewImageFileOpenInput" accept=".jpg,.png" onChange="CheckUploadFileSize(this); CheckuploadFileExt(this);"></input>
						<input type="button" value="파일안의 내용 초기화하기" class="delete_btn" onClick="RemoveFile();"></input>
						<div class="guide_review">
							<span id="count">0</span>/400
							<span>(최소5자이상)</span>
						</div>	
					</div>

					<!-- 리뷰 포토 -->
					<div class="review_photos review_photos_write">
						<div class="item_preview_thumbs">
							<ul class="lst_thumb">
								<li class="preview_item">
									<a href="#" class="anchor">
										<span class="spr_book ico_del">삭제</span>
									</a>
									<img src="" width="130" class="item_thumb" display="none">
									<span class="img_border"></span>
								</li>
							</ul>
						</div>
					</div>
					<!-- //리뷰 포토 -->

				</div>
				<!-- //리뷰 작성 푸터 -->
				
				<!-- productId, reservationInfoId 인풋 -->
				<input class="review_write_productid" name="productId" value="${reservationInfo.productId}">
				<input class="review_write_reserveid" name="reservationInfoId" value="${reservationInfo.reservationInfoId}">

				<!-- 리뷰 등록 -->
				<div class="box_bk_btn review_button">
					<span class="btn_txt">리뷰 등록</span>
				</div>
				<!-- //리뷰 등록 -->
			</div>
			</form>
		</div>
	</div>
	<footer>
		<div class="gototop">
			<a href="#" class="lnk_top"> <span class="lnk_top_text">TOP</span></a>
		</div>
		<div id="footer" class="footer">
			<p class="dsc_footer">이 사이트는 테스트용 포트폴리오 사이트입니다. 실제 결제 및 예약은 이루어지지 않으며, 상품의정보, 이벤트 등은 네이버 커넥츠 재단에서 제공한 데이터베이스를 활용하였습니다.</p>
			<span class="copyright">© TEST Corp.</span>
		</div>
		<div class="testFooterDiv"></div>
	</footer>
</body>

<script>
//reserve.jsp의 예약폼 ajax 모방해서 업로드폼 ajax 구현하기
var form = document.querySelector(".uploadDataForm"); //전체 폼에대한 dataform

var rating_value = document.querySelector(".rating_value");//인풋점수저장

var email_read = document.querySelector(".email_read");

var review_write_info = document.querySelector(".review_write_info");

var review_textarea = document.querySelector(".review_textarea");

var review_button = document.querySelector(".review_button");

review_write_info.addEventListener('click',function(e){
    let elem = e.target;
    review_write_info.style.display='none';
    review_textarea.focus();
})

review_textarea.onblur = function(e){
	if(review_textarea.value===""){
		review_write_info.style.display='block';
	}else{
		review_write_info.style.display='none';
	}
};


function RemoveFile() 
{
   let preview = document.querySelector(".item_thumb");
   let btn_upload = document.querySelector(".btn_upload");
   var file = document.getElementById('reviewImageFileOpenInput');
   file.value = '';
   document.querySelector('.delete_btn').style.display = 'none';
   preview.src = ''; // 썸네일 이미지 src 데이터 해제
   preview.style.display = 'none';
   btn_upload.style.display = 'block';

}


function CheckUploadFileSize(objFile){
     var nMaxSize = 4 * 1024 * 1024; // 4 MB
	 var nFileSize = objFile.files[0].size;
	 
     if(nFileSize > nMaxSize){
		 //alert("4MB보다 큼!!\n" + nFileSize + " byte");
		 RemoveFile();
	 }else{
		 //alert("4MB보다 작음!!\n" + nFileSize + " byte");
	 }
}

function CheckuploadFileExt(objFile){
	var strFilePath = objFile.value;
	if(strFilePath.match(/(.png|.jpg)$/)){
		//alert("허용되는 확장자");
	}else{
		//alert("허용하지 않는 확장자");
		RemoveFile();
	}
}	

//별점 마킹 모듈 프로토타입으로 생성
function Rating(){};
Rating.prototype.rate = 0;
Rating.prototype.setRate = function(newrate){
    //별점 마킹 - 클릭한 별 이하 모든 별 체크 처리
    this.rate = newrate;
    let items = document.querySelectorAll('.rating span');
    items.forEach(function(item, idx){
        if(idx < newrate){
            item.classList.add("active");
            item.classList.remove("disable");
        }else{
            item.classList.add("disable");
            item.classList.remove("active");
        }
    });
}
let rating = new Rating();//별점 인스턴스 생성

document.addEventListener('DOMContentLoaded', function(){
    //별점선택 이벤트 리스너
    document.querySelector('.rating').addEventListener('click',function(e){
        let elem = e.target;
        if(elem.classList.contains('chk')){
            rating.setRate(elem.dataset.value);
            console.log(parseInt(elem.dataset.value));
            console.log(elem);
            rating_value.value = elem.dataset.value;
        }
    })
});

//리뷰등록을 클릭할때 이벤트리스너를 줘서 각 form의 값을 정규식으로 판별하고 나서 submit 하기, 올바르지않은 값에대해서 경고창띄우기


//상품평 작성 글자수 체크 후 렌더링
review_textarea.onkeyup = function () {
  document.getElementById('count').innerHTML = this.value.length;
};

//상품평 작성 글자수 초과 체크 이벤트 리스너
document.querySelector('.review_textarea').addEventListener('keydown',function(){
    //리뷰 400자 초과 안되게 자동 자름
    let review = document.querySelector('.review_textarea');
    let lengthCheckEx = /^.{400,}$/;
    if(lengthCheckEx.test(review.value)){
        //400자 초과 컷
        review.value = review.value.substr(0,399);
    }
});

//리뷰버튼 submit + 글자수판별
document.querySelector('.review_button').addEventListener('click',function(){
	
    //리뷰 400자 초과 안되게 자동 자름
    let review = document.querySelector('.review_textarea');
    let lengthCheckEx = /^.{400,}$/;
    if(lengthCheckEx.test(review.value)){
        //400자 초과 컷
        review.value = review.value.substr(0,399);
    }
    
    if(review.value.length >= 5){
    	form.submit();	
    } else {
    	alert('내용은 5자 이상으로 작성해주세요');
    }
    
});

</script>
<script type="text/javascript">

var validateType = function(img){

	  return (['image/jpeg','image/jpg','image/png'].indexOf(img.type) > -1);

	}
//파일 선택 필드에 이벤트 리스너 등록

document.getElementById('reviewImageFileOpenInput').addEventListener('change', function(e){
	
	let label = document.querySelector('.btn_upload');
	label.style.display = 'none';

  let elem = e.target;
	document.querySelector('.delete_btn').style.display = 'block'; // 이미지 삭제 링크 표시
if(validateType(elem.files[0])){ // 파일 확장자 체크해서 이미지 파일이면

  let preview = document.querySelector('.item_thumb'); // 미리보기 썸네일 <img> 엘리먼트 얻기

  preview.style.display = 'block';
  
  preview.src = URL.createObjectURL(elem.files[0]); //파일 객체에서 이미지 데이터 가져옴.

  

  //이미지 로딩 후 객체를 메모리에서 해제

  preview.onload = function() {

    URL.revokeObjectURL(preview.src);

  }

}else{

  console.log('이미지 파일이 아닙니다.');

}
});
</script>
<script type="text/javascript" src="js/headerSubModule.js" defer></script>
</html>