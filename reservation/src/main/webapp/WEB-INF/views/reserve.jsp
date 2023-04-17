<!-- 진짜 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
<title>예약 페이지</title>
<link rel="shortcut icon" href="#">
<link href="css/style.css?ver=3.5" rel="stylesheet" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.js"></script>
</head>
<body>

<div class="container reserve_container">
<div class="header fade">
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
			<div class="ct_wrap">
				<div class="top_title">
					<!-- 이전페이지로 돌아가는 버튼으로, 디테일 페이지로 돌아가면서 전시정보id값을 같이 줘서 지금 예약중이던 상품의 상세페이지로 돌아감 -->
					<a href="./detail?id=${displayInfoId }" class="btn_back"
						title="이전 화면으로 이동"> <i class="fn fn-backward1"></i>
					</a>
					<h2>
						<span class="title"></span>
					</h2>
				</div>
				<div class="group_visual">
					<div class="container_visual" style="width: 414px">
						<ul class="visual_img">
							<!-- 상품이미지 리스트에서 알맞은 이미지를 for문돌며 뽑아서 렌더링, 그 id와 관련된 전시정보들도 다 가져와서 화면에 뿌리기 -->
							<c:forEach var="productImages" items="${productImages}"
								varStatus="status">
								<c:if test="${status.first eq true}">
									<li class="item" style="width: 414px"><img
										alt="${productImages.productId }" class="img_thumb"
										src="${productImages.fileInfoId }" /> <span class="img_bg"></span>
										<div class="preview_txt">
											<h2 class="preview_txt_tit">${displayInfo.productDescription }</h2>
											<em class="preview_txt_dsc">₩ <c:forEach
													var="productPrices" items="${productPrices }"
													varStatus="priceStatus">
													<c:if test="${priceStatus.first eq true}">
													${productPrices.price }
												</c:if>
												</c:forEach> ~
											</em><em class="preview_txt_dsc">${reserveStartDate }~${reserveEndDate },
												잔여티켓 2769매</em>
										</div></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
				<!-- 여러 예약에 관련된 상품정보들을 랜더링 -->
				<div class="section_store_details">
					<div class="store_details">
						<h3 class="in_tit"></h3>
						<p class="dsc">
							장소 : ${displayInfo.placeName }<br /> 기간 : ${reserveStartDate }~${reserveEndDate }
						</p>
						<h3 class="in_tit">관람시간</h3>
						<p class="dsc">${displayInfo.openingHours }</p>
						<h3 class="in_tit">요금</h3>
						<p class="dsc">
							<c:forEach var="productPrices" items="${productPrices}"
								varStatus="priceStatus">

								<c:if test="${priceStatus.index eq 0 }">
									성인(만 19~64세) ${productPrices.price }원 <br />
								</c:if>
								<c:if test="${priceStatus.index eq 1 }">
									어린이(만 4~12세) ${productPrices.price }원 <br />
								</c:if>
								<c:if test="${priceStatus.index eq 2 }">
									20인 이상 단체 20% 할인 / 국가유공자, 장애인, 65세 이상 ${productPrices.price }원 <br />
								</c:if>
								<c:if test="${priceStatus.index eq 3 }">
									청소년(만 13~18세) ${productPrices.price }원
									</c:if>
							</c:forEach>
						</p>
					</div>
				</div>
				<span class="divide_bar"></span>
				<!-- 티켓 텍스트박스 단 -->
				<div class="section_booking_ticket">
					<div class="ticket_body">
					<!-- 아마도 여기가 문제???? -->
						<c:forEach var="productPrices" items="${productPrices}"
							varStatus="priceStatus">
							<input type="hidden"
								name="prices[${priceStatus.index}].productPriceId"
								value="${productPrices.productPriceId }">
							<div class="qty">
								<div class="count_control">
									<!-- [D] 수량이 최소 값이 일때 ico_minus3, count_control_input에 disabled 각각 추가, 수량이 최대 값일 때는 ico_plus3에 disabled 추가 -->
									<div class="clearfix">
										<button type="button"
											class="btn_plus_minus spr_book2 ico_minus3 disabled"
											title="빼기">-</button><input type="tel" class="count_control_input disabled"
											value="0" name="prices[${priceStatus.index}].count" readonly
											title="수량" /><button type="button"
											class="btn_plus_minus spr_book2 ico_plus3" title="더하기">+
										</button>
									</div>
									<!-- [D] 금액이 0 이상이면 individual_price에 on_color 추가 -->
									<div class="individual_price">
										<span class="total_price">0</span><span class="price_type">원</span>
									</div>
								</div>
								<div class="qty_info_icon">
									<strong class="product_amount"> <span> <c:if
												test="${priceStatus.index eq 0 }">
									성인
									</c:if> <c:if test="${priceStatus.index eq 1 }">
									유아
									</c:if> <c:if test="${priceStatus.index eq 2 }">
									세트1
									</c:if> <c:if test="${priceStatus.index eq 3 }">
									청소년
									</c:if>
									</span>

									</strong> <strong class="product_price"> <span class="price"><fmt:formatNumber
												type="number" maxFractionDigits="3"
												value="${productPrices.price }" /></span> <span class="price_type">원</span>
									</strong> <em class="product_dsc"> <c:set var="price"
											value="${productPrices.price - (productPrices.price * (productPrices.discountRate * 0.01))}" />
										<fmt:formatNumber type="number" maxFractionDigits="3"
											value="${price}" /> 원 (${productPrices.discountRate }% 할인가)
									</em>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
				<span class="divide_bar"></span>
				<div class="section_booking_form">
					<div class="booking_form_wrap">
						<div class="form_wrap">
							<h3 class="out_tit_2">예매자 정보</h3>
							<div class="agreement_nessasary help_txt needit">
								<span class="spr_book ico_nessasary"></span> <span>필수입력</span>
							</div>
							<span class="divide_bar_reserv_info"></span>
							<form class="form_horizontal" action="./reserve" method="post">
								<div class="inline_form">
									<label class="label" for="name"> <span
										class="spr_book ico_nessasary">필수</span> <span>예매자</span>
									</label>
									<div class="inline_control">
										<input type="text" name="reservationName" id="name"
											class="text" placeholder="RESERVATION" maxlength="17" />
										<div class="warning_msg">형식에 틀렸거나 너무 짧아요</div>
									</div>
								</div>
								<div class="inline_form">
									<label class="label" for="tel"> <span
										class="spr_book ico_nessasary">필수</span> <span>연락처</span>
									</label>
									<div class="inline_control tel_wrap">
										<input type="tel" name="reservationTelephone" id="tel"
											class="tel1" value="" placeholder="010"
											maxlength="3" /> -
										<input type="tel" name="reservationTelephone" class="tel2" value="" placeholder="1234" maxlength="4"> -
										<input type="tel" name="reservationTelephone" class="tel3" value="" placeholder="5678" maxlength="4">
										<div class="warning_msg">형식이 틀렸거나 너무 짧아요</div>
									</div>
								</div>
								<div class="inline_form">
									<label class="label" for="email"> <span
										class="spr_book ico_nessasary">필수</span> <span>이메일</span>
									</label>
									<div class="inline_control">
										<input type="email" name="reservationEmail" id="email"
											class="email" value="" placeholder="crong@codesquad.kr"
											maxlength="50" />
										<div class="warning_msg">이메일 양식에 맞게 적어주세요.</div>
									</div>
								</div>
								<div class="inline_form last">
									<label class="label" for="message">예매내용</label>
									<div class="inline_control">
										<p class="inline_txt selected">
											<input type="hidden" class="d_id" name="displayInfoId"
												value="${displayInfoId }"> <input type="hidden"
												class="p_id" name="productId" value="${productId }">
												<!-- 랜덤생성된 예약날짜 랜더링 단 -->
											<input type="hidden" class="r_ymd"
												name="reservationYearMonthDay" value="${reserveRandomDate }">
											${reserveRandomDate }, 총 <span id="totalCount">0</span>매
										</p>
									</div>
								</div>
							</form>
						</div>
					</div>
					<span class="divide_bar_reserv_info"></span>
					<div class="section_booking_agreement">
						<div class="agreement all">
							<input type="checkbox" id="chk3" class="chk_agree" /> <label
								for="chk3" class="label chk_txt_label"> <span>이용자
									약관 전체동의</span>
							</label>
							<div class="agreement_nessasary">
								<span class="bottom_agree">필수동의</span>
							</div>
						</div>
						<!-- [D] 약관 보기 클릭 시 agreement에 open 클래스 추가 -->
						<span class="divide_bar_reserv_info"></span>
						<div class="agreement agree_sub">
							<span class="chk_txt_span"> <i
								class="spr_book ico_arr_ipc2"></i> <span>개인정보 수집 및 이용 동의</span>
							</span>
							<button type="button" class="btn_agreement">
								<span class="btn_text">보기</span> <i class="fn fn-down2"></i>
							</button>
							<div class="useragreement_details">
								&lt;개인정보 수집 및 이용 동의&gt;<br /> <br /> 1. 수집항목 : [필수] 이름, 연락처,
								[선택] 이메일주소<br /> <br /> 2. 수집 및 이용목적 : 사업자회원과 예약이용자의 원활한 거래
								진행, 고객상담, 불만처리 등 민원 처리, 분쟁조정 해결을 위한 기록보존, 서비스 이용 후 리뷰작성에 따른
								포인트 지급 및 관련 안내<br /> <br /> 3. 보관기간<br /> - 회원탈퇴 등 개인정보
								이용목적 달성 시까지 보관<br /> - 단, 상법 및 ‘전자상거래 등에서의 소비자 보호에 관한 법률’ 등 관련
								법령에 의하여 일정 기간 보관이 필요한 경우에는 해당 기간 동안 보관함<br /> <br /> 4. 동의 거부권
								등에 대한 고지: 정보주체는 개인정보의 수집 및 이용 동의를 거부할 권리가 있으나, 이 경우 상품 및 서비스 예약이
								제한될 수 있습니다.<br />
							</div>
						</div>
						<!-- [D] 약관 보기 클릭 시 agreement에 open 클래스 추가 -->
						<div class="agreement agree_sub">
							<span class="chk_txt_span"> <i
								class="spr_book ico_arr_ipc2"></i> <span>개인정보 제3자 제공 동의</span>
							</span>
							<button type="button" class="btn_agreement">
								<span class="btn_text">보기</span> <i class="fn fn-down2"></i>
							</button>
							<div class="useragreement_details custom_details_wrap">
								<div class="custom_details">
									&lt;개인정보 제3자 제공 동의&gt;<br /> <br /> 1. 개인정보를 제공받는 자 : TEST<br />
									<br /> 2. 제공하는 개인정보 항목 : [필수]아이디, 이름, 연락처 [선택] 이메일 주소<br />
									<br /> 3. 개인정보를 제공받는 자의 이용목적 : 사업자회원과 예약이용자의 원활한 거래 진행, 고객상담,
									불만처리 등 민원 처리, 서비스 이용에 따른 설문조사 및 혜택 제공, 분쟁조정 해결을 위한 기록보존<br />
									<br /> 4. 개인정보를 제공받는 자의 개인정보 보유 및 이용기간 : 개인정보 이용목적 달성 시 까지
									보관합니다.<br /> <br /> 5. 동의 거부권 등에 대한 고지 : 정보주체는 개인정보 제공 동의를
									거부할 권리가 있으나, 이 경우 상품 및 서비스 예약이 제한될 수 있습니다.<br />
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="box_bk_btn">
					<!-- [D] 약관 전체 동의가 되면 disable 제거 -->
					<div class="bk_btn_wrap disable">
						<button type="button" class="bk_btn">
							<span>예약하기</span>
							<div class="warning_msg">입력값을 다시 작성해주세요.</div>
						</button>
					</div>
				</div>
			</div>
		</div>
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
	<script>
		$.ajax({
			type : "GET",
			url : "/myinfo",
			success : function(data) {
				console.log(data);
				//alert(JSON.stringify(data));
				$('#name').val(data.userId);
				$('#name').prop('disabled', true);
				$('#email').val(data.email);
				$('#email').prop('disabled', true);
				$('#myReserveBtn').attr("href", "/myreservation?reservationId="+data.userId);
				$('#myReserveBtnStr').text(data.userId+" 님 환영합니다!");
				console.log("correct jwt!");
			},
			error : function(xhr, status, error) {
				console.log("code:" + xhr.status + "\n"
						+ "message:" + xhr.responseText + "\n"
						+ "error:" + error);
				//alert("회원정보가 올바르지 않습니다.");
			}
		});
	</script>

	<script type="text/javascript">
	
	var form_horizontal = document.querySelector(".form_horizontal"); //예매 텍스트박스 폼 부분 쿼리셀렉터
	var bk_btn_wrap = document.querySelector(".bk_btn_wrap"); //약관전체 동의여부확인 단 부분 쿼리셀렉터
	var chk_txt_label = document.querySelector(".chk_txt_label"); //약관 동의버튼 부분 쿼리셀렉터
	var name_txt = document.querySelector(".text"); //폼요소 텍스트박스
	var tel1 = document.querySelector(".tel1"); // 010
	var tel2 = document.querySelector(".tel2"); // 8590
	var tel3 = document.querySelector(".tel3"); // 4139
	var email = document.querySelector(".email"); //폼요소 이메일적는부분
	
	
	chk_txt_label.addEventListener("click", function(evt){ //약관 전체동의 버튼 이벤트리스너 주입
		if(bk_btn_wrap.classList.contains("disable")){ //만약 아직 안누른 상태라서 예약버튼이 disable 상태인경우
			bk_btn_wrap.classList.remove("disable"); //누르면 disable 상태를 해제한다
		}else{
			bk_btn_wrap.classList.add("disable"); //이미 동의했는데 다시누르는 경우는 disable 상태로 만든다
		}
	});
	bk_btn_wrap.addEventListener("click", function(evt){ //약관전체 동의 버튼과 실제 입력된 내용의 유효성 동적검사를위한 부분, 예약버튼에 이벤트리스너 주입
		const check_name = /^[가-힣a-zA-Z0-9 _]{2,30}$/; //유효한 이름 체크 정규표현식
		const check_tel = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/; //유효한 전화번호 체크 정규표현식
		const check_email = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/; //유효한 이메일 체크 정규표현식
		const name_val = name_txt.value; //이름 폼요소 텍스트박스 객체의 밸류값을 뽑아서 name_val const 객체에 대입
		const tel_val = tel1.value +'-'+ tel2.value +'-'+ tel3.value; //휴대전화 폼요소 텍스트박스의 문자열을 합쳐서 저장
		const email_val = email.value; //이메일 폼요소 텍스트박스 객체의 벨류값을 뽑아서 email_val const 객체에 대입
		if(!bk_btn_wrap.classList.contains("disable")){ //bk_btn_wrap(예매하기버튼)의 클래스명에 disable 이 포함되어있지않은경우
			if((check_name.test(name_val))&&(check_tel.test(tel_val))&&(check_email.test(email_val))){ //.test 정규표현식은 문장안에 찾으려는 문자열이 있는지 확인한다. 만약 이름 전화번호 이메일 세개 전부 알맞은 문자열이라면			
				
				const displayInfoId = document.querySelector(".d_id").value; //displayInfoId를 지역변수에 저장하기 위해 쿼리셀렉터로 해당 값을 가리킨다
		        var reservationprices = []; //reservationPrices 리스트를 위한 배열 지역변수의 선언 및 초기화
		        var count_control = document.querySelectorAll('.count_control_input'); //현재 예약상품 수량을 가져오기위한 쿼리셀렉터올 input은 총 4가지 종류로 성인 청소년 유아 세트1 이 있음
		        
		        for(let i = 0; i <count_control.length; i++){ //사용자가 입력한 상품 수량만큼 iterate
		        	
		        	let prices = {count : 0, productPriceId : 0, reservationInfoId : 0, reservationInfoPriceId : 0}; //prices 객체를 위한 초기값 설정
		        	var counts = document.querySelector('input[name="prices['+i+'].count"]');
		        	//[D] 수량이 최소 값일때 ico_minus3, count_control_input에 disabled 각각 추가, 수량이 최대 값일 때는 ico_plus3에 disabled 추가
		        	// var counts 는 input[0~4]의 .count를 전부 돌면서 유아,세트,청소년,성인의 각각의 예약수량을 쿼리셀렉터로 찾게됨
		        	
		        	//input type="hidden" productPriceId와 관련된 노드
		        	//위의 노드는 아래의 쿼리셀렉터로 찾게되는 노드임. 각각의 클라이언트가 채워넣은 연령별 티켓숫자를 가리키기위한 쿼리셀렉터임
		        	var tempProductPriceid = document.querySelector('input[name="prices['+i+'].productPriceId"]');
		        	
		        	//쿼리셀렉터로 필요한 노드들을 전부 가르켰으니 이제 준비해둔 지역변수(prices) 에 다 집어넣으면됨
		        	prices.count = counts.value; //노드의 벨류값으로부터 티켓 수량 넣기
		        	prices.productPriceId = tempProductPriceid.value; //노드의 밸류값으로부터 productPriceId 넣기
		        	prices.reservationInfoId = 0 ;
		        	prices.reservationInfoPriceId = 0;
		        	
		        	reservationprices.push(prices); //배열에 prices 밀어넣기
		        }
		        
		        const productId = document.querySelector(".p_id").value; //productId값 html노드에서 가져오기
		        const reservationEmail = email_val; //위에서 이미만들어둔 이메일값 대입
		        const reservationName = name_val; //이름값 대입
		        const reservationTelephone = tel_val; //전화번호 대입
		        const reservationYearMonthDay = document.querySelector(".r_ymd").value; //value="${reserveRandomDate }" 이값 대입
		        
		        var url = "/reserve"; //url 경로 지정
		        var params = { //비동기 통신용 데이터 통신을 위한 params 객체 미리 만들어놓기
		        	  "displayInfoId": displayInfoId,
		        	  "prices": reservationprices,
		        	  "productId": productId,
		        	  "reservationEmail": reservationEmail,
		        	  "reservationName": reservationName,
		        	  "reservationTelephone": reservationTelephone,
		        	  "reservationYearMonthDay": reservationYearMonthDay
		        	};
				var data = JSON.stringify(params); //JSON.stringify ()는 자바스크립트 객체를 JSON 형식의 문자열로 바꿔준다. 즉 params 자바스크립트 객체를 인자로 받아서 json으로 파싱하여 비동기통신에 쓰기쉽게만든다
				function sendAjax(url){ //ajax 통신 함수
					var oReq = new XMLHttpRequest();
					oReq.open("POST", url);	// method: POST
					oReq.setRequestHeader("Content-Type", "application/json"); // Content-Type: json
					oReq.responseType = "text";		// text for json
					oReq.addEventListener("load", function () { // when success
						console.log("성공 : "+data);
						// 예약하기 버튼을 눌러 클라이언트로부터 받은 데이터를 서버로 보내면 성공시에 /reservation 즉 초기화면으로 보낸다
						location.href = "./";
						});
					oReq.send(data); //받아놓은 json 데이터는 send 한다
					
				}
				
				sendAjax(url); //실제 ajax 함수 실행
	
			}else{ //bk_btn_wrap(예약하기버튼)의 클래스명에 disable 이 포함되어있는경우
				if(evt.target.tagName === "I" || evt.target.tagName === "SPAN"){ //만약 클릭한 이벤트타겟의 태그이름이 I 이거나 SPAN 이라면, 즉 예약하기 버튼을 눌렀다면
					evt.target.focus(); //이벤트 타겟에 커서를 위치시킨다
					evt.target.parentNode.children[2].style.visibility="visible"; //"입력값을 다시 작성해주세요" 를 visible로 바꾼다
					setTimeout(function() {
						evt.target.parentNode.children[2].style.visibility="hidden";
					}, 1000); //경고문은 1초뒤에 다시 히든으로 바뀐다
				}else{ //뭐 기타 다른곳을 눌렀더라도
					evt.target.focus(); //이벤트 타겟에 커서를 위치시킨다
					evt.target.children[2].style.visibility="visible"; //"입력값을 다시 작성해주세요" 를 visible로 바꾼다
					setTimeout(function() {
						evt.target.children[2].style.visibility="hidden";
					}, 1000);//경고문은 1초뒤에 다시 히든으로 바뀐다
				}
			}
		}
		
		
		/* .parentNode.children[1];
		if(!email_regExp.test(email_value)){
			evt.target.focus();
			warning_msg.style.visibility="visible";
			setTimeout(function() {
				warning_msg.style.visibility="hidden";
			}, 1000);
		} */
		
	});
	
	
	var totalCount = document.querySelector("#totalCount"); //총 티켓 갯수 html 노드 쿼리셀렉터
	var qty = document.querySelectorAll('.qty'); //count_control 의 parent 노드
	for(var i = 0; i <qty.length; i++){ //qty의 수만큼 iterate
		var each_qty = qty[i]; //각 qty노드에 접근하기위한 지역변수
		each_qty.addEventListener('click', function(evt){ //각 qty노드에 이벤트리스너 주입
			// 예매 내용의 totalCount 값 변동 & individual_price의 값 변동
			var target = evt.target; //이벤트 타겟(클릭한 각 qty노드안의 버튼) 노드를 가리키는 변수
			if(target.classList.contains('ico_plus3')){ //만약 각qty노드안의 버튼(더하기버튼 노드)을 클릭했을때 그 노드에 'ico_plus3' 클래스가 존재하는지 확인하여 있는경우
				//[D] 수량이 최소 값이 일때 ico_minus3, count_control_input에 disabled 각각 추가, 수량이 최대 값일 때는 ico_plus3에 disabled 추가
				const plus_count = target.previousElementSibling.value; //현재 버튼노드의 바로이전 같은 랭크의 노드(즉 바로이전 수량입력 인풋노드)의 밸류값을 plus_count 변수에 지정 
				let discount_price_text = target.parentNode.parentNode.parentNode.children[1].children[2].innerText; //현재 더하기버튼 노드의 parent의 parent의 parent노드(qty)의 두번째 자식의 (qty_info_icon) 세번째 자식 (<em class="product_dsc">) 노드를 지정하여 innerText로  discount_price_text 변수에 문자열 삽입. 해당 문자열은 할인율과 관련된 정보들임
				let total_price = target.parentNode.parentNode.children[1].children[0]; //현재 더하기버튼 노드의 parent의 parent노드(count_control)의 두번째 자식의 (<div class="individual_price">) 첫번째 자식 (<span class="total_price">) 노드를 지정 해당 노드는 상품전체가격을 표시
				let discount_price = discount_price_text.split('원'); //'원' 문자를 기준으로 discount_price_text 를 분할하여 discount_price 배열에 집어넣음
				let price = parseInt(discount_price[0].replace(/[^0-9]/g,'')); // /g 수정자 : 글로벌 매치(첫번째 조건을 찾은뒤 모든 조건을 다 검색)
				//discount_price 배열의 가장 첫번째 노드의 0-9를 제외한 문자 하나를 찾는다, 찾아서 그 값을 빈문자로 교체한다
				//xxxx원 문자열에서 원을 제거하여 숫자값만 남기는 정규표현식
				let tempcount = total_price.innerText; //tempcount 변수에 total_price노드의 문자열 대입
				total_price.innerHTML = price + parseInt(tempcount); //상품전체가격 노드에 접근하여 그 값을 제대로된 값으로 집어넣음
				
				var totalCountvalue = totalCount.innerHTML; //총 티켓갯수 를  나타내는 노드에 접근해서 그 값을 복사하고 totalCountvalue 변수에 저장
				if(plus_count == "0"){ //현재 plus_count 수량이 0일경우
					target.previousElementSibling.classList.remove('disabled'); //티켓 갯수가 마이너스가 될순 없으므로 빼기버튼 disabled
				}
				const plus_result = parseInt(plus_count) + 1; //plus_result 변수에는 plus_count에서 1을 더한 후 int형으로 형변환한 정수값을 저장
				target.previousElementSibling.value = plus_result; //더하기 버튼을 눌렀을때의 결과로 인풋노드의 밸류값을 plus_result 값 즉 버튼 한번당 1이 늘어난 값으로 저장
				totalCountvalue = parseInt(totalCountvalue) + 1; //총 티켓 갯수도 1만큼 더해줌
				totalCount.innerHTML = totalCountvalue; //위의 변수로 총 티켓갯수 노드값도 바꿔줌
				
			}else if(target.classList.contains('ico_minus3')){ //누른 버튼이 ico_minus3 즉 빼기 버튼일 경우에
				const minus_count = target.nextElementSibling.value; //현재 버튼노드의 바로다음 같은 랭크의 노드(즉 바로다음 수량입력 인풋노드)의 밸류값을 minus_count 변수에 지정 
				var totalCountvalue = totalCount.innerHTML; //총 티켓갯수 를  나타내는 노드에 접근해서 그 값을 복사하고 totalCountvalue 변수에 저장

				let discount_price_text = target.parentNode.parentNode.parentNode.children[1].children[2].innerText; //현재 더하기버튼 노드의 parent의 parent의 parent노드(qty)의 두번째 자식의 (qty_info_icon) 세번째 자식 (<em class="product_dsc">) 노드를 지정하여 innerText로  discount_price_text 변수에 문자열 삽입. 해당 문자열은 할인율과 관련된 정보들임
				let total_price = target.parentNode.parentNode.children[1].children[0];  //현재 뺴기버튼 노드의 parent의 parent노드(count_control)의 두번째 자식의 (<div class="individual_price">) 첫번째 자식 (<span class="total_price">) 노드를 지정 해당 노드는 상품전체가격을 표시
				let discount_price = discount_price_text.split('원'); //'원' 문자를 기준으로 discount_price_text 를 분할하여 discount_price 배열에 집어넣음
				let price = parseInt(discount_price[0].replace(/[^0-9]/g,'')); // /g 수정자 : 글로벌 매치(첫번째 조건을 찾은뒤 모든 조건을 다 검색)
				//discount_price 배열의 가장 첫번째 노드의 0-9를 제외한 문자 하나를 찾는다, 찾아서 그 값을 빈문자로 교체한다
				//xxxx원 문자열에서 원을 제거하여 숫자값만 남기는 정규표현식
				let tempcount = total_price.innerText; //tempcount 변수에 total_price노드의 문자열 대입
				
				
				if(minus_count != '0'){ //현재 minus_count 수량이 0이 아닐경우, 즉 상품갯수가 1개 이상이여서 빼기버튼을 누를 여지가 있는 경우
					const minus_result = parseInt(minus_count) - 1; //minus_result 변수에는 minus_count에서 1을 뺀 후 int형으로 형변환한 정수값을 저장
					target.nextElementSibling.value	 = minus_result; //빼기 버튼을 눌렀을때의 결과로 인풋노드의 밸류값을 minus_result 값 즉 버튼 한번당 1이 감소한 값으로 저장
					totalCountvalue = parseInt(totalCountvalue) -1; //총 티켓 갯수도 1만큼 더해줌
					totalCount.innerHTML = totalCountvalue; //위의 변수로 총 티켓갯수 노드값도 바꿔줌
					total_price.innerHTML = parseInt(tempcount) - price; //상품전체가격 노드에 접근하여 그 값을 제대로된 값으로 집어넣음
					if(minus_result == 0){ //minus_result 가 0이 되어 더이상 빼기버튼을 사용하지 못하게 해야하는 경우
						target.nextElementSibling.classList.add('disabled'); //빼기버튼 노드의 클래스명에 disabled를 추가한다
					}
				}
			}
		});
	}

	var agreement_btns = document.querySelectorAll('.btn_agreement'); //약관동의 펼치기 버튼 쿼리셀렉터 배열
		for (var i = 0; i < agreement_btns.length; i++) { //모든 btn_agreement를 iterate
			var each_btn = agreement_btns[i]; //각 버튼은 지역변수로 매번 사용됨

			each_btn.addEventListener('click', function(evt) { //각 약관펼치기 버튼에 이벤트리스너 주입
				var target = evt.target; //약관펼치기 버튼을 가리키는 이벤트 타겟
				if (target.tagName == "BUTTON") { //지금 클릭한 노드의 태그이름이 button 일경우
					if (!target.parentNode.classList.contains('open')) {//클릭한 노드의 parent노드의 클래스이름에 open 이 포함되어 있지않은경우
						target.parentNode.classList.add('open') //open을 추가하여 약관을 열린상태로 만든다
					} else if (target.parentNode.classList.contains('open')) { //그렇지 않고 이미 열린 경우라면
						target.parentNode.classList.remove('open') //open을 지워서 닫힌 상태로 만든다
					}
				} else { //딱 버튼부분이 아니라 다른곳을 눌렀을 경우
					if (!target.parentNode.parentNode.classList //버튼 노드 안의 span노드를 눌렀더라면 parent의 parent까지 가야됨. open을 포함하지 않는 경우
							.contains('open')) { //
						target.parentNode.parentNode.classList.add('open') //open을 추가
					} else if (target.parentNode.parentNode.classList
							.contains('open')) { //이미 열려있는 상태라면
						target.parentNode.parentNode.classList.remove('open') //닫음
					}
				}
			}, false);
		}

		
		
		form_horizontal.addEventListener('change', function(evt){ //예매 텍스트박스 폼 부분 쿼리셀렉터
			if(evt.target.classList.contains('tel1')||evt.target.classList.contains('tel2')||evt.target.classList.contains('tel3')){ //만약 전화번호 폼 태그를 클릭한 경우
				const tel_regExp = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/; //0~9가 2개이상 3개이하 - 0~9가 3개이상 4개이하 - 0~9가 4개
				const tel1_regExp = /^[0-9]{2,3}$/; //휴대전화번호 첫번째 박스 : 0~9가 2개이상 3개이하
				const tel2_regExp = /^[0-9]{3,4}$/; //휴대전화번호 두번째 박스 : 0~9가 3개이상 4개이하
				const tel3_regExp = /^[0-9]{4}$/; //휴대전화번호 세번째 박스 : 0~9가 4개
				const tel_value = evt.target.value; //해당 휴대전화번호 입력 박스 폼태그의 밸류값
				const warning_msg = evt.target.parentNode.children[3]; //<div class="warning_msg">형식이 틀렸거나 너무 짧아요</div>
				if(evt.target.classList.contains('tel1')){ //만약 클릭한 폼요소가 tel1 박스일경우
					if(!tel1_regExp.test(tel_value)){ //만약 tel_value(클라이언트가 입력한 문자열)의 값이 tel1_regExp 정규표현식과 일치하지 않는경우
						evt.target.focus(); //현재 이벤트 타겟에 다시 포커스(커서)를 둔다
						warning_msg.style.visibility="visible"; //잘못적었다는 경고문의 visible 로 바꾼다
						setTimeout(function() { //경고문은 1000ms 후에 다시 가린다
							warning_msg.style.visibility="hidden";
						}, 1000);
					}
				};
				if(evt.target.classList.contains('tel2')){ //만약 클릭한 폼요소가 tel2 박스일경우 
					if(!tel2_regExp.test(tel_value)){ //만약 tel_value(클라이언트가 입력한 문자열)의 값이 tel2_regExp 정규표현식과 일치하지 않는경우
						evt.target.focus(); //현재 이벤트 타겟에 다시 포커스(커서)를 둔다
						warning_msg.style.visibility="visible"; //잘못적었다는 경고문의 visible 로 바꾼다
						setTimeout(function() { //경고문은 1000ms 후에 다시 가린다
							warning_msg.style.visibility="hidden";
						}, 1000);
					}
				};
				if(evt.target.classList.contains('tel3')){ //만약 클릭한 폼요소가 tel3 박스일경우
					if(!tel3_regExp.test(tel_value)){ //만약 tel_value(클라이언트가 입력한 문자열)의 값이 tel3_regExp 정규표현식과 일치하지 않는경우
						evt.target.focus(); //현재 이벤트 타겟에 다시 포커스(커서)를 둔다
						warning_msg.style.visibility="visible"; //잘못적었다는 경고문의 visible 로 바꾼다
						setTimeout(function() { //경고문은 1000ms 후에 다시 가린다
							warning_msg.style.visibility="hidden";
						}, 1000);
					}
				};
			}else if(evt.target.classList.contains('text')){ //그렇지않고 만약 클릭한 부분이 text 클래스를 포함한다면(예매자 이름 입력 폼)
				const name_regExp = /^[가-힣a-z0-9_]{2,12}$/; //가~힣, a~z, 0~9와 언더바 들 중에 2개이상 12개 이하
				const name_value = evt.target.value; //해당 태그의 밸류값을 가리킴
				const warning_msg = evt.target.parentNode.children[1]; //이름입력 부분의 경고문 출력용 태그
				
				if(!name_regExp.test(name_value)){ //만약 name_value(클라이언트가 입력한 문자열)의 값이 name_regExp 정규표현식과 일치하지 않는경우
					evt.target.focus(); //현재 이벤트 타겟에 다시 포커스(커서)를 둔다
					warning_msg.style.visibility="visible"; //잘못적었다는 경고문의 visible 로 바꾼다
					setTimeout(function() { //경고문은 1000ms 후에 다시 가린다
						warning_msg.style.visibility="hidden";
					}, 1000);
				}
			}else if(evt.target.classList.contains('email')){ //그렇지않고 만약 클릭한 부분이 email 클래스를 포함한다면(이메일 입력 폼)
				const email_regExp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/; // \w = [0-9a-zA-Z_] , 시작은 모든 문자로 해야한다 -> 그 후 '.'다음에 오는 모든문자는 딱히 캡쳐하지않는다 ->근데 .이 왔으면 모든문자가 오긴해야된다 -> @ -> 모든문자+. 추가로 .다음에는 모든문자가 다시와야함. 그리고 문자열의 길이는 0개이상 66개 이하여야함 -> '.' -> a~z가 2개이상 6개이하 -> 만약 '.'을 한번 더쓴다면 'a~z 2개'를 묶어서 여러번 쓸수만 있음
				const email_value = evt.target.value; //해당 태그의 밸류값을 가리킴
				const warning_msg = evt.target.parentNode.children[1]; //잘못적었다는 경고문의 visible 로 바꾼다
				
				if(!email_regExp.test(email_value)){  //만약 email_value(클라이언트가 입력한 문자열)의 값이 email_regExp 정규표현식과 일치하지 않는경우
					evt.target.focus(); //현재 이벤트 타겟에 다시 포커스(커서)를 둔다
					warning_msg.style.visibility="visible"; //잘못적었다는 경고문의 visible 로 바꾼다
					setTimeout(function() { //경고문은 1000ms 후에 다시 가린다
						warning_msg.style.visibility="hidden";
					}, 1000);
				}
			}
		});
		
		function compare_regexp(inputValue){ //
			this.form_horizontal = inputValue; //텍스트박스 폼 전체부분을 노드를 인자로받은 inputValue을 대입
			this.registerEvents(); //이벤트 등록함수
		}
		
		/* RegExp.prototype = {
			registerEvents : function () {
				this.tabmenu.addEventListener("change", function (evt) {
					const value = evt.target.value;
					const warning_msg = evt.target.parentNode.children[1];
					let regExp;
					if(evt.target.classList.contains('text')){
						regExp = /^[가-힣a-z0-9_]{2,12}$/;
					}else if(evt.target.classList.contains('tel')){
						regExp = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/;
					}else if(evt.target.classList.contains('email')){
						regExp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
					}
					if(!regExp.test(value)){
						evt.target.focus();
						warning_msg.style.visibility="visible";
						setTimeout(function() {
							warning_msg.style.visibility="hidden";
						}, 1000);
					}
				}.bind(this));
			}
		} */
		
		
		/* form_horizontal.addEventListener('click', function(evt){
			console.log(evt.target.classList);
		}); */
		/* 
		 */


		
		
		/* btn_agreement.addEventListener("click", function(evt) {
			var target = evt.target;
			if(!target.parentNode.parentNode.classList.contains('open')){
				target.parentNode.parentNode.classList.add('open')
			}else if(target.parentNode.parentNode.classList.contains('open')){
				target.parentNode.parentNode.classList.remove('open')
			}
		}); */

		/* var buttons = document.querySelectorAll('.btn-click-me');
		for (var i = 0; i < buttons.length; i++) {
		    var self = buttons[i];

		    self.addEventListener('click', function (event) {  
		        // prevent browser's default action
		        event.preventDefault();

		        // call your awesome function here
		        MyAwesomeFunction(this); // 'this' refers to the current button on for loop
		    }, false);
		} */
	</script>

</body>
</html>