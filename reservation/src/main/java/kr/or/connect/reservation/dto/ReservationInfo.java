package kr.or.connect.reservation.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReservationInfo {
	//descriptopn : 예약하기 Response 모델
	private boolean cancelYn; //예약 취소 여부
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
	private Date createDate; //예약 생성일시
	
	private List<DisplayInfo> displayInfo; //상품 전시(Display) 모델
	private int displayInfoId; //전시상품 Id
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
	private Date modifyDate; //예약 수정일시
	
	private int productId; //상품 id
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
	private java.sql.Date reservationDate; // 예약일
	
	private String reservationEmail; //예약자 이메일
	private int reservationInfoId; //예약 Id
	private String reservationName; //예약자명
	private String reservationTelephone; //예약자 전화번호
	private Long totalPrice; //예약한 상품 총 가격
	
	private int transactionFlag;
	
	public ReservationInfo() {}

	public ReservationInfo(boolean cancelYn, Date createDate, List<DisplayInfo> displayInfo, int displayInfoId,
			Date modifyDate, int productId, java.sql.Date reservationDate, String reservationEmail,
			int reservationInfoId, String reservationName, String reservationTelephone, Long totalPrice,
			int transactionFlag) {
		super();
		this.cancelYn = cancelYn;
		this.createDate = createDate;
		this.displayInfo = displayInfo;
		this.displayInfoId = displayInfoId;
		this.modifyDate = modifyDate;
		this.productId = productId;
		this.reservationDate = reservationDate;
		this.reservationEmail = reservationEmail;
		this.reservationInfoId = reservationInfoId;
		this.reservationName = reservationName;
		this.reservationTelephone = reservationTelephone;
		this.totalPrice = totalPrice;
		this.transactionFlag = transactionFlag;
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

	public List<DisplayInfo> getDisplayInfo() {
		return displayInfo;
	}

	public void setDisplayInfo(List<DisplayInfo> displayInfo) {
		this.displayInfo = displayInfo;
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

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTransactionFlag() {
		return transactionFlag;
	}

	public void setTransactionFlag(int transactionFlag) {
		this.transactionFlag = transactionFlag;
	}

	@Override
	public String toString() {
		return "ReservationInfo [cancelYn=" + cancelYn + ", createDate=" + createDate + ", displayInfo=" + displayInfo
				+ ", displayInfoId=" + displayInfoId + ", modifyDate=" + modifyDate + ", productId=" + productId
				+ ", reservationDate=" + reservationDate + ", reservationEmail=" + reservationEmail
				+ ", reservationInfoId=" + reservationInfoId + ", reservationName=" + reservationName
				+ ", reservationTelephone=" + reservationTelephone + ", totalPrice=" + totalPrice + ", transactionFlag="
				+ transactionFlag + "]";
	}

}
