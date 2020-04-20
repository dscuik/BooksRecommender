<?php

// getting the require files
require './databaseConnection.php';

//Creating a database_connection object, we will use it in the queries

$connection = new database_connection();

//the response array
$response = array();


//see if the url has the user parameters and the password parameter
if (isset($_GET['id_user'])) {

    $id_user = $_GET['id_user'];

    //the query
    $select_query = "SELECT notification FROM users WHERE (id_user='$id_user')";
    $result = mysqli_query($connection->connection, $select_query);
	

    $data = mysqli_fetch_array($result);

    $response['notification'] = $data['notification'];

        
	header('Content-type: application/json');

    echo json_encode($response);

}