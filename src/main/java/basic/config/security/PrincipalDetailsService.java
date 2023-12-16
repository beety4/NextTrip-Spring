package basic.config.security;

//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import basic.domain.sign.SignService;
import basic.domain.sign.UserDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	private final SignService signService;
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		UserDTO userDTO = signService.getUserDTO(id);
		
		if(userDTO == null) {
			return null;
		}	
		
		return new PrincipalDetails(userDTO);
	}
}
