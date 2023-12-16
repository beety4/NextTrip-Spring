package basic.domain.tourAPI;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import basic.config.api.ApiService;
import basic.domain.tourAPI.DTO.TourSpotDTO;
import basic.domain.tourAPI.DTO.TourSpotDetailDTO;
import basic.domain.tourAPI.DTO.TourSpotSearchDTO;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ApiController {
	private final ApiService apiService;
	
	@GetMapping("/showTourSpot.do")
	public String showTourSpot() {
		return "showTourSpot";
	}
	
	@PostMapping("/showTourSpot.do")
	public String showTourSpotP(Model m, @RequestParam("keyword") String keyword, 
										 @RequestParam("contentTypeId") String contentTypeId) {
		try { 
			keyword = URLEncoder.encode(keyword, "UTF-8");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<TourSpotSearchDTO> list = apiService.getTourSpotSearch(keyword, contentTypeId);
		m.addAttribute("tourSpotSearchList", list);
		return "showTourSpot";
	}
	
	@GetMapping("/showTourSpotDetail.do")
	public String showTourSpotDetail(Model m, @RequestParam("contentid") String contentid) {
		TourSpotDetailDTO dto = apiService.getDetailTourSpot(contentid);
		m.addAttribute("tourSpotDetailDTO", dto);
		return "showTourSpotDetail";
	}
	
	
	@GetMapping("/myTrip.do")
	public String myTrip() {
		return "myTrip";
	}
	
	@ResponseBody
	@PostMapping("/myTripPoint.do")
	public ArrayList<TourSpotDTO> getTourMap(@RequestParam("x") double x,
											 @RequestParam("y") double y,
											 @RequestParam("pageNo") int pageNo) {
		return apiService.getTourMap(x, y, pageNo);
	}
	

	
}
