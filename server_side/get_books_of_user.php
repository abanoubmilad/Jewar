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
define( "BOOKS", "books" );
$response = array();
session_start();
if ( isset( $_SESSION['id'] ) ) {
    if ( isset( $_POST['book_status'] ) ) {
        $book_status=$_POST['book_status'];
        if ( $book_status =="o" || $book_status =="s" ) {
            include "Opr.php";
            $opr=new Opr();
            $books=$opr -> get_books_of_user( $_SESSION['id'], $book_status );
            if ( $books===false )
                $response[STATUS] = 3;
            else {
                $response[STATUS] = 7;
                $response[BOOKS] = $books;
            }
        }else
            $response[STATUS] = 2;
    }else
        $response[STATUS] = 1;
} else
    $response[STATUS] = 0;

echo json_encode( $response );
?>
