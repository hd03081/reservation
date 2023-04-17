$.ajax({
		type : "GET",
		url : "/myinfo",
		success : function(data) {
			console.log(data);
			//alert(JSON.stringify(data));
			$('#myReserveBtn').attr("href", "/myreservation?reservationId="+data.userId);
			$('#myReserveBtnStr').text(data.userId+" 님 환영합니다!");
			console.log("correct jwt!");
		},
		error : function(xhr, status, error) {
			console.log("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
		}
	});