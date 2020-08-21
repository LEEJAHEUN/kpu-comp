<?php
	$con=mysqli_connect("localhost", "root", "", "teamProject");

	//$postNum = 3;
	//$userID = "dkdk";

	$postNum = $_POST["postNum"];
	$userID = $_POST["userID"];

	$sql = mysqli_prepare($con, "DELETE from postRoadInformation where postNum = ? and writerID = ?");

	mysqli_stmt_bind_param($sql, "is", $postNum, $userID);
	mysqli_stmt_execute($sql);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>