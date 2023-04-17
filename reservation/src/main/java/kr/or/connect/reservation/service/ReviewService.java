package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.Comment;
import kr.or.connect.reservation.dto.CommentResponse;
import kr.or.connect.reservation.dto.ReviewParam;

public interface ReviewService {
	
	public CommentResponse setReview(ReviewParam param);

	public CommentResponse setReviewNoImage(ReviewParam param);
	
	public List<Comment> getComments(Integer reservationInfoId);
}
