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
    if ( isset( $_POST['user_id'] ) ) {
        $user_id=$_POST['user_id'];
        if (  preg_match( "/[0-9]+/", $user_id )  ) {
            include "Opr.php";
            $opr=new Opr();
            $location=$opr -> get_location( $user_id );
            if ( $info===false )
                $response[STATUS] = 3;
            else {
                $response[STATUS] = 7;
                $response[LOCATION] = $location;
            }
        }else
            $response[STATUS] = 2;
    }else
        $response[STATUS] = 1;
} else
    $response[STATUS] = 0;

echo json_encode( $response );
?>
