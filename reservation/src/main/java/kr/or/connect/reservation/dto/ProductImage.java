package kr.or.connect.reservation.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


import com.fasterxml.jackson.annotation.JsonFormat;

public class ProductImage { //상품 이미지 모델
	private String contentType;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
	private Date createDate;
	
	private boolean deleteFlag; //삭제 여부
	private int fileInfoId; //파일 Id
	private String fileName; //파일 이름
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
	private Date modifyDate; // 수정일
	
	private int productId; // 상품Id
	private int productImageId; //상품 이미지 id
	private String saveFileName; //파일 저장 위치 이름
	private String type; //이미지 타입 (main,thumbnail,etc)
	
	public ProductImage() {
		
	}

	public ProductImage(String contentType, Date createDate, boolean deleteFlag, int fileInfoId, String fileName,
			Date modifyDate, int productId, int productImageId, String saveFileName, String type) {
		super();
		this.contentType = contentType;
		this.createDate = createDate;
		this.deleteFlag = deleteFlag;
		this.fileInfoId = fileInfoId;
		this.fileName = fileName;
		this.modifyDate = modifyDate;
		this.productId = productId;
		this.productImageId = productImageId;
		this.saveFileName = saveFileName;
		this.type = type;
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

	public int getFileInfoId() {
		return fileInfoId;
	}

	public void setFileInfoId(int fileInfoId) {
		this.fileInfoId = fileInfoId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public int getProductImageId() {
		return productImageId;
	}

	public void setProductImageId(int productImageId) {
		this.productImageId = productImageId;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ProductImage [contentType=" + contentType + ", createDate=" + createDate + ", deleteFlag=" + deleteFlag
				+ ", fileInfoId=" + fileInfoId + ", fileName=" + fileName + ", modifyDate=" + modifyDate
				+ ", productId=" + productId + ", productImageId=" + productImageId + ", saveFileName=" + saveFileName
				+ ", type=" + type + "]";
	}
	
}
