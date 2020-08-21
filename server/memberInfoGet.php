<?php
  $con=mysqli_connect("localhost", "root", "", "teamProject");
  //$userID = $_POST["userID"];
  @mysqli_query("set names utf8", $con);

  $userID = $_POST["userID"];

  $sql = mysqli_prepare($con, "SELECT * from userinformation where userID = ?");
  mysqli_stmt_bind_param($sql, "s", $userID);
  mysqli_stmt_execute($sql);

  mysqli_stmt_store_result($sql);
  mysqli_stmt_bind_result($sql, $userID, $userPW, $userMail, $userType);

  $response = array();
  $response["success"] = false;

  while(mysqli_stmt_fetch($sql)){
    $response["success"] = true;
    $response["userID"] = $userID;
    $response["userPW"] = $userPW;
    $response["userMail"] = $userMail;
    $response["userType"] = $userType;
  }

  echo json_encode($response);

?>