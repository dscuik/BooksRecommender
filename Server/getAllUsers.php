<?php

require './databaseConnection.php';

//Creating a database_connection object, we will use it in the queries

$connection = new database_connection();

//creating and array in which we gonna store some information and output it in json
$response = array();

    $select_query = "SELECT * FROM users GROUP BY first_name";

    $result = mysqli_query($connection->connection, $select_query);

    //the result is bigger than 0, so we always have users
       
        $response['user'] = array();

        while ($data = mysqli_fetch_array($result)) {
            $user = array();
			
			$user['id_user'] =mb_convert_encoding($data['id_user'], 'UTF-8', 'UTF-8');
			$user['first_name'] =mb_convert_encoding($data['first_name'], 'UTF-8', 'UTF-8');
			$user['family_name'] =mb_convert_encoding($data['family_name'], 'UTF-8', 'UTF-8');
			$user['year'] =mb_convert_encoding($data['year'], 'UTF-8', 'UTF-8');
			$user['field'] =mb_convert_encoding($data['field'], 'UTF-8', 'UTF-8');
			
		
			
            array_push($response['user'], $user);
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



