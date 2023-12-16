package basic.domain.bookmark;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import basic.domain.tourAPI.DTO.TourSpotDTO;

@Mapper
public interface BookMarkMapper {
	public void addBookMark(TourSpotDTO tourSpotDTO);
	public void deleteBookMark(BookMarkDTO bookMarkDTO);
	public Integer isCheck(BookMarkDTO bookMarkDTO);
	public ArrayList<TourSpotDTO> getBookMarkList(String name);
}
