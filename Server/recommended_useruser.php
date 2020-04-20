<?php

require './databaseConnection.php';

//Creating a database_connection object, we will use it in the queries

$connection = new database_connection();

//the response array
$response = array();
$response1 = array();


function pearson($id_user_one,$id_user_two){
	
	global $connection, $response;
	
	
// getting the require files

$part=0;
$part1=0;
$part2=0;
$part3=0;

//see if the url has the user parameters and the password parameter
	
	
    //the query
	$select_query = "SELECT id_book FROM books";
	$result = mysqli_query($connection->connection, $select_query);
	
	
	while ($data = mysqli_fetch_array($result)) {
	
	$id_book = array();
	$id_book['id'] =mb_convert_encoding($data['id_book'], 'UTF-8', 'UTF-8');
	
	$select_query_note_i = "SELECT rating FROM ratings WHERE ((id_book='".$id_book['id']."') AND (id_user='$id_user_one'))";
    $select_query_moy_i = "SELECT avg(rating) FROM ratings WHERE (id_user='$id_user_one')";
	
	$result_note_i = mysqli_query($connection->connection, $select_query_note_i);
	$data_note_i = mysqli_fetch_array($result_note_i);
	$note_i = $data_note_i['rating'];
	
	$result_moy_i = mysqli_query($connection->connection, $select_query_moy_i);
	$data_moy_i = mysqli_fetch_array($result_moy_i);
	$moyenne_i = $data_moy_i['avg(rating)'];

	
	
	$select_query_note_j = "SELECT rating FROM ratings WHERE ((id_book='".$id_book['id']."') AND (id_book='$id_user_two'))";
    $select_query_moy_j = "SELECT avg(rating) FROM ratings WHERE (id_user='$id_user_two')";
	
	$result_note_j = mysqli_query($connection->connection, $select_query_note_j);
	$data_note_j = mysqli_fetch_array($result_note_j);
	$note_j = $data_note_j['rating'];
	
	$result_moy_j = mysqli_query($connection->connection, $select_query_moy_j);
	$data_moy_j = mysqli_fetch_array($result_moy_j);
	$moyenne_j = $data_moy_j['avg(rating)'];
	
	$part1 += ($note_i-$moyenne_i)*($note_j-$moyenne_j);
	$part2 += pow(($note_i-$moyenne_i),2);
	$part3 += pow(($note_j-$moyenne_j),2);
	
	}
	
	$part2 = sqrt($part2);
	$part3 = sqrt($part3);
	$part2 = $part2 * $part3;
	
	if($part2 ==0) {
		$part=0;
	} else {
	$part = $part1 / $part2;
	}
	
	
	
    return $part;
	}
	
   
   if (isset($_GET['id_user'])) {
	   
	   $id_user = $_GET['id_user'];
	   $response['book'] = array();
	   
	   
	   $select_query = "SELECT DISTINCT id_user FROM ratings WHERE (id_user != '$id_user') GROUP BY id_user HAVING (avg(rating)>4)";
	   $result = mysqli_query($connection->connection, $select_query);

		while ($data = mysqli_fetch_array($result)) {
			
			$id_users = array();
			$id_users['id_user'] =mb_convert_encoding($data['id_user'], 'UTF-8', 'UTF-8');
			
					if(pearson($id_user,$id_users['id_user'])>0.90) {
						
						$select_query_books = "SELECT id_book FROM ratings a WHERE NOT EXISTS 
						(SELECT DISTINCT NULL FROM ratings b WHERE ((a.id_book=b.id_book)AND(id_user = '$id_user')))
						AND(id_user = '".$id_users['id_user']."')AND(rating>3)";
						
						$result_books = mysqli_query($connection->connection, $select_query_books);
						
						while ($data = mysqli_fetch_array($result_books)) {
						array_push($response1, $data['id_book']);
						}
					}
		}
		
		if( sizeof($response1)>0 )
{
		$str = implode(",",$response1);
		$sql = "SELECT DISTINCT * FROM books WHERE id_book IN ($str)";
		$res = mysqli_query($connection->connection, $sql);
		
	
		while ($data = mysqli_fetch_array($res)) {
						$id_books = array();
						
						$id_books['id_book'] =mb_convert_encoding($data['id_book'], 'UTF-8', 'UTF-8');
						$id_books['ISBN'] =mb_convert_encoding($data['ISBN'], 'UTF-8', 'UTF-8');
						$id_books['title'] =mb_convert_encoding($data['title'], 'UTF-8', 'UTF-8');
						$id_books['author'] =mb_convert_encoding($data['author'], 'UTF-8', 'UTF-8');
						$id_books['year_of_publication'] =mb_convert_encoding($data['year_of_publication'], 'UTF-8', 'UTF-8');
						$id_books['publisher'] =mb_convert_encoding($data['publisher'], 'UTF-8', 'UTF-8');
						$id_books['imageURL_L'] =mb_convert_encoding($data['imageURL_L'], 'UTF-8', 'UTF-8');
						array_push($response['book'], $id_books);
		}
}
//and we output the damn json file
        header('Content-type: application/json');

        echo json_encode($response);

	
}