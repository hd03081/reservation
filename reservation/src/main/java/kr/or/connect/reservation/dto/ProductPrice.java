package kr.or.connect.reservation.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProductPrice {

	// description: 상품 가격
		@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
		private Date createDate;        // 생성일
		private double discountRate;    // 할인율
		@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Seoul")
		private Date modifyDate;        // 수정일
		private int price;              // 가격
		private String priceTypeName;	// 가격 타입명
		private int productId;          // 상품 Id
		private int productPriceId;     // 상품 가격 Id
		
		public ProductPrice() {}
		
		public ProductPrice(Date createDate, double discountRate, Date modifyDate, int price, String priceTypeName,
				int productId, int productPriceId) {
			super();
			this.createDate = createDate;
			this.discountRate = discountRate;
			this.modifyDate = modifyDate;
			this.price = price;
			this.priceTypeName = priceTypeName;
			this.productId = productId;
			this.productPriceId = productPriceId;
		}

		public Date getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}

		public double getDiscountRate() {
			return discountRate;
		}

		public void setDiscountRate(double discountRate) {
			this.discountRate = discountRate;
		}

		public Date getModifyDate() {
			return modifyDate;
		}

		public void setModifyDate(Date modifyDate) {
			this.modifyDate = modifyDate;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public String getPriceTypeName() {
			return priceTypeName;
		}

		public void setPriceTypeName(String priceTypeName) {
			this.priceTypeName = priceTypeName;
		}

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

		public int getProductPriceId() {
			return productPriceId;
		}

		public void setProductPriceId(int productPriceId) {
			this.productPriceId = productPriceId;
		}

		@Override
		public String toString() {
			return "ProductPrice [createDate=" + createDate + ", discountRate=" + discountRate + ", modifyDate="
					+ modifyDate + ", price=" + price + ", priceTypeName=" + priceTypeName + ", productId=" + productId
					+ ", productPriceId=" + productPriceId + "]";
		}
		
		
}
