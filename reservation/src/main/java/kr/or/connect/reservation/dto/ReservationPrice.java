package kr.or.connect.reservation.dto;

public class ReservationPrice {
	// description : 예약가격 내역
	private int count; // 예약 상품 수
	private int productPriceId; // 상품 가격 Id
	private int reservationInfoId; // 예약 Id
	private int reservationInfoPriceId; // 예약 가격 Id
	
	public ReservationPrice() {}

	public ReservationPrice(int count, int productPriceId, int reservationInfoId, int reservationInfoPriceId) {
		super();
		this.count = count;
		this.productPriceId = productPriceId;
		this.reservationInfoId = reservationInfoId;
		this.reservationInfoPriceId = reservationInfoPriceId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getProductPriceId() {
		return productPriceId;
	}

	public void setProductPriceId(int productPriceId) {
		this.productPriceId = productPriceId;
	}

	public int getReservationInfoId() {
		return reservationInfoId;
	}

	public void setReservationInfoId(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}

	public int getReservationInfoPriceId() {
		return reservationInfoPriceId;
	}

	public void setReservationInfoPriceId(int reservationInfoPriceId) {
		this.reservationInfoPriceId = reservationInfoPriceId;
	}

	@Override
	public String toString() {
		return "ReservationPrice [count=" + count + ", productPriceId=" + productPriceId + ", reservationInfoId="
				+ reservationInfoId + ", reservationInfoPriceId=" + reservationInfoPriceId + "]";
	}

}
