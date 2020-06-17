<?php
	$con=mysqli_connect("localhost", "root", "", "teamProject");

	$userID = $_POST["userID"];
	$userPW = $_POST["userPassword"];
	$userMail = $_POST["userMail"];
	$userType = $_POST["userType"];

	$sql = mysqli_prepare($con, "INSERT into userinformation values(?,?,?,?)");
	mysqli_stmt_bind_param($sql, "ssss", $userID, $userPW, $userMail, $userType);
	mysqli_stmt_execute($sql);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>