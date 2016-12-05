<?php
/**
 * web service
 *
 * @copyright  Abanoub Milad Nassief
 * @license    MIT
 * @created    11/30/2016
 * @edited     11/30/2016
 */
define( "STATUS", "status" );
$response = array();
session_start();
if ( isset( $_SESSION['id'] ) ) {
    if ( isset( $_POST['book_id'] ) && isset( $_POST['title'] ) && isset( $_POST['author'] ) && isset( $_POST['rating'] )&& isset( $_POST['photo_url'] ) ) {
        $book_id=$_POST['book_id'];
        $title=$_POST['title'];
        $author=$_POST['author'];
        $rating=$_POST['rating'];
        $photo_url=$_POST['photo_url'];
        if ( preg_match( "/[0-9]+/", $book_id ) ) {
            include "Opr.php";
            $opr=new Opr();
            $check=$opr -> add_book( $book_id, $title, $author, $rating, $photo_url);
            if ( $check===false )
                $response[STATUS] = 3;
            else
                $response[STATUS] = 7;
        }else
            $response[STATUS] = 2;
    }else
        $response[STATUS] = 1;
} else
    $response[STATUS] = 0;
echo json_encode( $response );
?>
