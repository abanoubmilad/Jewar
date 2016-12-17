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
	if ( isset( $_POST['lat'] ) && isset( $_POST['lng'] ) ){
        $lat = $_POST['lat'];
        $lng = $_POST['lng'];
		 if (filter_var($lat, FILTER_VALIDATE_FLOAT) && filter_var($lng, FILTER_VALIDATE_FLOAT) ) {
		 	include "Opr.php";
            $opr=new Opr();
            $check=$opr -> update_location( $_SESSION['id'], $lat, $lng );
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