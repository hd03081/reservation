window.onload = function() {
    	if(ACCESS_TOKEN.length>0){
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
				
				$(".login").empty();
				$(".login").append("<div class='logined'>Hello! " + data.userId + "!</div>");
				$(".sign").empty();
				$(".sign").append("<div class='signed'>Have a nice day!</div>");
				$(".logoutSystem").css('display','table');
				saveTokenCookies();
			},
			error : function(xhr, status, error) {
				alert("code:" + xhr.status + "\n"
						+ "message:" + xhr.responseText + "\n"
						+ "error:" + error);
				}
			});
		}
		

/*


	
*/


};