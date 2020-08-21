<?php
	$con=mysqli_connect("localhost", "root", "", "teamProject");

	$userID = $_POST["userID"];
	$latitude = number_format((double)$_POST["latitude"], 15);
	$longitude = number_format((double)$_POST["longitude"], 15);
	$availability = $_POST["availability"];
	$type = $_POST["type"];
	$stair = $_POST["stair"];
	$roadBreakage = $_POST["roadBreakage"];
	$slope = $_POST["slope"];

	$sql = mysqli_prepare($con, "INSERT into postRoadInformation(writerID, latitude, longitude, availability, tag, stair, roadBreakage, slope) values(?,?,?,?,?,?,?,?)");

	mysqli_stmt_bind_param($sql, "sddsssss", $userID, $latitude, $longitude, $availability, $type, $stair, $roadBreakage, $slope);
	mysqli_stmt_execute($sql);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>