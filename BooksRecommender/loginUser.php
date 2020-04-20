<?php

// getting the require files
require './databaseConnection.php';

//Creating a database_connection object, we will use it in the queries

$connection = new database_connection();

//the response array
$response = array();


//see if the url has the email parameters and the password parameter
if (isset($_GET['email']) && isset($_GET['password'])) {

    $email = $_GET['email'];
    $password = $_GET['password'];
    $response['status'] = 'Okay';

    //the query
    $select_query = "SELECT * FROM users WHERE (email='$email' OR name='$email') AND password='$password'";
    $select_query_email = "SELECT * FROM users WHERE email='$email' OR name='$email' ";


    //getting the result
    $result = mysqli_query($connection->connection, $select_query);
    $result_email = mysqli_query($connection->connection, $select_query_email);
    //check if the result is bigger than 0, so we have a user
    if ($result->num_rows > 0) {

        //in this case we fetch the result in a variable
        $data = mysqli_fetch_array($result);

        //putting the result in the response variable
        $response['id_user'] = $data['_id_user'];
        $response['name'] = $data['name'];


        //in this case w set the message to success
        $response['message'] = 1;

        //and we output the damn json file
        header('Content-type: application/json');

        echo json_encode($response);

    } else if ($result_email->num_rows > 0) {
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