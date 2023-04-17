package kr.or.connect.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewController {

	@GetMapping("reviewPage")
	public String reviewPage() {
		
		return "reviewPage";
	}
}
