package basic.domain.sign;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignMapper {
	public String login(String id);
	public UserDTO getUserDTO(String id);
	public UserDTO getUserDTOprincipal(UserDTO userDTO);
	public void register(UserDTO userDTO);
	public void editProfile(UserDTO userDTO);
}
