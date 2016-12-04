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
	if ( isset( $_POST['email'] ) ){
		$email = $_POST['email'];
		 if (filter_var( $email, FILTER_VALIDATE_EMAIL )) {
		 	include "Opr.php";
            $opr=new Opr();
            $check=$opr -> update_email( $_SESSION['id'], $email );
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