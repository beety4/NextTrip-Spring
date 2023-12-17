package basic.domain.review;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewMapper reviewMapper;
	
	public int getLastNo() {
		return reviewMapper.getLastNo();
	}
	
	public int getReviewCnt() {
		return reviewMapper.getReviewCnt();
	}
	
	public int getTotalPage() {
		int reviewCnt = getReviewCnt();
		int pageCnt = 10 / reviewCnt;
		return (reviewCnt % 10 != 0) ? ++pageCnt : pageCnt;
	}
	
	public ArrayList<ReviewDTO> getReviewList(int pageNo, int search, String searchIt) {
		switch(search) {
			case 1: return reviewMapper.getReviewList1(searchIt);
			case 2: return reviewMapper.getReviewList2(searchIt);
			case 3: return reviewMapper.getReviewList3(searchIt);
			default: return reviewMapper.getReviewList4((pageNo-1) * 10);
		}
	}
	
	public ReviewDTO getReviewDetail(int reviewNo) {
		return reviewMapper.getReviewDetail(reviewNo);
	}
	
	public void addReview(ReviewDTO reviewDTO, MultipartFile file) {
		// 파일을 첨부 시
		if (file.isEmpty() == false) {
			// 파일 경로 및 확장자 처리
			String realPath = "C:/Users/sysop/eclipse-workspace/NextTrip-Spring/src/main/resources/static/assets/img/reviewIMG";
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
			reviewDTO.setImg("/assets/img/reviewIMG/" + newFileName);
		} 

		// 리뷰 만들기
		reviewMapper.addReview(reviewDTO);
	}
	
	public void deleteReview(int reviewNo) {
		reviewMapper.deleteReview(reviewNo);
	}
	
	public void editReview(ReviewDTO reviewDTO) {
		reviewMapper.editReview(reviewDTO);
	}
	
	
	public void addReviewComment(CommentDTO commentDTO) {
		reviewMapper.addReviewComment(commentDTO);
	}
	
	public void deleteReviewComment(int commentNo) {
		reviewMapper.deleteReviewComment(commentNo);
	}
	
	public ArrayList<CommentDTO> getCommentList(int reviewNo) {
		return reviewMapper.getCommentList(reviewNo);
	}
	
	public CommentDTO getComment(int commentNo) {
		return reviewMapper.getComment(commentNo);
	}
	
	
	public void addView(int reviewNo) {
		reviewMapper.addView(reviewNo);
	}
	
	

	public void addReviewLike(ReviewDTO reviewDTO) {
		reviewMapper.addReviewLike(reviewDTO);
	}

	public void deleteReviewLike(ReviewDTO reviewDTO) {
		reviewMapper.deleteReviewLike(reviewDTO);
	}

	public boolean isLike(ReviewDTO reviewDTO) {
		if(reviewMapper.isLike(reviewDTO) == null) {
			return false;
		} else {
			return true;
		}
	}

	public int getLikeCnt(int reviewNo) {
		return reviewMapper.getLikeCnt(reviewNo);
	}
	
	
	public int likeAction(ReviewDTO reviewDTO) {
		if(isLike(reviewDTO)) {
			deleteReviewLike(reviewDTO);
		} else {
			addReviewLike(reviewDTO);
		}
		return getLikeCnt(reviewDTO.getReviewNo());
	}
	
	
	
	
	
	
}
