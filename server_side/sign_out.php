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
    unset($_SESSION['id']); 
    session_destroy();
    $response[STATUS] = 7;
} else
    $response[STATUS] = 0;
echo json_encode( $response );
?>