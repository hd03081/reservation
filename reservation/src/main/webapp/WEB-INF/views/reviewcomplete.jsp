<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
	<title>예약 성공</title>
	<link rel="shortcut icon" href="#">
	<link href="../css/style.css?ver=3.6" rel="stylesheet" />
</head>
<body>
<div id="container">
<div class="header">
			<div class="gnb">
     		<!-- 세션, 세션스코프 이용한 이메일값 탐색 및 유지 -->
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
	  		<span class="review_complete">리뷰를 남겨주셔서 감사합니다!</span>
		  		<a href="/myreservation?reservationEmail=" class="backtomy"> 
					<span title="예약확인"> 마이페이지로 돌아가기 </span>
				</a>
	  		</div>
	  		<footer>
		<div class="gototop">
			<a href="#" class="lnk_top"> <span class="lnk_top_text">TOP</span></a>
		</div>
		<div class="footer">
			<p class="dsc_footer">이 사이트는 테스트용 포트폴리오 사이트입니다. 실제 결제 및 예약은 이루어지지 않으며, 상품의정보, 이벤트 등은 네이버 커넥츠 재단에서 제공한 데이터베이스를 활용하였습니다.</p>
			<span class="copyright">© TEST Corp.</span>
		</div>
	</footer>
	</div>
  	<script type="text/javascript" src="js/headerSubModule.js" defer></script>
</body>
</html>