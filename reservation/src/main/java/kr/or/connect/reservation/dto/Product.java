package kr.or.connect.reservation.dto;

public class Product {
	private int id; //primary , productid
	private int category_id; //foreign
	private String description;
	private String content;
	
	//erd에 없는것들
	private int displayInfoId;
	private String placeName;
	private int productFileId;
	
	public Product() {
		
	}
	
	public Product(int id,int category_id,String description,String content,int displayInfoId,String placeName,int productFileId) {
		this.id=id;
		this.category_id=category_id;
		this.description=description;
		this.content=content;
		this.displayInfoId=displayInfoId;
		this.placeName=placeName;
		this.productFileId=productFileId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getDisplayInfoId() {
		return displayInfoId;
	}

	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public int getProductFileId() {
		return productFileId;
	}

	public void setProductFileId(int productFileId) {
		this.productFileId = productFileId;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", category_id=" + category_id + ", description=" + description + ", content="
				+ content + ", displayInfoId=" + displayInfoId + ", placeName=" + placeName + ", productFileId="
				+ productFileId + "]";
	}

}
