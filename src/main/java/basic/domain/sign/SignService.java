package basic.domain.sign;

import java.io.File;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SignService {
	private final SignMapper signMapper;
	private final BCryptPasswordEncoder bcryptEncoder;
	
	public String login(String id) {
		return signMapper.login(id);
	}
	
	public UserDTO getUserDTO(String id) {
		return signMapper.getUserDTO(id);
	}
	
	public UserDTO getUserDTO(String id, String principal) {
		UserDTO userDTO = UserDTO.builder()
				.id(id)
				.principal(principal).build();
		
		return signMapper.getUserDTOprincipal(userDTO);
	}
	
	public void register(UserDTO userDTO) {
		String digest = bcryptEncoder.encode(userDTO.getPassword());
		userDTO.setPassword(digest);
		
		if(userDTO.getImg() == null) {
			userDTO.setImg("/assets/img/profileIMG/default.png");
		}
		signMapper.register(userDTO);
	}
	
	public void editProfile(UserDTO userDTO, MultipartFile file) {
		// 파일을 첨부 시
		if (file.isEmpty() == false) {
			// 파일 경로 및 확장자 처리
			String realPath = "C:/Users/sysop/eclipse-workspace/NextTrip-Spring/src/main/resources/static/assets/img/profileIMG";
			String fileName = StringUtils.cleanPath(file.getOriginalFilename()); // 파일 원본 이름
			String fileExtension = StringUtils.getFilenameExtension(fileName); // 확장자
			UUID uuid = UUID.randomUUID(); // 랜덤 UUID
			String newFileName = uuid + "." + fileExtension; // 저장할 파일 이름
			File newFile = new File(realPath + "/" + newFileName); // 저장할 파일 객체

			// 파일 업로드
			try {
				file.transferTo(newFile);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 파일 경로 DTO에 저장
			userDTO.setImg("/assets/img/profileIMG/" + newFileName);
		} else {
			// 파일 미첨부 시
			userDTO.setImg("/assets/img/profileIMG/default.png");
		}
		
		String digest = bcryptEncoder.encode(userDTO.getPassword());
		userDTO.setPassword(digest);
		
		signMapper.editProfile(userDTO);
	}
}
