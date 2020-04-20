<?php

// getting the require files
require './databaseConnection.php';

//Creating a database_connection object, we will use it in the queries

$connection = new database_connection();

//see if the url has the user parameters and the password parameter
if (isset($_GET['id_user']) && isset($_GET['notification'])) {

    $id_user = $_GET['id_user'];
	$notification = $_GET['notification'];

    //the query
    $query = "UPDATE users SET notification='$notification' WHERE id_user='$id_user'";
    $result = mysqli_query($connection->connection, $query);


}