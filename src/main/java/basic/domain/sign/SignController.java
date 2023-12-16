package basic.domain.sign;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import basic.config.email.EmailService;
import basic.config.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SignController {
	private final SignService signService;
	private final EmailService emailService;
	
	@GetMapping("/sign-in.do")
	public String signin() {
		return "login";
	}
	
	@GetMapping("/sign-up.do")
	public String signup() {
		return "register";
	}
	
	@PostMapping("/sign-up.do")
	public String signuppost(UserDTO userDTO) {
		signService.register(userDTO);
		return "redirect:/sign-in.do";
	}
	
	@ResponseBody
	@PostMapping("/sendMail.do")
	public String sendMail(@RequestParam("email") String email) {
		String authKey = emailService.sendMail(email);
		return authKey;
	}
	
	@GetMapping("/myProfile.do")
	public String myProfile(@AuthenticationPrincipal PrincipalDetails details, Model m) {
		if(details == null) {
			return "redirect:/error.do?msg=401";
		}
		
		UserDTO userDTO = signService.getUserDTO(details.getId());
		m.addAttribute("userDTO", userDTO);
		return "myProfile";
	}
	

	@PostMapping("/myProfile.do")
	public String myProfileP(@AuthenticationPrincipal PrincipalDetails details,
							UserDTO userDTO,
							@RequestParam("file") MultipartFile file) {
		signService.editProfile(userDTO, file);
		details.editProfile(userDTO);
		return "redirect:/index.do";
	}
	
}
