<?php 
  $con=mysqli_connect("localhost", "root", "", "teamProject");

  $userID = $_POST["userID"];
  $userPassword = $_POST["userPassword"];

  $sql = mysqli_prepare($con, "SELECT userID, userPW from userinformation where userID = ? and userPW = ?");
  mysqli_stmt_bind_param($sql, "ss", $userID, $userPassword);
  mysqli_stmt_execute($sql);

  mysqli_stmt_store_result($sql);
  mysqli_stmt_bind_result($sql, $userID, $userPW);

  $response = array();

  $response["success"] = false;

  while(mysqli_stmt_fetch($sql)){
  	$response["success"] = true;
    $response["userID"] = $userID;
    $response["userPW"] = $userPW;
  }

  echo json_encode($response);

?>