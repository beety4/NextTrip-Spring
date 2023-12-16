package basic.domain.review;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {
	public int getLastNo();
	public int getReviewCnt();
	
	public ArrayList<ReviewDTO> getReviewList1(String searchIt);
	public ArrayList<ReviewDTO> getReviewList2(String searchIt);
	public ArrayList<ReviewDTO> getReviewList3(String searchIt);
	public ArrayList<ReviewDTO> getReviewList4(int page);
	
	public ReviewDTO getReviewDetail(int reviewNo);
	public void addReview(ReviewDTO reviewDTO);
	public void deleteReview(int reviewNo);
	public void editReview(ReviewDTO reviewDTO);
	
	public void addView(int reviewNo);
	public void addReviewComment(CommentDTO commentDTO);
	public void deleteReviewComment(int commentNo);
	public ArrayList<CommentDTO> getCommentList(int reviewNo);
	public CommentDTO getComment(int commentNo);
	
	public void addReviewLike(ReviewDTO reviewDTO);
	public void deleteReviewLike(ReviewDTO reviewDTO);
	public Integer isLike(ReviewDTO reviewDTO);
	public int getLikeCnt(int reviewNo);
}
