<?php

require './databaseConnection.php';

//Creating a database_connection object, we will use it in the queries

$connection = new database_connection();


//see if the url has the user parameters and the password parameter
if (isset($_GET['id_user']) && isset($_GET['id_book']) && isset($_GET['rating'])) {

    $id_user = $_GET['id_user'];
    $id_book = $_GET['id_book'];
    $rating = $_GET['rating'];

    $insert_query = "INSERT INTO ratings VALUES($id_user,$id_book,$rating)";
	$insert_query_alternative = "UPDATE ratings SET rating=$rating WHERE (id_user=$id_user AND id_book=$id_book)";
	$verify_query = "SELECT id_user FROM ratings WHERE (id_user=$id_user AND id_book=$id_book)";
		
	$result_verify = mysqli_query($connection->connection, $verify_query);
	
	if ($result_verify->num_rows > 0) {
			echo "We have records.";
			$result_alternative = mysqli_query($connection->connection, $insert_query_alternative);
		} else {
			echo "We don't have records. ";
			$result = mysqli_query($connection->connection, $insert_query);
			}

} else {
    //if we had missing parameters we output then
    $response['status'] = 'Error';
    $response['message'] = -1;
    header('Content-type: application/json');
    echo json_encode($response);
}

