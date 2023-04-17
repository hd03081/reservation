package kr.or.connect.reservation.dto;

import java.util.List;

public class ReservationInfoResponse {
	// description : 예약조회 Response 모델
	private List<ReservationInfo> reservations; //예약 정보들
	private int size; //예약 수
	
	public ReservationInfoResponse() {}

	public ReservationInfoResponse(List<ReservationInfo> reservations, int size) {
		super();
		this.reservations = reservations;
		this.size = size;
	}

	public List<ReservationInfo> getReservations() {
		return reservations;
	}

	public void setReservations(List<ReservationInfo> reservations) {
		this.reservations = reservations;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "ReservationInfoResponse [reservations=" + reservations + ", size=" + size + "]";
	}
	
}
