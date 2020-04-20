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
    $select_query = "SELECT books.id_book,ratings.rating,books.title,books.author FROM ratings,books WHERE ((books.id_book=ratings.id_book)AND(id_user='$id_user'))";


    //getting the result
    $result = mysqli_query($connection->connection, $select_query);
	
    //check if the result is bigger than 0, so we have a rating


        //in this case we fetch the result in a variable
        
		$response['rating'] = array();

        while ($data = mysqli_fetch_array($result)) {
            $book = array();
			
			$book['id_book'] =mb_convert_encoding($data['id_book'], 'UTF-8', 'UTF-8');
			$book['title'] =mb_convert_encoding($data['title'], 'UTF-8', 'UTF-8');
			$book['author'] =mb_convert_encoding($data['author'], 'UTF-8', 'UTF-8');
			$book['rating'] =mb_convert_encoding($data['rating'], 'UTF-8', 'UTF-8');
			
            array_push($response['rating'], $book);
        }
		
		
        //in this case we set the message to success
        $response['message'] = 1;

        //and we output the damn json file
        header('Content-type: application/json');

        echo json_encode($response);

    
} else {
    //if we had missing parameters we output then
    $response['status'] = 'Error';
    $response['message'] = -1;
    header('Content-type: application/json');
    echo json_encode($response);
}