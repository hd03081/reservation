<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
<title>전시 예약</title>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
<script>
	src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.min.js"
	integrity="sha512-fujQJs+fkj7+Az7XKDxMLbVuuaeljjqMQDh3TAI7nYKQMQhEztrmyuex6hlnRuttjXJ9BFvnl4r/t8r8L6gFfA=="
	crossorigin="anonymous"</script>
	<link rel="shortcut icon" href="#">
<link href="../css/style.css?ver=3.5" rel="stylesheet" />
</head>
<body>
	<div id="container">
		<!-- [D] 예약하기로 들어오면 header에 fade 클래스 추가로 숨김 -->
    	
		<div class="ct">
			<div class="wrap_review_list">
				<div class="review_header">
					<div class="top_title gr">
						<a href="../detail?id=${displayInfoId}" class="btn_back" title="이전 화면으로 이동"> <span
							class="fn fn-backward1">←</span>
						</a>
						<h2>
							<a class="title" href="#">${displayInfo.productDescription }</a>
						</h2>
					</div>
				</div>
				<h3 class="title_h3">예매자 한줄평</h3>
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
								<span class="underline"></span>
								<ul class="list_short_review">
								<c:set var="doneLoop" value="0" />

										<c:forEach var="commentsList" items="${comments}"
											varStatus="status">
											<!-- 댓글이미지가 있는경우 -->
												<c:if test="${!empty commentsList.commentImages}">
													<li class="list_item">
														<div>
															<div class="review_area">
																<div class="thumb_area">
																	<c:forEach var="commentImages"
																		items="${commentsList.commentImages}" begin="0"
																		end="0">
																		<a href="" class="thumb" title="이미지 크게 보기"> <img
																			width="90" height="90" class="img_vertical_top"
																			src="<c:out value="../${commentImages.fileId} "/>"
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
																		class="name">${commentsList.reservationName }</span> <span
																		class="date">${commentsList.createDate }방문</span>
																</div>
															</div>
														</div>
													</li>
													<c:set var="imageIndex" value="${status.index }" />
													<c:set var="doneLoop" value="${doneLoop + 1 }" />
												</c:if>
												<!-- 댓글이미지가 없는경우 -->
												<c:if test="${imageIndex ne status.index}">
													<li class="list_item">
														<div>
															<div class="review_area no_img">
															<span class="product">${displayInfo.productDescription }</span>
																<h4 class="resoc_name"></h4>
																<p class="review">${commentsList.comment }</p>
															</div>
															<div class="info_area">
																<div class="review_info">
																	<span class="grade">${commentsList.score }</span> <span
																		class="name">${commentsList.reservationName }</span> <span
																		class="date">${commentsList.createDate }방문</span>
																</div>
															</div>
														</div>
													</li>
													<c:set var="doneLoop" value="${doneLoop + 1 }" />
												</c:if>
												
										</c:forEach>

							</ul>
			</div>
			<p class="guide">
							<i class="spr_book2 ico_bell"></i> <span>이용자가 남긴 평가입니다.</span>
						</p>
			<hr>
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
</body>
</html>