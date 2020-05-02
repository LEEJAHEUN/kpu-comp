<%--내 제보글 관리 페이지 --%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>내 제보글 관리</title>
<link href="style3.css" rel="stylesheet" type="text/css">
</head>
<body>
<%--페이지. 모든 영역 포함 --%>
<div id="page">

	<div id="header">
	<%--헤더 부분 내용 --%>
		<div id=title align="center">
		<%--타이틀 부분 --%>
		<h2>내 제보글 목록</h2>
		</div>
			
		<%--마이페이지/지도 화면 이동 버튼 --%>	
		<div id="pageMoving">
			<a href="./myPage.jsp">마이페이지로&emsp;</a>
			<a href="./memberMain.jsp">지도 화면으로</a>
		</div>
		
		<%--드롭다운 추가 --%>
		<div id="dropdown">
		  	<button id="dropbtn">
		  		최신순으로 정렬
		  	</button>
		  	<div id="dropdown-content">
			    <a href="#">오래된 순으로 <br>정렬</a>
		  	</div>
		</div>
	</div>

	
	<div id="main">
	<%-- 작성한 제보글 목록  등--%>
		<table>
			<tr>
				<td>
					<b>번호 &emsp;</b>
				</td>
				<td>
					<b>시설명 &emsp;</b>
				</td>
				<td>
					<b>주소 &emsp;</b>
				</td>
				<td>
					<b>이용 가능 정도 &emsp;</b>
				</td>
				<td>
					<b>작성일자 &emsp;</b>
				</td>
			</tr>
			<%--작성한 제보글 목록을 불러옴 --%>
			<%--페이지 번호도 추가 --%>
		</table>
	</div>
	
</div>
</body>
</html>