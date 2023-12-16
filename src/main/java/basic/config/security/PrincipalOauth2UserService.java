package basic.config.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import basic.domain.sign.SignService;
import basic.domain.sign.UserDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	private final SignService signService;
	private final BCryptPasswordEncoder bcryptEncoder;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		//System.out.println(super.loadUser(userRequest).getAttributes());
		OAuth2User oauth2User = super.loadUser(userRequest);
		
		//String provider = userRequest.getClientRegistration().getClientId();
		String providerId = oauth2User.getAttribute("sub");
		String password = bcryptEncoder.encode("NO-MEANS");
		String email = oauth2User.getAttribute("email");
		String img = oauth2User.getAttribute("picture");
		
		UserDTO userDTO = signService.getUserDTO(providerId, "GOOGLE");
		if(userDTO == null) {
			userDTO = UserDTO.builder()
					.id(providerId)
					.password(password)
					.name(email.split("@")[0])
					.birth(null)
					.img(img)
					.email(email).build();
			signService.register(userDTO);
		}
		return new PrincipalDetails(userDTO, oauth2User.getAttributes());
	}
}

