<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
<title>전시 예약</title>
<link rel="shortcut icon" href="#">
<link href="css/style.css?ver=3.5" rel="stylesheet" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.js"></script>
</head>
<body>
	<div class="container">
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
    <div class="contents">
    <div class="promotion_wrap">
      <div class="promotion">
      	<ul class="slideshow">
		<c:forEach items="${promotion_items}" var="Promotion">
		
			<li><img alt=${Promotion.product_id } 
			src="${pageContext.request.contextPath}/${Promotion.promotionFileId}"/></li>
			
		</c:forEach>
		</ul>
      </div>
	</div>
			<div class="section_event_tab">
			<ul class="categoryTabList categoryTabArr">
			
				<li class="item" data-category="0"><a class="anchor active">
						<span>전체리스트</span>
				</a></li>
								<!-- varStatus : 상태표시용 변수, data- : html에서 지원하는 커스텀 속성 -->

					<c:forEach var="category" items="${category_items}" varStatus="status">
						<li class="item" data-category=<c:out value="${category.id }" />>
						<a class="anchor"> <span><c:out value="${category.name }" /></span></a></li>
					</c:forEach>
			
			</ul>
			</div>
			
			
		<div class="section_event_lst">	
			<!-- 전체리스트 상품갯수 -->
        	<p class="allproductcount_p">
				바로 예매 가능한 행사가 <span class="pink">${product_count}개</span> 있습니다
			</p>
			
        <div class="productlistdiv">
       		
        <!-- varStatus 상태변수를 이용하여 가져온 상품정보의 순서(index는 0부터의 순서를 반환함)를 기준으로 홀수,짝수로 ul태그를 배치함 -->
      			   <div class="left">
						<c:forEach var="product_item" items="${product_items}" varStatus="status">
						<c:if test="${status.index%2==0}">
								<li class="item odd"><a href="detail?id=${product_item.id}" class="item_book">
										<div class="item_preview">
											<img alt=<c:out value="${product_item.content }" />class="img_thumb" src="${product_item.productFileId}" />
											<span class="img_border"></span>
										</div>
										<div class="event_txt">
											<h4 class="event_txt_tit">
												<span><c:out value="${product_item.description }" /></span><br><small class="sm"><c:out value="${product_item.placeName }" /></small>
											</h4>
											<p class="event_txt_dsc"><c:out value="${product_item.content }" /></p>
										</div>
								</a></li>
							</c:if>
						</c:forEach>
					</div>
					<div class="right">
						<c:forEach var="product_item" items="${product_items}" varStatus="status">
							<c:if test="${status.index%2!=0}">
								<li class="item even"><a href="detail?id=${product_item.id}" class="item_book">
										<div class="item_preview">
											<img alt=<c:out value="${product_item.content }" />class="img_thumb" src="${product_item.productFileId}" />
											<span class="img_border"></span>
										</div>
										<div class="event_txt">
											<h4 class="event_txt_tit">
												<span><c:out value="${product_item.description }" /></span><br><small class="sm"><c:out value="${product_item.placeName }" /></small>
											</h4>
											<p class="event_txt_dsc"><c:out value="${product_item.content }" /></p>
										</div>
								</a></li>
							</c:if>
						</c:forEach>
					</div>
        
      <div class="more">
		<button class="btn">
			<span>더보기</span>
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

<script type="text/javascript" src="js/promotionAnimeTest.js" defer></script>
<script type="text/javascript" src="js/headerSubModule.js" defer></script>
	
<script>
	function sendAjax(url,clickedName){//categorytab 과 더보기 기능의 비동기통신을 위한 ajax 스크립트. url은 사용자가 클릭한 탭에따라 서블릿에서 알맞은 카테고리id의 데이터를 그리도록 가리킴
										//clickedName 은 탭ui를 위한것인지 더보기버튼을 위한 것인지 구별하기위한 변수로 이벤트리스너가 해당 변수를 읽어들여서 알맞은 데이터를 받아들이고
										//조건문의 매서드들을 활용하여 최종적으로 html에 비동기식으로 렌더링하게됨
		var httpRequest = new XMLHttpRequest(); //비동기통신을 위한 xmlhttprequest 객체 생성
		httpRequest.open("GET", url);	// get방식 사용, 매개변수로 받은 url쪽으로 향함
		httpRequest.setRequestHeader("Content-Type", "application/json"); // 주고받을 데이터의 타입을 json으로 정의
		httpRequest.responseType = "text";		// json으로 받기위해서 text형식으로 정의
		httpRequest.addEventListener("load", function () { // "로드에 성공했을 경우" 발동하는 이벤트리스너
			console.log(this); //여기서 this는 가리켜진 url의 서블릿에서 받은 데이터의 객체임(httpRequest), 아래에서 json으로 파싱되어서 make/addHtmlcontent 매서드들에서 쓰임 
			if( clickedName === "tab" ) 	{	makeHtmlContent(url, this);	  }  // tabui기능일 경우 makehtmlContent 매서드 실행
			else if( clickedName === "more" ) {	addhtmlContent(url, this);  } // 더보기버튼기능일경우 addhtmlcontent 매서드 실행
		});
		httpRequest.send(); //올바른 경로의 데이터를 알맞은 기능, 타입으로 받았으면 send 한다
	}
		var cateogorytabmenu = document.querySelector(".categoryTabList"); //카테고리탭기능을 위한 http 노드를 가리킴
		cateogorytabmenu.addEventListener("click", function(evt) { //해당 노드에 이벤트리스너(click)를 주입한다
			var categoryId; //카테고리id를 위한 변수 선언, 만약 이벤트가 발생한 노드(evt.target) 의 태그이름이 리스트(LI) 일 경우엔
			if( evt.target.tagName === "LI" ) {
				categoryId = evt.target.dataset.category; //dataset : 문자열의 형태로 데이터를 저장하는 html의 방법, dom객체를 이용하여 자바스크립트에서 데이터 속성을 조작가능+이벤트 타겟의 데이터 속성 중 category를 categoryId 로 정의함. html단의 카테고리탭 부분의 jstl반복문에서 이미 정의하였는데 + data-category=<c:out value="${category.id}" /> 라고 써서 data-category 속성에 데이터베이스의 카테고리id 값을 대입함
			} else if ( evt.target.tagName === "UL" ) { //추가로, 이벤트 타겟의 태그이름이 UL일 경우엔
				categoryId = evt.target.firstChild.dataset.category; //이벤트 타겟 하위의 firstChild 노드의 데이터 속성 중 category를 categoryId로 정의함 
			} else if ( evt.target.tagName === "A" ) { //추가로, 이벤트 타겟의 태그이름이 A일 경우엔
				categoryId = evt.target.parentElement.dataset.category; //이벤트 타겟 상위의 parentElement 노드의 데이터 속성 중 category를 categoryId로 정의함 
			} else if ( evt.target.tagName === "SPAN" ) { //추가로, 이벤트 타겟의 태그이름이 SPAN일 경우엔
				categoryId = evt.target.parentElement.parentElement.dataset.category; //이벤트 타겟 상위, 상위의 parentELement 노드의 데이터 속성을 조작함
			}
			//document.getElementById("start").value = 0;
			sendAjax("/maincontents?categoryId="+categoryId, "tab"); //탭ui를 위한 ajax 함수 실행 및 매개변수 지정+이벤트리스너, 자바스크립트 매서드들로 읽어들인 "유저가 클릭한 카태고리탭의 categoryId" 값을 ajax 매서드의 url매개변수에 포함시켜서 해당 카테고리id의 데이터를 서버에서 접근하도록 가리킴 
		});
		
		var morebtn = document.querySelector(".more"); //더보기 버튼이 있는 노드를 가리키는 구문
		
		morebtn.addEventListener("click", function() { //더보기 버튼에 이벤트 리스너 주입
			var categoryId; //카테고리id를 위한 변수 선언
			var startNum; //상품번호 인덱싱을 위한 startNum변수
			var activeEle = document.querySelector(".active"); //anchor active(최초에는 '전체리스트' 탭)를 가리킴
			console.log(activeEle); //anchor active 를 잘 가리키는지 로그확인
			categoryId = activeEle.parentElement.dataset.category; //activeEle가 가리키는 anchor active 노드의 parentElement 노드의 dataset을 확인
																   //해당 노드의 데이터 속성 category의 현재 값을 categoryId 변수에 대입
			console.log(categoryId); //achor active parent 노드로부터 categoryId를 잘 얻어왔는지 로그확인
			startNum = document.querySelectorAll(".item_preview").length; //재생성되는 LI태그 안의 item_preview 클래스 전체를 가리키고 그 길이를 구하여 startNum에 대입
																		  //querySelectorAll은 해당하는 모든 구문을 배열로 가져오기때문에 .length로 전체개수를 구할수있음
			console.log(startNum); //staryNum이 잘 계산되었는지 로그확인
			sendAjax("/maincontents?categoryId="+categoryId+"&start="+startNum, "more"); //더보기버튼을 위한 ajax 함수 실행 및 매개변수 지정
																						 		  	 //이벤트리스너, 매서드로 받은 "현재 노드의 categoryId와 startNum"값을
																						 		  	 //ajax 메서드의 url매개변수에 포함시켜서 해당 카테고리id의 데이터에 접근
		});
		
		function makeHtmlContent(url,res) { //tab ui를 위한 매서드
			// debugger;
			/* 1) 결과 데이터 값 json으로 받기 */
			var resJson = JSON.parse(res.responseText); //매개변수로 받아온 res값을 JSON으로 파싱

			/* 2) 결과 - HTML 그려주기 */
			// 1. 카테고리 변경
			var categoryId = resJson.categoryId //새로 렌더링하기위한 카테고리Id 변수 선언 및 resJson으로 정의 
			drawCategoryHtml(resJson,categoryId); //drawCategoryhtml은 실제로 html 노드에 접근하여 새로 렌더링하는 매서드임. 해당매서드로 카테고리id값과 res를 보내서 카테고리 탭 변경
			
			// 2. 상품 리스트
			Array.from(document.querySelector(".productlistdiv").querySelectorAll("li")).forEach( v => {	v.remove();	}); // 기존 상품 Element 모두 제거
			drawProductHtml(resJson); //drawProductHtml은 상품리스트부분의 데이터를 새로 그려주는 매서드임. 카테고리별 상품 화면에 추가(기본 4개)
			
			// 3. product 갯수
			document.querySelector(".pink").innerHTML = resJson.totalProductCount+"개"; //allproductcount 단의 노드에 접근하여 그 내용을 다시 렌더링함.
																					   //resJson 객체의 totalProductCount를 이용하여 상품총갯수를 계산
																					   //map.put("totalProductCount", productResponse.getCount());
																					   //컨트롤러에서 맵에 데이터베이스>>service로부터 가져온 상품갯수를 담고 그 접근자를 설정했었음
			
			// 4. 더보기 탭
			if( resJson.morebtn ) { //만약 resJson 맵객체에 morebtn 접근자가 존재할경우엔 (뒤에 더 표시할 자료가 있는경우)
				document.querySelector(".more").style.display = "block"; //더보기버튼 노드에 접근해여 해당 버튼의 스타일을 display : block 즉 보이게 한다
			} else {
				document.querySelector(".more").style.display = "none"; //그 외에 morebtn접근자가 존재하지 않는다면 버튼의 스타일을 display : none 즉 안보이게 한다
			}
		}
		
		// Category HTML 동적 생성
		function drawCategoryHtml(resJson,categoryId) { //매개변수로 받은 데이터에 따라 알맞은 카테고리탭부분 노드를 새로이 랜더링함
			var categoryhtml = document.getElementById("categoryItem").innerHTML; //DOM객체를 이용하여 스크립트태그로 숨겨놓은 템플릿에 접근하여 categoryhtml 변수에 대입한다
			var categoryResult = document.getElementById("totalCategoryItem").innerHTML; //totalCategoryItem 템플릿에 접근하여 categoryResult 변수에 대입한다
			
			for(var i=0; i<resJson.categories.length; i++) { //카테고리별 id, name, count에 대한 테이블의 첫번째 요소부터 맵객체의 length만큼 iterate를 하며 템플릿의 내용을 replace함 
				categoryResult += categoryhtml.replace("{categoryId}", resJson.categories[i].id) //템플릿의"{categoryId}" 부분을 맵객체의 categories객체의 요소로 교체
									.replace("{className}","anchor") //템플릿의 "{className}" 부분은 문자열 "anchor"로 교체
									.replace("{categoryName}", resJson.categories[i].name) //템플릿의 "{categoryName}" 부분은 맵객체 categories의 name요소로 교체
				
			}
			document.querySelector(".categoryTabList").innerHTML = categoryResult; //총갯수 표시노드에 접근하여 그 값을 템플릿값으로 교체함
			
			Array.from(document.querySelector(".categoryTabArr").querySelectorAll("li")).forEach( a => { //Array.from 함수는 배열과 유사한 객체(map,set,또는 length속성을 가지는 객체들)를 
																									  //얕은복사(shallow copy)하여 새로운 배열 객체를 만들어준다.
																									  //이 구문에서는 querySelectorAll로  li태그를 가진 모든 노드들을 배열화하는데 쓰인다
																									  //a는 배열의 원소들을 가리킨다.각각의 배열의 노드의 첫번째 자식노드를찾는다
				a.firstElementChild.className = (a.dataset.category == categoryId) ? "anchor active" : "anchor"; 
			//삼항연산자를 이용하여 각각의 배열의 노드(a)의 첫번째 자식노드를찾는다. a.dataset.category == categoryId이 참이면  a.firstElementChild.className = "anchor active"
																								//거짓이면  a.firstElementChild.className = "anchor"
			//클래스네임 속성의 데이터를 anchor와 anchor active 로 나누어서 현재 활성화 된것만 active를 주고 나머지는 안주게만드는 매서드
			});
		}
		
		
		function drawProductHtml(resJson) { //매개변수로 받은 맵객체의 리스트를 돌면서 상품리스트부분 노드를 새로이 랜더링함
			var producthtml = document.getElementById("productItem").innerHTML;//DOM객체를 이용하여 스크립트태그로 숨겨놓은 템플릿에 접근하여 producthtml 변수에 대입한다
			console.log("프로덕트리스트 길이 : "+resJson.product_list.length);//상품리스트 갯수계산을 위한 로그
			var result=""; //= "<ul class='oddeven_ul odd'>";//새로운 ul생성을 위한 result 변수
				for(var i=0; i<resJson.product_list.length; i+=2) { //ul 안에다 담을 짝수번 html 정보들을 반복문으로 입력
					
						result += producthtml.replace("{id}", resJson.product_list[i].displayInfoId) //product dto 접근
						.replace("{alt}", resJson.product_list[i].content)
						.replace("{content}", resJson.product_list[i].content)
						.replace("{description}", resJson.product_list[i].description)
						.replace("{placeName}", resJson.product_list[i].placeName)
						.replace("{productFileId}", resJson.product_list[i].productFileId)
						.replace("{item}", "item odd");
				}
				//result += "</ul>";//마지막에 ul 태그 닫기
				document.querySelector(".left").insertAdjacentHTML("beforeend",result); //엘리먼트 앞에.result문자열 삽입
				result="";// = "<ul class='oddeven_ul even'>"; //다음 for문 실행 전에 result 변수 재정의
				for(var i=1; i<resJson.product_list.length; i+=2) {//1,3,5 홀수번 데이터들을 반복문으로 입력
					
					result += producthtml.replace("{id}", resJson.product_list[i].displayInfoId) //product dto 접근
					.replace("{alt}", resJson.product_list[i].content)
					.replace("{content}", resJson.product_list[i].content)
					.replace("{description}", resJson.product_list[i].description)
					.replace("{placeName}", resJson.product_list[i].placeName)
					.replace("{productFileId}", resJson.product_list[i].productFileId)
					.replace("{item}", "item even");
				}
				//result += "</ul>";
				document.querySelector(".right").insertAdjacentHTML("beforeend",result); //엘리먼트 앞에().result문자열 삽입
			}
		
		function addhtmlContent(url, res) { //더보기버튼을 위한 매서드
			var resJson = JSON.parse(res.responseText);
			drawProductHtml(resJson); // 카테고리별 상품 화면에 추가(기본 4개)
			
			if( resJson.morebtn ) {
				document.querySelector(".more").style.display = "block"; //남아있는 데이터가 있는경우 더보기 버튼 보이기
			} else {
				document.querySelector(".more").style.display = "none"; //남아있는 데이터가 없는경우 더보기 버튼 숨기기
			}
		}
		//아래 스크립트는 템플릿을 위한 html 정보들임
	</script>

	<script type="totalCategory-template" id="totalCategoryItem">
	<li class="item" data-category=0>
		<a class="anchor"> <span>전체리스트</span></a>
	</li>
	</script>
	<script type="rv-template" id="categoryItem">
	<li class="item" data-category={categoryId}>
	  	<a class="{className}"> <span>{categoryName}</span></a>
	</li>
	</script>


	<script type="rv-template" id="productItem">
    <li class="{item}">
       <a href="detail?id={id}" class="item_book">
            <div class="item_preview">
               <img alt="{alt}" class="img_thumb" src="{productFileId}">
               <span class="img_border"></span>
            </div>
            <div class="event_txt">
                <h4 class="event_txt_tit"><span>{description}</span><br><small class="sm">{placeName}</small></h4>
                <p class="event_txt_dsc">{content}</p>
            </div>
        </a>
    </li>
	</script>

</body>

</html>