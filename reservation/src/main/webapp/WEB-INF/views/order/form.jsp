<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>배송정보</title>
<link rel="stylesheet" href="../css/bootstrap.min.css">
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">배송 정보</h1>
		</div>
	</div>
	<div class="container">
		<table class="table table-hover">
			<tr>
				<th>상품</th>
				<th>가격</th>
				<th>수량</th>
				<th>소계</th>
			</tr>
			<c:forEach var="data" items="${datas}">
			<tr>
				<td>${data.bookId}-${data.bookName}</td>
				<td>${data.unitPrice}</td>
				<td>${data.cnt}</td>
				<td>${data.sumPrice}</td>
			</tr>
			</c:forEach>
			<tr>
				<th></th>
				<th></th>
				<th>총액</th>
				<th>${totalPrice}</th>
				<th>
			</tr>
		</table>
		<form action="./pay.do" class="form-horizontal" method="post">
			<div class="form-group row">
				<label class="col-sm-2">주문자 이름</label>
				<div class="col-sm-3">
				<input name="orderName" type="text" class="form-control" />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2">주문자 연락처</label>
				<div class="col-sm-3">
				<input name="orderTel" type="text" class="form-control" />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2">주문자 이메일</label>
				<div class="col-sm-3">
				<input name="orderEmail" type="text" class="form-control" />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2">받는 사람 이름</label>
				<div class="col-sm-3">
				<input name="receiveName" type="text" class="form-control" />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2">받는 사람 연락처</label>
				<div class="col-sm-3">
				<input name="receiveTel" type="text" class="form-control" />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2">받는 사람 주소</label>
				<div class="col-sm-5">
				<input name="receiveAddress" type="text" class="form-control" />
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10">
					<a href="../shop_db/cart.jsp" class="btn btn-secondary" role="button">이전</a>
					<input type="submit" class="btn btn-primary" value="등록" />
				</div>
			</div>
		</form>
		</div>
</body>
</html>