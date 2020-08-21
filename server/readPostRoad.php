<?php
header('Content-Type: application/json; charset=utf8');
  $con=mysqli_connect("localhost", "root", "", "teamProject");
  //$userID = $_POST["userID"];
  @mysqli_query("set names utf8", $con);

  //$latitude = number_format(37.34264553282121, 14);
  //$longitude = number_format(126.73274517059326, 14);

  $latitude = number_format((double)$_POST["latitude"], 14);
  $longitude = number_format((double)$_POST["longitude"], 14);

  $data = array();

  $select_query = "SELECT * from postRoadInformation where latitude = '$latitude' and longitude = '$longitude'";
  $result_set = mysqli_query($con, $select_query);

  while ($row = mysqli_fetch_array($result_set)){
    array_push($data, 
           array('postNum'=>$row[0],
           'writerID'=>$row[1],
           'latitude'=>$row[2],
           'longtitude' => $row[3],
           'availability' => $row[4],
           'tag' => $row[5],
           'stair' => $row[6],
           'roadBreakage' => $row[7],
           'slope' => $row[8]
       ));

    //echo json_encode($row);
  }
  $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

  echo $json;
  //echo "<pre>"; print_r($data); echo '</pre>';

  mysqli_close($con);

?>