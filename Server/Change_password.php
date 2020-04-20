<?php 
// getting the require files
require './databaseConnection.php';

//Creating a database_connection object, we will use it in the queries

$connection = new database_connection();

//the response array
$response = array();

//see if the url has the user parameters and the password parameter
if (isset($_GET['pseudo']) && isset($_GET['password'])){
	$pseudo = $_GET['pseudo'];
	$password= $_GET['password'];
	$response['status'] = 'Okay';
	//the query
	$query="UPDATE users SET password = '$password' WHERE pseudo='$pseudo'";
	$user_query="SELECT pseudo FROM users WHERE pseudo='$pseudo'";
	
	//getting the result
    $result = mysqli_query($connection->connection, $query);
	$user_result=mysqli_query($connection->connection, $user_query);
	if($user_result->num_rows > 0){
	
	$response['status'] = 'Success';
    $response['message'] = 1;
	header('Content-type: application/json');
    echo json_encode($response);
	}else{
	$response['status'] = 'User name not found';
    $response['message'] = 0;
	header('Content-type: application/json');
    echo json_encode($response);
	}
	
}else{
	 
    //if we had missing parameters we output then
    $response['status'] = 'Error';
    $response['message'] = -1;
    header('Content-type: application/json');
    echo json_encode($response);

	
}


?>