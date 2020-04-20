<?php

require './databaseConnection.php';

//Creating a database_connection object, we will use it in the queries

$connection = new database_connection();

//the response array
$response = array();


function sim($id_book_one,$id_book_two){
	
	global $connection, $response;
	
	
// getting the require files

$part=0;
$part1=0;
$part2=0;
$part3=0;

//see if the url has the user parameters and the password parameter




    //the query
	$select_query = "SELECT DISTINCT id_user FROM ratings";
	$result = mysqli_query($connection->connection, $select_query);
	
	
	while ($data = mysqli_fetch_array($result)) {
	
	$id_user = array();
	$id_user['id'] =mb_convert_encoding($data['id_user'], 'UTF-8', 'UTF-8');
	
	$select_query_note_i = "SELECT rating FROM ratings WHERE ((id_user='".$id_user['id']."') AND (id_book='$id_book_one'))";
    $select_query_moy_i = "SELECT avg(rating) FROM ratings WHERE (id_user='".$id_user['id']."')";
	
	$result_note_i = mysqli_query($connection->connection, $select_query_note_i);
	$data_note_i = mysqli_fetch_array($result_note_i);
	$note_i = $data_note_i['rating'];
	
	$result_moy_i = mysqli_query($connection->connection, $select_query_moy_i);
	$data_moy_i = mysqli_fetch_array($result_moy_i);
	$moyenne_i = $data_moy_i['avg(rating)'];

	
	
	$select_query_note_j = "SELECT rating FROM ratings WHERE ((id_user='".$id_user['id']."') AND (id_book='$id_book_two'))";
    $select_query_moy_j = "SELECT avg(rating) FROM ratings WHERE (id_user='".$id_user['id']."')";
	
	$result_note_j = mysqli_query($connection->connection, $select_query_note_j);
	$data_note_j = mysqli_fetch_array($result_note_j);
	$note_j = $data_note_j['rating'];
	
	$result_moy_j = mysqli_query($connection->connection, $select_query_moy_j);
	$data_moy_j = mysqli_fetch_array($result_moy_j);
	$moyenne_j = $data_moy_j['avg(rating)'];
	
	$part1 += ($note_i-$moyenne_i)*($note_j-$moyenne_j);
	$part2 += pow(($note_i-$moyenne_i),2);
	$part3 += pow(($note_i-$moyenne_j),2);
	
	}
	
	
	$part2 = sqrt($part2);
	$part3 = sqrt($part3);
	$part2 = $part2 * $part3;
	
	$part = $part1 / $part2;
	
	
    return $part;
	}
	
   
   if (isset($_GET['id_user']) && isset($_GET['id_book'])) {
	   
	   $id_book = $_GET['id_book'];
	   $id_user = $_GET['id_user'];
	   
	   $part=0;
	   $part1=0;
	   $part2=0;
	   
	   
	   $select_query = "SELECT id_book FROM ratings WHERE (id_book <> '$id_book')";
	   $result = mysqli_query($connection->connection, $select_query);
   
		while ($data = mysqli_fetch_array($result)) {
			
			$id_books = array();
			$id_books['id'] =mb_convert_encoding($data['id_book'], 'UTF-8', 'UTF-8');
			
			$select_query_uk = "SELECT rating FROM ratings WHERE ((id_book = '".$id_books['id']."') AND (id_user = '$id_user'))";
			$select_query_k = "SELECT avg(rating) FROM ratings WHERE (id_book = '".$id_books['id']."')";
			$select_query_i = "SELECT avg(rating) FROM ratings WHERE (id_book = '$id_book')";
			
			$result_uk = mysqli_query($connection->connection, $select_query_uk);
			$data_uk = mysqli_fetch_array($result_uk);
			$R_uk = $data_uk['rating'];
			
			$result_k = mysqli_query($connection->connection, $select_query_k);
			$data_k = mysqli_fetch_array($result_k);
			$R_k = $data_k['avg(rating)'];
			
			
			$part1 =+ sim($id_book,$id_books['id'])*($R_uk - $R_k);
			$part2 =+ abs(sim($id_book,$id_books['id']));
			
		}
			
			$result_i = mysqli_query($connection->connection, $select_query_i);
			$data_i = mysqli_fetch_array($result_i);
			$R_i = $data_i['avg(rating)'];
			
			$part = $R_i + ($part1 / $part2);
	
		$response['p_item'] = $part;


        //in this case we set the message to success
        $response['message'] = "success";

        //and we output the damn json file
        header('Content-type: application/json');

        echo json_encode($response);
	
}