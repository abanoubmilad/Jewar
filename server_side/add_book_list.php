<?php
/**
 * web service
 *
 * @copyright  Abanoub Milad Nassief
 * @license    MIT
 * @created    11/30/2016
 * @edited     11/30/2016
 */
define( "STATUS", "s" );
$response = array();
session_start();
if ( isset( $_SESSION['id'] ) ) {
    if ( isset( $_POST['book_id'] ) && isset( $_POST['book_status'] ) ) {
        $book_id=$_POST['book_id'];
        $book_status=$_POST['book_status'];
        if ( preg_match( "/[0-9]+/", $book_id ) && ($book_status =="o" || $book_status =="s") ) {
            include "Opr.php";
            $opr=new Opr();
            $check=$opr -> add_book_list( $_SESSION['id'], $book_id, $book_status );
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
