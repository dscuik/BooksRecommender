<?php

require './databaseConnection.php';

//Creating a database_connection object, we will use it in the queries

$connection = new database_connection();

//creating and array in which we gonna store some information and output it in json
$response = array();

    $select_query = "SELECT * FROM books";

    $result = mysqli_query($connection->connection, $select_query);

    //check if the result is bigger than 0, so we have books
    if ($result->num_rows > 0) {
       
        $response['book'] = array();

        while ($data = mysqli_fetch_array($result)) {
            $book = array();
			
			$book['id_book'] =mb_convert_encoding($data['id_book'], 'UTF-8', 'UTF-8');
			$book['ISBN'] =mb_convert_encoding($data['ISBN'], 'UTF-8', 'UTF-8');
			$book['title'] =mb_convert_encoding($data['title'], 'UTF-8', 'UTF-8');
			$book['author'] =mb_convert_encoding($data['author'], 'UTF-8', 'UTF-8');
			$book['year_of_publication'] =mb_convert_encoding($data['year_of_publication'], 'UTF-8', 'UTF-8');
			$book['publisher'] =mb_convert_encoding($data['publisher'], 'UTF-8', 'UTF-8');
			$book['imageURL_L'] =mb_convert_encoding($data['imageURL_L'], 'UTF-8', 'UTF-8');
			
		
			
            array_push($response['book'], $book);
        }

        //putting the result in the response variable
        //in this case w set the message to succes
        $response['message'] = 'success';
        $response['status'] = 1;

        //and we output the damn json file
        header('Content-type: application/json');
        $json = json_encode($response);
		
		if ($json)
			echo $json;
		else
			echo json_last_error_msg();
		
    } else {
        //if we had no result we output it
        $response['message'] = 'there is no books';
        $response['status'] = 0;
        header('Content-type: application/json');
        echo json_encode($response);
    }


