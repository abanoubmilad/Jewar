<?php
/**
 * web service
 *
 * @copyright  Abanoub Milad Nassief
 * @license    MIT
 * @created    11/30/2016
 * @edited     11/30/2016
 */
class Opr {
	const TB_BOOK = "book_tb", TB_USER = "user_tb",
	TB_BOOK_USER = "book_user_tb";
/*
	attributes go here
`book_tb` 
  `book_id` varchar(20) NOT NULL,
  `title` varchar(100) NOT NULL,
  `author` varchar(100) NOT NULL,
  `rating` varchar(10) NOT NULL,
  `photo_url` varchar(100) NOT NULL

`book_user_tb` 
  `user_fk` int(11) NOT NULL,
  `book_fk` int(11) NOT NULL,
  `book_status` char(1) NOT NULL


`user_tb` 
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `mobile` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `map_lat` double NOT NULL,
  `map_lng` double NOT NULL,
  PRIMARY KEY (`user_id`)
*/

	// The database connection
	protected static $db;
	function __construct() {
		include 'DB.php';
		self::$db = new DB();
	}




	/*                    user related operations go here          */





	/**
	 * validate the user access to the database
	 *
	 * @param string  $email the user's email
	 * @param string  $pass  the user's password
	 * @return int user's id if exists, false otherwise
	 */
	public function get_id( $email, $password ) {
		$query ="SELECT user_id FROM ". self::TB_USER ." WHERE email='$email' AND password='$password' LIMIT 1";
		$result =self::$db -> query( $query );
		if ( mysqli_num_rows( $result )>0 ) {
			$row=$result -> fetch_assoc();
			return $row['user_id'];
		}
		return false;
	}
	/**
	 * check the existance of an email address in the database
	 *
	 * @param string  $email the user's email string
	 * @return bool true if email exists, false otherwise
	 */
	public function get_email( $email ) {
		$query ="SELECT user_id FROM ".self::TB_USER." WHERE email='$email' LIMIT 1";
		$result =self::$db -> query( $query );
		return mysqli_num_rows( $result )>0;
	}
	/**
	 * add user to the database
	 *
	 * @param string  $email    the user's email
	 * @param string  $password the user'password
	 * @return int id of the user
	 */
	public function add_user( $email, $password, $name, $mobile ) {
		$query ="INSERT INTO ".self::TB_USER." VALUES (NULL,'$name', '$mobile','$email','$password','','')";
		return self::$db -> insert( $query );
	}
	/**
	 * add user to the database
	 *
	 * @param string  $email       the user's email
	 * @param string  $password    the user's password
	 * @param string  $name        the user's name
	 * @param string  $mobile      the user's mobile
	 * @param string  $lat         the user's telephone
	 * @param string  $lng         the user's address
	 * @param string  $photo       the user's notification rate
	 * @return int id of the user
	 */
	public function add_user_excess( $email, $password, $name, $mobile, $lat, $lng ) {
		$query ="INSERT INTO ".self::TB_USER." VALUES (NULL,'$name', '$mobile','$email','$password','$lat','$lng')";
		return self::$db -> insert( $query );
	}

	/**
	 * update user info
	 *
	 * @param int     $user_id the user's id
	 * @param string  $name      the user's name
	 * @param string  $mobile    the user's mobile
	 * @param string  $telephone the user's telephone
	 * @param string  $address   the user's address
	 * @return bool true if success, false if failure
	 */
	public function update_user_info( $user_id, $name, $mobile, $lat, $lng, $photo ) {
		$query ="UPDATE ".self::TB_USER." SET name='$name', mobile='$mobile', map_lat='$lat', map_lng='$lng' WHERE user_id='$user_id'";
		return self::$db -> query_and_check( $query );
	}
		/**
	 * update user info
	 *
	 * @param int     $user_id the user's id
	 * @param string  $address   the user's address
	 * @return bool true if success, false if failure
	 */
	public function update_user_location( $user_id,  $lat, $lng ) {
		$query ="UPDATE ".self::TB_USER." SET map_lat='$lat', map_lng='$lng' WHERE user_id='$user_id'";
		return self::$db -> query_and_check( $query );
	}
			/**
	 * update user info
	 *
	 * @param int     $user_id the user's id
	 * @param string  $address   the user's address
	 * @return bool true if success, false if failure
	 */
	public function update_user_photo( $user_id,  $photo) {
		$query ="UPDATE ".self::TB_USER." SET photo='$photo' WHERE user_id='$user_id'";
		return self::$db -> query_and_check( $query );
	}
	/**
	 * update user's email
	 *
	 * @param int     $user_id the user's id
	 * @param string  $new_email the user's new email
	 * @return bool true if success, false if failure
	 */
	public function update_email( $user_id, $new_email ) {
		$query ="UPDATE ".self::TB_USER." SET email='$new_email' WHERE user_id='$user_id'";
		return self::$db -> query_and_check( $query );
	}
	/**
	 * update user's password
	 *
	 * @param int     $user_id    the user's id
	 * @param string  $new_password the user's new password
	 * @return bool true if success, false if failure
	 */
	public function update_password( $user_id, $new_password ) {
		$query ="UPDATE ".self::TB_USER." SET password='$new_password' WHERE user_id='$user_id'";
		return self::$db -> query_and_check( $query );
	}


	/*              start edting from here and on         */



	/**
     * add_list_book
	 *
	 * @param int     $user_fk      the user's id
	 * @param int     $book_fk      the book's id
	 * @param char    $Book_status  the book's status
	 * @return bool true if success, false if failure
	 */


	public function add_list_book( $user_fk, $book_fk, $book_status ) {
		$query ="INSERT INTO ".self::TB_BOOK_USER." VALUES ('$user_fk','$book_fk','$book_status')";
		return self::$db -> query_and_check( $query );
	}
    

    /**
     * get_books_of_user
	 * @param int     $user_fk      the user's id
	 * @param char    $Book_status  the book's status
	 * @return array of books  if success, false if failure
	 */
    public function get_books_of_user( $user_fk , $book_status ) {
		$query ="SELECT book_id, title, author, rating, photo_url  FROM ".self::TB_BOOK_USER." INNER JOIN ".self::TB_BOOK." ON book_fk=book_id  WHERE user_fk='$user_fk' AND book_status='$book_status'";
		$result= self::$db -> query( $query );
		if ( mysqli_num_rows( $result )>0 ) {
			$list_book=array();
			while ( $row = $result -> fetch_assoc() ) {
				$list_book[] = array_values( $row );
			}
			return $list_book;
		}
		return false;

	}

	 /**
     * get_owners_of_book
	 * @param int     $user_fk      the user's id
	 * @param char    $Book_status  the book's status
	 * @return array of book's owners  if success, false if failure
	 */
    public function get_owners_of_book( $user_id, $book_fk) {
    	$location=  $this->get_user_location( $user_id );
		$query ="SELECT user_id, name, email, mobile, map_lat, map_lng FROM ".
		self::TB_BOOK_USER." INNER JOIN ".self::TB_USER.
		" ON user_fk=user_id WHERE book_fk='$book_fk' AND book_status='o' ORDER BY ".
		"DEGREES(ACOS(COS(RADIANS('$location[0]')) * COS(RADIANS(map_lat)) * COS(RADIANS(map_lng) - RADIANS('$location[1]')) + SIN(RADIANS('$location[0]')) * SIN(RADIANS(map_lat))))";
		$result= self::$db -> query( $query );
		if ( mysqli_num_rows( $result )>0 ) {
			$list_owners=array();
			while ( $row = $result -> fetch_assoc() ) {
				$list_owners[] = array_values( $row );
			}
			return $list_owners;
		}
		return false;

	}

     /**
     * remove a book from books list
	 *
	 * @param int     $book_fk      the book's id
	 * @param int     $user_fk      the user's id
	 * @param char    $Book_status  the book's status
	 * @return bool true if success, false if failure
	 */


	public function remove_from_list_book( $user_fk,$book_fk ) {
		$query ="DELETE FROM ".self::TB_BOOK_USER." WHERE book_fk='$book_fk' AND user_fk='$user_fk'";
		return self::$db -> query_and_check( $query );
	}

	/**
     * update a list book
	 *
	 * @param int     $user_fk      the user's id
	 * @param int     $book_fk      the book's id
	 * @param char    $Book_status  the book's status
	 * @return bool true if success, false if failure
	 */


	public function update_list_book( $user_fk, $book_fk, $book_status ) {
		$query ="UPDATE ".self::TB_BOOK_USER." SET book_status='$book_status' WHERE book_fk='$book_fk' AND user_fk='$user_fk'";
		return self::$db -> query_and_check( $query );
	}


	public function get_user_info( $user_id ) {
			$query ="SELECT * FROM ".self::TB_USER." WHERE user_id='$user_id' LIMIT 1";
			$result= self::$db -> query( $query );
			if ( mysqli_num_rows( $result ) > 0 ) {
		   		$row = $result -> fetch_assoc();
				return array_values( $row );
			}
			return false;
	}

	public function get_user_location( $user_id ) {
			$query ="SELECT map_lat, map_lng FROM ".self::TB_USER." WHERE user_id='$user_id' LIMIT 1";
			$result= self::$db -> query( $query );
			if ( mysqli_num_rows( $result ) > 0 ) {
		   		$row = $result -> fetch_assoc();
				return array_values( $row );
			}
			return false;
	}

	public function add_book( $book_id, $title, $author, $rating, $photo_url) {
		$query ="INSERT INTO ".self::TB_BOOK." VALUES ('$book_id','$title', '$author','$rating','$photo_url')";
		return self::$db -> query_and_check( $query );
	}
}
