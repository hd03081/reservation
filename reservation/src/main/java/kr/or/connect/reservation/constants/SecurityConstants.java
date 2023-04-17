package kr.or.connect.reservation.constants;

public final class SecurityConstants {
	
	//사용자명과 역할을 개인 클레임에 지정하여 JWT를 생성한다
	//클레임에 지정한 값은 단지 Base64-URL 로 인코딩되므로 보안에 취약하다. 때문에 민감한 값은 클레임에 지정하면 안된다
	
	/*
	 * 비밀키를 프로퍼티 파일에서 가지고오므로 상수는 이제 사용하지 않는다
	public static final String JWT_SECRET="588506d0c604d8270ac4de9fdc520abe4779128ff5b7940d38fcd13d5e5fd07f";
	*/
	
	public static final String TOKEN_HEADER="Authorization";
	public static final String TOKEN_PREFIX="Bearer0";
	public static final String TOKEN_TYPE="JWT";
	public static final String AUTH_LOGIN_URL="/login";
	/*
	 * 개인 클레임 사용을 위해 삭제
	public static final String TOKEN_ISSUER="runeah-api";
	public static final String TOKEN_AUDIENCE="runeah-app";
	*/
}
