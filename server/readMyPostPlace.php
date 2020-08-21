<?php header('Content-Type: application/json; charset=utf8');
  $con=mysqli_connect("localhost", "root", "", "teamProject");

  //$userID = "dkdk";
  $userID = $_POST["userID"];
  $data = array();

  $select_query = "SELECT * from postPlaceInformation where writerID = '$userID'";
  $result_set = mysqli_query($con, $select_query);

  while ($row = mysqli_fetch_array($result_set)){
    array_push($data, 
           array('postNum'=>$row[0],
           'writerID'=>$row[1],
           'latitude'=>$row[2],
           'longtitude' => $row[3],
           'availability' => $row[4],
           'tag' => $row[5],
           'elevator' => $row[6],
           'wheelchairSlope' => $row[7]
       ));

    //echo json_encode($row);
  }
  $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo $json;
  //echo "<pre>"; print_r($data); echo '</pre>';

  mysqli_close($con);
?>