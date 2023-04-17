package kr.or.connect.reservation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.connect.reservation.dto.CategoryResponse;
import kr.or.connect.reservation.dto.DisplayInfoResponse;
import kr.or.connect.reservation.dto.ProductResponse;
import kr.or.connect.reservation.dto.PromotionResponse;
import kr.or.connect.reservation.service.DetailService;

@Controller
@RequestMapping(path="") //detail 페이지의 기본 상태
public class DetailController {
	
	@Autowired
	DetailService detailService;
	
	@GetMapping(path="/detail") //detail 경로일때의 컨트롤러. 메인화면에서 클릭한 상품의 displayInfoId에 따른 페이지를 출력하기위한 컨트롤러
	public String detailPage(@RequestParam(name="id",required=false,defaultValue="0") Integer displayInfoId, ModelMap model) { //모델맵 복습필요
		DisplayInfoResponse displayInfoResponse = detailService.getDisplayInfoResponse(displayInfoId); //초기화 해야됨
		
		Map<String, Object> map = new HashMap<>(); //해쉬맵의 용도랑 활용방법을 좀 알아봐야겠음. 이거 어떻게 쓰는거임?
		
		//System.out.println(promotion_items.getItems()); //콘솔에 로그찍기
		
		map.put("displayInfo", displayInfoResponse.getDisplayInfo()); //dao>>serviceimp을 거쳐 받아온 displayInfoResponse에는 데이터베이스로부터 받아온 정보들이 있다, displayInfo 컨트롤
		
		if(displayInfoResponse.getProductImages().size() > 1) { //상품이미지리스트를 불러와서 그 갯수를 계산하여 1개 초과일경우(조건)
			map.put("etc", "hasEtc"); //해쉬맵에 키값 "etc" 밸류값 "hasEtc"를 삽입하여 해당 id의 전시정보가 상품이미지를 2개이상 가지고 있음을 표시한다 
		}else { //그렇지 않고 상품 이미지리스트의 이미지가 1개 이하일 경우
			map.put("etc", "notEtc"); //해쉬맵에 키값 "etc" 밸류값 "notEtc"를 삽입하여 해당 id의 전시정보가 상품이미지를 하나 이하로 가지고 있음을 표시한다 
		}
		
		map.put("productImages", displayInfoResponse.getProductImages()); //displayInfoResponse에는 데이터베이스로부터 받아온 모든 정보들이 있다, productImage 컨트롤
		map.put("displayInfoImage", displayInfoResponse.getDisplayInfoImage()); //displayInfoImage 컨트롤
		map.put("comments", displayInfoResponse.getComments()); //comment 컨트롤
		map.put("averageScore", (Double)displayInfoResponse.getAverageScore()); //averatgeScore 컨트롤    
		map.put("productPrices", displayInfoResponse.getProductPrices()); //productPrice 컨트롤
		map.put("displayInfoId", displayInfoId); //displayInfoId 컨트롤
		model.addAllAttributes(map); //모델객체에 맵객체 집어넣기
		return "detail"; //"detail" 리턴
	}
	
	//상세페이지, 처음 상세페이지로 이동할땐 위의 컨트롤러가 쓰이지만, 이후 비동기통신이 필요하거나 클라이언트의 행동에 따라 데이터가 동적으로 변화하여야할때를 위한 컨트롤러
		@GetMapping(path="/review/{id}") // 리뷰페이지 경로, {id}값도 받음
		public String review(@PathVariable("id") Integer displayInfoId, ModelMap model) { //@PathVariable("id")는 같은 이름을 같는 템플릿 변수 {id}로 부터 변수를 받음
			
			DisplayInfoResponse displayInfoResponse = detailService.getDisplayInfoResponse(displayInfoId); //displayInfoResponse에는 데이터베이스로부터 받아온 모든 정보들이 있다
			Map<String, Object> map = new HashMap<>(); //모델맵에 속성 추가할때 사용할 해쉬맵 객체 생성
			map.put("displayInfoId", displayInfoId); //displayInfoId 맵핑
			map.put("comments", displayInfoResponse.getComments()); //commentList 맵핑
			map.put("averageScore", (Double)displayInfoResponse.getAverageScore()); //평균평점 맵핑
			map.put("displayInfo", displayInfoResponse.getDisplayInfo()); //전시정보response 맵핑 //!!!!!!!!!!!!!!
			model.addAllAttributes(map); //모델맵에 집어넣고
			return "review"; //"review"로 리턴
		}
	
		@GetMapping(path="/info_tab_list", produces = "application/json; charset=utf-8") //info_tab_list 경로, json으로 받음
		@ResponseBody 
		public Map<String,Object> content(@RequestParam("displayInfoId")Integer displayInfoId) //displayInfoId를 패러미터로 갖는 content 맵객체
		{
			DisplayInfoResponse displayInfoResponse = detailService.getDisplayInfoResponse(displayInfoId);
			Map<String, Object> map = new HashMap<>();
			map.put("displayInfoId", displayInfoId); //전시정보 id 맵핑
			map.put("displayInfoImage", displayInfoResponse.getDisplayInfoImage()); //전시정보 이미지 맵핑
			map.put("displayInfo", displayInfoResponse.getDisplayInfo()); //전시정보response 맵핑
			return map; //데이터 다 담았으면 맵객체 리턴
		}
		
}
