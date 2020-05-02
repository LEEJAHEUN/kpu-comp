<%--제보글(장소) 작성 페이지 --%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>장소 제보글 작성 페이지</title>
<link href="style2.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div>
		<%--제보글 작성 시 작성한 정보가 placePostCheck.jsp로 전송됨--%>
		<form name="writePlaceForm"action="placePostCheck.jsp" method="post">
			<table>
				<tr>
					<td colspan="2">
						새로운 제보글 등록을 위해 아래 내용을 작성해주세요.<br>
						(필수)항목은 반드시 작성해야 등록 가능합니다.
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<h2>장소 제보글 작성</h2>
					</td>
				</tr>
				
				<tr>
					<td width="40%" align=right>
						<b>이용 가능한 정도(필수)</b>
					</td>
					<td align=left>
						<input type="radio" name="availability" value="available" checked="checked"/> 이용 가능
						<input type="radio" name="availability" value="help"/> 도움 필요
						<input type="radio" name="availability" value="unavailable"/> 이용 불가
					</td>
				</tr>
				
				<tr>
					<td align="right">
						<b>위치(필수)</b>
					</td> 
					<td align=left>
					<%--버튼을 눌러 지도 화면으로 가서 검색하고 결과만 텍스트로 가져옴--%>
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
						<b>엘리베이터 유무</b>
					</td>
					<td align=left>
						<input type="radio" name="elevator" value="true"/> 엘리베이터 있음
						<input type="radio" name="elevator" value="false"/> 엘리베이터 없음
					</td>
				</tr>
				
				<tr>
					<td align=right>
						<b>휠체어 경사로</b>
					</td>
					<td align=left>
						<input type="radio" name="wheelchairSlope" value="true"/> 경사로 있음
						<input type="radio" name="wheelchairSlope" value="false"/> 경사로 없음
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
						<%--취소:지도 화면으로 돌아감 --%>
						<input type="submit" value="작성하기">&nbsp; &nbsp;
						<input type="reset" value="다시쓰기"> &nbsp; &nbsp;
						<input type="button" value="취소" onClick="javascript:location.href='memberMain.jsp'">
					</td>
				</tr>
			
			</table>
		</form>
	</div>
</body> 
</html>
