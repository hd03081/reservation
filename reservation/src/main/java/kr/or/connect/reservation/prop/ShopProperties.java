package kr.or.connect.reservation.prop;

import org.springframework.stereotype.Component;


@Component
public class ShopProperties {
	//업로드된 파일 저장 위치값		
	private String uploadPath;
	
	private String secretKey="";

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
}
