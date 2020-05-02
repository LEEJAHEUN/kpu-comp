<%--기본 지도 화면(회원용) 페이지 --%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>기본 지도 화면(회원)</title>
<link href="mapStyle.css" rel="stylesheet" type="text/css">
<%--지도 화면 호출 함수 --%>
<script
	src="https://apis.openapi.sk.com/tmap/jsv2?version=1&appKey=l7xxe03c36cd42194f0597280d25a140da4c">
</script>
<script type="text/javascript">
	// 페이지가 로딩이 된 후 호출하는 함수입니다.
	function initTmap(){
		// map 생성
		// Tmapv2.Map을 이용하여 지도의 초기좌표,지도가 들어갈 div, 넓이, 높이, 확대 정도를 설정합니다.
		var map = new Tmapv2.Map("main", { 
			center: new Tmapv2.LatLng(37.340187, 126.733518),
			width : "1000px", // 지도의 넓이
			height : "700px", //지도의 높이
			zoom: 16
		});
	} 
</script>
</head>
<body onload="initTmap()"><!-- 맵 생성 실행 -->
<%--page영역. header, main(지도화면) 포함 --%>
<%
	//로그인한 사람은 userID에 아이디가 담기고, 아니면 null값임
	String userId = null;
    if(session.getAttribute("userId") != null) // 로그인이 안되었을 때
    { 
    	userId=(String)session.getAttribute("userId");
     }
    else
    {
        // 로그인 상태가 아닌경우 로그인 화면으로 이동
      	response.sendRedirect("./login.jsp");
    }
%>
<div id="page">
	
	<div id="header">
	<%--헤더 부분 내용 --%>
	
		<div id="title">
			<%--타이틀 부분 --%>
			<%--검색창 배치 --%>
			<%--search는 임의로 설정. 값 이름 바꿀 것 --%>
			&emsp;&emsp;<input type="text" name="search" size=30 >
			<%--검색 버튼 클릭시 onClick(검색)으로 넘어감--%>
			<input type="button" value="검색" onClick="">
		</div>
	</div>
	
	<%--content 영역.메뉴랑 지도 --%>
	<div id="content">
	
		<%--(왼쪽)메뉴 --%>
		<div id="leftMenu">
			<p><a href="./myPage.jsp">&emsp;마이 페이지</a>
			<p>&emsp;길찾기
			<p>&emsp;제보글 작성
			<p><a href="./logout.jsp">&emsp;로그아웃</a>
			<%--웹 정보 페이지 생성 후 링크 수정할것 --%>
			<p><a href="./###.jsp">&emsp;웹 정보</a>
		</div>
		
		<%--(오른쪽)메뉴 --%>
		<%--검색 결과가 나타나는 부분 --%>
		<div id="rightMenu">
			<p>&emsp;
		</div>
		
		<%--지도화면 부분(main)--%>
		<div id="main">
		</div>
	</div>
</div>
</body>
</html>