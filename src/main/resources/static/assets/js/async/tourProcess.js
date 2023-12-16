var markers = [];
var overlays = [];

var nowx = 37.44913;
var nowy = 126.6572;
var nowPage = 1;

window.addEventListener('DOMContentLoaded', function() {
	getContent(37.44913, 126.6572, 1);
});

// 위치 정보 전송
function getContent(x, y, pageNo) {
	var myArr = [];
	$.ajax({
    	url:"/myTripPoint.do",
        type:"post",
        dataType:"json",
        data:{"x" : x, "y" : y, "pageNo" : pageNo},
        success: function(data){
			$(data).each(function() {
				var content = [this.title, this.contentid, this.firstimage, this.firstimage2, this.addr1, this.mapx, this.mapy, this.addr2];		
				myArr.push(content);
			});
			
			writeMarker(myArr);
			writeBoard(myArr);
        },
        error: function(request, status, error) {
			console.log("code : " + request.status);
			console.log("message : " + request.responseText);
			console.log("error : " + error);
		}
	});
}

// 테이블에 상세정보 작성
function writeBoard(myArr) {
	document.getElementById("inputData").innerHTML = '';
	for(var i=0; i<myArr.length; i++) {
		var view = document.createElement("tr");
		view.setAttribute("onclick", "location.href='showTourSpotDetail.do?contentid=" + myArr[i][1] + "'");
		view.innerHTML = `
			<td>
				${myArr[i][1]}
			</td><td>
				${myArr[i][0]}
			</td><td>
				${myArr[i][4]}
			</td>		
						`
	document.getElementById("inputData").appendChild(view);
	}
}

// 지도에 마커 생성
function writeMarker(myArr) {
	setMarkers(null);
	markers = [];
	overlays = [];
	for(i=0; i<myArr.length; i++) {
		var marker = new kakao.maps.Marker({
			position: new kakao.maps.LatLng(myArr[i][6], myArr[i][5])
		});
		
		var iwContent = '<div style="padding:5px;">' + myArr[i][0] + '</div>';
   		var iwPosition = new kakao.maps.LatLng(33.450701, 126.570667);
    	var iwRemoveable = true;
		
		var infowindow = new kakao.maps.InfoWindow({
			map: map,
			position: iwPosition,
			content: iwContent,
			removable: iwRemoveable
    	});

		kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
    	kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
		
		marker.setMap(map);
		markers.push(marker);
	}
}


function makeOverListener(map, marker, infowindow) {
    return function() {
        infowindow.open(map, marker);
    };
}

function makeOutListener(infowindow) {
    return function() {
        infowindow.close();
    };
}


function setMarkers(map) {
    for (var i=0; i < markers.length; i++) {
        markers[i].setMap(map);
    }            
}


function pageup() {
	getContent(nowx, nowy, nowPage + 1);
	nowPage = nowPage + 1;
}
function pagedown() {
	if(nowPage == 1) {
		return;
	}
	getContent(nowx, nowy, nowPage - 1);
	nowPage = nowPage - 1;
}


