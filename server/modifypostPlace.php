<?php
	$con=mysqli_connect("localhost", "root", "", "teamProject"); // 데이터베이스 연결

	//$postNum = 1;
	//$userID = "dkdk";
	//$latitude = 37.34028333333333;
	//$longitude = 126.73350666666666;
	//$availability = "1";
	//$type = "휠체어";
	//$elevator = "있음";
	//$wheel = "없음";

	$postNum = $_POST["postNum"];
	$userID = $_POST["userID"];
	$latitude = $_POST["latitude"];
	$longitude = $_POST["longitude"];
	$availability = $_POST["availability"];
	$type = $_POST["type"];
	$elevator = $_POST["elevator"];
	$wheel = $_POST["wheel"];


	if (mysqli_connect_errno()){
 		echo "MySQL 연결 실패 : " . mysqli_connect_error();
	}

	$sql = mysqli_prepare($con, "UPDATE postPlaceInformation SET latitude=?, longitude=?, availability=?, tag=?, elevator=?, wheelchairSlope=? WHERE postNum = ? and writerID = ?");
	mysqli_stmt_bind_param($sql, "ddssssis", $latitude, $longitude, $availability, $type, $elevator, $wheel, $postNum, $userID);
	mysqli_stmt_execute($sql);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>