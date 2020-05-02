<%--제보글(도로) 관리 페이지 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>길 제보글 수정 페이지</title>
<link href="style2.css" rel="stylesheet" type="text/css">
</head>
<body>
<div>
	<%--제보글 관리는 기존에 작성한 내용을 가져와야 함 --%>
	<%--제보글 수정 시 작성한 정보가 roadPostCheck.jsp로 전송됨--%>
	<form name="manageRoadForm"action="roadPostCheck.jsp" method="post">
		<table>
			<tr>
				<td colspan="2">
					작성한 도로 제보글의 내용입니다.<br>
					수정 시 (필수)항목은 반드시 작성해야 합니다.
				</td>
			</tr>
				
			<tr>
				<td colspan="2">
					<h2>도로 제보글 수정</h2>
				</td>
			</tr>
			
			<tr>
				<td width="40%" align="right">
					<b>이용 가능한 정도(필수)</b>
				</td>
				<td align="left">
					<input type="radio" name="availability" value="available" checked="checked"/> 이용 가능
					<input type="radio" name="availability" value="help"/> 도움 필요
					<input type="radio" name="availability" value="unavailable"/> 이용 불가
				</td>
			</tr>
				
			<tr>
				<td align="right">
					<b>위치(필수)</b>
				</td> 
				<td align="left">
				<%--버튼을 눌러  지도 화면으로 가서 검색하고 결과만 텍스트로 가져옴--%>
				<input type="text" name="poiID" size="30">
				</td> 	
			</tr>
				
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
				
			<tr>
				<td align=right>
					<b>계단 유무</b>
				</td>
				<td align=left>
					<input type="radio" name="stair" value="true"/> 계단 있음
					<input type="radio" name="stair" value="false"/> 계단 없음
				</td>
			</tr>
			
			<tr>
				<td align=right>
					<b>도로 경사 각도</b>
				</td>
				<td align=left>
					<input type="radio" name="slope" value="1"/> 있음
					<input type="radio" name="slope" value="0"/> 없음
				</td>
			</tr>
				
			<tr> 
				<td align=right>
					<b>도로 파손 유무</b>
				</td> 
				<td align=left>
					<input type="radio" name="roadBreakage" value="false"/> 파손되지 않음
					<input type="radio" name="roadBreakage" value="true"/> 파손됨
				</td> 
			</tr> 
			
			<tr>
				<td align="right">
					<b>사진 첨부</b>
				</td> 
				<td align="left">
					<input type="text" name="postImage" size="30"/> 
					<input type="button" name="findImage" value="사진 찾기">
				</td> 
			</tr>
			
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<%--수정하기:제보글 확인으로 이동 --%>
					<%--다시쓰기:제보글 내용 초기화 --%>
					<%--취소:제보글 목록 화면으로 돌아감 --%>
					<input type="submit" value="수정하기">&nbsp; &nbsp;
					<input type="reset" value="다시쓰기"> &nbsp; &nbsp;
					<input type="button" value="취소" onClick="javascript:location.href='myPost.jsp'">
				</td>
			</tr>
		
		</table>
	</form>
</div>
</body>
</html>