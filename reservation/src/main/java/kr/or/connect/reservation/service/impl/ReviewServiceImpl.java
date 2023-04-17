package kr.or.connect.reservation.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.reservation.dao.CommentImageDao;
import kr.or.connect.reservation.dao.FileDao;
import kr.or.connect.reservation.dao.ReviewWriteDao;
import kr.or.connect.reservation.dto.Comment;
import kr.or.connect.reservation.dto.CommentImage;
import kr.or.connect.reservation.dto.CommentResponse;
import kr.or.connect.reservation.dto.FileInfo;
import kr.or.connect.reservation.dto.ReservationInfo;
import kr.or.connect.reservation.dto.ReviewParam;
import kr.or.connect.reservation.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewWriteDao reviewWriteDao; //dao를 쓰기위해 dao 객체 선언 및 autowired 어노테이션을 붙임
	
	@Autowired
	CommentImageDao commentImageDao; //dao를 쓰기위해 dao 객체 선언 및 autowired 어노테이션을 붙임
	
	@Autowired
	FileDao fileDao; //dao를 쓰기위해 dao 객체 선언 및 autowired 어노테이션을 붙임
	
	@Transactional
	@Override
	public CommentResponse setReview(ReviewParam param) {
		//1.commentId O //반드시생성
		//2.commentImageId O //이미지가 만약 있다면 새성
		//3.reservationInfoId O
		//4.fileId //이미지가 만약 있다면 생성
		
		Date newDate = new Date();
		
		//commentId 생성
		CommentResponse commentResponse = new CommentResponse(); //Response처리를 위한 응답용 객체 생성
		commentResponse.setCreateDate(newDate); 
		commentResponse.setReservationInfoId(param.getReservationInfoId());
		commentResponse.setModifyDate(newDate); 
		commentResponse.setProductId(param.getProductId()); //param 인자로부터 ProductId를 추출하여 Response에 셋팅
		commentResponse.setComment(param.getComment()); //param 인자로부터 comment를 추출하여 commentResponse에 셋팅
		commentResponse.setScore(param.getScore()); //param 인자로부터 ReservationTelephone를 추출하여 reservationResponse에 셋팅
		Integer commentId = reviewWriteDao.insertCommentResponse(commentResponse); //commentId 변수생성, '클라이언트가 작성한 리뷰를 데이터베이스에 삽입할때 사용할 매서드'(insertCommentResponse) 를 사용할 commentResponse가 완성되었음으로 해당매서드를 이용한다. 반환값으로는 새로이 생성된 id값을 받는다
		System.out.println("commentId : "+commentId); //생성된 리뷰id 콘솔에 출력
		commentResponse.setCommentId(commentId.intValue()); //commentResponse 객체에 인자로부터 추출한 commentId를 셋팅

		
		MultipartFile attachedImage = param.getAttachedImage(); //멀티파트객체 받아오기
		CommentImage commentImage = new CommentImage(); //뷰에서 받아온 멀티파트객체는 CommentImage형의 객체에 옮겨담아야함
		
		//fileId 생성
		FileInfo fileInfo = new FileInfo();
		
		fileInfo.setContentType(attachedImage.getContentType());
		fileInfo.setCreateDate(newDate);
		fileInfo.setDeleteFlag(false);
		fileInfo.setFileName(attachedImage.getOriginalFilename());
		//fileInfo.setModifyDate(newDate);
		fileInfo.setSaveFileName("img/"+attachedImage.getOriginalFilename());
		
		
	
		//commentImageId 생성
		
		
		commentImage.setContentType(attachedImage.getContentType());
		//commentImage.setCreateDate(newDate);
		commentImage.setDeleteFlag(false);
		commentImage.setFileName(attachedImage.getOriginalFilename()); //파일 이름
		commentImage.setModifyDate(newDate);
		commentImage.setReservationInfoId(param.getReservationInfoId());
		commentImage.setReservationUserCommentId(commentId);
		commentImage.setSaveFileName("img/"+attachedImage.getOriginalFilename()); //파일 저장위치 이름
		
		Integer fileId = fileDao.insertFileInfo(commentImage); 
		System.out.println("fileId : "+fileId); 
		commentImage.setFileId(fileId.intValue());
		fileInfo.setFileId(fileId.intValue());
		
		Integer commentImageId = commentImageDao.insertCommentImage(commentResponse,commentImage); //commentId 변수생성, '클라이언트가 작성한 리뷰를 데이터베이스에 삽입할때 사용할 매서드'(insertCommentResponse) 를 사용할 commentResponse가 완성되었음으로 해당매서드를 이용한다. 반환값으로는 새로이 생성된 id값을 받는다
		System.out.println("commentImageId : "+commentImageId); //생성된 리뷰id 콘솔에 출력
		commentImage.setImageId(commentImageId); //reservation_user_comment_image_id
		commentResponse.setCommentImage(commentImage); //처리가끝난 commentImage를 인자로 commentResponse에 서버에 넣을 이미지의 정보를 넣는다
		//=========================
		
		return commentResponse; //처리가 끝낫으면 reservationResponse 객체를 반환한다
	}

	@Transactional
	@Override
	public CommentResponse setReviewNoImage(ReviewParam param) {
				//1.commentId O //반드시생성
				//2.reservationInfoId O
				
				Date newDate = new Date();
				
				//commentId 생성
				CommentResponse commentResponse = new CommentResponse(); //Response처리를 위한 응답용 객체 생성
				commentResponse.setCreateDate(newDate); 
				commentResponse.setReservationInfoId(param.getReservationInfoId());
				commentResponse.setModifyDate(newDate); 
				commentResponse.setProductId(param.getProductId()); //param 인자로부터 ProductId를 추출하여 Response에 셋팅
				commentResponse.setComment(param.getComment()); //param 인자로부터 comment를 추출하여 commentResponse에 셋팅
				commentResponse.setScore(param.getScore()); //param 인자로부터 ReservationTelephone를 추출하여 reservationResponse에 셋팅
				Integer commentId = reviewWriteDao.insertCommentResponse(commentResponse); //commentId 변수생성, '클라이언트가 작성한 리뷰를 데이터베이스에 삽입할때 사용할 매서드'(insertCommentResponse) 를 사용할 commentResponse가 완성되었음으로 해당매서드를 이용한다. 반환값으로는 새로이 생성된 id값을 받는다
				System.out.println("commentId : "+commentId); //생성된 리뷰id 콘솔에 출력
				commentResponse.setCommentId(commentId.intValue()); //commentResponse 객체에 인자로부터 추출한 commentId를 셋팅

				//=========================
				
				return commentResponse; //처리가 끝낫으면 reservationResponse 객체를 반환한다
	}

	@Override
	public List<Comment> getComments(Integer reservationInfoId) {
		List<Comment> comment = reviewWriteDao.getReservationInfo(reservationInfoId);
		return comment;
	}

}
