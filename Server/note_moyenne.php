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
    $response['status'] = 'Okay';

    //the query
    $select_query = "SELECT avg(rating),id_user FROM ratings WHERE (id_user='$id_user')";


    //getting the result
    $result = mysqli_query($connection->connection, $select_query);
	
    //check if the result is bigger than 0, so we have a rating
    

        //in this case we fetch the result in a variable
        $data = mysqli_fetch_array($result);

        //putting the result in the response variable
        $response['id_user'] = $data['id_user'];
        $response['avg(rating)'] = $data['avg(rating)'];
		
if ($response['avg(rating)'] > 0) {
	
        //in this case we set the message to success
        $response['message'] = 1;

        //and we output the damn json file
        header('Content-type: application/json');

        echo json_encode($response);

    } else {
        //if we had no result so the user didn't rate the book
        $response['message'] = 0;
        header('Content-type: application/json');

        echo json_encode($response);
    }
} else {
    //if we had missing parameters we output then
    $response['status'] = 'Error';
    $response['message'] = -1;
    header('Content-type: application/json');
    echo json_encode($response);
}