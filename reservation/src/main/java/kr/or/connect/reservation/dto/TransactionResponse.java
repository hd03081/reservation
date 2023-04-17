package kr.or.connect.reservation.dto;

public class TransactionResponse {
	int transaction_flag;
	
	public TransactionResponse() {}

	public TransactionResponse(int transaction_flag) {
		super();
		this.transaction_flag = transaction_flag;
	}

	public int getTransaction_flag() {
		return transaction_flag;
	}

	public void setTransaction_flag(int transaction_flag) {
		this.transaction_flag = transaction_flag;
	}

	@Override
	public String toString() {
		return "TransactionResponse [transaction_flag=" + transaction_flag + "]";
	}
	
}
