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
if ( isset( $_POST['email'] ) && isset( $_POST['pass'] ) && isset( $_POST['name'] ) ) {
    $email = $_POST['email'];
    $password = $_POST['pass'];
    $name = $_POST['name'];
    $mobile="";
    if(isset( $_POST['mobile'] ))
        $mobile = $_POST['mobile'];  
    if ( filter_var( $email, FILTER_VALIDATE_EMAIL ) && preg_match( "/[a-zA-Z0-9]{5,20}/", $password )&& preg_match( "/[a-zA-Z]{4,40}/", $name ) && ($mobile=="" || preg_match( "/[0-9]{5,20}/", $mobile )) ) {
        include "Opr.php";
        $opr=new Opr();
        $check=$opr -> get_email( $email );
        if ( $check===false ){
            $id=$opr -> add_user( $email, $password, $name, $mobile );
            if ( $id===false )
                $response[STATUS] = 3;
            else {
                session_start();
                $_SESSION['id'] = $id;
                $response[STATUS] = 7;
            }
        }else{
           $response[STATUS] = 4;
        }
    }else
        $response[STATUS] = 2;
} else
    $response[STATUS] = 1;
echo json_encode( $response );
?>
