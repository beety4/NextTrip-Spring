<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ page import="basic.domain.tourAPI.DTO.TourSpotDetailDTO"%>
<%@ page import="basic.domain.tourAPI.DTO.TourSpotImgDTO"%>
<%@ page import="java.util.ArrayList"%>


<style>
	#slider-container {
		width: 80%;
		margin: auto;
		overflow: hidden;
	}
	#image-slider {
		display: flex;
		transition: transform 0.5s ease-in-out;
	}
	.slide {
		min-width: 100%;
		box-sizing: border-box;
	}
</style>


<link href="/assets/css/table.css" rel="stylesheet">
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=392bbb4349f581b6a65c70d04b097e8d"></script>
<script src="/assets/js/async/bookmarkProcess.js"></script>

<section class="pt-5" style="padding-top: 5rem; margin-top: 100px;">
<div class="container wConfig75">


	<br><br>
		<div class="card mb-3" style="max-width: 100%;">
			<div class="row no-gutters">
				<div class="col-md-6">
					<div id="slider-container">
					<div id="image-slider">
						<c:forEach var="p" items="${tourSpotDetailDTO.tourSpotImgList }">
							<input type="text" id="contentid" value="${p.contentid }" style="display: none;">
							<div class="slide">
								<img src="${p.originimgurl }" class="card-img" title="${p.imgname }" width="400px;">
							</div>
						</c:forEach>
					</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="card-body">
						<h5 class="card-title">${tourSpotDetailDTO.title }</h5>
						<p class="card-text">${tourSpotDetailDTO.overview }</p>
						<p class="card-text">
							<small class="text-muted">${tourSpotDetailDTO.homepage }</small>
						</p>
					</div>
				</div>
			</div>
		</div>
		
		<input type="button" class="btn btn-success" value="북마크 추가" id="bookmark" style="float:right; width:100px;"
					onclick="bookmarkAction('${tourSpotDetailDTO.contentid }', '${tourSpotDetailDTO.firstimage }', '${tourSpotDetailDTO.addr1 }', '${tourSpotDetailDTO.title }');">



		<br><br>
		<p>주소 : ${tourSpotDetailDTO.addr1 } - (${tourSpotDetailDTO.zipcode })</p>
		<div id="map" style="width:100%;height:600px;"></div>
		
		
		
		
		
		<script>
			var container = document.getElementById("map");
			var options = {
					//center: new kakao.maps.LatLng(37.44913, 126.6572),
					center : new kakao.maps.LatLng(${tourSpotDetailDTO.mapy }, ${tourSpotDetailDTO.mapx }),
					level : 3
			};
			
			// 지도 생성
			var map = new kakao.maps.Map(container, options);
			
			// 일반 뷰, 스카이 뷰 전환
			var mapTypeControl = new kakao.maps.MapTypeControl();
			
			// 지도에 컨트롤 추가
			map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
			
			// 확대 축소 컨트롤바
			var zoomControl = new kakao.maps.ZoomControl();
			map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
			
			// 마커 위치
			var markerPosition  = new kakao.maps.LatLng(${tourSpotDetailDTO.mapy }, ${tourSpotDetailDTO.mapx });
			
			// 마커 생성
			var marker = new kakao.maps.Marker({
			    position: markerPosition
			});
			
			// 마커 표시
			marker.setMap(map);
		</script>
	

	</div>
</section>


<script type="text/javascript" src="/assets/js/slider.js"></script>

<%@ include file="footer.jsp"%>