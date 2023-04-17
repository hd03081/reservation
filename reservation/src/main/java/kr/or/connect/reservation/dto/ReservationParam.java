package kr.or.connect.reservation.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ReservationParam {
	// description : 예약하기 Request Body 모델
	@ApiModelProperty(position = 1, notes = "전시상품 Id") //ApiModelProperty로 설명을 달면 나중에 swagger-ui로 보면 어노테이션한 설명들이 표시가 된다
	private int displayInfoId; //전시상품 Id
	
	@ApiModelProperty(position = 2, notes = "예약 가격 정보")
	private List<ReservationPrice> prices; //예약 가격 정보
	
	@ApiModelProperty(position = 3, notes = "상품 Id")
	private int productId; //상품 Id
	
	@ApiModelProperty(position = 4, notes = "예약자 이메일")
	private String reservationEmail; //예약자 이메일
	
	@ApiModelProperty(position = 5, notes = "예약자명")
	private String reservationName; //예약자명
	
	@ApiModelProperty(position = 6, notes = "예약자 전화번호")
	private String reservationTelephone; //예약자 전화번호
	
	@ApiModelProperty(position = 7, notes = "예약일")
	private String reservationYearMonthDay; //예약일

	public ReservationParam() {}

	public ReservationParam(int displayInfoId, List<ReservationPrice> prices, int productId, String reservationEmail,
			String reservationName, String reservationTelephone, String reservationYearMonthDay) {
		super();
		this.displayInfoId = displayInfoId;
		this.prices = prices;
		this.productId = productId;
		this.reservationEmail = reservationEmail;
		this.reservationName = reservationName;
		this.reservationTelephone = reservationTelephone;
		this.reservationYearMonthDay = reservationYearMonthDay;
	}

	public int getDisplayInfoId() {
		return displayInfoId;
	}

	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
	}

	public List<ReservationPrice> getPrices() {
		return prices;
	}

	public void setPrices(List<ReservationPrice> prices) {
		this.prices = prices;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getReservationEmail() {
		return reservationEmail;
	}

	public void setReservationEmail(String reservationEmail) {
		this.reservationEmail = reservationEmail;
	}

	public String getReservationName() {
		return reservationName;
	}

	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}

	public String getReservationTelephone() {
		return reservationTelephone;
	}

	public void setReservationTelephone(String reservationTelephone) {
		this.reservationTelephone = reservationTelephone;
	}

	public String getReservationYearMonthDay() {
		return reservationYearMonthDay;
	}

	public void setReservationYearMonthDay(String reservationYearMonthDay) {
		this.reservationYearMonthDay = reservationYearMonthDay;
	}

	@Override
	public String toString() {
		return "ReservationParam [displayInfoId=" + displayInfoId + ", prices=" + prices + ", productId=" + productId
				+ ", reservationEmail=" + reservationEmail + ", reservationName=" + reservationName
				+ ", reservationTelephone=" + reservationTelephone + ", reservationYearMonthDay="
				+ reservationYearMonthDay + "]";
	}
	
}
