<%--회원정보 수정 페이지 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="user.UserDAO" %> <!-- userdao의 클래스 가져옴 -->
<%@ page import="user.User" %>
<% request.setCharacterEncoding("UTF-8"); %>

<!-- user클래스를 자바 빈즈로 사용, (범위)현재의 페이지에서만 사용-->
<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty name="user" property="*" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>회원정보 수정 페이지</title>
<link href="style2.css" rel="stylesheet" type="text/css">
</head>
<body onLoad="userInfoForm.pwd.focus()">
<%
	//세션값을 얻어옴
	String userId = session.getAttribute("userId").toString();
	System.out.println(userId);

	//세션에 저장된 아이디를 가져와 해당 아이디의 회원정보를 가져옴
	UserDAO userDAO = new UserDAO();
	User userNow = userDAO.getUserInfo(userId);
%>
<div>
	<%--정보 수정 시 작성한 정보가 userInfoCheck.jsp로 전송됨--%>
	<form name="userInfoForm" method="post" action="userInfoCheck.jsp">
		<table>
			<tr>
				<td colspan="2">
					<h2>내 정보</h2>
					<%=userId%>님의 회원 정보입니다.
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			
			<tr>
			<%--아이디는 수정 불가함.DB에서 불러와서 보여주기만 하도록 수정할 것 --%>	 
				<td width="40%" align=right>
					<b>아이디</b>
				</td> 
				<td align=left> 
					<%=userNow.getUserId()%>
				</td> 
			</tr>
			
			<tr> 
				<td align=right>
					<b>비밀번호</b>
				</td> 
				<td align=left> 
					<input type="password" name="userPW" size="30">
					<%-- value="<%=bean.getPwd()%>" --%>
				</td> 
			</tr>
			
			<tr> 
				<td align=right>
					<b>비밀번호 확인</b>
				</td> 
				<td align=left>
					<input type="password" name="rePW" size="30">
					<%--비밀번호 확인은 지워둔다 --%>
				</td>
			</tr>
			
			<tr> 
				<td align=right>
				<b>이메일</b>
				</td> 
				<td align=left>
					<input name="userEmail" size="30" value="<%=userNow.getUserEmail()%>">
					<input type="button" value="이메일 중복확인" onClick="emailCheck(this.form.email.value)">
				</td> 
			</tr> 
			 
			<tr> 
				<td align=right>
					<b>교통약자 유형</b>
				</td>
				<td align=left>
				<% 
					String ut[] = {"elderly","pregnant","disabled","assistance","wheelchair","infant"};
					String userType[] = new String[6];
					String userNowType[] = userNow.getUserType();
					System.arraycopy(userNowType,0,userType,0,userNowType.length);
					String flag[]={"","","","","",""};
					int i,j;
					for(i=0; i<userType.length; i++)	//체크 여부는 사용자가 체크한 개수만큼 반복
					{ 
						System.out.println(ut[i]);
						System.out.println(userType[i]);
						
						for(j=0;j<ut.length;j++)	//모든 교통약자 유형과 비교해 체크
						{
							if(userType[i]==null){break;}
							else
							{
								flag[j] = ((ut[j].toString()).equals(userType[i]) ? "checked" : "");
								System.out.println(flag[i]);
								System.out.println(ut[j]);
								System.out.println(userType[i]);
								System.out.println(i+","+j);
								if(flag[j].equals("checked")){break;}
							}
						}
					} 
				%> 
					<br>고령자 <input type="checkbox" name="userType" value="elderly"<%=flag[0]%>> &ensp;
					임산부 <input type="checkbox" name="userType" value="pregnant"<%=flag[1]%>> &ensp;
					장애인 <input type="checkbox" name="userType" value="disabled"<%=flag[2]%>>  &ensp;
					<p>
					보조기구 이용자 <input type="checkbox" name="userType" value="assistance"<%=flag[3]%>>  &ensp;
					휠체어 이용자 <input type="checkbox" name="userType" value="wheelchair"<%=flag[4]%>>  &ensp;
					<p>
					영유아 동반자 <input type="checkbox" name="userType" value="infant"<%=flag[5]%>> 
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<%--수정하기:(회원)기본 지도 화면으로 이동 --%>
					<%--다시쓰기:내용 초기화 --%>
					<%--취소:(회원)기본 지도 화면으로 돌아감 --%>
					<input type="submit" value="수정하기">&nbsp; &nbsp;
					<input type="reset" value="다시쓰기"> &nbsp; &nbsp;
					<input type="button" value="취소" onClick="javascript:location.href='memberMain.jsp'">
				</td>
			</tr>
			 
		</table>
	</form>
</div>
</body>
</html>