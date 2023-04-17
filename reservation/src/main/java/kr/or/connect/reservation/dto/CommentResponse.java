package kr.or.connect.reservation.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CommentResponse {
	private String comment;                     // 평
	private int commentId;                      // 한줄평 Id
	private CommentImage commentImage;	// 상품평 이미지
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
	private Date createDate;                    // 등록일
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
	private Date modifyDate;                    // 수정일
	private int productId;                      // 상품 Id
	private int reservationInfoId;              // 예약정보 Id
	private int score;                          // 별점
	
	public CommentResponse() {}

	public CommentResponse(String comment, int commentId, CommentImage commentImage, Date createDate,
			Date modifyDate, int productId, int reservationInfoId, int score) {
		super();
		this.comment = comment;
		this.commentId = commentId;
		this.commentImage = commentImage;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.productId = productId;
		this.reservationInfoId = reservationInfoId;
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public CommentImage getCommentImage() {
		return commentImage;
	}

	public void setCommentImage(CommentImage commentImage) {
		this.commentImage = commentImage;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
		return "CommentResponse [comment=" + comment + ", commentId=" + commentId + ", commentImage=" + commentImage
				+ ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", productId=" + productId
				+ ", reservationInfoId=" + reservationInfoId + ", score=" + score + "]";
	}

	
	
	
}
