<?php
	$con=mysqli_connect("localhost", "root", "", "teamProject"); // 데이터베이스 연결

	$ID = $_POST["userID"];
	$userPW = $_POST["userPW"];
	$userMail = $_POST["userMail"];
	$userType = $_POST["userType"];

	//$ID = "dkdk";
	//$userPW = "dkdkaa";
	//$userMail = "dkalf@naver.com";
	//$userType = "보조기구 이용자";

	if (mysqli_connect_errno()){
 		echo "MySQL 연결 실패 : " . mysqli_connect_error();
	}

	$sql = mysqli_prepare($con, "UPDATE userinformation SET userPW = ?, userMail = ?, userType = ? WHERE userID = ?");
	mysqli_stmt_bind_param($sql, "ssss", $userPW, $userMail, $userType, $ID);
	mysqli_stmt_execute($sql);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>