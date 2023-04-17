package kr.or.connect.reservation.dto;

import java.util.List;

public class ProductResponse {
	private List<Product> items; //Producty 객체들을 담을 list
	private int count; //리스트의 전체 상품개수 계산을 위한 변수
	
	public ProductResponse() {}

	public ProductResponse(List<Product> items,int count) {
		super();
		this.items = items;
		this.count = count;
	}

	public List<Product> getItems() {
		return items;
	}

	public void setItems(List<Product> items) {
		this.items = items;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ProductResponse [items=" + items + ", count=" + count + "]";
	}
	
}
