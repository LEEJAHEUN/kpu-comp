<?php 
  $con=mysqli_connect("localhost", "root", "", "teamProject");

  $userID = $_POST["userID"];

  //$userID = "dkdk";

  $sql = mysqli_prepare($con, "SELECT userPW from userinformation where userID = ?");
  mysqli_stmt_bind_param($sql, "s", $userID);
  mysqli_stmt_execute($sql);

  mysqli_stmt_store_result($sql);
  mysqli_stmt_bind_result($sql, $userPW);

  $response = array();

  $response["success"] = false;

  while(mysqli_stmt_fetch($sql)){
  	$response["success"] = true;
    $response["result"] = $userPW;
  }

  echo json_encode($response);

?>