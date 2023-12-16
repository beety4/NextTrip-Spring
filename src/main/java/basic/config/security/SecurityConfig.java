package basic.config.security;

import java.util.stream.Stream;

import jakarta.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	PrincipalOauth2UserService service;
	
	private static final String[] PERMIT_URL = new String[] {
		"/", "/index.do", "/error.do", "/bookmark.do", "/bookmarkAction.do", "/bookmarkCheck.do",
		"/tripReview.do", "/tripReviewSearch.do", "/tripReviewDetail.do", "/myTrip.do",
		"/addReviewLike.do", "/isLike.do", "/sign-in.do", "/sign-up.do",
		"/logout.do", "/sendMail.do", "/login/oauth2/code/google", "/showTourSpot.do",
		"/showTourSpotDetail.do", "/assets/**", "/myTripPoint.do"
	};
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {        
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests((auth) -> auth
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers(
					Stream
				        .of(PERMIT_URL)
				        .map(AntPathRequestMatcher::antMatcher)
				        .toArray(AntPathRequestMatcher[]::new)
				).permitAll()
				.anyRequest().authenticated())
			
			.formLogin((login) -> login
				.loginPage("/sign-in.do")
				.loginProcessingUrl("/sign-in.do")
				.usernameParameter("id")
				.passwordParameter("password")
				.defaultSuccessUrl("/", true)
				.failureUrl("/sign-in.do")
				.permitAll())
			.logout((logout) -> logout
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.permitAll())
			
			.oauth2Login((oauth) -> oauth
                .loginPage("/sign-in.do")
                .defaultSuccessUrl("/", true)
                .userInfoEndpoint((userInfo) -> userInfo
                		.userService(service)));

		return http.build();
	}

}
