package kr.or.connect.reservation.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import kr.or.connect.reservation.dto.CommentResponse;
import kr.or.connect.reservation.dto.DisplayInfoResponse;
import kr.or.connect.reservation.dto.FileInfo;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReservationInfoResponse;
import kr.or.connect.reservation.dto.ReviewParam;
import kr.or.connect.reservation.service.DetailService;
import kr.or.connect.reservation.service.FileService;
import kr.or.connect.reservation.service.ReservationMainService;
import kr.or.connect.reservation.service.ReservationService;
import kr.or.connect.reservation.service.ReviewService;



@Controller
public class FileController {
	
		@Autowired
		ReservationMainService mainService;
	
		@Autowired
		ReviewService reviewService;
		
		@Autowired
		ReservationService reservationService;
		
		@Autowired
		FileService fileService;
		
		@Autowired
		DetailService detailService;
	
	 // get방식으로 요청이 올 경우 업로드 폼을 보여줍니다.
		@GetMapping("/reviewwrite/{reservationInfoId}") //form
		public String uploadform(@PathVariable(name = "reservationInfoId") Integer reservationInfoId,@RequestParam("id") Integer displayInfoId, ModelMap model) {
			
			DisplayInfoResponse displayInfoResponse = detailService.getDisplayInfoResponse(displayInfoId); //displayInfoResponse에는 데이터베이스로부터 받아온 모든 정보들이 있다
			ReservationInfo reservationInfo = reservationService.getReservationInfo(reservationInfoId);
			Map<String, Object> map = new HashMap<>(); //모델맵에 속성 추가할때 사용할 해쉬맵 객체 생성
			
			map.put("displayInfoDescription", displayInfoResponse.getDisplayInfo().getProductDescription()); //전시정보 description 맵핑 //!!!!!!!!!!!!!!	
			
			map.put("reservationInfo", reservationInfo);
			model.addAllAttributes(map); //모델맵에 모든 해쉬맵 요소들을 전부 맵핑
			return "reviewwrite";
		}
		
		@PostMapping(path="/checkReview") //클라이언트가 입력한 이메일로된 예약이 있는지 데이터베이서에서 확인하는 매서드
		@ResponseBody
		public String checkReview(@RequestBody String reservationInfoId_proto) { //@RequestBody : http요청의 body 부분을 java 객체로 받을 수 있게 해주는 어노테이션. 주로 json을 받을 때 활용한다.
				System.out.println("reservationInfoId : "+reservationInfoId_proto);
				Integer reservationInfoId = Integer.parseInt(reservationInfoId_proto);
				System.out.println("코멘트객체 : "+reviewService.getComments(reservationInfoId).toString());
			if(reviewService.getComments(reservationInfoId).isEmpty()) { //reservationInfoResponse 객체의 사이즈가 0인경우, 즉 위에서 reservationEmail을 못집어넣어서 객체가 비어있는경우
				return "1"; //작성한 코멘트가 없음. 문자열 1 반환
			}else {
				return "0"; //작성한 코멘트가 이미 존재함. 문자열 0 반환
			}
			
		}
		
		
		/*
		@PostMapping(path="/checkReservation") //클라이언트가 입력한 이메일로된 예약이 있는지 데이터베이서에서 확인하는 매서드
		@ResponseBody
		public String checkReservation(@RequestBody String reservationEmail) { //@RequestBody : http요청의 body 부분을 java 객체로 받을 수 있게 해주는 어노테이션. 주로 json을 받을 때 활용한다.
			ReservationInfoResponse reservationInfoResponse = reservationService.getReservationInfo(reservationEmail); //예약정보 체크를 위해 예약자 이메일을 얻어 새로만든 reservationResponse객체에 담는다
			Map<String, Object> map = new HashMap<>(); //이 후 데이터 맵핑을 위한 해쉬맵생성
			
			if(reservationInfoResponse.getSize() == 0) { //reservationInfoResponse 객체의 사이즈가 0인경우, 즉 위에서 reservationEmail을 못집어넣어서 객체가 비어있는경우
				map.put("size", 0); //해쉬맵 객체에 size 키값으로 0 밸류를 삽입한다
				return "0"; //문자열 0 반환
			}else {
				map.put("size", reservationInfoResponse.getSize()); //해쉬맵 객체에 size 키값으로 reservationInfoResponse의 사이즈를 추출하여 밸류값으로 삽입한다
				map.put("reservations",reservationInfoResponse.getReservations()); //해쉬맵 객체에 reservations 키값으로 현재 이메일에 맞는 예약정보 리스트를 벨류값으로 삽입한다
			}
			
			return "1"; //문자열 1 반환
		}
		*/
		
		@PostMapping(value = "/review/upload")//업로드인경우
		public String upload(MultipartFile attachedImage,String comment,int productId,int reservationInfoId,int score){ //@RequestBody : http요청의 body 부분을 java 객체로 받을 수 있게 해주는 어노테이션. 주로 json을 받을 때 활용한다. //ReservationParam 객체를 외부로부터 요소로받아서 하여 /reserve로 post요청이 왔을때의 처리를 수행하는 매서드
			System.out.println("여기가 중복?1");
			ReviewParam param= new ReviewParam();
			ReservationInfo result = new ReservationInfo();
			result=reservationService.getReservationInfo(reservationInfoId);
			String id=Integer.toString(result.getDisplayInfoId());
			
			System.out.println("여기가 중복?2");
			if(attachedImage.getSize()!=0) { //이미지 파일이 있는경우
			//들어온 파일 이름 확인하여 이미 서버에 같은 이름이 있는경우 _2 붙여서 서버와 데이터베이스 양쪽에 집어넣기

				
				
				if(fileService.getFileNameForChecking(attachedImage.getOriginalFilename()).isEmpty()) { //데이터베이스에 똑같은 파일명이 없는경우
					
					
					param.setAttachedImage(attachedImage);
					param.setComment(comment);
					//reviewwrite 컨트롤러부터 미리 productId와 reservationInfId가 들어있는 겍체를 가져가서 upload url에서도 해당정보 쓸수있도록하기
					param.setProductId(productId);
					param.setReservationInfoId(reservationInfoId);
					param.setScore(score);
					reviewService.setReview(param); //서비스클래스를 이용하여 인자로받은 Param 객체를 등록(?)한다. 아마도?
					
					//실제 파일 업로드 구현하기
					
					System.out.println("============================");
					System.out.println("데이터베이스에 똑같은 파일명이 없는경우");
					System.out.println("파일 이름 : " + attachedImage.getOriginalFilename());
					System.out.println("파일 크기 : " + attachedImage.getSize());
					System.out.println("============================");
					
			        try(
			                //FileOutputStream fos = new FileOutputStream("c:/tmp/img/" + attachedImage.getOriginalFilename());
			        		FileOutputStream fos = new FileOutputStream("/runeahz/tomcat/webapps/images/img/" + attachedImage.getOriginalFilename());
			        		InputStream is = attachedImage.getInputStream();
			        ){
			        	    int readCount = 0;
			        	    byte[] buffer = new byte[1024];
			            while((readCount = is.read(buffer)) != -1){
			                fos.write(buffer,0,readCount);
			            }
			        }catch(Exception ex){
			        	ex.printStackTrace();
			            throw new RuntimeException("file Save Error");
			        }
			        System.out.println("여기가 중복?3");
			        
				} else { //데이터베이스에 똑같은 파일명이 있는경우, 파일이름에 (2)붙여서 데이터베이스와 파일저장소에 업로드
					

					System.out.println("============================");
					System.out.println("데이터베이스에 똑같은 파일명이 있습니다!");
					System.out.println("파일 이름 : " + attachedImage.getOriginalFilename());
					System.out.println("파일 크기 : " + attachedImage.getSize());
					System.out.println("============================");
					
					
					
					try {
						for(Integer i=0;;i++) {
							if(fileService.getFileNameForChecking(i.toString()+attachedImage.getOriginalFilename()).isEmpty()) {
								File newFile = new File(i.toString()+attachedImage.getOriginalFilename());
								attachedImage.transferTo(newFile);
								DiskFileItem fileItem = new DiskFileItem("newFile", Files.probeContentType(newFile.toPath()), false, newFile.getName(), (int) newFile.length() , newFile.getParentFile());
								InputStream input = new FileInputStream(newFile);
								OutputStream os = fileItem.getOutputStream();
								IOUtils.copy(input, os);
								MultipartFile newAttachedImage = new CommonsMultipartFile(fileItem);
								
								String filename = newAttachedImage.getOriginalFilename(); // Give a random filename here.
								try {
									byte[] bytes = newAttachedImage.getBytes();
									//String insPath = "c:/tmp/img/"+filename; // Directory path where you want to save ;
									String insPath = "/runeahz/tomcat/webapps/images/img/"+filename;
									
									Files.write(Paths.get(insPath), bytes);
								    param.setAttachedImage(newAttachedImage);
								    System.out.println("파일이름생성 성공!");
								    break;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									System.out.println("파일이름생성 오류!");
									break;
								}
							}
						}
						
						
						
						
						
						

					} catch (IllegalStateException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					
					param.setComment(comment);
					//reviewwrite 컨트롤러부터 미리 productId와 reservationInfId가 들어있는 겍체를 가져가서 upload url에서도 해당정보 쓸수있도록하기
					param.setProductId(productId);
					param.setReservationInfoId(reservationInfoId);
					param.setScore(score);
					reviewService.setReview(param); //서비스클래스를 이용하여 인자로받은 Param 객체를 등록(?)한다. 아마도?
					
					//실제 파일 업로드 구현하기
					
					/*
			        try(
			                FileOutputStream fos = new FileOutputStream("c:/tmp/img/" + attachedImage.getOriginalFilename());
			                InputStream is = attachedImage.getInputStream();
			        ){
			        	    int readCount = 0;
			        	    byte[] buffer = new byte[1024];
			            while((readCount = is.read(buffer)) != -1){
			                fos.write(buffer,0,readCount);
			            }
			        }catch(Exception ex){
			            throw new RuntimeException("file Save Error");
			        }
			        */
			        
				}
				
				System.out.println("여기가 중복?22");
				
			
			} else { //이미지없는 경우
				System.out.println("여기가 중복?44");
				param.setComment(comment);
				//reviewwrite 컨트롤러부터 미리 productId와 reservationInfId가 들어있는 겍체를 가져가서 upload url에서도 해당정보 쓸수있도록하기
				param.setProductId(productId);
				param.setReservationInfoId(reservationInfoId);
				param.setScore(score);
				reviewService.setReviewNoImage(param); //서비스클래스를 이용하여 인자로받은 Param 객체를 등록(?)한다. 아마도?
			}
			
			
	        return "reviewcomplete";
		}
		
		//1.view에서 특정 이미지의 url값을 필요로함(상품사진, 프로모션사진 등)
		//2.원래는 jsp로 ~~~imageUrl을 내부경로로 바로 받아서 경로로 사용하였으나 외부경로(로컬서버의 c/tmp/img, c/tmp/img_map으로 바꿔야함
		//3.이러면 브라우저에선 서버의 로컬까지 기어들어갈순 없기때문에 브라우저(클라이언트PC)사용자의 c드라이브에 tmp와 이미지 폴더들을 만들어주고 컨트롤러를 이용하여 서버 로컬 저장소의 이미지들을 클라이언트의 저장소로 다운로드시켜야함
		//4.이미지가 필요한 상황(메인페이지url 요청시, 상세페이지url 요청시, 리뷰페이지url 요청시)
		//5.<img alt=${Promotion.product_id } src="${Promotion.productImageUrl}"> 이러한 코드가 있다면
		//6.<img alt=${Promotion.product_id } src=/download/${Promotion.productImageUrl}(이미지 다운로드 컨트롤러 url)/+"${Promotion.productImageUrl}"> 이런식으로 el로 추가변수(imageurl)를 받는 매서드를 만들고 반환값으로 클라이언트PC의 c/tmp/img/{productImageUrl} 이런식으로 문자열을 주면된다
		
		@GetMapping(path="/{fileId}")
	    public void getProductImageUrlForPromotion(@PathVariable(name="fileId") Integer fileId,HttpServletResponse response, ModelMap model) throws IOException {
			System.out.println("여기가 중복?");
			FileInfo fileInfo = fileService.getFileInfo(fileId);
			//StringBuilder sb = new StringBuilder("file:///images/");
			StringBuilder sb = new StringBuilder("file:///runeahz/tomcat/webapps/images/");
			//sftp://runeahz@runeahz.cafe24.com/runeahz/tomcat/webapps/images/img/bg_card_hr.png
			String fileName = fileInfo.getSaveFileName();
			//String promotionFileId = id; //param = save_file_name
			sb.append(fileName);
			
			URL imgUrl = new URL(sb.toString());//Java.net import
			System.out.println(imgUrl+"의 이미지 컨트롤러 실행, file_id : "+fileId);
			IOUtils.copy(imgUrl.openStream(), response.getOutputStream());
			
			//============
			
	        
	    }
		
}

