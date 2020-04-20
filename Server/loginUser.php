<?php

// getting the require files
require './databaseConnection.php';

//Creating a database_connection object, we will use it in the queries

$connection = new database_connection();

//the response array
$response = array();


//see if the url has the user parameters and the password parameter
if (isset($_GET['pseudo']) && isset($_GET['password'])) {

    $pseudo = $_GET['pseudo'];
    $password = $_GET['password'];
    $response['status'] = 'Okay';

    //the query
    $select_query = "SELECT * FROM users WHERE pseudo='$pseudo' AND password='$password'";
    $select_query_username = "SELECT * FROM users WHERE pseudo='$pseudo' ";


    //getting the result
    $result = mysqli_query($connection->connection, $select_query);
    $result_username = mysqli_query($connection->connection, $select_query_username);
    //check if the result is bigger than 0, so we have a user
    if ($result->num_rows > 0) {

        //in this case we fetch the result in a variable
        $data = mysqli_fetch_array($result);

        //putting the result in the response variable
        $response['id_user'] = $data['id_user'];
        $response['pseudo'] = $data['pseudo'];
		$response['first_name'] = $data['first_name'];
		$response['family_name'] = $data['family_name'];
		$response['year'] = $data['year'];
		$response['field'] = $data['field'];

        //in this case w set the message to success
        $response['message'] = 1;

        //and we output the damn json file
        header('Content-type: application/json');

        echo json_encode($response);

    } else if ($result_username->num_rows > 0) {
        $response['message'] = 2;

        //and we output the damn json file
        header('Content-type: application/json');

        echo json_encode($response);

    } else {
        //if we had no result so the user either is not in the database or password or email are not correct
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