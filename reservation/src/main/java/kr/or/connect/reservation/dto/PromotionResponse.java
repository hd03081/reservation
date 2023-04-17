package kr.or.connect.reservation.dto;

import java.util.List;

public class PromotionResponse {
	private List<Promotion> items; //Promotion 객체들을 담을 list

	public PromotionResponse() {
		
	}

	public PromotionResponse(List<Promotion> items) {
		super();
		this.items = items;
	}

	public List<Promotion> getItems() {
		return items;
	}

	public void setItems(List<Promotion> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "PromotionResponse [items=" + items + "]";
	}
	
}
