package kr.or.connect.reservation.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.or.connect.reservation.dao.MemberDao;
import kr.or.connect.reservation.dto.CategoryResponse;
import kr.or.connect.reservation.dto.CustomUser;
import kr.or.connect.reservation.dto.Member;
import kr.or.connect.reservation.dto.MemberAuth;
import kr.or.connect.reservation.dto.ProductResponse;
import kr.or.connect.reservation.dto.PromotionResponse;
import kr.or.connect.reservation.security.encoder.CustomNoOpPasswordEncoder;
import kr.or.connect.reservation.service.ReservationMainService;

@Controller
@RequestMapping(path="") //path가 없을때 기본화면일때의 처리(?)
public class ReservationMainController {
	@Autowired
	ReservationMainService mainService;
	@Autowired
	MemberDao memberDao;
	private CustomNoOpPasswordEncoder encoder = new CustomNoOpPasswordEncoder();
	@GetMapping("")
	public String mainPage(@RequestParam(name="categoryId",required=false,defaultValue="0") int categoryId, @RequestParam(name="start",required=false,defaultValue="0") int start, ModelMap model) { //모델맵 복습필요
		PromotionResponse promotion_items = mainService.getPromotionItems(); //Promotion의 컨트롤을 위한 Response객체
		CategoryResponse category_items = mainService.getCategoryItems();
		ProductResponse product_items = mainService.getProductItems(start);
		
		Map<String, Object> map = new HashMap<>(); //해쉬맵의 용도랑 활용방법을 좀 알아봐야겠음. 이거 어떻게 쓰는거임?
		
		System.out.println("메인컨트롤러 실행"); //콘솔에 로그찍기
		
		map.put("promotion_items", promotion_items.getItems()); //실제 jsp 페이지에서 사용하게될 참조할수있는 리스트 호출부호, promotion 컨트롤
		map.put("category_items", category_items.getItems()); //category 컨트롤
		map.put("product_items", product_items.getItems()); //product 리스트
		map.put("product_count", product_items.getCount()); //product 갯수
		model.addAllAttributes(map);
		//return "mainPage";
		return "Test1";
	}
	
	//메인컨텐츠, 처음화면을 열었을땐 위의 컨트롤러가 쓰이지만, 이후 비동기통신이 필요하거나 클라이언트의 행동에 따라 데이터가 동적으로 변화하여야할때를 위한 컨트롤러
	@GetMapping(path="/maincontents", produces = "application/json; charset=utf-8") //. GetMapping의 produces 속성을 설정하면 브라우저가 응답이 어떤 내용인지 인식할 수 있다. 지금은 json방식을 쓰기때문에 json으로 설정
	@ResponseBody //이 어노테이션을 사용함으로써 HTTP response body에 데이터를 그대로 담아서 전송할 수 있다. 이 어노테이션이 없다면 Spring은 View resolver를 통해 view 파일을 찾으려 할 것
	public Map<String,Object> content(@RequestParam(name="categoryId", required=false, defaultValue="0") Integer categoryId, @RequestParam(name="start", required=false, defaultValue="0") int start) {
		//String,Object를 키 밸류료 갖는 Map인 content는 매개변수로 categoryId 와 한번에 출력할 데이터의 갯수의 시작숫자(start)를 클라이언트로부터 요청받는다
		System.out.println("카테고리 아이디 : "+categoryId);
		System.out.println("시작 숫자 : "+start);

		ProductResponse productResponse = new ProductResponse(); //product객체들을 처리하기위한 ProductResponse 클래스 객체 선언및 정의, 이건 출력할 데이터들의 시작지점의 데이터를 위한 것이다
		ProductResponse checkProductResponse = new ProductResponse(); //출력을 끊는 지점의 데이터를 가리키기위함

		if(categoryId == 0) //만약 카테고리아이디가 0일경우 즉 전체리스트를 출력해야하는 경우이다
		{
			productResponse = mainService.getProductItems(start); //전체를 출력하면 되므로 전체출력을위한 dao 호출
			checkProductResponse = mainService.getProductItems(start + 4); //check,한번에 4개씩 출력하는것이기 때문에 start+4 지점에서 끊음
		}
		else { //그렇지 않고 카테고리아이디를 클라이언트에서 받아온 경우, 즉 전시, 연극 등 어떤카테고리인지 알아서 해당 카테고리의 데이터리스트를 출력해야하는 경우이다
			productResponse = mainService.getProductItems(categoryId, start); //카테고리별 전체리스트를 출력하면 되므로 해당 dao 호출
			checkProductResponse = mainService.getProductItems(categoryId, start + 4 ); //check,한번에 4개씩 출력, start+4지점에서 끊기
		}
		
		CategoryResponse categories = mainService.getCategoryItems(); //키테고리별 상품갯수도 비동기통신or동적으로 출력해야함, 그러므로 카테고리 테이블의 데이터를 함수에서 이용하기위해 리스트를 받아옴

		Map<String, Object> map = new HashMap<>(); //카테고리별 상품갯수를 담기위한 해쉬맵 생성
		map.put("categoryId", categoryId); //맵에 categoryId를 담음
		map.put("totalProductCount", productResponse.getCount()); //맵에 데이터베이스>>service로부터 가져온 상품갯수를 담음
		if(checkProductResponse.getItems().isEmpty()) { //만약 start+4 지점에서 끊었는데 이미 모든 데이터를 다 출력해서 비어있는 경우에는
			map.put("categories", categories.getItems()); // 맵에 카테고리객체(카테고리id,이름,해당카테고리총상품갯수)를 담음
			map.put("product_list", productResponse.getItems()); //맵에 상품객체(product에 대한 데이터들)를 담음
		}
		else { //그렇치 않고 start+4 지점에서 끊었는데 아직 뒤에 더 데이터가 남아있는 경우에는
			map.put("categories", categories.getItems()); // 맵에 카테고리객체(카테고리id,이름,해당카테고리총상품갯수)를 담음, 여기까진 위와 같음
			map.put("product_list", productResponse.getItems()); //맵에 상품객체(product에 대한 데이터들)를 담음, 여기까지도 똑같음
			map.put("morebtn", "morebtn"); //이후의 데이터들을 더 출력하기위한 '더보기'버튼을 위한 정보를 맵에 담는다.
		}
		
		return map; //메인페이지에 필요한 모든 정보를 다 담았으면 맵객체를 리턴한다.
		
	}
	
	@GetMapping(path = "/logout")
	public String logout(HttpSession session) {
		session.setMaxInactiveInterval(0);
		session.invalidate();
		System.out.println("logout");
		return "redirect:./";
		
	}
	
	@GetMapping(path = "/bookinglogin")
	public String bookinglogin(){
		return "bookinglogin";
	}
	
	@Transactional
	@PostMapping(path = "/registerConfirm")
	public void registerConfirm(HttpSession tempSession, HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException{
		/*
		 * 모든 폼값 받아서 데이터베이스에 집어넣기 ㅇ
		 * sendRedirect 메인페이지로ㅇ 
		 * 비밀번호 암호화, 기존것 참고 ㅇ
		 * users 테이블은 이제 안쓰고 member 테이블에만 유저정보를 담을거임 ㅇ
		 * 기존 인증시스템은 username 필드를 기반으로 설정되어있음. 이를 user_id로 일원화시킬 필요성 ㅇ
		 * authorities 테이블에 user_no만 넣을수 있다면 member_auth 테이블도 아마 필요없을것임  ㅇ              
		 * 결제모듈 장착해보기
		 * 카카오로그인 장착ㅇ
		 * 네이버로그인 장착
		 * 쿠키 생성시 헤더 ㅇ                                                                                                                                                                                                                                                                                                                                                                                    
		 */
		String userId = req.getParameter("memberId");
		String userPw = req.getParameter("pw");
		userPw = encoder.encode(userPw);
		String email = req.getParameter("email");
		String zipcode = req.getParameter("zipCode");
		String address01 = req.getParameter("address01");
		String address02 = req.getParameter("address02");
		
		Member member = new Member(userId, userPw, email, zipcode, address01, address02);
		member.setUserName(userId);
		member.setJob("def");
		
		if(memberDao.isMemberId(userId)||memberDao.isMemberEmail(email)) {
			System.out.println("실패! 중복된 아이디거나 이메일입니다");
			resp.getWriter().write("<script>alert('실패! 중복된 아이디거나 이메일입니다')</script>");
			resp.sendRedirect("./");
		}else {
			try {
				memberDao.create(member);
				MemberAuth memberAuth = new MemberAuth(memberDao.readByUserId(userId).getUserNo(), userId,"ROLE_MEMBER");
				memberDao.createAuth(memberAuth);
				System.out.println("가입 성공!");
				resp.sendRedirect("./");
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	@GetMapping(path = "/register")
	public String register(){
		return "register";
	}
	
	@PostMapping(path = "/memberDuplCheck")
	public void memberDuplCheck(HttpSession tempSession, @RequestParam(name="memberId", required=true)String memberId, HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
		if(memberDao.isMemberId(memberId)) {
			resp.getWriter().write("dupe");
		}else {
			resp.getWriter().write("nodupe");
		}
	}
	
	@PostMapping(path = "/emailAuthCheck")
	public void emailAuthCheck(HttpSession tempSession, @RequestParam(name="email", required=true)String email,@RequestParam(name="authKey", required=true)String authKey,HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(((String)tempSession.getAttribute(email)).equals(authKey)) {
			resp.getWriter().write("success");
		}else {
			System.out.println("이메일이 일치하지 않음");
			resp.getWriter().write("fail");
		}
	}
	
	@PostMapping(path = "/emailAuth")
	public void sendMail(HttpSession tempSession, @RequestParam(name="email", required=true)String email,HttpServletRequest req, HttpServletResponse resp) throws IOException {
		/* 인증코드 생성 */
		Random random = new Random();
		String authKey = "";
		for(int i=0; i<3; i++) {
			// 랜덤 알파벳 생성
			int index = random.nextInt(25) + 65;
			authKey += (char)index;
		}
		// 4자리 랜덤 정수 생성
		int numIndex = random.nextInt(9999) + 1000;
		authKey += numIndex;
		
		/* 이메일 보내기 */
		// mail server 설정
		String host = "smtp.googlemail.com";
		String user = "hd03081@gmail.com"; // 자신의 구글 계정
		String password = "lnathbopnwxtjelr";// 구글 메일 앱 비밀번호 16자리
		
		// 메일 받을 주소
		String to_email = email;

		// SMTP 서버 정보를 설정한다.
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", 465);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.ssl.enable", "true");
        
		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		MimeMessage msg = new MimeMessage(session);
		
		// email 전송
		try {
			msg.setFrom(new InternetAddress(user));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to_email));

			// 메일 제목
			msg.setSubject("TEST RESERVATION 이메일 인증 번호 전송", "UTF-8");
			// 메일 내용
			msg.setText("인증 번호 :" + authKey);

			Transport.send(msg);
			System.out.println("이메일 전송 : " + authKey);

		} catch (AddressException e) { 
			e.printStackTrace(); 
		} catch (MessagingException e) { 
			e.printStackTrace(); 
		}
		System.out.println("authKey"+authKey);
		try {
			//세션에 임시로 authKey저장
			tempSession.setAttribute(to_email, authKey);
		}catch(Exception e) {
			e.printStackTrace();
			resp.getWriter().write("fail");
		}
		resp.getWriter().write("success");
	}
	
	//Authentication 객체 주입
	//AuthenticationPrincipal 어노테이션을 메서드 파라미터에 지정
	@GetMapping("/myinfo")
	public ResponseEntity<Member> getMyInfo(@AuthenticationPrincipal CustomUser customUser) throws Exception {
		
		int userNo = customUser.getUserNo();
		System.out.println("getMyInfo userNo = " + userNo);
		
		Member member = memberDao.read(userNo);
		
		member.setUserPw("");
		
		return new ResponseEntity<>(member,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/naverLoginGetUrl")
	public @ResponseBody String getNaverAuthUrl(
			HttpServletRequest request) throws Exception {
		
		String clientId = "";//애플리케이션 클라이언트 아이디값";
	    //String redirectURI = URLEncoder.encode("http://localhost:8080/naverLoginGetToken", "UTF-8");
		String redirectURI = URLEncoder.encode("http://runeahz.cafe24.com/naverLoginGetToken", "UTF-8");
		SecureRandom random = new SecureRandom();
	    String state = new BigInteger(130, random).toString();
	    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
	    apiURL += "&client_id=" + clientId;
	    apiURL += "&redirect_uri=" + redirectURI;
	    apiURL += "&state=" + state;
	    
	    return apiURL;
	}
	
	@RequestMapping(value = "/kakaoLoginGetUrl")
	public @ResponseBody String getKakaoAuthUrl(
			HttpServletRequest request) throws Exception {
		/*
		String reqUrl = 
				"https://kauth.kakao.com/oauth/authorize"
				+ "?client_id="
				+ "&redirect_uri=http://localhost:8080/kakaoLoginGetToken"
				+ "&response_type=code";
		*/
		String reqUrl = 
				"https://kauth.kakao.com/oauth/authorize"
				+ "?client_id="
				+ "&redirect_uri=http://runeahz.cafe24.com/kakaoLoginGetToken"
				+ "&response_type=code";
		
		return reqUrl;
	}
	
	// 네이버 겟토큰
	@RequestMapping(value = "/naverLoginGetToken", produces="application/json; charset=utf-8")
	public void oauthNaver(
			@RequestParam(value = "code", required = false) String code,@RequestParam(value = "state", required = false) String state,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {

		System.out.println("#########" + code);
        String access_Token = getNaverAccessToken(code,state);
        System.out.println("###access_Token#### : " + access_Token);
        
        
        HashMap<String, Object> userInfo = getUserInfoNaver(access_Token);
        System.out.println("###access_Token#### : " + access_Token);
        System.out.println("###userInfo#### : " + userInfo.get("email"));
        System.out.println("###nickname#### : " + userInfo.get("nickname"));
       
        JSONObject naverInfo =  new JSONObject(userInfo);
        
        
        /*
         * 1. 엑세스토큰을 카카오api에 보낸 결과값으로 받은 이메일과 닉네임을 기준으로 멤버테이블에 정보삽입
         * 2. userid는 닉네임으로, email은 이메일로, 그외에는 없어도됨
         * 3. 카카오로 들어온사람의 경우엔 닉네임앞에 kakao_ 이런식으로 구별을 함
         * 4. 카카오로 들어왔는데 이메일이 같은 카카오 회원이 있는 경우엔 닉네임이 달라도 같은사람으로 취급함
         */
        String userId = "naver_"+(String)userInfo.get("nickname");
		String userPw = "naver_pw";
		String email = "naver_"+(String)userInfo.get("email");
		String zipcode = "naver_zipcode";
		String address01 = "naver_address01";
		String address02 = "naver_address02";
		
		Member member = new Member(userId, userPw, email, zipcode, address01, address02);
		member.setUserName(userId);
		member.setJob("def");
		if(memberDao.isMemberEmail(email)) {
			System.out.println("해당 이메일로 된 네이버계쩡이 이미 존재함");
			resp.getWriter().write("<script>alert('기존에 방문했던적이 있는 네이버계정입니다!')</script>");
		}else {
			
			if(memberDao.isMemberId(userId)) {
				try {
					String tempId = userId;
					for(;;) {
						tempId = tempId+"_a";
						if(memberDao.isMemberId(tempId)==false) {
							userId = tempId;
							member.setUserId(userId);
							break;
						}
					}
					memberDao.create(member);
					MemberAuth memberAuth = new MemberAuth(memberDao.readByUserId(userId).getUserNo(), userId,"ROLE_MEMBER");
					memberDao.createAuth(memberAuth);
					System.out.println("같은 아이디가있어서 변형 후 가입 성공!");
				}catch(Exception e) {
					e.printStackTrace();
				}
			}else {
				try {
					memberDao.create(member);
					MemberAuth memberAuth = new MemberAuth(memberDao.readByUserId(userId).getUserNo(), userId,"ROLE_MEMBER");
					memberDao.createAuth(memberAuth);
					System.out.println("가입 성공!");
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
        
        Cookie cookie = new Cookie("naver_token", access_Token);
        resp.addCookie(cookie);
        
        resp.sendRedirect("./");
	}
	
	// 카카오 연동정보 조회
	@RequestMapping(value = "/kakaoLoginGetToken", produces= "text/plain; charset=UTF-8"
)
	public void oauthKakao(
			@RequestParam(value = "code", required = false) String code,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {

		System.out.println("#########" + code);
        String access_Token = getKakaoAccessToken(code);
        System.out.println("###access_Token#### : " + access_Token);
        
        
        HashMap<String, Object> userInfo = getUserInfo(access_Token);
        System.out.println("###access_Token#### : " + access_Token);
        System.out.println("###userInfo#### : " + userInfo.get("email"));
        System.out.println("###nickname#### : " + userInfo.get("nickname"));
       
        JSONObject kakaoInfo =  new JSONObject(userInfo);
        
        
        /*
         * 1. 엑세스토큰을 카카오api에 보낸 결과값으로 받은 이메일과 닉네임을 기준으로 멤버테이블에 정보삽입
         * 2. userid는 닉네임으로, email은 이메일로, 그외에는 없어도됨
         * 3. 카카오로 들어온사람의 경우엔 닉네임앞에 kakao_ 이런식으로 구별을 함
         * 4. 카카오로 들어왔는데 이메일이 같은 카카오 회원이 있는 경우엔 닉네임이 달라도 같은사람으로 취급함
         */
        String userId = "kakao_"+(String)userInfo.get("nickname");
		String userPw = "kakao_pw";
		String email = "kakao_"+(String)userInfo.get("email");
		String zipcode = "kakao_zipcode";
		String address01 = "kakao_address01";
		String address02 = "kakao_address02";
		
		Member member = new Member(userId, userPw, email, zipcode, address01, address02);
		member.setUserName(userId);
		member.setJob("def");
		if(memberDao.isMemberEmail(email)) {
			System.out.println("해당 이메일로 된 카카오계쩡이 이미 존재함");
			resp.getWriter().write("<script>alert('기존에 방문했던적이 있는 카카오계정입니다!')</script>");
		}else {
			
			if(memberDao.isMemberId(userId)) {
				try {
					String tempId = userId;
					for(;;) {
						tempId = tempId+"_a";
						if(memberDao.isMemberId(tempId)==false) {
							userId = tempId;
							member.setUserId(userId);
							break;
						}
					}
					memberDao.create(member);
					MemberAuth memberAuth = new MemberAuth(memberDao.readByUserId(userId).getUserNo(), userId,"ROLE_MEMBER");
					memberDao.createAuth(memberAuth);
					System.out.println("같은 아이디가있어서 변형 후 가입 성공!");
				}catch(Exception e) {
					e.printStackTrace();
				}
			}else {
				try {
					memberDao.create(member);
					MemberAuth memberAuth = new MemberAuth(memberDao.readByUserId(userId).getUserNo(), userId,"ROLE_MEMBER");
					memberDao.createAuth(memberAuth);
					System.out.println("가입 성공!");
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
        
        Cookie cookie = new Cookie("kakao_token", access_Token);
        resp.addCookie(cookie);
        
        resp.sendRedirect("./");
	}
	
    //토큰발급 네이버
	public String getNaverAccessToken (String authorize_code,String state) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://nid.naver.com/oauth2.0/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //  URL연결은 입출력에 사용 될 수 있고, POST 혹은 PUT 요청을 하려면 setDoOutput을 true로 설정해야함.
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //	POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=");  //본인이 발급받은 key
            sb.append("&client_secret=");
            //sb.append("&redirect_uri=http://localhost:8080/naverLoginGetToken");     // 본인이 설정해 놓은 경로
            sb.append("&redirect_uri=http://runeahz.cafe24.com/naverLoginGetToken");
            sb.append("&code=" + authorize_code);
            sb.append("&state=" + state);
            bw.write(sb.toString());
            bw.flush();

            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return access_Token;
    }
	
    //토큰발급
	public String getKakaoAccessToken (String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //  URL연결은 입출력에 사용 될 수 있고, POST 혹은 PUT 요청을 하려면 setDoOutput을 true로 설정해야함.
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //	POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=");  //본인이 발급받은 key
            //sb.append("&redirect_uri=http://localhost:8080/kakaoLoginGetToken");     // 본인이 설정해 놓은 경로
            sb.append("&redirect_uri=http://runeahz.cafe24.com/kakaoLoginGetToken");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return access_Token;
    }
	
    //유저정보조회
	//여기서 utf-8로 바꿔야함
    public HashMap<String, Object> getUserInfo (String access_Token) {

        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            
            userInfo.put("accessToken", access_Token);
            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userInfo;
    }
    
  //유저정보조회
//여기서 utf-8로 바꿔야함
  public HashMap<String, Object> getUserInfoNaver (String access_Token) {

      //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
      HashMap<String, Object> userInfo = new HashMap<String, Object>();
      String reqURL = "https://openapi.naver.com/v1/nid/me";
      try {
          URL url = new URL(reqURL);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setRequestMethod("GET");

          //    요청에 필요한 Header에 포함될 내용
          conn.setRequestProperty("Authorization", "Bearer " + access_Token);
          int responseCode = conn.getResponseCode();
          System.out.println("responseCode : " + responseCode);

          BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
          
          String line = "";
          String result = "";

          while ((line = br.readLine()) != null) {
              result += line;
          }
          System.out.println("response body : " + result);

          JsonParser parser = new JsonParser();
          JsonElement element = parser.parse(result);

          JsonObject naver_account = element.getAsJsonObject().get("response").getAsJsonObject();
          String nickname = naver_account.getAsJsonObject().get("nickname").getAsString();
          String email = naver_account.getAsJsonObject().get("email").getAsString();
          
          userInfo.put("accessToken", access_Token);
          userInfo.put("nickname", nickname);
          userInfo.put("email", email);

      } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }

      return userInfo;
  }
	
}
