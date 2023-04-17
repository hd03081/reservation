package kr.or.connect.reservation.dto;

public class Promotion {
	private int id;				//primary
	private int product_id;		//foreign
	private int promotionFileId; //프로모션용 이미지 url, 주의(!) 기획서 erd에는 안나와있음!
	
	public Promotion() {}

	public Promotion(int id, int product_id, int promotionFileId) {
		this.id = id;
		this.product_id = product_id;
		this.promotionFileId = promotionFileId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getPromotionFileId() {
		return promotionFileId;
	}
	public void setPromotionFileId(int promotionFileId) {
		this.promotionFileId = promotionFileId;
	}

	@Override
	public String toString() {
		return "Promotion [id=" + id + ", product_id=" + product_id + ", promotionFileId=" + promotionFileId + "]";
	}

}
