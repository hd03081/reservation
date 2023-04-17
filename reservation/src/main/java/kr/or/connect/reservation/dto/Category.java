package kr.or.connect.reservation.dto;

//작명방법:
//최대한 이클립스에서 자동생성 눌렀을때처럼 ,primary, foreign키는 주석으로 표기, erd에 없는 요소 적을때는 주석으로 표기, tostring은 자동생성

public class Category {
	private int id; //primary
	private String name; 
	private int count; //카테고리별 숫자세기위한 추가변수
	
	public Category() {
		
	}
	
	public Category(int id,String name, int count) {
		this.id=id;
		this.name=name;
		this.count=count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", count=" + count + "]";
	}
	
}
