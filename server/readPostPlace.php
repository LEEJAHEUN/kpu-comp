<?php header('Content-Type: application/json; charset=utf8');
  $con=mysqli_connect("localhost", "root", "", "teamProject");
  //$userID = $_POST["userID"];
  @mysqli_query("set names utf8", $con);
  //$latitude = number_format(37.34028333333333, 14);
  //$longitude = number_format(126.73350666666666, 14);

  $latitude = number_format((double)$_POST["latitude"], 14);
  $longitude = number_format((double)$_POST["longitude"], 14);
  //echo number_format($longitude,15);
  //echo $longitude;

  $data = array();

  $select_query = "SELECT * from postplaceInformation where latitude = '$latitude' and longitude = '$longitude'";

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
  }

  $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo $json;
  //echo "<pre>"; print_r($data); echo '</pre>';

  mysqli_close($con);

?>