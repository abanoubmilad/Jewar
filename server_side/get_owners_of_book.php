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
define( "OWNERS", "book_owners" );
$response = array();
session_start();
if ( isset( $_SESSION['id'] ) ) {
    if ( isset( $_POST['book_id'] ) ) {
        $book_id=$_POST['book_id'];
        if (  preg_match( "/[0-9]+/", $book_id )  ) {
            include "Opr.php";
            $opr=new Opr();
            $owners=$opr -> get_owners_of_book( $book_id );
            if ( $owners===false )
                $response[STATUS] = 3;
            else {
                $response[STATUS] = 7;
                $response[OWNERS] = $owners;
            }
        }else
            $response[STATUS] = 2;
    }else
        $response[STATUS] = 1;
} else
    $response[STATUS] = 0;

echo json_encode( $response );
?>
