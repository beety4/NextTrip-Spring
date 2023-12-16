package basic.domain.bookmark;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import basic.domain.tourAPI.DTO.TourSpotDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookMarkService {
	private final BookMarkMapper bookmarkMapper;
	
	public void addBookMark(TourSpotDTO tourSpotDTO) {
		bookmarkMapper.addBookMark(tourSpotDTO);
	}
	
	public void deleteBookMark(BookMarkDTO bookMarkDTO) {
		bookmarkMapper.deleteBookMark(bookMarkDTO);
	}
	
	public boolean isCheck(BookMarkDTO bookMarkDTO) {
		if(bookmarkMapper.isCheck(bookMarkDTO) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public ArrayList<TourSpotDTO> getBookMarkList(String name) {
		return bookmarkMapper.getBookMarkList(name);
	}
	
	public int bookmarkAction(TourSpotDTO tourSpotDTO, String name) {
		BookMarkDTO bookMarkDTO = new BookMarkDTO();
		bookMarkDTO.setContentid(tourSpotDTO.getContentid());
		bookMarkDTO.setName(name);
		
		if(isCheck(bookMarkDTO)) {
			deleteBookMark(bookMarkDTO);
			return 0;
		} else {
			tourSpotDTO.setName(name);
			addBookMark(tourSpotDTO);
			return 1;
		}
	}
}
