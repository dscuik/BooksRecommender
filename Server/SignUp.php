<?php
// getting the require files
require './databaseConnection.php';

//Creating a database_connection object, we will use it in the queries

$connection = new database_connection();

//the response array
$response = array();

if (isset($_GET['famillyname']) && isset($_GET['firstname'])&& isset($_GET['password']) && isset($_GET['year'])&& isset($_GET['specialty'])) {
	$famillyname = $_GET['famillyname'];
	$firstname=$_GET['firstname'];
	$pseudo=$firstname."_".$famillyname;
    $password = $_GET['password'];
    $year = $_GET['year'];
	$specialty	= $_GET['specialty'];
	
	$query= "INSERT INTO users VALUES (NULL,'$firstname','$famillyname','$pseudo','$password','$year','$specialty','1')";
    $username_query="SELECT * FROM users WHERE pseudo='$pseudo'";
	
	$result_username_query= mysqli_query($connection->connection, $username_query);
	
	
	
	
	
	if ($result_username_query->num_rows > 0){
		//user name exist
		$response['status']='Existing username';
		$response['message']= 0;
		header('Content-type: application/json');
        echo json_encode($response);
	}else{
	$insert_query= mysqli_query($connection->connection, $query);
    //putting the result in the response variable
    //in this case w set the message to succes
	$response['status']='Okey';
    $response['message'] = 1;
    //and we output json file
    header('Content-type: application/json');
    echo json_encode($response);
	}
	
}else{
	//if we had missing parametrs we output then
    $response['status'] = 'Error';
    $response['message'] = -1;
    header('Content-type: application/json');
    echo json_encode($response);
	
}





?>