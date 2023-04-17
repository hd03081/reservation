//엑세스 토큰 저장할 변수 선언
//서버로부터 전달받은 생성된 JWT를 이 변수에 저장한다. JWT를 요청헤더에 추가하여 로그인된 사용자 정보를 조회한다
ACCESS_TOKEN = $.cookie('token');

$(document).ready(function(){
	
	//로그인
	function loginEventListener() {
	
		$("#loginBtn").on("click",function(){
			
			var userId = $("#userId");
			var password = $("#password");
			var userIdVal = userId.val();
			var passwordVal = password.val();
			
			var userObject = {userId:userIdVal,password:passwordVal};
			//alert(JSON.stringify(userObject));
			$.ajax({
				type : "POST",
				url : "/login?userId="+userIdVal+"&password="+passwordVal,
				success : function(data, textStatus, request) {
					console.log("data:"+request.getResponseHeader('Authorization'));
					//엑세스 토큰 저장
					ACCESS_TOKEN = request.getResponseHeader('Authorization');
					saveTokenCookies();
					location.href = "./";
				},
				error : function(xhr, textStatus, error) {
					alert("code:" + xhr.status + "\n"
							+ "message:" + xhr.responseText + "\n"
							+ "error:" + error);
				}
			});
		});
	
		//로그인된 사용자 정보 조회
		$("#myInfoBtn").on("click",function(){
			$.ajax({
				type : "GET",
				url : "/myinfo",
				headers : {
					"Authorization" : ACCESS_TOKEN
				},
				success : function(data) {
					console.log(data);
					//alert(JSON.stringify(data));
					console.log("correct jwt!");
				},
				error : function(xhr, status, error) {
					console.log("code:" + xhr.status + "\n"
							+ "message:" + xhr.responseText + "\n"
							+ "error:" + error);
					alert("아이디 또는 비밀번호가 올바르지 않습니다.");
				}
			});
		});
	}
	
	function loginMemberEventListener() {
		
		//등록
		$("#memberRegisterBtn").on("click",function(){
			var userObject = {
				userId : $("#memberId").val(),
				userPw : $("#memberPw").val(),
				userName : $("#memberName").val(),
				job : $("#job").val()
			};
			
			//alert(JSON.stringify(userObject));
			
			$.ajax({
				type : "POST",
				url : "/users",
				data : JSON.stringify(userObject),
				contentType : "application/json; charset=UTF-8",
				success : function() {
					alert("Created");
					$(".sign").empty();
					$(".sign").append("<div class='signed'>Thank you for Signing!<br>Please Login!</div>");
				},
				error : function(xhr, textStatus, error) {
					alert("code:" + xhr.status + "\n"
							+ "message:" + xhr.responseText + "\n"
							+ "error:" + error);
				}
			});
		});
	}
		
	function saveTokenCookies(){
		if(ACCESS_TOKEN.length>0){ //엑세스토큰이 이미 다른 뷰에서 넘어온 경우 로그인처리 및 쿠키저장
			$.cookie('token', ACCESS_TOKEN);
			console.log("쿠키토큰"+$.cookie('token'));
			
			ACCESS_TOKEN = $.cookie('token');
			console.log("쿠키토큰"+ACCESS_TOKEN);
		} else { //처음 홈페이지에 들어온 경우,로그아웃한 경우는 빈상태로 만들기
			$.removeCookie('token');
			$.cookie('token', ACCESS_TOKEN);
			console.log("쿠키토큰"+$.cookie('token'));
		}
	}
	
	//function active chain start
	loginEventListener();
});