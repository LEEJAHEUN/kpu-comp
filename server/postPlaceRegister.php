<?php
	$con=mysqli_connect("localhost", "root", "", "teamProject");
	
	$userID = $_POST["userID"];
	$latitude = number_format((double)$_POST["latitude"], 15);
	$longitude = number_format((double)$_POST["longitude"], 15);
	$availability = $_POST["availability"];
	$type = $_POST["type"];
	$elevator = $_POST["elevator"];
	$wheel = $_POST["wheel"];

	$sql = mysqli_prepare($con, "INSERT into postPlaceInformation(writerID, latitude, longitude, availability, tag, elevator, wheelchairSlope) values(?,?,?,?,?,?,?)");
	mysqli_stmt_bind_param($sql, "sddssss", $userID, $latitude, $longitude, $availability, $type, $elevator, $wheel);
	mysqli_stmt_execute($sql);

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>