package basic.domain;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import basic.config.api.ApiService;
import basic.domain.tourAPI.DTO.TourSpotDTO;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final ApiService apiService;
	
	@GetMapping("/")
	public String intro(Model m) {
		ArrayList<TourSpotDTO> tourSpotList = apiService.getIndexTour();
		m.addAttribute("randomTourSpotList", tourSpotList);
		return "index";
	}
	
	@GetMapping("/index.do")
	public String index(Model m) {
		ArrayList<TourSpotDTO> tourSpotList = apiService.getIndexTour();
		m.addAttribute("randomTourSpotList", tourSpotList);
		return "index";
	}
	
	@GetMapping("/error.do")
	public String error(Model m, @RequestParam("msg") int msg) {
		m.addAttribute("msg", msg);
		return "error";
	}
	
}
