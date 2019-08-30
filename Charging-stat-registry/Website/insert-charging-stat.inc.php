<?php

$link = mysqli_connect("127.0.0.1", "root", "ziyadelba1997nna", "mydb");

if($link === false){
    die("ERROR: Could not connect. " . mysqli_connect_error());
}

$station_ID = $_POST['pass'];
$Name = $_POST['name'];
$Address = $_POST['address'];

session_start();

$Car_ID = $_SESSION['car_id'];
$ID = $_SESSION['id'];
$owner_Name =$_SESSION['owner-name'] ;
$email = $_SESSION['email'] ;
$Car_name = $_SESSION['car_name'] ;
$Car_year =$_SESSION['car_year'];
$Car_purchase_date =$_SESSION['car_pur_date'] ;
$phone =$_SESSION['phone'];

$query = "INSERT INTO owner  VALUES ('$ID', '$owner_Name','$email','$phone');";
$query .= "INSERT INTO car VALUES ('$Car_ID', '$Car_name','$Car_year','$Car_purchase_date', '$ID');";
$query .= "INSERT INTO `charging station`  VALUES ('$station_ID ','$Name','$Address');";
$query .= "INSERT INTO `charging station_has_car` VALUES ('$station_ID','$Car_ID','$ID');";


if($link->multi_query($query) or die (mysqli_error($link))) {
    echo "Records added successfully.";
    header("Location: index.html?signup=success");

} else{
    echo "ERROR: Could not able to execute " . mysqli_error($link);
    header("Location: Charging-stat-info.html?signup=failed");

}
mysqli_close($link);
?>
