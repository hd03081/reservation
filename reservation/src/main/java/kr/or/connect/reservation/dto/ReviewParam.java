package kr.or.connect.reservation.dto;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;

public class ReviewParam {
	
	// description : 리뷰남기기 Request Body 모델
	
		@ApiModelProperty(position = 1, notes = "첨부이미지")
		private MultipartFile attachedImage;
		
		@ApiModelProperty(position = 2, notes = "평") //ApiModelProperty로 설명을 달면 나중에 swagger-ui로 보면 어노테이션한 설명들이 표시가 된다
		private String comment;                     // 평
		
		@ApiModelProperty(position = 3, notes = "상품 Id")
		private int productId;                      // 상품 Id
		
		@ApiModelProperty(position = 4, notes = "예약정보 Id")
		private int reservationInfoId;              // 예약정보 Id
		
		@ApiModelProperty(position = 5, notes = "별점")
		private int score;                          // 별점

		public ReviewParam() {}

		public ReviewParam(MultipartFile attachedImage, String comment, int productId, int reservationInfoId, int score) {
			super();
			this.attachedImage = attachedImage;
			this.comment = comment;
			this.productId = productId;
			this.reservationInfoId = reservationInfoId;
			this.score = score;
		}

		public MultipartFile getAttachedImage() {
			return attachedImage;
		}

		public void setAttachedImage(MultipartFile attachedImage) {
			this.attachedImage = attachedImage;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

		public int getReservationInfoId() {
			return reservationInfoId;
		}

		public void setReservationInfoId(int reservationInfoId) {
			this.reservationInfoId = reservationInfoId;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		@Override
		public String toString() {
			return "ReviewParam [attachedImage=" + attachedImage + ", comment=" + comment + ", productId=" + productId
					+ ", reservationInfoId=" + reservationInfoId + ", score=" + score + "]";
		}

		
		
}
