<%--로그인 페이지 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>로그인 페이지</title>
<link href="style2.css" rel="stylesheet" type="text/css">
</head>
<body onLoad="loginForm.id.focus()">
	<div>
		<%--로그인시 작성한 정보가 loginCheck.jsp로 전송됨--%>
		<form action="loginCheck.jsp" method="post" name="loginForm">
			<table>

				<tr> 
					<td colspan="3">
					<h2>로그인</h2>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				
				<tr>
					<td width="40%" align="right"> 
						<b>아이디</b>
					</td>
					<td align="left">
						<input type="text" name="userId" size="30">
					</td>
				</tr>
				
				<tr>
					<td align="right"> 
						<b>비밀번호</b>
					</td>
					<td align="left">
						<input type="password"  name="userPW" size="30">
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				
				<tr> 
				<%--로그인버튼 클릭시 로그인 체크후 맞으면 지도화면으로, 틀리면 로그인화면으로 이동 --%>
				<%--아이디 비밀번호 찾기 클릭시 해당 페이지로 이동 --%>
					<td colspan="2"> 
						<input type="submit" value="로그인" >&nbsp; 
						<input type="button" value="아이디/비밀번호 찾기" onClick="javascript:location.href='./###.jsp'"> 
					</td> 
				</tr> 
				
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				
				<tr>
				<%--버튼 클릭시 비회원 지도 화면으로 이동 --%>
					<td colspan="2">
						체험해보고 싶으시다면&nbsp;
						<input type="button" value="비회원 이용" onClick="javascript:location.href='./nonmemberMain.jsp'">
					</td>
				</tr>
				
				<tr>
				<%--버튼 클릭시 회원가입 화면으로 이동 --%>
					<td colspan="2">
						회원으로 등록하시겠어요?&nbsp;
						<input type="button" value="회원가입" onClick="javascript:location.href='./join.jsp'">
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>