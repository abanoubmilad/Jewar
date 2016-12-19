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
define( "LOCATION", "location" );
$response = array();
session_start();
if ( isset( $_SESSION['id'] ) ) {
    include "Opr.php";
    $opr=new Opr();
    $location=$opr -> get_user_location( $_SESSION['id'] );
    if ( $location===false )
        $response[STATUS] = 3;
    else {
        $response[STATUS] = 7;
        $response[LOCATION] = $location;
    }
} else
    $response[STATUS] = 0;

echo json_encode( $response );
?>
