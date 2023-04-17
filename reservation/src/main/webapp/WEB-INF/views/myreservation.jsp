<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.UUID" %>

<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no"
    />
    <title>전시 예약</title>
    <link rel="shortcut icon" href="#">
    <link href="css/style.css?ver=3.5" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.js"></script>
  </head>

  <body>
    <div id="container">
    
    <!-- 상단바 부분 -->
    
      <div class="header">
        <header class="header_tit gnb">
          <h1 class="reservation">
            <a class="logo" href="./">내 예약</a>
          </h1>
          <a href="#" class="btn_my">
            <span title="내예약" class="viewReservation"></span>
            <span title="로그아웃" id="logoutBtn" class="logout"> / 로그아웃</span>
          </a>
          	<script>
          	$.ajax({
        		type : "GET",
        		url : "/myinfo",
        		success : function(data) {
        			console.log(data);
        			//alert(JSON.stringify(data));
        			$('.viewReservation').text(data.userId+" 님 환영합니다!");
        			console.log("correct jwt!");
        		},
        		error : function(xhr, status, error) {
        			console.log("code:" + xhr.status + "\n"
        					+ "message:" + xhr.responseText + "\n"
        					+ "error:" + error);
        		}
        	});
          	</script>
        </header>
      </div>
      <hr/>
      <div class="ct">
        <div class="section_my">
        
          <!--상단 전체 예약 현황 : 전체 / 이용예정 / 이용완료 / 취소환불 -->
          
          <div class="my_summary">
            <ul class="summary_board">
              <li class="item">
                <!--[D] 선택 후 .on 추가 link_summary_board -->
                <a href="#" class="link_summary_board on">
                  <i class="spr_book2 ico_book2"></i> <em class="tit">전체</em>
                  <span class="figure">${size }</span>
                </a>
              </li>
              <li class="item">
                <a href="#" class="link_summary_board">
                  <i class="spr_book2 ico_book_ss"></i>
                  <em class="tit">이용예정</em> <span class="figure">
                  <c:choose>
                  	<c:when test="${!empty reservations }">
                  		${fn:length(reservations) }
                  	</c:when>
                  	<c:otherwise>
                  	0
                  	</c:otherwise>
                  </c:choose>
                  </span>
                </a>
              </li>
              <li class="item">
                <a href="#" class="link_summary_board">
                  <i class="spr_book2 ico_check"></i>
                  <em class="tit">이용완료</em> <span class="figure">
                  <c:choose>
                  	<c:when test="${!empty completeReservations}">
                  		${fn:length(completeReservations) }
                  	</c:when>
                  	<c:otherwise>
                  	0
                  	</c:otherwise>
                  </c:choose>
                  </span>
                </a>
              </li>
              <li class="item">
                <a href="#" class="link_summary_board">
                  <i class="spr_book2 ico_back"></i>
                  <em class="tit">취소·환불</em> <span class="figure">
                  <c:choose>
                  	<c:when test="${!empty cancelReservations}">
                  		${fn:length(cancelReservations) }
                  	</c:when>
                  	<c:otherwise>
                  	0
                  	</c:otherwise>
                  </c:choose>
                  </span>
                </a>
              </li>
            </ul>
          </div>
          <!--// 예약 현황 -->

          <!-- 내 예약 리스트 바디 부분 -->
          
          <div class="wrap_mylist">
            <ul class="list_cards" ng-if="bookedLists.length > 0">
            
            <!-- 예약확정 상태인 예약들 목록 -->
            
            <c:if test="${reservations ne null}">
            	<c:forEach var="reservations" items="${reservations}" varStatus="status">
            		<li class="card confirmed">
                <div class="link_booking_details">
                  <div class="card_header">
                    <div class="left"></div>
                    <div class="middle">
                      <!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book -->
                      <i class="spr_book2 ico_check2"></i>
                      <span class="tit">예약 확정</span>
                    </div>
                    <div class="right"></div>
                  </div>
                </div>
                <article class="card_item">
                  <a href="#" class="link_booking_details">
                    <div class="card_body">
                      <div class="left"></div>
                      <div class="middle">
                        <div class="card_detail">
                        
                          <!-- No. 넘버 표시부분 -->
                        
                          <em class="booking_number">
                          No.${reservations.reservationInfoId}<!-- 7자리 fmt -->
                          </em>
                          
                          <!-- 서비스명 / 상품명 부분 -->
                          
                          <h4 class="tit">
                          <c:forEach var="displayInfo" items="${reservations.displayInfo}" begin="0" end="0">
                              		${displayInfo.categoryName} / ${displayInfo.productDescription}
                          </c:forEach>
                          </h4>
                          
                          <!-- 일정 내역 장소 업체 -->
                          
                          <ul class="detail">
                            <li class="item">
                              <span class="item_tit">일정</span>
                              <em class="item_dsc">
                                <fmt:formatDate value="${reservations.createDate }" pattern="yyyy.MM.dd.(EEE)"/>
                                ~
                                <fmt:formatDate value="${reservations.reservationDate }" pattern="yyyy.MM.dd.(EEE)"/>
                              </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">내역</span>
                              <em class="item_dsc"> 
                              <c:forEach var="displayInfo" items="${reservations.displayInfo}" begin="0" end="0">
                              		${displayInfo.productContent}
                              </c:forEach>
                              </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">장소</span>
                              <em class="item_dsc">
                              <c:forEach var="displayInfo" items="${reservations.displayInfo}" begin="0" end="0">
                              		${displayInfo.placeName}
                              </c:forEach>
                              </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">업체</span>
                              <em class="item_dsc">
                              	<c:forEach var="displayInfo" items="${reservations.displayInfo}" begin="0" end="0">
                              		<c:choose>
                              			<c:when test="${!empty displayInfo.homepage}">
                              				${displayInfo.homepage}
                              			</c:when>
                              			<c:otherwise>
                              				업체명이 없습니다.
                              			</c:otherwise>
                              		</c:choose>
                              </c:forEach>
                              </em>
                            </li>
                          </ul>
                          
                          <!-- 결제 예정금액 ~원 부분 -->
                          
                          <div class="price_summary">
                            <span class="price_tit">결제 예정금액</span>
                            <em class="price_amount">
                              <span>
                              <fmt:formatNumber type="number" maxFractionDigits="3" value="${reservations.totalPrice}" />
                              </span>
                              <span class="unit">원</span>
                            </em>
                          </div>
                          
                          <!-- 예약취소 버튼 부분 -->
                          
                          <!-- [D] 예약 신청중, 예약 확정 만 취소가능, 취소 버튼 클릭 시 취소 팝업 활성화 -->
                          <div class="booking_cancel" data-reserveid="${reservations.reservationInfoId}">
                            <button class="btn"><span>취소</span></button>
                          </div>
                          <div class="booking_transaction" data-reserveid="${reservations.reservationInfoId}">
                            <c:choose>
                       			<c:when test="${reservations.transactionFlag==1}">
                       				<span class="trasactionComplete">결제가 완료되었습니다</span>
                       			</c:when>
                       			<c:otherwise>
                       				<button class="btn"><span>결제하기</span></button>업체명이 없습니다.
                       			</c:otherwise>
                       		</c:choose>
                          </div>
                        </div>
                      </div>
                      <div class="right"></div>
                    </div>
                    <div class="card_footer">
                      <div class="left"></div>
                      <div class="middle"></div>
                      <div class="right"></div>
                    </div>
                  </a>
                  
                  <!-- 공유하기 버튼 부분 -->
                  
                  <a
                    href="#"
                    title="공유하기"
                  ></a>
                  
                </article>
              	</li>
            	</c:forEach>
            </c:if>
            
            <!-- 이용완료 상태인 예약들 목록 -->
            
            <c:if test="${completeReservations ne null}">
            	<c:forEach var="completeReservations" items="${completeReservations}" varStatus="status"> <!-- ==============카드 복사 시작============= -->
            		<li class="card used">
                <div class="link_booking_details">
                  <div class="card_header">
                    <div class="left"></div>
                    <div class="middle">
                      <!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book -->
                      <i class="spr_book2 ico_check2"></i>
                      <span class="tit">이용 완료</span>
                    </div>
                    <div class="right"></div>
                  </div>
                </div>
                <article class="card_item">
                  <a href="#" class="link_booking_details">
                    <div class="card_body">
                      <div class="left"></div>
                      <div class="middle">
                        <div class="card_detail">
                        
                          <!-- No. 넘버 표시부분 -->
                        
                          <em class="booking_number">No.${completeReservations.reservationInfoId}</em>
                          
                          <!-- 서비스명 / 상품명 부분 -->
                          
                          <h4 class="tit">
                          	<c:forEach var="displayInfo" items="${completeReservations.displayInfo}" begin="0" end="0">
                              		${displayInfo.categoryName} / ${displayInfo.productDescription}
                          </c:forEach>
                          </h4>
                          
                          <!-- 일정 내역 장소 업체 -->
                          
                          <ul class="detail">
                            <li class="item">
                              <span class="item_tit">일정</span>
                              <em class="item_dsc">
                                <fmt:formatDate value="${completeReservations.createDate }" pattern="yyyy.MM.dd.(EEE)"/>
                                ~
                                <fmt:formatDate value="${completeReservations.reservationDate }" pattern="yyyy.MM.dd.(EEE)"/>
                              </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">내역</span>
                              <em class="item_dsc"> 
                              <c:forEach var="displayInfo" items="${completeReservations.displayInfo}" begin="0" end="0">
                              		${displayInfo.productContent}
                              </c:forEach>
                              </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">장소</span>
                              <em class="item_dsc">
                              <c:forEach var="displayInfo" items="${completeReservations.displayInfo}" begin="0" end="0">
                              		${displayInfo.placeName}
                              </c:forEach>
                              </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">업체</span>
                              <em class="item_dsc">
                              	<c:forEach var="displayInfo" items="${completeReservations.displayInfo}" begin="0" end="0">
                              		<c:choose>
                              			<c:when test="${!empty displayInfo.homepage}">
                              				${displayInfo.homepage}
                              			</c:when>
                              			<c:otherwise>
                              				업체명이 없습니다.
                              			</c:otherwise>
                              		</c:choose>
                              </c:forEach>
                              </em>
                            </li>
                          </ul>
                          <div class="price_summary">
                          
                          	<!-- 결제 예정금액 ~원 부분 -->
                          
                            <span class="price_tit">결제 예정금액</span>
                            <em class="price_amount">
                              <span>
                              <fmt:formatNumber type="number" maxFractionDigits="3" value="${completeReservations.totalPrice}" />
                              </span>
                              <span class="unit">원</span>
                            </em>
                          </div>
                          <div class="booking_review" data-reserveid="${completeReservations.reservationInfoId}" data-displayinfoid="<c:forEach var="displayInfo" items="${completeReservations.displayInfo}" begin="0" end="0">${displayInfo.displayInfoId}</c:forEach>">
                            <button class="btn">
                            <span>예매자 리뷰 남기기</span>
                            </button>
                          </div>
                        </div>
                      </div>
                      <div class="right"></div>
                    </div>
                    <div class="card_footer">
                      <div class="left"></div>
                      <div class="middle"></div>
                      <div class="right"></div>
                    </div>
                  </a>
                </article>
              </li>
            	</c:forEach> <!-- ==============카드종료============= -->
            </c:if>
            
            <!-- 예약취소 버튼, 공유버튼은 없음 -->
            
            <!-- 취소된 예약 상태인 예약들 목록 -->
            
            <c:if test="${cancelReservations ne null}">
            	<c:forEach var="cancelReservations" items="${cancelReservations}" varStatus="status">
            		<li class="card used cancel">
                <div class="link_booking_details">
                  <div class="card_header">
                    <div class="left"></div>
                    <div class="middle">
                      <!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book -->
                      <i class="spr_book2 ico_cancel"></i>
                      <span class="tit">취소된 예약</span>
                    </div>
                    <div class="right"></div>
                  </div>
                </div>
                <article class="card_item">
                  <a href="#" class="link_booking_details">
                    <div class="card_body">
                      <div class="left"></div>
                      <div class="middle">
                        <div class="card_detail">
                        
                          <!-- No. 넘버 표시부분 -->
                        
                          <em class="booking_number">No.${cancelReservations.reservationInfoId}</em>
                          
                          <!-- 서비스명 / 상품명 부분 -->
                          
                          <h4 class="tit">서비스명/상품명</h4>
                          
                          <!-- 일정 내역 장소 업체 -->
                          
                          <ul class="detail">
                            <li class="item">
                              <span class="item_tit">일정</span>
                              <em class="item_dsc">
                                <fmt:formatDate value="${cancelReservations.createDate }" pattern="yyyy.MM.dd.(EEE)"/>
                                ~
                                <fmt:formatDate value="${cancelReservations.reservationDate }" pattern="yyyy.MM.dd.(EEE)"/>
                              </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">내역</span>
                              <em class="item_dsc"> 
                              <c:forEach var="displayInfo" items="${cancelReservations.displayInfo}" begin="0" end="0">
                              		${displayInfo.productContent}
                              </c:forEach>
                              </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">장소</span>
                              <em class="item_dsc">
                              <c:forEach var="displayInfo" items="${cancelReservations.displayInfo}" begin="0" end="0">
                              		${displayInfo.placeName}
                              </c:forEach>
                              </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">업체</span>
                              <em class="item_dsc">
                              	<c:forEach var="displayInfo" items="${cancelReservations.displayInfo}" begin="0" end="0">
                              		<c:choose>
                              			<c:when test="${!empty displayInfo.homepage}">
                              				${displayInfo.homepage}
                              			</c:when>
                              			<c:otherwise>
                              				업체명이 없습니다.
                              			</c:otherwise>
                              		</c:choose>
                              </c:forEach>
                              </em>
                            </li>
                          </ul>
                          <div class="price_summary">
                          
                          <!-- 결제 예정금액 ~원 부분 -->
                          
                            <span class="price_tit">결제 예정금액</span>
                            <em class="price_amount">
                              <span>
                              <fmt:formatNumber type="number" maxFractionDigits="3" value="${cancelReservations.totalPrice}" />
                              </span>
                              <span class="unit">원</span>
                            </em>
                          </div>
                        </div>
                      </div>
                      <div class="right"></div>
                    </div>
                    <div class="card_footer">
                      <div class="left"></div>
                      <div class="middle"></div>
                      <div class="right"></div>
                    </div>
                  </a>
                </article>
              </li>
            	</c:forEach>
            </c:if>
              <!--[D] 예약확정: .confirmed, 취소된 예약&이용완료: .used 추가 card -->
              
              <!-- 예약신청중 임시 하드코딩 부분 -->
              
              <li id="complete" class="card" style="display: none">
                <div class="link_booking_details">
                  <div class="card_header">
                    <div class="left"></div>
                    <div class="middle">
                      <!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book2 -->
                      <i class="spr_book2 ico_clock"></i>
                      <span class="tit">예약 신청중</span>
                    </div>
                    <div class="right"></div>
                  </div>
                </div>
                <article class="card_item">
                  <a href="#" class="link_booking_details">
                    <div class="card_body">
                      <div class="left"></div>
                      <div class="middle">
                        <div class="card_detail">
                          <em class="booking_number">No.0000001</em>
                          <h4 class="tit">서비스명/상품명1</h4>
                          <ul class="detail">
                            <li class="item">
                              <span class="item_tit">일정</span>
                              <em class="item_dsc">
                                2000.0.00.(월)2000.0.00.(일)
                              </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">내역</span>
                              <em class="item_dsc"> 내역이 없습니다. </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">장소</span>
                              <em class="item_dsc"> 내역이 없습니다. </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">업체</span>
                              <em class="item_dsc"> 업체명이 없습니다. </em>
                            </li>
                          </ul>
                          <div class="price_summary">
                            <span class="price_tit">결제 예정금액</span>
                            <em class="price_amount">
                              <span>000,000,000</span>
                              <span class="unit">원</span>
                            </em>
                          </div>
                          <!-- [D] 예약 신청중, 예약 확정 만 취소가능, 취소 버튼 클릭 시 취소 팝업 활성화 -->
                          <div class="booking_cancel">
                            <button class="btn"><span>취소</span></button>
                          </div>
                        </div>
                      </div>
                      <div class="right"></div>
                    </div>
                    <div class="card_footer">
                      <div class="left"></div>
                      <div class="middle"></div>
                      <div class="right"></div>
                    </div>
                  </a>
                  <a
                    href="#"
                    title="공유하기"
                  ></a>
                </article>
                <article class="card_item">
                  <a href="#" class="link_booking_details">
                    <div class="card_body">
                      <div class="left"></div>
                      <div class="middle">
                        <div class="card_detail">
                          <em class="booking_number">No.0000000</em>
                          <h4 class="tit">서비스명/상품명</h4>
                          <ul class="detail">
                            <li class="item">
                              <span class="item_tit">일정</span>
                              <em class="item_dsc">
                                2000.0.00.(월)2000.0.00.(일)
                              </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">내역</span>
                              <em class="item_dsc"> 내역이 없습니다. </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">장소</span>
                              <em class="item_dsc"> 내역이 없습니다. </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">업체</span>
                              <em class="item_dsc"> 업체명이 없습니다. </em>
                            </li>
                          </ul>
                          <div class="price_summary">
                            <span class="price_tit">결제 예정금액</span>
                            <em class="price_amount">
                              <span>000,000,000</span>
                              <span class="unit">원</span>
                            </em>
                          </div>
                          <!-- [D] 예약 신청중, 예약 확정 만 취소가능, 취소 버튼 클릭 시 취소 팝업 활성화 -->
                          <div class="booking_cancel">
                            <button class="btn"><span>취소</span></button>
                          </div>
                        </div>
                      </div>
                      <div class="right"></div>
                    </div>
                    <div class="card_footer">
                      <div class="left"></div>
                      <div class="middle"></div>
                      <div class="right"></div>
                    </div>
                  </a>
                  <a
                    href="#"
                    title="공유하기"
                  ></a>
                </article>
              </li>
            </ul>
          </div>
          <!--// 내 예약 리스트 -->

          <!-- 예약 리스트 없음 -->
          <!-- <div class="err">
            <i class="spr_book ico_info_nolist"></i>
            <h1 class="tit">예약 리스트가 없습니다</h1>
          </div> -->
          <!--// 예약 리스트 없음 -->
        </div>
      </div>
      <hr />
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

    <!-- 취소 팝업창 -->
    <!-- [D] 활성화 display:block, 아니오 버튼 or 닫기 버튼 클릭 시 숨김 display:none; -->
    <div class="popup_booking_wrapper" style="display: none">
      <div class="dimm_dark" style="display: block"></div>
      <div class="popup_booking refund">
        <h1 class="pop_tit">
          <span>서비스명/상품명</span>
          <small class="sm">2000.0.00.(월)2000.0.00.(일)</small>
        </h1>
        <div class="nomember_alert">
          <p>취소하시겠습니까?</p>
        </div>
        <div class="pop_bottom_btnarea">
          <div class="btn_gray">
            <a href="#" class="btn_bottom"><span>아니오</span></a>
          </div>
          <div class="btn_green">
            <a href="#" class="btn_bottom"><span>예</span></a>
          </div>
        </div>
        <!-- 닫기 -->
        <a href="#" class="popup_btn_close" title="close">
          <i class="spr_book2 ico_cls"></i>
        </a>
        <!--// 닫기 -->
      </div>
    </div>
    <!--// 취소 팝업 -->
    
    <!-- 예약정보 카드 템플릿 -->
    
    <script type="rv-template" id="canceltItem">
                <div class="link_booking_details">
                  <div class="card_header">
                    <div class="left"></div>
                    <div class="middle">
                      <!--[D] 예약 신청중: .ico_clock, 예약확정&이용완료: .ico_check2, 취소된 예약: .ico_cancel 추가 spr_book -->
                      <i class="spr_book2 ico_cancel"></i>
                      <span class="tit">취소된 예약</span>
                    </div>
                    <div class="right"></div>
                  </div>
                </div>
                <article class="card_item">
                  <a href="#" class="link_booking_details">
                    <div class="card_body">
                      <div class="left"></div>
                      <div class="middle">
                        <div class="card_detail">
                          <em class="booking_number">No.{reservationInfoId}</em>
                          <h4 class="tit">서비스명/상품명</h4>
                          <ul class="detail">
                            <li class="item">
                              <span class="item_tit">일정</span>
                              <em class="item_dsc">
                                {createDate}
                                ~
                                {reservationDate}
                              </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">내역</span>
                              <em class="item_dsc"> 
                              
                              		{productContent}
                              
                              </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">장소</span>
                              <em class="item_dsc">
                              
                              		{placeName}
                             
                              </em>
                            </li>
                            <li class="item">
                              <span class="item_tit">업체</span>
                              <em class="item_dsc">
                              	{homepage}
                              </em>
                            </li>
                          </ul>
                          <div class="price_summary">
                            <span class="price_tit">결제 예정금액</span>
                            <em class="price_amount">
                              <span>
                              {totalPrice}
                              </span>
                              <span class="unit">원</span>
                            </em>
                          </div>
                        </div>
                      </div>
                      <div class="right"></div>
                    </div>
                    <div class="card_footer">
                      <div class="left"></div>
                      <div class="middle"></div>
                      <div class="right"></div>
                    </div>
                  </a>
                </article>
              
	</script>
  </body>
  <!--  날짜표현을 위한 moment 라이브러리를 쓰기위한 스크립트단 -->
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
  <script src="https://js.tosspayments.com/v1"></script>
  <script type="text/javascript">
  moment.locale('ko');//moment.lang은 옛날꺼라 이제안씀
  function getCookie(cookieName){
      var cookieValue=null;
      if(document.cookie){
          var array=document.cookie.split((escape(cookieName)+'='));
          if(array.length >= 2){
              var arraySub=array[1].split(';');
              cookieValue=unescape(arraySub[0]);
          }
      }
      return cookieValue;
  }
   
  function deleteCookie(cookieName){
      var temp=getCookie(cookieName);
      if(temp){
          setCookie(cookieName,temp,(new Date(1)));
      }
  }
  var logoutBtn = document.querySelector(".logout"); //로그아웃 버튼 쿼리셀렉터
  logoutBtn.addEventListener('click', function(evt){ //로그아웃 버튼에 이벤트리스너 주입
	  const checkLogout = confirm("로그아웃 하시겠습니까?"); //로그아웃 물어보는 확인창 띄우기
	  if((checkLogout == true)){ //확인창에서 예를 누르는 경우
		  console.log("logout clicked");
		  $.removeCookie('token');
		  $.removeCookie('kakao_token');
		  $.removeCookie('naver_token');
		  location.href="./";
	  }else {
		  alert("logout clicked but token is already null");
	  }
  });

  var reserveCount = document.querySelector(".spr_book2.ico_book_ss").parentNode.children[2]; //이용예정 예약정보 노드의 부모노드(<a href="#" class="link_summary_board">)의 3번째 자식노드(<span class="figure">) 쿼리셀렉터, 이용예정 상태인 예약들의 숫자
  var cancelCount = document.querySelector(".spr_book2.ico_back").parentNode.children[2]; //취소환불 예약정보 노드의 부모노드(<a href="#" class="link_summary_board">)의 3번째 자식노드(<span class="figure">) 쿼리셀렉터, 취소환불 상태인 예약들의 숫자
  var cancelBtns = document.querySelectorAll(".booking_cancel"); //모든 예약취소버튼들의 쿼리셀렉터 (배열)
  var reviewBtns = document.querySelectorAll(".booking_review"); //모든 예매자리뷰남기기버튼들의 쿼리셀렉터 (배열)
  var transacBtns = document.querySelectorAll(".booking_transaction");
  
  let transI = 0;
  for(transI; transI < transacBtns.length; transI++){ //모든 결제버튼들을 iterate
	  transacBtns[transI].addEventListener('click', function(evt){ //현재 인덱스의 취소버튼에 이벤트리스너 주입
		  var data = {} //임시로 데이터 담을 객체 생성
		  let reserveId; //임시로 사용할 reserveId 지역변수 선언
			  if( evt.target.tagName === "DIV" ) { //현재 누른 노드가 div 노드인경우
				  reserveId = evt.target.dataset.reserveid; //현재 노드의 dataset에서 reserveid를 추출하여 지역변수(reserveId)에 대입
				  data.reservationInfoId = evt.target.dataset.reserveid; //date객체에 reservationInfoId키값을 갖는 밸류값으로 이벤트 타겟의 reserveid dataset을 추출하여 대입
				} else if ( evt.target.tagName === "BUTTON" ) {//현재 누른 노드가 button 노드인경우
					reserveId = evt.target.parentElement.dataset.reserveid; //부모노드까지 가서 거기의 데이터를 추출함
					data.reservationInfoId = evt.target.parentElement.dataset.reserveid; //부모 노드까지가서 data객체에 필요한 정보를 대입함
				} else if (evt.target.tagName === "SPAN") { //현재누른 노드가 span 노드인경우
					reserveId = evt.target.parentElement.parentElement.dataset.reserveid; //부모의 부모노드까지 가서 거기의 데이터를 추출함
 					data.reservationInfoId = evt.target.parentElement.dataset.reserveid; //부모 노드까지 가서 data객체에 필요한 정보를 대입함. 왜 위에는 부모의 부모까지 찾아놓고 여기는 부모만 찾지???
				}
		  		
		  		/*
		  			1. 폼jsp와 payjsps의 템플릿은 사실상 필요없었음
		  			2. 중요한건 토스 스크립트, 토스api가 요구하는 정보만 넘길수있다면 결제정보를 리턴받는것은 문제없음
		  			3. 관련 결제정보들을 html노드를 따라서 각각 채워넣고 보내기(또는 이미있는 id를 이용하여 ajax를 실행한다면?)
		  		*/
				  var url = "/getReservationForTransaction?reserveId="+reserveId;
				  var xhr = new XMLHttpRequest(); //비동기 통신용 http-request 객체
				  xhr.open("POST", url);
				  xhr.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
				  xhr.send();
				  xhr.onreadystatechange = function (e) { //전송상태 판별
					  if (xhr.readyState !== XMLHttpRequest.DONE) return; //오류발생시 널값 리턴
					  if(xhr.status === 200) { //전송 성공해서 요청에 따른 응답을 받아왔을 경우
						  resJson = JSON.parse(xhr.responseText); //서버로부터 받아온 응답을 json 형으로 파싱하여 resJson객체에 임시저장
					      console.log(resJson); //콘솔에 받아온 json데이터 출력해보기
					      
					      let tossPayments = TossPayments("test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq");
				          let paymentData = {
				                amount: resJson.amount,
				                orderId: resJson.orderId,
				                orderName: resJson.orderName,
				                customerName: resJson.customerName,
				                successUrl: window.location.origin + "/order/transactionSuccess", // 성공시 리턴될  주소
				                failUrl: window.location.origin + "/transactionFail",  // 실패시 리턴될 주소
				          };
				          tossPayments.requestPayment('카드', paymentData);
					  	//http://localhost:8080/order/transactionSuccess?orderId=13345073d4a-6a87-44a1-940d-218167071a35&paymentKey=Kl56WYb7w4vZnjEJeQVxKRelzEoKG93PmOoBN0k12dzgRG9p&amount=2010
					  } else {
					    console.log("Error!"); //다른 예외상황에는 에러 콘솔에 띄우기
					  }
					};
	  });
  }
  
  let i = 0; //포문에 쓸 지역변수
  for(i; i < cancelBtns.length; i++){ //모든 취소버튼들을 iterate
	  cancelBtns[i].addEventListener('click', function(evt){ //현재 인덱스의 취소버튼에 이벤트리스너 주입
		  const checkCancle = confirm("정말로 취소하시겠습니까?"); //클릭했을경우의 확인창 띄우기
		  
		  var data = {} //임시로 데이터 담을 객체 생성
		  let reserveId; //임시로 사용할 reserveId 지역변수 선언
		  if(checkCancle === true){ //확인창에서 예를 눌렀을 경우
			  if( evt.target.tagName === "DIV" ) { //현재 누른 노드가 div 노드인경우
				  reserveId = evt.target.dataset.reserveid; //현재 노드의 dataset에서 reserveid를 추출하여 지역변수(reserveId)에 대입
				  data.reservationInfoId = evt.target.dataset.reserveid; //date객체에 reservationInfoId키값을 갖는 밸류값으로 이벤트 타겟의 reserveid dataset을 추출하여 대입
				} else if ( evt.target.tagName === "BUTTON" ) {//현재 누른 노드가 button 노드인경우
					reserveId = evt.target.parentElement.dataset.reserveid; //부모노드까지 가서 거기의 데이터를 추출함
					data.reservationInfoId = evt.target.parentElement.dataset.reserveid; //부모 노드까지가서 data객체에 필요한 정보를 대입함
				} else if (evt.target.tagName === "SPAN") { //현재누른 노드가 span 노드인경우
					reserveId = evt.target.parentElement.parentElement.dataset.reserveid; //부모의 부모노드까지 가서 거기의 데이터를 추출함
 					data.reservationInfoId = evt.target.parentElement.dataset.reserveid; //부모 노드까지 가서 data객체에 필요한 정보를 대입함. 왜 위에는 부모의 부모까지 찾아놓고 여기는 부모만 찾지???
				}
			  
			  evt.target.closest(".confirmed").remove(); //현재 누른 노드와 가장 가까운 예약확정 노드 삭제
			  
			  var json = JSON.stringify(data); //data에는 서버에서 현재 삭제처리된 예약을 지우기위한 reservationInfoId값이 들어있음
			  var url = "/cancleReservation/"; //예약취소처리를 위한 url
			  var xhr = new XMLHttpRequest(); //비동기 통신용 http-request 객체
			  xhr.open("PUT", url+reserveId, true); //put방식의 통신은 헤당 데이터를 수정함
			  xhr.setRequestHeader('Content-type','application/json; charset=utf-8'); //json 형식의 데이터를 전송하므로 관련설정해줌
			  xhr.send(json); //json 객체 send
			  xhr.onreadystatechange = function (e) { //전송상태 판별
				  if (xhr.readyState !== XMLHttpRequest.DONE) return; //오류발생시 널값 리턴
				  if(xhr.status === 200) { //전송 성공해서 요청에 따른 응답을 받아왔을 경우
					  var resJson = JSON.parse(xhr.responseText); //서버로부터 받아온 응답을 json 형으로 파싱하여 resJson객체에 임시저장
				      console.log(resJson); //콘솔에 받아온 json데이터 출력해보기
				      drawCancelHtml(resJson); //받아온 json데이터로 drawCancelhtml 매서드 실행
				      reserveCount.innerHTML = Number(reserveCount.innerHTML) - 1; //예약을 삭제했으므로 이용예정 예약정보 노드가 하나 줄었을것임. 예약갯수를 표시하는 노드에 접근하여 -1 해주기
				      cancelCount.innerHTML = Number(cancelCount.innerHTML) + 1; //예약을 삭제했으므로 취소된예약의 카운트는 하나 늘어날것임. 취소된예약 갯수를 표시하는 노드에 접근하여 +1 해주기
				  } else {
				    console.log("Error!"); //다른 예외상황에는 에러 콘솔에 띄우기
				  }
				};
			  
		  }
	  });
  }
  
  let idx = 0; //포문에 쓸 인덱스용 지역변수
  for(idx; idx < reviewBtns.length; idx++){ //모든 예매자리뷰남기기버튼의 갯수만큼 for문 iterate
	  reviewBtns[idx].addEventListener('click', function(evt){ //현재 인덱스의 예매자리뷰남기기버튼에 이벤트리스너 주입
	  var data = {}; //json 데이터 담을 객체
	  let reserveId; //reserveId 임시로 담을 지역변수
	  let displayInfoId; //displayInfoId 임시로담을 지역변수
	  
	  
	  if( evt.target.tagName === "DIV" ) { //만약 클릭한 타겟이 div인 경우
		  reserveId = evt.target.dataset.reserveid; //타겟의 dataset에 접근하여 reserveId 추출하여 지역변수에 담음
		} else if ( evt.target.tagName === "BUTTON" ) { //외에 타겟이 button인 경우
			reserveId = evt.target.parentElement.dataset.reserveid; //부모노드 까지 올라가서 dataset에 접근, reserveid 추출하여 대입
		} else if (evt.target.tagName === "SPAN") { //외에 타겟이 span 인 경우
			reserveId = evt.target.parentElement.parentElement.dataset.reserveid; //부모의 부모노드까지 올라가서 dataset에 접근, reserveId 추출하여 대입
		}
	  
	  if( evt.target.tagName === "DIV" ) { //만약 클릭한 타겟이 div인 경우
		  displayInfoId = evt.target.dataset.displayinfoid; //타겟의 dataset에 접근하여 reserveId 추출하여 지역변수에 담음
		} else if ( evt.target.tagName === "BUTTON" ) { //외에 타겟이 button인 경우
			displayInfoId = evt.target.parentElement.dataset.displayinfoid; //부모노드 까지 올라가서 dataset에 접근, reserveid 추출하여 대입
		} else if (evt.target.tagName === "SPAN") { //외에 타겟이 span 인 경우
			displayInfoId = evt.target.parentElement.parentElement.dataset.displayinfoid; //부모의 부모노드까지 올라가서 dataset에 접근, reserveId 추출하여 대입
		}
	  
	  //=======================================
	  //reviewCheck 이용한 리뷰창 이동or경고창 띄우기 구현
	  //그렇지않은 경우(일치하는 경우)
				var url = "/checkReview"; //checkReview url 경로지정 변수
				function sendAjax(url){ //checkReview으로 가는 비동기통신을 위한 함수
					var xhr = new XMLHttpRequest(); //비동기 통신에 쓸 데이터를 담을 객체
					xhr.open("POST", "/checkReview");	// method: POST 포스트 방식으로 서버에 보냄
					xhr.setRequestHeader("Content-Type", "text/javascript"); // Content-Type: json
					xhr.responseType = "text";		// text for json
					xhr.addEventListener("load", function () { // when success
						console.log("성공 : "+this.response); 
						if(this.response === "1"){ //if open() has been called
							location.href = "/reviewwrite/"+reserveId+"?id="+displayInfoId; //reviewwrite url로 이메일값과 함께 페이지 이동
						}else if(this.response === "0"){ //그렇지않고 open()을 제대로 call 할수없었을 경우
							alert('이미 리뷰를 작성하셨습니다!');
						}else {
							console.log("error!");
						}
					});
					
					xhr.send(reserveId); 
					
				}
				
				sendAjax(url); //실제 ajax함수 실행
	  //==============================
	  });
  }
  
  
  function drawCancelHtml(resJson) { //자바스크립트 이용한 동적 랜더링 매서드
		var producthtml = document.getElementById("canceltItem").innerHTML; //취소된 상품카드 랜더링용 템플릿
		var result = "<li class='card used cancel'>";//상품 카드의 시작단
		result += producthtml.replace("{reservationInfoId}", resJson.reservationInfoId) //li태그 시작단부터 출발해서 인자로받아온 json데이터로 템플릿에서 replace한 값들을 대입한다
		.replace("{createDate}", moment(resJson.createDate).format("YYYY.MM.DD.(ddd)")) //moment라이브러리 사용하여 json날짜 데이터 받아와서 포맷팅하기
		.replace("{reservationDate}", moment(resJson.reservationDate).format("YYYY.MM.DD.(ddd)")) //moment라이브러리 사용하여 json날짜 데이터 받아와서 포맷팅하기
		.replace("{totalPrice}", resJson.totalPrice) //상품가격 대입
		.replace("{productContent}", resJson.displayInfo[0].productContent) //상품정보 대입
		.replace("{placeName}", resJson.displayInfo[0].placeName)
		.replace("{homepage}", resJson.displayInfo[0].homepage)
		result += "</li>"; //태그의 끝단 마무리
			document.querySelector("#complete").insertAdjacentHTML("beforebegin",result); //예약 신청중 목록 바로앞(취소예약단 가장 끝)에 집어넣기
		}
  
  </script>
  
</html>
