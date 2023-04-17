<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko" class="no-js" >
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	  <meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
      <style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide:not(.ng-hide-animate){display:none !important;}ng\:form{display:block;}.ng-animate-shim{visibility:hidden;}.ng-anchor{position:absolute;}</style>
      <meta http-equiv="X-UA-Compatible" content="IE=Edge"> <!-- ie 호환성보기 설정을위한 meta태그 -->
      <meta http-equiv="cache-control" content="no-cache"> <!-- 현 프로젝트에는 캐시가 필요없으므로 캐시를 사용하지않는 설정을 해준다 -->
      <meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT"> <!-- 캐시의 만료/파기시간을 설정한다. 이미 만료된 시간을 설정함으로써 캐시사용을 금지 -->
      <meta http-equiv="pragma" content="no-cache"> <!-- 현 프로젝트에는 캐시가 필요없으므로 캐시를 사용하지않는 설정을 해준다 -->
      <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
      <meta name="format-detection" content="telephone=no, address=no, email=no"> <!-- 포맷이 적용되면 기존스타일을 먹지않으므로 meta태그로 재설정해준다 -->
      <title translate="CM-NBOOKING">전시 예약</title>
      <link rel="shortcut icon" href="#">
      <link rel="stylesheet" href="css/bookinglogin.css?ver=1.0">
      <link rel="stylesheet" href="css/register.css?ver=1.0">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.js"></script>
      <script src="https://spi.maps.daum.net/imap/map_js_init/postcode.v2.js"></script>
	  <script src="js/register.js" defer></script>
   </head>
   <body class="biz  ko" >
         <div class="_view_content" ui-view="content" id="container" >
               <div class="booking_login">
                  <h1 class="login_header"> <a href="./" ><span class="logo">RESERVATION</span></a></h1>
                  <div>
                       <h2 class="login_header_sub border_bottom"> <span translate="CM-NON_MEMBER_BK_CONFIRMATION">회원가입</span> </h2>
                       <form name="registerForm" id="registerForm" method="post">
                       		<ul>
                       			<li>
                       				<label>
										<span class="explanation">이메일</span>
										<input type="text" name="email" placeholder="abc@abc.com">
									</label>
									<span class="emailAuthBtn spanBtn">인증번호 전송</span>
                       			</li>
                       			<li>
                       				<label>
										<input type="text" name="emailAuthNum" placeholder="이메일로 보낸 인증번호를 입력해주세요.">
									</label>
										<span class="emailAuthCheck spanBtn">인증하기</span>
                       			</li>
                       			<li>
                       				<label>
										<span class="explanation">아이디</span>
										<input type="text" name="memberId" placeholder="아이디를 입력해주세요.">
									</label>
										<span class="memberDuplCheck spanBtn">중복확인</span>
                       			</li>
                       			<li>
                       				<label>
										<span class="explanation">비밀번호</span>
										<input type="password" name="pw" placeholder="비밀번호를 입력해주세요.">
									</label>
                       			</li>
                       			<li>
                       				<label>
										<span class="explanation">비밀번호 확인</span>
										<input type="password" name="pwCheck" placeholder="비밀번호를 다시 입력해주세요.">
									</label>
                       			</li>
                       			<li>
                       				<label>
										<span class="explanation">우편번호</span>
										<input type="text" name="zipCode" id="zipcode" placeholder="우편번호 검색 클릭" readonly>
									</label>
									<span class="zipCodeCheck spanBtn">우편번호 검색</span>
                       			</li>
                       			<li>
                       				<label>
										<span class="explanation">주소</span>
										<input type="text" name="address01" id="address01" placeholder="우편번호 검색 클릭" readonly>
									</label>
                       			</li>
                       			<li>
                       				<label>
										<span class="explanation">상세 주소</span>
										<input type="text" name="address02" id="address02" placeholder="상세 주소를 입력해주세요.">
									</label>
                       			</li>
                       		</ul>
						</form>
                     <button id="loginBtn" type="button" class="login_btn registerBtn confirm" ><span translate="CM-MY_BOOKING_CHECK">회원 가입 하기</span></button>
                  </div>
               </div>
         </div>
         <footer  aria-hidden="false">
			<div class="gototop">
				<a href="#" class="lnk_top"> <span class="lnk_top_text">TOP</span></a>
			</div>
            <div id="footer" class="footer"  >
               <ul class="lst_nav">
                  <li class="lnk_item" > <a href="" class="anchor _logout_link"> <span translate="CM-LOGIN">로그인</span> </a> </li>
                  <li class="lnk_item"> <a href="http://m.naver.com/services.html" class="anchor"> <span translate="CM-WHOLE_SERVICE">전체서비스</span> </a> </li>
               </ul>
               <p class="dsc_footer" translate="CM-FOOTER_DESC">이 사이트는 테스트용 포트폴리오 사이트입니다. 실제 결제 및 예약은 이루어지지 않으며, 상품의정보, 이벤트 등은 네이버 커넥츠 재단에서 제공한 데이터베이스를 활용하였습니다.</p>
               <dl class="box_vcard">
                  <dt class="tit_dt"> <a href="#" title="펼쳐보기"> <span>사업자정보</span> <i class="fn fn-down2" aria-hidden="true" ></i> </a> </dt>
               </dl>
               <ul class="lst_link">
                  <li class="item"> <a class="anchor" ui-sref="policy" href="#"> <span>이용약관</span> </a> </li>
                  <li class="item"> <a class="anchor" href="#"> <strong class="policy_em">개인정보처리방침</strong> </a> </li>
                  <li class="item"> <a href="#" class="anchor" > <span>예약 고객센터</span> </a> </li>
               </ul>
               <span class="copyright">© TEST.</span> 
            </div>
         </footer>
   </body>
</html>