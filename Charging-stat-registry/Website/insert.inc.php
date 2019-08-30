<?php
$link = mysqli_connect("127.0.0.1", "root", "ziyadelba1997nna", "mydb");
if($link === false){
    die("ERROR: Could not connect. " . mysqli_connect_error());
}

$Name = $_POST['owner-name'];
$email = $_POST['email'];
$phone = $_POST['phone'];
$ID = $_POST['owner-ID'];
$Car_ID = $_POST['pass'];
$Car_name = $_POST['model_name'];
$Car_year = $_POST['model_year'];
$Car_purchase_date = $_POST['date_purchased'];

session_start();

$_SESSION['car_id'] = $Car_ID;
$_SESSION['id'] = $ID;
$_SESSION['owner-name'] = $Name;
$_SESSION['email'] = $email;
$_SESSION['car_name'] = $Car_name;
$_SESSION['car_year'] = $Car_year;
$_SESSION['car_pur_date'] = $Car_purchase_date;
$_SESSION['phone'] = $phone;


/*$query = "INSERT INTO owner  VALUES ('$ID', '$Name','$email','$phone');";
$query .= "INSERT INTO car VALUES ('$Car_ID', '$Car_name','$Car_year','$Car_purchase_date', '$ID');";

if($link->multi_query($query) or die (mysqli_error($link))){
    echo ("Records added successfully.");
    header("Location: Charging-stat-info.html?signup=success");
} else{
    echo ("ERROR: Could not able to execute. " . mysqli_error($link));
    header("Location: Form-filling.html?signup=failed");
}*/
header("Location: Charging-stat-info.html?signup=continue");
mysqli_close($link);
?>
<a href="insert-charging-stat.inc.php" title="">Go to the other page</a>
