<%--회원가입 페이지 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>회원가입 페이지</title>
<link href="style2.css" rel="stylesheet" type="text/css">
</head>
<body onLoad="joinForm.id.focus()">
<div>
	<%--회원가입시 작성한 정보가 joinCheck.jsp로 전송됨--%>
	<form name="joinForm" method="post" action="joinCheck.jsp">
		<table>
		
			<tr>
				<td colspan="3">
				<h2>회원가입</h2>
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
				<%--아이디 중복확인 클릭시 idCheck 통해  id 값을 비교하여 확인함 --%>
					<input name="userId" size="30">
					<input type="button" value="아이디 중복확인" onClick="idCheck(this.form.id.value)">
				</td>
			</tr>
			
			<tr>
				<td align="right"> 
					<b>비밀번호</b>
				</td>
				<td align="left">
					<input type="password" name="userPW" size="30">
				</td>
			</tr>
			
			<tr>
				<td align="right"> 
					<b>비밀번호 확인</b>
				</td>
				<td align="left">
					<input type="password" name="rePW" size="30">
				</td>
			</tr>
			
			<tr>
				<td align="right"> 
					<b>이메일</b>
				</td>
				<td align="left">
				<%--이메일 중복확인 클릭시 emailCheck 통해  email 값을 비교하여 확인함 --%>
					<input name="userEmail" size="30">
					<input type="button" value="이메일 중복확인" onClick="emailCheck(this.form.email.value)">
				</td>
			</tr>
			
			<tr>
				<td align="right"> 
					<b>교통약자 유형</b>
				</td>
				<td align="left">
					<br>고령자 <input type="checkbox" name="userType" value="elderly"> &ensp;
					임산부 <input type="checkbox" name="userType" value="pregnant"> &ensp;
					장애인 <input type="checkbox" name="userType" value="disabled">  &ensp;
					<p>
					보조기구 이용자 <input type="checkbox" name="userType" value="assistance">  &ensp;
					휠체어 이용자 <input type="checkbox" name="userType" value="wheelchair">  &ensp;
					<p>
					영유아 동반자 <input type="checkbox" name="userType" value="infant"> 
				</td>
			</tr>
			
			<tr>
				<td colspan="3">
				<%--가입하기 클릭시 회원가입 확인(joinCheck)으로 이동 --%>
				<%--취소 클릭시 로그인 화면으로 이동 --%>
					<input type="submit" value="가입하기"> &nbsp; &nbsp; 
					<input type="button" value="취소" onClick="javascript:location.href='login.jsp'"> 
			</tr>
		</table>	
	</form>
</div>
</body> 
</html>

