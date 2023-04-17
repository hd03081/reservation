package kr.or.connect.reservation.dto;

public class OrderDataDTO {
	private int num;
	private String orderNo;
	private int cartId;
	private String bookId;
	private String bookName;
	private int unitPrice;
	private int cnt;
	private int sumPrice;
	
	public OrderDataDTO() {
		super();
	}
	
	public OrderDataDTO(int num, String orderNo, int cartId, String bookId, String bookName, int unitPrice, int cnt,
			int sumPrice) {
		super();
		this.num = num;
		this.orderNo = orderNo;
		this.cartId = cartId;
		this.bookId = bookId;
		this.bookName = bookName;
		this.unitPrice = unitPrice;
		this.cnt = cnt;
		this.sumPrice = sumPrice;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public int getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}

	@Override
	public String toString() {
		return "OrderDataDTO [num=" + num + ", orderNo=" + orderNo + ", cartId=" + cartId + ", bookId=" + bookId
				+ ", bookName=" + bookName + ", unitPrice=" + unitPrice + ", cnt=" + cnt + ", sumPrice=" + sumPrice
				+ "]";
	}

}
