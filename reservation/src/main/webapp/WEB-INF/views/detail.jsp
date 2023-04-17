<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
<title>상세페이지</title>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
<script>
	src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.min.js"
	integrity="sha512-fujQJs+fkj7+Az7XKDxMLbVuuaeljjqMQDh3TAI7nYKQMQhEztrmyuex6hlnRuttjXJ9BFvnl4r/t8r8L6gFfA=="
	crossorigin="anonymous"</script>
	<link rel="shortcut icon" href="#">
<link href="css/style.css?ver=3.5" rel="stylesheet" />
</head>
<body>
	<div id="container">
	
		<!-- 상단 로고 및 이메일 표시부분 -->
	
		<div class="header fade">
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
    	
		<div class="contents_detail">
			<div class="section_visual_wrapper">
				<div class="section_visual">
				
				<!-- 상품 배경사진 영역 (배경사진 번호 및 슬라이드) -->
				
					<div class="pagination">
						<div class="bg_pagination"></div>
						<div class="figure_pagination">
							<c:choose>
								<c:when test="${etc eq 'hasEtc'}">
									<span class="num">1</span>
									<span class="num off">/ <span>2</span></span>
								</c:when>
								<c:otherwise>
									<span class="num">1</span>
									<span class="num off">/ <span>1</span></span>
								</c:otherwise>
							</c:choose>

						</div>
					</div>
					
					<!-- 상품 배경사진 영역 (배경사진 이미지 보이기) -->
					
					<div class="group_visual">
						<div>
							<div class="container_visual" style="width: 414px">
								<ul class="visual_img detail_swipe">
									<c:forEach var="productImages" items="${productImages}">
										<li class="item"><img class="img_thumb"
											alt="${productImages.productId }"
											src="${productImages.fileInfoId }" /> <span class="img_bg"></span>
											<div class="visual_txt">
												<div class="visual_txt_inn">
													<h2 class="visual_txt_tit">
														<span>${displayInfo.productDescription }</span>
													</h2>
													<p class="visual_txt_dsc"></p>
												</div>
											</div></li>
									</c:forEach>
								</ul>
							</div>
							<div class="prev">
								<div class="prev_inn">
									<a class="btn_prev" title="이전"> <!-- [D] 첫 이미지 이면 off 클래스 추가 -->
										<span class="spr_book2 ico_arr6_lt off">&lt;</span>
									</a>
								</div>
							</div>
							<div class="nxt">
								<div class="nxt_inn">
									<a class="btn_nxt" title="다음"> <span
										class="spr_book2 ico_arr6_rt">&gt;</span>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<!-- 펼쳐보기 영역 -->
				
				<div class="section_store_details">
					<!-- [D] 펼쳐보기 클릭 시 store_details에 close3 제거 -->
					<div class="store_details close3">
						<p class="dsc">${displayInfo.productContent }</p>
					</div>
					<!-- [D] 토글 상황에 따라 bk_more에 display:none 추가 -->
					<a href="#" class="bk_more _open"> <span class="bk_more_txt">펼쳐보기 ∨</span>
						<i class="fn fn-down2"></i>
					</a> <a href="#" class="bk_more _close" style="display: none;"> <span
						class="bk_more_txt">접기 ∧</span> <i class="fn fn-up2"></i>
					</a>
				</div>
				
				<!-- 이벤트 정보 영역 -->
				
				<div class="section_event">
					<div class="event_info_box">
						<div class="event_info_tit">
							<h4 class="in_tit">
								<i class="spr_book ico_evt"></i> <span>이벤트 정보</span>
							</h4>
						</div>
						<div class="event_info">
							<div class="in_dsc">
							<br />
								[특별할인]<br />R석 50%, S석 60% 할인
							</div>
						</div>
					</div>
				</div>
				
				<!-- 예매하기 버튼 영역 -->
				
				<div class="section_btn"
					data-displayinfo="${displayInfo.displayInfoId}">
					<button type="button" class="bk_btn">
						<i class="fn fn-nbooking-calender2"></i> <span>예매하기</span>
					</button>
				</div>
				
				<!-- 예약자 한줄 평 영역 -->
				
				<div class="section_review_list">
					<div class="review_box">
						<h3 class="title_h3">예매자 한줄평</h3>
						<div class="short_review_area">
						
						<!-- 평균평점, 한줄평 갯수 출력 영역 -->
						
							<div class="grade_area">
								<!-- [D] 별점 graph_value는 퍼센트 환산하여 width 값을 넣어줌 -->
								<div class="grade_mask_wrap">
								<span class="graph_mask"> <em class="graph_value"
									style="width: ${averageScore*20}%"></em>
								</span>
								</div>
								<strong class="text_value"> <span><c:out
											value="${fn:substring(averageScore,0,3) }" /></span> <em
									class="total">5.0</em>
								</strong> <span class="join_count"><em class="green">${fn:length(comments)}건</em>
									등록</span>
							</div>
							
							<!-- 개별 한줄평 UL 영역 -->
							
							<ul class="list_short_review">
								<c:set var="doneLoop" value="0" />
								<c:choose>
									<c:when test="${!empty comments}">
										<c:forEach var="commentsList" items="${comments}"
											varStatus="status">
											<c:if test="${doneLoop eq 0}">
												<c:if test="${!empty commentsList.commentImages}">
													<li class="list_item">
														<div>
															<div class="review_area">
																<div class="thumb_area">
																	<c:forEach var="commentImages"
																		items="${commentsList.commentImages}" begin="0"
																		end="0">
																		<a href="javascript:void(window.open('${commentImages.fileId}', '_blank'))" class="thumb thumb_zoom" title="이미지 크게 보기" ><img 
																			width="90" height="90" class="img_vertical_top"
																			src="./<c:out value="${commentImages.fileId} "/>"
																			alt="리뷰이미지" />
																		</a>
																	</c:forEach>
																	<span class="img_count" style="display: none">${commentsList.commentId}</span>
																</div>
																<span class="product">${displayInfo.productDescription }</span>
																<h4 class="resoc_name"></h4>
																<p class="review">${commentsList.comment }</p>
															</div>
															<div class="info_area">
																<div class="review_info">
																	<span class="grade">${commentsList.score }</span> <span
																		class="name">${commentsList.reservationName}</span> <span
																		class="date">${commentsList.createDate}</span>
																</div>
															</div>
														</div>
													</li>
													<c:set var="imageIndex" value="${status.index }" />
													<c:set var="doneLoop" value="${doneLoop + 1 }" />
												</c:if>
											</c:if>
										</c:forEach>
										<c:forEach var="comments" items="${comments}"
											varStatus="status">
											<c:if test="${doneLoop lt 3 }">
												<c:if test="${doneLoop eq 0 }">
													<li class="list_item">
														<div>
															<div class="review_area">
																<div class="thumb_area">
																	<a href="" class="thumb" title="이미지 크게 보기"> <img
																		width="90" height="90" class="img_vertical_top"
																		src=""
																		alt="리뷰이미지" />
																	</a> <span class="img_count" style="display: none">${comments.commentId}</span>
																</div>
																<span class="product">${displayInfo.productDescription }</span>
																<h4 class="resoc_name"></h4>
																<p class="review">${comments.comment }</p>
															</div>
															<div class="info_area">
																<div class="review_info">
																	<span class="grade">${comments.score }</span> <span
																		class="name">${comments.reservationName}</span> <span
																		class="date">${comments.createDate}</span>
																</div>
															</div>
														</div>
													</li>
													<c:set var="doneLoop" value="${doneLoop + 1 }" />
												</c:if>
												<c:if test="${imageIndex ne status.index}">
													<li class="list_item">
														<div>
															<div class="review_area no_img">
															<span class="product">${displayInfo.productDescription }</span>
																<h4 class="resoc_name"></h4>
																<p class="review">${comments.comment }</p>
															</div>
															<div class="info_area">
																<div class="review_info">
																	<span class="grade">${comments.score }</span> <span
																		class="name">${comments.reservationName }</span> <span
																		class="date">${comments.createDate }방문</span>
																</div>
															</div>
														</div>
													</li>
													<c:set var="doneLoop" value="${doneLoop + 1 }" />
												</c:if>
											</c:if>
										</c:forEach>
									</c:when>
								</c:choose>
							</ul>
						</div>
						<p class="guide">
							<i class="spr_book2 ico_bell"></i> <span>이용자들이 남긴 평가입니다.</span>
						</p>
					</div>
					
					<!-- 예매자 한줄평 더보기 버튼 영역 -->
					
					<a class="btn_review_more"
						href="./review/${displayInfo.displayInfoId}"> <span>예매자
							한줄평 더보기</span> <i class="fn fn-forward1"></i>
					</a>
					
					
					
				</div>
				
				<!-- 상세정보/오시는길 탭 UI 영역 -->
				
				<div class="section_info_tab">
					<!-- [D] tab 선택 시 anchor에 active 추가 -->
					<ul class="info_tab_lst">
						<li class="item active _detail"><a class="anchor active">
								<span>상세정보</span>
						</a></li>
						<li class="item _path"><a class="anchor"> <span>오시는길</span>
						</a></li>
					</ul>
					
					<!-- [D] 상세정보 외 다른 탭 선택 시 detail_area_wrap에 hide 추가 -->
					<!-- 상세정보 탭 -->
					
					<div class="detail_area_wrap">
						<div class="detail_area">
							<div class="detail_info">
								<h3 class="blind">상세정보</h3>
								<ul class="detail_info_group">
									<li class="detail_info_lst"><strong class="in_tit">[소개]</strong>
									
									<!-- [소개] 영역 -->
									
										<p class="in_dsc">${displayInfo.productContent }</p></li>
										
									<!--  [공지사항] 영역 -->	
										
									<li class="detail_info_lst"><strong class="in_tit">[공지사항]</strong>
										<ul class="in_img_group">
											<li class="in_img_lst"><img alt="" class="img_thumb"
												src="./att/att.jpg" />
											</li>
										</ul></li>
									<!-- <li class="detail_info_lst"> <strong class="in_tit">[공연정보]</strong>
                                        <ul class="in_img_group">
                                            <li class="in_img_lst"> <img alt="" class="img_thumb" src="https://ssl.phinf.net/naverbooking/20170131_255/1485825099482NmYMe_JPEG/%B0%F8%BF%AC%C1%A4%BA%B8.jpg?type=a1000"> </li>
                                        </ul>
                                    </li> -->
								</ul>
							</div>
						</div>
					</div>
					
					<!-- [D] 오시는길 외 다른 탭 선택 시 detail_location에 hide 추가 -->
					<!-- 오시는길 탭 -->
					
					<div class="detail_location hide">
						<div class="box_store_info no_topline">
							<a href="" class="store_location" title="지도웹으로 연결"> <img
								class="store_map img_thumb" alt="map"
								src="${displayInfoImage.fileId }" /> <span
								class="img_border"></span> <span class="btn_map"><i
									class="spr_book2 ico_mapview"></i></span>
							</a>
							<h3 class="store_name">${displayInfo.productDescription }</h3>
							<div class="store_info">
								<div class="store_addr_wrap">
									<span class="fn fn-pin2"></span>
									<p class="store_addr store_addr_bold">${displayInfo.placeLot }</p>
									<p class="store_addr">
										<span class="addr_old">지번</span> <span class="addr_old_detail">${displayInfo.placeStreet }
										</span>
									</p>
									<p class="store_addr addr_detail">${displayInfo.placeName }</p>
								</div>
								<div class="lst_store_info_wrap">
									<ul class="lst_store_info">
										<li class="item"><span class="item_lt"> <i
												class="fn fn-call2"></i> <span class="sr_only">전화번호</span>
										</span> <span class="item_rt"> <a href="tel:02-548-0597"
												class="store_tel">${displayInfo.telephone }</a></span></li>
									</ul>
								</div>
							</div>
							
							<!-- [D] 모바일 브라우저에서 접근 시 column2 추가와 btn_navigation 요소 추가 -->
							<!-- 오시는길 탭 내의 길찿기/네비게이션 버튼 영역 -->
		
							<div class="bottom_common_path column2">
								<a href="" class="btn_path"> <i class="fn fn-path-find2"></i>
									<span>길찾기</span>
								</a> <a href="" class="btn_navigation before"> <i
									class="fn fn-navigation2"></i> <span>내비게이션</span>
								</a>
							</div>
						</div>
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
	<div id="photoviwer"></div>
	<script type="text/javascript" src="js/headerSubModule.js" defer></script>
	<script>
		// html dom 이 다 로딩된 후 실행된다.
		$(document).ready(function() {
			// menu 클래스 바로 하위에 있는 a 태그를 클릭했을때
			// jquery $({content}) 요소에 .click() 함수를 실행
			// 클릭시의 이벤트를 정의하는 .click() 함수는 function(){......} 의 형태로 배개변수 부분에 함수를 선언하여 여러 자바스크립트 작업들을 할 수 있음
			$(".section_store_details>.bk_more").click(function() { // $("#menu li") 는 #menu 안에 있는 모든 li를 가리키고, $("#menu > li") 는 #menu 바로 아래의 li만 가리킨다.
				var more = $(this); //변수 more 에 jquery 객체 $(this) 즉 클릭이 일어난 현재 노드를 대입

				// submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
				
				if (more.hasClass("_open") === true) { //hasClass는 현재 객체의 위치에(즉 more이 가리키는 현재 노드를 말함) 인자로 받은 문자열을 포함하는 html 클래스가 있는지 확인하여 boolean 타입으로 리턴하는 메서드이다
					var details = $(".store_details.close3"); //변수 more 에 jquery 객체 .store_details.close3 노드를 대입
					var close = more.next("a"); //next(), 선택한 요소(more)의 바로 다음에 위치한 형제 요소(a태그를 포함하는)를 선택한다.
					more.hide(); //hide(), display 속성을 none 으로 바꾼다
					close.show(); //show(), display 속성을 block 으로 바꾼다
					details.removeClass("close3"); //.removeClass()로 선택한 요소에서 클래스 값을 제거할 수 있습니다.
				} else {
					var details = $(".store_details"); //변수 more 에 jquery 객체 .store_details 노드를 대입
					var open = more.prev("a"); //next(), 선택한 요소(more)의 바로 이전에 위치한 형제 요소(a태그를 포함하는)를 선택한다.
					more.hide(); //hide(), display 속성을 none 으로 바꾼다
					open.show(); //show(), display 속성을 block 으로 바꾼다
					details.addClass("close3"); //.addClass()로 선택한 요소에 클래스 값을 추가할 수 있습니다.
				}
			});
		});
		var reserveView_btn = document.querySelector(".section_btn"); //reserveView_btn 변수는 DOM객체에서 .section_btn 클래스를 가리킨다
		reserveView_btn.addEventListener("click", function(evt) { //이벤트리스너(클릭 시) 주입
			var displayInfoId; //전시정보 id를 담기위한 지역변수
			if( evt.target.tagName === "DIV" ) { //만약 클릭한 이벤트 타겟의 태그이름이 DIV 일 경우
				displayInfoId = evt.target.dataset.displayinfo; //전시정보id 지역변수에 해당 이벤트 타겟의 dataset의 displayInfo 값을 대입한다
			} else if ( evt.target.tagName === "BUTTON" ) { //또한 추가로 이벤트 타겟의 태그이름이 BUTTON 일 경우
				displayInfoId = evt.target.parentElement.dataset.displayinfo; //전시정보id 지역변수에 해당 이벤트 타겟의 dataset의 displayInfo 값을 대입한다
			} else if ( evt.target.tagName === "I" ) { //또한 추가로 이벤트 타겟의 태그이름이 I 일 경우
				displayInfoId = evt.target.parentElement.parentElement.dataset.displayinfo; //전시정보id 지역변수에 해당 이벤트 타겟의 dataset의 displayInfo 값을 대입한다
			} else if( evt.target.tagName === "SPAN" ) { //또한 추가로 이벤트 타겟의 태그이름이 SPAN 일 경우
				displayInfoId = evt.target.parentElement.parentElement.dataset.displayinfo; //또한 추가로 이벤트 타겟의 태그이름이 SPAN 일 경우
			}
			
			//document.getElementById("start").value = 0;
			location.href = "/reservePage?id="+displayInfoId; //페이지 이동을 위한 객체 location.href, 예약페이지로 이동하는 경로 설정
		});
		
		
			var image_ul = document.querySelector(".visual_img.detail_swipe"); //변수 image_ul 은 .visual_img.detail_swipe 클래스를 가리킴
			var prevBtn = document.querySelector(".prev"); //변수 prevBtn은 .prev 클래스를 가리킴
			var nextBtn = document.querySelector(".nxt"); //변수 nextBtn 은 .nxt 클래스를 가리킴
			var numSpan = document.querySelector(".figure_pagination>.num:not(.off)") //변수 numSpan은 .figure_pagination>.num:not(.off) 클래스를 가리킴
			var imgCnt = 0; //이미지 갯수 표시를 위한 변수를 0으로 초기화
			
			/* Animation: sliding setting */
			image_ul.querySelectorAll("li").forEach(()=> { //이미지 ul 안의 li들을 전부 읽은 뒤 하나씩 iteration 하며 할때마다 imgCnt 변수를 1씩 증가시킴
				imgCnt ++;
			});	
			image_ul.style.width = (image_ul.offsetWidth * imgCnt) + "px"; //image_ul의 넓이를 이미지의 갯수만큼 길게 늘림
			if(imgCnt > 1){ //민약 이미지의 길이가 1개 초과인경우
				
				prevBtn.addEventListener("click", function() { //'이전' 버튼에 이벤트 리스너 주입
					var curIndex = numSpan.innerText - 1; //배경이미지 커서 숫자 표시부분에 -1을 함
					if(numSpan.innerText === '1'){ //-1을 했음에도 1일 경우엔
						numSpan.innerText = '2'; //사진이 2장 있는 것이므로 2
					}else{ //그렇지 않을경우
						numSpan.innerText = '1'; //사진이 1장이므로 1
					}
					slideShow(curIndex); //curIndex 숫자에 따른 위치로 슬라이드쇼
				});
				nextBtn.addEventListener("click", function() { //'다음' 버튼에 이벤트리스너 주입
					var curIndex = numSpan.innerText - 1; //배경이미지 커서 숫자 표시부분에 -1을 함
					if(numSpan.innerText === '1'){ //-1을 했음에도 1일 경우엔
						numSpan.innerText = '2';  //사진이 2장 있는 것이므로 2
					}else{  //그렇지 않을경우
						numSpan.innerText = '1'; //사진이 1장이므로 1
					}
					slideShow(curIndex); //curIndex 숫자에 따른 위치로 슬라이드쇼
				});
			}
			
			/* Animation: sliding */
			//실제 슬라이딩이 일어나는 방식에 대한 스크립트
			function slideShow(curIndex) { //curIndex 변수를 인자로 받는 slideShow 함수
				if(curIndex === 0){ //curIndex가 0인경우 즉 현재 첫번째 사진을 보여주고 있는 경우
					image_ul.style.transition = "transform 0.1s ease-out"; //transform으로 애니메이션 구현
					image_ul.style.transform = "translate3d(-" + 414*(curIndex+1)+"px, 0px, 0px)"; //2번째 사진으로 이동할수 있도록 x축값 1 증가시켜서 설정
					curIndex++; //이동 후엔 curIndex 값 1 증가
				}else if(curIndex === 1){ //curIndex가 0인경우 즉 현재 두번째 사진을 보여주고 있는 경우
					image_ul.style.transition = "transform 0.1s ease-out"; //transform으로 애니메이션 구현
					image_ul.style.transform = "translate3d(-" + 414*(curIndex-1)+"px, 0px, 0px)"; //1번째 사진으로 이동할수 있도록 x축값 1 감소시켜서 설정
					curIndex = 0; //이동 후엔 curIndex 값 0으로 초기화하여 무한반복 할수있도록 함
				}
			}
			
			//tab ui 부분의 데이터를 표시하는것을 컨트롤하는 스크립트
			
			var infoTab = document.querySelector(".info_tab_lst"); //상세정보,오시는길 탭을 가리키는 infoTab 변수
			infoTab.addEventListener("click", function(evt) { //탭에 이벤트리스너 주입 elegation
				var clickEle = evt.target.tagName; // clickEle는 클릭한 이벤트 타겟의 태그이름을 가리킴
				var target = evt.target; //target 변수는 이벤트라 일어난 노드를 가리킴
				var activeEle = document.querySelector('.anchor.active'); //activeEle 변수는 .anchor.active, 즉 현재 활성화된 탭을 표시하기위한 노드를 가리킴
				var notactiveEle = document.querySelector('.anchor:not(.active)'); // not(.active), 즉 현재 활성화된 탭이 아닌 노드를 가리킴
				var detail = document.querySelector('._detail'); //상세설명 내용부분을 가리킴
				var path = document.querySelector('._path'); //길찾기 내용부분을 가리킴
				var detail_area_wrap = document.querySelector('.detail_area_wrap'); //상세설명 래퍼
				var detail_location = document.querySelector('.detail_location'); //길찾기 래퍼
				
				var detailChild = detail.childNodes[0].classList; //셍세정보탭의 첫번째 자식노드의 classList 객체로 접근. 클래스리스트 객체는 add나 remove등으로 클래스 속성에 조작을 가할수있게 한다
				var pathChild = path.childNodes[0].classList; //길찾기탭의 첫번째 자식노드의 classList 객체로 접근
				
				if(clickEle === 'LI'){ //만약 '클릭한 이벤트 타겟의 태그이름'(clickEle)이 LI 인 경우
					if(!target.childNodes[0].classList.contains('active')){ //만약 현재 이벤트 타겟의 첫번째 자식노드의 클래스명이 'active' 문자열을 포함하고있지 않다면(.contains)
						activeEle.classList.toggle('active'); //이미 클래스이름이 active 라면 지우고, 클래스이름이 없다면 active를 집어넣는다
						notactiveEle.classList.add('active'); //원래 활성화된 탭이 아닌 노드에 active를 추가한다
						
						if(detailChild.contains('active')){ //만약 상세정보탭의 첫번째 자식노드의 클래스명이 'active' 문자열을 포함하고있다면
							detail_area_wrap.classList.remove('hide'); //상세정보탭의 래퍼의 hide 클래스 값을 삭제한다
							detail_location.classList.remove('hide'); //길찾기 내용의 hide 클래스 값을 삭제한다
							detail_location.classList.add('hide') //길찾기 내용의 hide 클래스 값을 추가한다
						}else if(pathChild.contains('active')){ //또한 추가로 길찾기의 첫번째 아들노드의 클래스명이 'active' 문자열을 포함하고있다면
							detail_area_wrap.classList.remove('hide');//상세정보탭의 래퍼의 hide 클래스 값을 삭제한다
							detail_location.classList.remove('hide');//길찾기 내용의 hide 클래스 값을 삭제한다
							detail_area_wrap.classList.add('hide')//상세정보탭의 래퍼의 hide 클래스 값을 추가한다
						}
					}
				}else if(clickEle === 'A'){ //또한 추가로 이벤트 타겟의 태그이름이 'A' 라면
					if(!target.classList.contains('active')){ //만약 현재 이벤트 타겟의 클래스명이 'active' 문자열을 포함하고있지 않다면(.contains)
						activeEle.classList.toggle('active'); //.anchor 클래스의 active 상태를 토글한다
						notactiveEle.classList.add('active'); //그리고 원래 활성화된 탭이 아닌 노드에 active를 추가한다
						
						if(detailChild.contains('active')){ //만약 셍세정보탭의 첫번째 자식노드의 클래스명이 'active' 문자열을 포함하고 있다면
							detail_area_wrap.classList.remove('hide');  //상세정보탭의 래퍼의 hide 클래스 값을 삭제한다
							detail_location.classList.remove('hide'); //길찾기 내용의 hide 클래스 값을 삭제한다
							detail_location.classList.add('hide') //길찾기 내용의 hide 클래스 값을 추가한다
						}else if(pathChild.contains('active')){  //또한 추가로 길찾기탭의 첫번째 자식노드의 클래스명이 'active' 문자열을 포함하고 있다면
							detail_area_wrap.classList.remove('hide'); //상세정보탭의 래퍼의 hide 클래스 값을 삭제한다
							detail_location.classList.remove('hide');  //길찾기 내용의 hide 클래스 값을 삭제한다
							detail_area_wrap.classList.add('hide') //상세정보 내용의 hide 클래스 값을 추가한다
						}
					} 
				}else if(clickEle === 'SPAN'){ //또한 추가로 이벤트 타겟의 태그이름이 'SPAN' 라면
					if(!target.parentNode.classList.contains('active')){ //만약 현재 이벤트 타겟의 부모노드 클래스명이 'active' 문자열을 포함하고있지 않다면(.contains)
						activeEle.classList.toggle('active'); //.anchor 클래스의 active 상태를 토글한다
						notactiveEle.classList.add('active'); //그리고 원래 활성화된 탭이 아닌 노드에 active를 추가한다
						
						if(detailChild.contains('active')){ //만약 셍세정보탭의 첫번째 자식노드의 클래스명이 'active' 문자열을 포함하고 있다면
							detail_area_wrap.classList.remove('hide'); //상세정보탭의 래퍼의 hide 클래스 값을 삭제한다
							detail_location.classList.remove('hide');//길찾기 내용의 hide 클래스 값을 삭제한다
							detail_location.classList.add('hide')//길찾기 내용의 hide 클래스 값을 추가한다
						}else if(pathChild.contains('active')){ //또한 추가로 길찾기탭의 첫번째 자식노드의 클래스명이 'active' 문자열을 포함하고 있다면
							detail_area_wrap.classList.remove('hide');//상세정보탭의 래퍼의 hide 클래스 값을 삭제한다
							detail_location.classList.remove('hide');//길찾기 내용의 hide 클래스 값을 삭제한다
							detail_area_wrap.classList.add('hide')//상세정보 내용의 hide 클래스 값을 추가한다
						}
					}
				}
			});
	</script>

</body>
</html>