<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!-- userdao의 클래스 가져옴 -->
<%@ page import="user.UserDAO" %>
<!-- 자바 클래스 사용 -->
<%@ page import="java.io.PrintWriter" %> 

<!-- 한명의 회원정보를 담는 user클래스를 자바 빈즈로 사용 
scope:page 현재의 페이지에서만 사용-->
<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty name="user" property="userId" />
<jsp:setProperty name="user" property="userPW" /> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 처리</title>
</head>
<body>
<% 
	//한글 인코딩 처리
	request.setCharacterEncoding("UTF-8"); 

	//인스턴스 생성
	UserDAO userDAO = new UserDAO();
	int result = userDAO.login(user.getUserId(), user.getUserPW());
	
	//로그인 성공
	if(result == 1)
	{
		session.setAttribute("userId", user.getUserId());
		response.sendRedirect("memberMain.jsp");
	}

	//로그인 실패
	else if(result == 0)
	{
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('비밀번호가 틀립니다.')");
		script.println("history.back()");
		script.println("</script>");
	}

	//아이디 없음
	else if(result == -1)
	{
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('존재하지 않는 아이디 입니다.')");
		script.println("history.back()");
		script.println("</script>");
	}
	
	//DB오류
	else if(result == -2)
	{
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('DB오류가 발생했습니다.')");
		script.println("history.back()");
		script.println("</script>");
	}		

%>
</body>
</html>