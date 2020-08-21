<?php 
  $con=mysqli_connect("localhost", "root", "", "teamProject");

  $userMail = $_POST["userMail"];

  //$userMail = "dkalf@naver.com";

  $sql = mysqli_prepare($con, "SELECT userID from userinformation where userMail = ?");
  mysqli_stmt_bind_param($sql, "s", $userMail);
  mysqli_stmt_execute($sql);

  mysqli_stmt_store_result($sql);
  mysqli_stmt_bind_result($sql, $userID);

  $response = array();

  $response["success"] = false;

  while(mysqli_stmt_fetch($sql)){
  	$response["success"] = true;
    $response["result"] = $userID;
  }

  echo json_encode($response);

?>