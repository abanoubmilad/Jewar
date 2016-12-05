import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

public class JewarApi {

	private static final String URL_HOST = "http://www.abanoubcs.webatu.com/jewar/";
	private static final String URL_sign_up = URL_HOST + "sign_up.php";
	private static final String URL_sign_in = URL_HOST + "sign_in.php";
	private static final String URL_update_email = URL_HOST + "update_email.php";
	private static final String URL_update_list_book = URL_HOST + "update_list_book.php";
	private static final String URL_update_password = URL_HOST + "update_password.php";
	private static final String URL_add_list_book = URL_HOST + "add_list_book.php";
	private static final String URL_get_books_of_user = URL_HOST + "get_books_of_user.php";
	private static final String URL_get_owners_of_book = URL_HOST + "get_owners_of_book.php";
	private static final String URL_get_user_info = URL_HOST + "get_user_info.php";
	private static final String URL_remove_from_list_book = URL_HOST + "remove_from_list_book.php";
	private static final String URL_add_book = URL_HOST + "add_book.php";

	public static int extractStatus(JSONObject json) {
		try {
			return json.getInt("status");
		} catch (Exception e) {
			return -1;
		}
	}

	// 1- sign in
	public static int signIn(String email, String password) {
		RequestBody formBody = new FormEncodingBuilder().add("email", email).add("pass", password).build();
		JSONObject json = HTTPClient.getInstance().POST(URL_sign_in, formBody);
		return extractStatus(json);
	}

	// 2 - sign up
	public static int signUp(String email, String password) throws IOException, JSONException {
		RequestBody formBody = new FormEncodingBuilder().add("email", email).add("pass", password).build();
		JSONObject json = HTTPClient.getInstance().POST(URL_sign_up, formBody);
		return extractStatus(json);
	}

	// 3 - update_email
	public static int update_email(String email) throws IOException, JSONException {
		RequestBody formBody = new FormEncodingBuilder().add("email", email).build();
		JSONObject json = HTTPClient.getInstance().POST(URL_update_email, formBody);
		return extractStatus(json);

	}

	// 4-add_list_book
	public static int add_list_book(String book_id, String book_status) throws IOException, JSONException {
		RequestBody formBody = new FormEncodingBuilder().add("book_id", book_id).add("book_status", book_status)
				.build();
		JSONObject json = HTTPClient.getInstance().POST(URL_add_list_book, formBody);
		return extractStatus(json);

	}

	// 5 -URL_get_books_of_user
	public static APIResponse get_books_of_user(String book_status) throws IOException, JSONException {
		RequestBody formBody = new FormEncodingBuilder().add("book_status", book_status).build();
		JSONObject json = HTTPClient.getInstance().POST(URL_get_books_of_user, formBody);



		int status =extractStatus(json);
		ArrayList<Book> books=null;
		if(status==7){
			try {
				
				JSONArray arr = json.getJSONArray("books");
// fill it
				books.add( new Book(ID, title, rating, author, photoURL));
			} catch (Exception e) {
			}
		}
		return new APIResponse<>(status, books);

	}

	// 6-/get_owners_of_book.php
	public static APIResponse get_owners_of_book(String book_id) throws IOException, JSONException {
		RequestBody formBody = new FormEncodingBuilder().add("book_id", book_id).build();
		JSONObject json = HTTPClient.getInstance().POST(URL_get_owners_of_book, formBody);


		int status =extractStatus(json);
		ArrayList<BookOwner> bookOwners=null;
		if(status==7){
			try {
				
				JSONArray arr = json.getJSONArray("book_owners");
// fill it
				bookOwners.add( new BookOwner(ID, name, lat, lng, photo));
			} catch (Exception e) {
			}
		}
		return new APIResponse<>(status, bookOwners);

	}

	// 7-get_user_info.php
	public static APIResponse get_user_info(String user_id) throws IOException, JSONException {
		RequestBody formBody = new FormEncodingBuilder().add("user_id", user_id).build();
		JSONObject json = HTTPClient.getInstance().POST(URL_get_user_info, formBody);

		int status =extractStatus(json);
		BookOwner bookOwner=null;
		if(status==7){
			try {
				JSONArray arr = json.getJSONArray("user_info");
// fill it
				bookOwner= new BookOwner(ID, name, lat, lng, photo)
			} catch (Exception e) {
			}
		}
		return new APIResponse<>(status, bookOwner);

		

	}

	// 8- remove_from_list_book.php
	public static int remove_from_list_book(String book_id) throws IOException, JSONException {
		RequestBody formBody = new FormEncodingBuilder().add("book_id", book_id).build();
		JSONObject json = HTTPClient.getInstance().POST(URL_remove_from_list_book, formBody);
		return extractStatus(json);

	}

	// 9 - update_list_book.php

	public static int update_list_book(String book_id, String book_status) throws IOException, JSONException {
		RequestBody formBody = new FormEncodingBuilder().add("book_id", book_id).add("book_status", book_status)
				.build();
		JSONObject json = HTTPClient.getInstance().POST(URL_update_list_book, formBody);
		return extractStatus(json);

	}

	// 10 - URL_update_password
	public static int update_password(String password) throws IOException, JSONException {
		RequestBody formBody = new FormEncodingBuilder().add("password", password).build();
		JSONObject json = HTTPClient.getInstance().POST(URL_update_password, formBody);
		return extractStatus(json);

	}

	// 11 - add books
	public static int add_book(String book_id, String title, String author, String rating, String photo_url)
			throws IOException, JSONException {
		RequestBody formBody = new FormEncodingBuilder().add("book_id", book_id).add("title", title)
				.add("author", author).add("rating", rating).add("photo_url", photo_url).build();
		JSONObject json = HTTPClient.getInstance().POST(URL_add_book, formBody);
		return extractStatus(json);
	}
}
