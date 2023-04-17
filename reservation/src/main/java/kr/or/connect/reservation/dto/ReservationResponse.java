package kr.or.connect.reservation.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReservationResponse {
	//description : 예약하기 Response 모델
	private boolean cancelYn; //취소 여부
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
	private Date createDate; //예약 생성일시
	
	private int displayInfoId; //전시상품 Id
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
	private Date modifyDate; //예약 수정일시
	
	private List<ReservationPrice> prices; //예약 가격 정보
	private int productId; //상품 Id
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
	private java.sql.Date reservationDate; //예약일
	
	private String reservationEmail; //예약자 이메일
	private int reservationInfoId; //예약 Id
	private String reservationName; //예약자명
	private String reservationTelephone; //예약자 전화번호
	
	public ReservationResponse() {}

	public ReservationResponse(boolean cancelYn, Date createDate, int displayInfoId, Date modifyDate,
			List<ReservationPrice> prices, int productId, java.sql.Date reservationDate, String reservationEmail,
			int reservationInfoId, String reservationName, String reservationTelephone) {
		super();
		this.cancelYn = cancelYn;
		this.createDate = createDate;
		this.displayInfoId = displayInfoId;
		this.modifyDate = modifyDate;
		this.prices = prices;
		this.productId = productId;
		this.reservationDate = reservationDate;
		this.reservationEmail = reservationEmail;
		this.reservationInfoId = reservationInfoId;
		this.reservationName = reservationName;
		this.reservationTelephone = reservationTelephone;
	}

	public boolean isCancelYn() {
		return cancelYn;
	}

	public void setCancelYn(boolean cancelYn) {
		this.cancelYn = cancelYn;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getDisplayInfoId() {
		return displayInfoId;
	}

	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
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

	public java.sql.Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(java.sql.Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public String getReservationEmail() {
		return reservationEmail;
	}

	public void setReservationEmail(String reservationEmail) {
		this.reservationEmail = reservationEmail;
	}

	public int getReservationInfoId() {
		return reservationInfoId;
	}

	public void setReservationInfoId(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
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

	@Override
	public String toString() {
		return "ReservationResponse [cancelYn=" + cancelYn + ", createDate=" + createDate + ", displayInfoId="
				+ displayInfoId + ", modifyDate=" + modifyDate + ", prices=" + prices + ", productId=" + productId
				+ ", reservationDate=" + reservationDate + ", reservationEmail=" + reservationEmail
				+ ", reservationInfoId=" + reservationInfoId + ", reservationName=" + reservationName
				+ ", reservationTelephone=" + reservationTelephone + "]";
	}
	
}
