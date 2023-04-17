package kr.or.connect.reservation.dto;

import java.util.List;

public class CategoryResponse {
	private List<Category> items; //Category 객체들을 담을 list

	public CategoryResponse() {}

	public CategoryResponse(List<Category> items) {
		super();
		this.items = items;
	}

	public List<Category> getItems() {
		return items;
	}

	public void setItems(List<Category> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "CategoryResponse [items=" + items + "]";
	}

}
