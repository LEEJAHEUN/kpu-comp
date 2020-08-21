<?php 
  $con=mysqli_connect("localhost", "root", "", "teamProject");

  $Address = $_POST["Ad"];

  $sql = mysqli_prepare($con, "INSERT into mapLocation values(?)");
  mysqli_stmt_bind_param($sql, "s", $Address);
  mysqli_stmt_execute($sql);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);

?>