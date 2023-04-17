package kr.or.connect.reservation.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CommentImage {
	private String contentType; //파일 확장자
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
	private Date createDate; //생성일
	
	private boolean deleteFlag; //삭제 여부
	private int fileId; //파일 id
	private String fileName; //파일 이름
	private int imageId; //이미지 id, reservation_user_comment_image
		
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
	private Date modifyDate; //수정일
	
	private int reservationInfoId; //예약 id
	private int reservationUserCommentId; //예야가 상품평 id
	private String saveFileName; //파일 저장 위치 이름
	
	public CommentImage(){
		
	}

	public CommentImage(String contentType, Date createDate, boolean deleteFlag, int fileId, String fileName,
			int imageId, Date modifyDate, int reservationInfoId, int reservationUserCommentId, String saveFileName) {
		super();
		this.contentType = contentType;
		this.createDate = createDate;
		this.deleteFlag = deleteFlag;
		this.fileId = fileId;
		this.fileName = fileName;
		this.imageId = imageId;
		this.modifyDate = modifyDate;
		this.reservationInfoId = reservationInfoId;
		this.reservationUserCommentId = reservationUserCommentId;
		this.saveFileName = saveFileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getReservationInfoId() {
		return reservationInfoId;
	}

	public void setReservationInfoId(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}

	public int getReservationUserCommentId() {
		return reservationUserCommentId;
	}

	public void setReservationUserCommentId(int reservationUserCommentId) {
		this.reservationUserCommentId = reservationUserCommentId;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	@Override
	public String toString() {
		return "CommentImage [contentType=" + contentType + ", createDate=" + createDate + ", deleteFlag=" + deleteFlag
				+ ", fileId=" + fileId + ", fileName=" + fileName + ", imageId=" + imageId + ", modifyDate="
				+ modifyDate + ", reservationInfoId=" + reservationInfoId + ", reservationUserCommentId="
				+ reservationUserCommentId + ", saveFileName=" + saveFileName + "]";
	}
	
}
