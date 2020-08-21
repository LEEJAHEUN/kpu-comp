<?php
	$con=mysqli_connect("localhost", "root", "", "teamProject"); // 데이터베이스 연결
	@mysqli_query("set names utf8", $con);

	//$postNum = 1;
	//$userID = "dkdk";
	//$latitude = 37.34264553282121;
	//$longitude = 126.73274517059326;
	//$availability = "2";
	//$type = "휠체어";
	//$stair = "없음";
	//$roadBreakage = "있음";
	//$slope = "높음";

	$postNum = $_POST["postNum"];
	$userID = $_POST["userID"];
	$latitude = number_format((double)$_POST["latitude"], 15);
	$longitude = number_format((double)$_POST["longitude"], 15);
	$availability = $_POST["availability"];
	$type = $_POST["type"];
	$stair = $_POST["stair"];
	$roadBreakage = $_POST["roadBreakage"];
	$slope = $_POST["slope"];

	if (mysqli_connect_errno()){
 		echo "MySQL 연결 실패 : " . mysqli_connect_error();
	}

	$sql = mysqli_prepare($con, "UPDATE postRoadInformation SET latitude=?, longitude=?, availability=?, tag=?, stair=?, roadBreakage=?, slope=? WHERE postNum = ? and writerID = ?");
	mysqli_stmt_bind_param($sql, "ddsssssis", $latitude, $longitude, $availability, $type, $stair, $roadBreakage, $slope, $postNum, $userID);
	mysqli_stmt_execute($sql);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>