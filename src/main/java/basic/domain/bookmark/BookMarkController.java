package basic.domain.bookmark;

import java.util.ArrayList;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import basic.config.security.PrincipalDetails;
import basic.domain.tourAPI.DTO.TourSpotDTO;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookMarkController {
	private final BookMarkService bookMarkService;
	
	@GetMapping("/bookmark.do")
	public String bookmark(@AuthenticationPrincipal PrincipalDetails details, Model m) {
		if(details == null) {
			return "redirect:/error.do?msg=401";
		}
		
		ArrayList<TourSpotDTO> bookmarkList = bookMarkService.getBookMarkList(details.getName());
		m.addAttribute("bookmarkList", bookmarkList);
		return "bookmark";
	}
	
	@ResponseBody
	@PostMapping("/bookmarkAction.do")
	public int bookmarkAction(@AuthenticationPrincipal PrincipalDetails details,
								@RequestParam("contentid") String contentid,
								@RequestParam("firstimage") String firstimage,
								@RequestParam("addr1") String addr1,
								@RequestParam("title") String title) {
		if(details == null) {
			return 2;
		}
		
		TourSpotDTO tourSpotDTO = new TourSpotDTO();
		tourSpotDTO.setContentid(contentid);
		tourSpotDTO.setFirstimage(firstimage);
		tourSpotDTO.setAddr1(addr1);
		tourSpotDTO.setTitle(title);
		
		int result = bookMarkService.bookmarkAction(tourSpotDTO, details.getName());
		return result;
	}
	
	@ResponseBody
	@PostMapping("/bookmarkCheck.do")
	public int bookmarkCheck(@AuthenticationPrincipal PrincipalDetails details,
								@RequestParam("contentid") String contentid) {
		if(details == null) {
			return 0;
		}
		
		BookMarkDTO bookMarkDTO = new BookMarkDTO();
		bookMarkDTO.setContentid(contentid);
		bookMarkDTO.setName(details.getName());
		
		return bookMarkService.isCheck(bookMarkDTO) ? 1 : 0;
	}
}
