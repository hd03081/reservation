<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결제정보</title>
<link rel="stylesheet" href="../css/bootstrap.min.css">
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">결제정보</h1>
		</div>
	</div>
	<div class="container">
		<section>
			<h1>구매하기</h1>
			<h3>구매상품:${orderProductName}</h3>
			<span>${totalPrice}원</span>
			${info.orderName}
			<p>--------------</p>
			<div><label><input type="radio" name="method" value="카드" checked/>신용카드</label></div>
			<div><label><input type="radio" name="method" value="계좌이체" checked/>계좌이체</label></div>
			<div><label><input type="radio" name="method" value="가상계좌"/>가상계좌</label></div>
			<p>-------------</p>
			<button id="payment-button">결제하기</button>
		</section>
		<script src="https://js.tosspayments.com/v1"></script>
    <script>
        var tossPayments = TossPayments("test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq");
        var button = document.getElementById("payment-button");

        // var orderId = new Date().getTime();
        //
        button.addEventListener("click", function () {
            var method = document.querySelector('input[name=method]:checked').value; // "카드" 혹은 "가상계좌"

            var paymentData = {
                amount: ${totalPrice},
                orderId: '${info.orderNo}',
                orderName: '${orderProductName}',
                customerName: '${info.orderName}',
                successUrl: window.location.origin + "/java1212_todo/market1219/order/success.do", // 성공시 리턴될  주소
                failUrl: window.location.origin + "/java1212_todo/market1219/order/fail.do",  // 실패시 리턴될 주소
            };

            if (method === '가상계좌') {
                paymentData.virtualAccountCallbackUrl = window.location.origin + '/order/virtualAccountCallback.do'
            }

            tossPayments.requestPayment(method, paymentData);
        });
    </script>
	</div>
</body>
</html>