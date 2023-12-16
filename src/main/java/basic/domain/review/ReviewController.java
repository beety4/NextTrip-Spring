package basic.domain.review;

import java.util.ArrayList;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import basic.config.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;
	
	@GetMapping("/tripReview.do")
	public String tripReview(Model m, @RequestParam("pageNo") int pageNo) {
		ArrayList<ReviewDTO> reviewList = reviewService.getReviewList(pageNo, 0, null);
		m.addAttribute("reviewList", reviewList);
		m.addAttribute("totalPage", (int)Math.ceil((double) reviewService.getReviewCnt() / 10));
		m.addAttribute("pageNo", pageNo);
		return "tripReview";
	}
	
	@PostMapping("/tripReviewSearch.do")
	public String tripReviewSearch(Model m, @RequestParam("search") int search,
											@RequestParam("searchIt") String searchIt) {
		if(searchIt == null) {
			return "redirect:/tripReview.do";
		}
		
		ArrayList<ReviewDTO> reviewList = reviewService.getReviewList(1, search, searchIt);
		m.addAttribute("reviewList", reviewList);
		m.addAttribute("totalPage", 1);
		m.addAttribute("pageNo", 1);
		m.addAttribute("isSearch", true);
		return "tripReview";
	}
	
	@GetMapping("/tripReviewDetail.do")
	public String tripReviewDetail(@AuthenticationPrincipal PrincipalDetails details,
									Model m, @RequestParam("reviewNo") int reviewNo) {
		ReviewDTO reviewDTO = reviewService.getReviewDetail(reviewNo);
		if(reviewDTO == null) {
			return "redirect:/error.do?msg=504";
		}
		
		reviewDTO.setContent(reviewDTO.getContent().replace("\r\n", "<br>"));
		reviewService.addView(reviewNo);
		m.addAttribute("reviewDTO", reviewDTO);
		
		ArrayList<CommentDTO> commentList = reviewService.getCommentList(reviewNo);
		m.addAttribute("commentList", commentList);
		
		if(details == null) {
			m.addAttribute("myName", null);
		} else {
			m.addAttribute("myName", details.getName());
		}
		return "tripReviewDetail";
	}
	
	@GetMapping("/addReview.do")
	public String addReview(@AuthenticationPrincipal PrincipalDetails details) {
		if(details == null) {
			return "redirect:/error.do?msg=401";
		}
		return "tripReviewAdd";
	}
	
	@PostMapping("/addReview.do")
	public String addReviewR(@AuthenticationPrincipal PrincipalDetails details, 
							ReviewDTO reviewDTO,
							@RequestParam("file") MultipartFile file) {
		if(details == null) {
			return "redirect:/error.do?msg=401";
		}
		
		String name = details.getName();
		reviewDTO.setName(name);
		reviewService.addReview(reviewDTO, file);
		return "redirect:/tripReview.do?pageNo=1";
	}
	
	
	
	@GetMapping("/editReview.do")
	public String editReview(@AuthenticationPrincipal PrincipalDetails details,
							@RequestParam("reviewNo") int reviewNo, Model m) {
		ReviewDTO reviewDTO = reviewService.getReviewDetail(reviewNo);
		
		String name = reviewDTO.getName();
		if(details == null || details.getName().equals(name) == false) {
			return "redirect:/error.do?msg=503";
		}
		
		m.addAttribute("reviewDTO", reviewDTO);
		m.addAttribute("isEdit", true);
		return "tripReviewAdd";
	}
	
	@PostMapping("/editReview.do")
	public String editReviewP(@RequestParam("reviewNo") int reviewNo, 
							@RequestParam("title") String title,
							@RequestParam("region") String region,
							@RequestParam("content") String content,
							Model m) {
		ReviewDTO reviewDTO = ReviewDTO.builder()
				.reviewNo(reviewNo)
				.title(title)
				.region(region)
				.content(content).build();
		
		reviewService.editReview(reviewDTO);
		return "redirect:/tripReviewDetail.do?reviewNo=" + reviewNo;
	}
	
	
	@PostMapping("/deleteReview.do")
	public String deleteReview(@AuthenticationPrincipal PrincipalDetails details,
								@RequestParam("reviewNo") int reviewNo) {
		String reviewName = reviewService.getReviewDetail(reviewNo).getName();
		String name = details.getName();
		
		if(name.equals(reviewName) == false) {
			return "redirect:/error.do?msg=503";
		}
		
		reviewService.deleteReview(reviewNo);
		return "redirect:/tripReview.do?pageNo=1";
	}
	
	
	
	
	
	
	@PostMapping("/addReviewComment.do")
	public String addReviewComment(@AuthenticationPrincipal PrincipalDetails details,
									@RequestParam("reviewNo") int reviewNo,
									@RequestParam("comment") String comment) {
		if(details == null) {
			return "redirect:/error.do?msg=401";
		}
		
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setReviewNo(reviewNo);
		commentDTO.setName(details.getName());
		commentDTO.setContent(comment);
		
		reviewService.addReviewComment(commentDTO);
		return "redirect:/tripReviewDetail.do?reviewNo=" + reviewNo;
	}
	
	@PostMapping("/deleteReviewComment.do")
	public String deleteReviewComment(@AuthenticationPrincipal PrincipalDetails details,
										@RequestParam("reviewNo") int reviewNo,
										@RequestParam("commentNo") int commentNo) {
		CommentDTO commentDTO = reviewService.getComment(commentNo);
		if(commentDTO.getName().equals(details.getName()) == false) {
			return "redirect:/error.do?msg=506";
		}
		
		reviewService.deleteReviewComment(commentNo);
		return "redirect:/tripReviewDetail.do?reviewNo=" + reviewNo;
	}
	
	@ResponseBody
	@PostMapping("/addReviewLike.do")
	public int addReviewLike(@AuthenticationPrincipal PrincipalDetails details,
								@RequestParam("reviewNo") int reviewNo) {
		if(details == null) {
			return -1;
		}
		
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setName(details.getName());
		reviewDTO.setReviewNo(reviewNo);
		int likeCnt = reviewService.likeAction(reviewDTO);
		return likeCnt;
	}
	
	@ResponseBody
	@PostMapping("/isLike.do")
	public int isLike(@AuthenticationPrincipal PrincipalDetails details,
							@RequestParam("reviewNo") int reviewNo) {
		if(details == null) {
			return 2;
		}
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setName(details.getName());
		reviewDTO.setReviewNo(reviewNo);
		return reviewService.isLike(reviewDTO) ? 1 : 2;
	}
}
