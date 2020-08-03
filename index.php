<?php

function Edit_city($con,$id,$city){
    $query = "UPDATE Cases SET City = '$city' WHERE ID = $id";
    if(mysqli_query($con,$query))
        echo "done";
        else
        echo "fuck";
}
function Edit_active($con,$id,$active){
    $query = "UPDATE Cases SET Active = '$active' WHERE ID = $id";
    mysqli_query($con,$query);
}
function Edit_healed($con,$id,$healed){
    $query = "UPDATE Cases SET Healed = '$healed' WHERE ID = $id";
    mysqli_query($con,$query);
}
function Edit_date($con,$id,$date){
    $query = "UPDATE Cases SET TDate = '$date' WHERE ID = $id";
    mysqli_query($con,$query);
}

function login ($con,$name ,$pass){
	$result = mysqli_query($con,"SELECT * FROM users WHERE Username='$name' and Password = '$pass'");
	$count  = mysqli_num_rows($result);
	if($count==0) {
        print_r("Invalid Username or Password!");
        return false;
	} else {
        print_r("Authenticated");
        return true;
    }
    
}

function get_date($con,$start,$end){
    // $date = new DateTime($start);
    // $start=$date->format('Y-m-d');

    // $date = new DateTime($end);
    // $end=$date->format('Y-m-d');

    $query = "SELECT * FROM Cases WHERE TDate BETWEEN '$start' and '$end'";

    $result = mysqli_query($con,$query);
    $result = mysqli_fetch_all($result,MYSQLI_ASSOC);
    // $count = count($result);
    // print_r($count.",");
    foreach($result as $res){
        print_r($res["City"].",".$res["Active"].",".$res["Healed"].",");
    }
}


function get_total($con,$date){

    // $query = "SELECT SUM(Active),SUM(Healed) FROM Cases WHERE TDate IN (SELECT MAX(TDate) from Cases)";
    $query = "SELECT SUM(Active),SUM(Healed) FROM Cases WHERE TDate = '$date'";
    $result = mysqli_query($con,$query);
    $result = mysqli_fetch_all($result);
    print_r($result[0][0].",".$result[0][1]);

}

function get_city($con,$city,$date){
    // $query = "SELECT Active,Healed FROM Cases WHERE City = '$city' AND TDate IN (SELECT MAX(TDate) from Cases)";
    $query = "SELECT Active,Healed FROM Cases WHERE City = '$city' AND TDate = '$date')";
    $result = mysqli_query($con,$query);
    $result = mysqli_fetch_all($result,MYSQLI_ASSOC);
    print_r($result[0]["Active"]." , ". $result[0]["Healed"]);
}


$con = mysqli_connect('localhost','root','','PalCovid');
if(!$con){
    echo "Connection Error: ".mysqli_connect_error();
}


if(!empty($_GET["total"])){

    get_total($con);
}

if(!empty($_GET["city"])){
    $name = $_GET["city"];
    get_city($con,$name);
}


if(!empty($_GET["start"]) && !empty($_GET["end"])){
    $start = $_GET["start"];
    $end = $_GET["end"];
    get_date($con,$start,$end);
}

if(!empty($_POST["ID"])){
    $name = $_POST["name"];
    $pass = $_POST["pass"];
    $id = $_POST["ID"];
    if(login($con,$name,$pass)){
        if(!empty($_POST["city"])){
            Edit_city($con,$id,$_POST["city"]);
        }
        if(!empty($_POST["active"])){
            Edit_active($con,$id,$_POST["active"]);
        }
        if(!empty($_POST["healed"])){
            Edit_healed($con,$id,$_POST["healed"]);
        }
        if(!empty($_POST["date"])){
            Edit_date($con,$id,$_POST["date"]);
        }
    }
}

if(!empty($_POST["city"])&&!empty($_POST["active"])&&!empty($_POST["healed"])&&!empty($_POST["date"])){
    $city = $_POST["city"];
    $active = $_POST["active"];
    $healed = $_POST["healed"];
    $date = $_POST["date"];

    $query = "INSERT INTO Cases (City,Active,Healed,TDate) Values ('$city','$active','$healed','$date')";

    mysqli_query($con,$query);
}

// get_date($con,'2020-7-04','2020-7-09');

// get_total($con,'2020-7-09');

// get_city($con,"Jesrusalem");

// login($con,"hamza","123");

?>
