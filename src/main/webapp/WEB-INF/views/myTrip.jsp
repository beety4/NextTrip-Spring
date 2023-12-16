<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>


<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=392bbb4349f581b6a65c70d04b097e8d"></script>
<link href="/assets/css/myTrip.css" rel="stylesheet">


<section class="pt-5" style="padding-top: 5rem; margin-top: 100px;">
<div class="container wConfig75">
	
	<div id="wrap">
	<br><br>
	<div id="map" style="width:600px ;height:500px;"></div>
	
	
	<div id="list-container">
        <h2>주변 관광지 목록</h2>
        <div style="float:right;">
        	<input type="button" value="이전" onclick="pagedown();">
			<input type="button" value="다음" onclick="pageup();">
		</div>
        <table>
            <thead>
                <tr>
                    <th>contentID</th>
                    <th>title</th>
                    <th>address</th>
                </tr>
            </thead>
            <tbody id="inputData">

            </tbody>
        </table>
    </div>
	</div>
	
	
	
		<script>
			var container = document.getElementById("map");
			var options = {
					//center: new kakao.maps.LatLng(37.44913, 126.6572),
					center : new kakao.maps.LatLng(37.44913, 126.6572),
					level : 8
			};
			
			// 지도 생성
			var map = new kakao.maps.Map(container, options);
			
			// 일반 뷰, 스카이 뷰 전환 - 컨트롤 추가 - 확대 축소 컨트롤바
			var mapTypeControl = new kakao.maps.MapTypeControl();
			map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
			var zoomControl = new kakao.maps.ZoomControl();
			map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
			
			// 마커 위치 및 생성 표시
			var imageSrc = 'https://cdn-icons-png.flaticon.com/512/25/25613.png',
	    	imageSize = new kakao.maps.Size(40, 45),
	    	imageOption = {offset: new kakao.maps.Point(17, 48)};
	    
	    	var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
	    	markerPosition = new kakao.maps.LatLng(37.44913, 126.6572); // 마커가 표시될 위치입니다
	    
	    	var marker = new kakao.maps.Marker({
	        	position: markerPosition, 
	        	image: markerImage // 마커이미지 설정 
	    	});
	    	marker.setMap(map);
			
	    	
			// 지도에 클릭 이벤트를 등록합니다
			// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
			kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
			    // 클릭한 위도, 경도 정보를 가져옵니다 
			    var latlng = mouseEvent.latLng; 
			    
			    // 마커 위치를 클릭한 위치로 옮깁니다
			    marker.setPosition(latlng);
			    
			    nowx = latlng.getLng();
			    nowy = latlng.getLat();
			    nowPage = 1;
			    getContent(latlng.getLat(), latlng.getLng(), 1);
			});
		</script>
		
		<script src="/assets/js/async/tourProcess.js"></script>
</div>
</section>



<%@ include file="footer.jsp"%>