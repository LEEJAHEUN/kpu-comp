<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="user.UserDAO" %> <!-- userdao의 클래스 가져옴 -->
<%@ page import="java.io.PrintWriter" %> <!-- 자바 클래스 사용 -->
<% request.setCharacterEncoding("UTF-8"); %>

<!-- user클래스를 자바 빈즈로 사용, (범위)현재의 페이지에서만 사용-->
<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty name="user" property="*" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입 처리</title>
</head>
<body>
<%-- joinForm.jsp에서 입력한 정보를 넘겨받아 처리 --%>
<%
		if (user.getUserId() == null || user.getUserPW() == null || user.getUserEmail() == null || user.getUserType() == null)
			{
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('입력되지 않은 항목이 있습니다.')");
				script.println("history.back()");
				script.println("</script>");
			}

		else{
				UserDAO userDAO = new UserDAO(); //인스턴스생성
				int result = userDAO.join(user);	

				if(result == -1)
				{ // 아이디가 기본키이므로 중복되면 오류.
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('이미 존재하는 아이디 입니다.')");
					script.println("history.back()");
					script.println("</script>");
				}
				else if(result == 0)
				{
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('비밀번호와 확인이 서로 다릅니다.')");
					script.println("history.back()");
					script.println("</script>");
				}
				//가입성공
				else {
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('회원가입이 완료되었습니다.\n환영합니다!')");
					script.println("</script>");
					response.sendRedirect("login.jsp");
				}
			}
%>
</body>
</html>