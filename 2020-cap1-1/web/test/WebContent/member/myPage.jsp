<%--로그인 페이지 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>마이페이지</title>
<link href="mypageStyle.css" rel="stylesheet" type="text/css">
</head>
<body >  
	<div align="center">
		<br /> <br />
		<table>
			<tr> 
				<td bgcolor="#FFFFFF"> 
				<table> 
				<tr bgcolor="#FDD700"> 
				<td colspan="3" align="center">
				<h2>마이페이지</h2>
				</td>
			</tr>	

			<tr>
				<td align="center">
					<input type="image" src="file:///C:/Users/ssyon/Desktop/졸작/구현중/test/WebContent/image/userInfoImage.PNG" alt=""
					onClick="javascript:location.href='./memberUpdate.jsp'">
					&emsp;&emsp;<b><a href="./userInfo.jsp">&emsp;내 정보</a></b>
				</td>
			</tr>
			
			<tr>
				<td align="center">
					<input type="image" src="file:///C:/Users/ssyon/Desktop/졸작/구현중/test/WebContent/image/updatePost.PNG" alt=""
					onClick="javascript:location.href='.jsp'">
					<b><a href="./.jsp">&emsp;제보글 수정</a></b>
				</td>
			</tr>
			
			<tr>
				<td align="center">
					<input type="image" src="file:///C:/Users/ssyon/Desktop/졸작/구현중/test/WebContent/image/deletePost.PNG" alt=""
					onClick="javascript:location.href='.jsp'">
					<b><a href="./.jsp">&emsp;제보글 삭제</a></b>
				</td>
			</tr>
			
			<tr>
				<td align="center">
					<input type="image" src="file:///C:/Users/ssyon/Desktop/졸작/구현중/test/WebContent/image/withdrawal.PNG" alt=""
					onClick="javascript:location.href='.jsp'">
					&emsp;<b><a href="./.jsp">&emsp;회원 탈퇴</a></b>
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
				<input type="button" value="지도 화면으로" onClick="javascript:location.href='memberMain.jsp'">
				</td>
			</tr>
		</table>
		</td>
	</table>
	</div>
</body>
</html>