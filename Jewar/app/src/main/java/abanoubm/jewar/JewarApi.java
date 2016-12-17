package abanoubm.jewar;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private static final String URL_sign_out = URL_HOST + "sign_out.php";
    private static final String URL_get_location = URL_HOST + "get_location.php";
    private static final String URL_update_location = URL_HOST + "update_location.php";

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
    public static int signUp(String name, String mobile, String email, String password) {
        RequestBody formBody = new FormEncodingBuilder().add("name", name).add("mobile", mobile).add("email", email).add("pass", password).build();
        JSONObject json = HTTPClient.getInstance().POST(URL_sign_up, formBody);
        return extractStatus(json);
    }

    // 3 - update_email
    public static int update_email(String email) {
        RequestBody formBody = new FormEncodingBuilder().add("email", email).build();
        JSONObject json = HTTPClient.getInstance().POST(URL_update_email, formBody);
        return extractStatus(json);

    }

    // 4-add_list_book
    public static int add_list_book(String book_id, int book_status) {
        RequestBody formBody = new FormEncodingBuilder().add("book_id", book_id).add("book_status", book_status == DB.BOOK_STATUS_OWNED ? "o" : book_status == DB.BOOK_STATUS_SEEKING ? "s" : "e")
                .build();
        JSONObject json = HTTPClient.getInstance().POST(URL_add_list_book, formBody);
        return extractStatus(json);

    }

    // 5 -URL_get_books_of_user
    public static APIResponse get_books_of_user(int book_status) {
        RequestBody formBody = new FormEncodingBuilder().add("book_status", book_status + "").build();
        JSONObject json = HTTPClient.getInstance().POST(URL_get_books_of_user, formBody);

        int status = extractStatus(json);
        ArrayList<Book> books = null;
        if (status == 7) {
            try {

                JSONArray arr = json.getJSONArray("books");

                books = new ArrayList<>(arr.length());

                for (int i = 0; i < arr.length(); i++) {
                    JSONArray inArr = arr.getJSONArray(i); // inArr.getString(0),
                    // inArr.getString(1))
                    books.add(new Book(inArr.getString(0), book_status, inArr.getString(1), inArr.getString(3), inArr.getString(2),
                            inArr.getString(4)));
                }

            } catch (Exception e) {
            }
        }
        return new APIResponse<>(status, books);

    }

    // 6-/get_owners_of_book.php
    public static APIResponse get_owners_of_book(String book_id) {
        RequestBody formBody = new FormEncodingBuilder().add("book_id", book_id).build();
        JSONObject json = HTTPClient.getInstance().POST(URL_get_owners_of_book, formBody);

        int status = extractStatus(json);
        ArrayList<BookOwner> bookOwners = null;
        if (status == 7) {
            try {

                JSONArray arr = json.getJSONArray("book_owners");
                bookOwners = new ArrayList<>(arr.length());

                for (int i = 0; i < arr.length(); i++) {
                    JSONArray inArr = arr.getJSONArray(i); // inArr.getString(0),
                    // inArr.getString(1))
                    bookOwners.add(new BookOwner(inArr.getString(0), inArr.getString(1), inArr.getDouble(2),
                            inArr.getDouble(3), null)); // ID, name, lat, lng,
                    // photo
                }
                // fill it

            } catch (Exception e) {
            }
        }
        return new APIResponse<>(status, bookOwners);

    }

    // 7-get_user_info.php
    public static APIResponse get_user_info(String user_id) {
        RequestBody formBody = new FormEncodingBuilder().add("user_id", user_id).build();
        JSONObject json = HTTPClient.getInstance().POST(URL_get_user_info, formBody);

        int status = extractStatus(json);
        BookOwner bookOwner = null;
        if (status == 7) {
            try {
                JSONArray arr = json.getJSONArray("user_info");
                bookOwner = new BookOwner(arr.getString(0), arr.getString(1), arr.getDouble(2), arr.getDouble(3), null); // ID,
                // name,
                // lat,
                // lng,
                // photo

            } catch (Exception e) {
            }
        }
        return new APIResponse<>(status, bookOwner);

    }

    // 8- remove_from_list_book.php
    public static int remove_from_list_book(String book_id) {
        RequestBody formBody = new FormEncodingBuilder().add("book_id", book_id).build();
        JSONObject json = HTTPClient.getInstance().POST(URL_remove_from_list_book, formBody);
        return extractStatus(json);

    }

    // 9 - update_list_book.php

    public static int update_list_book(String book_id, String book_status) {
        RequestBody formBody = new FormEncodingBuilder().add("book_id", book_id).add("book_status", book_status)
                .build();
        JSONObject json = HTTPClient.getInstance().POST(URL_update_list_book, formBody);
        return extractStatus(json);

    }

    // 10 - URL_update_password
    public static int update_password(String password) {
        RequestBody formBody = new FormEncodingBuilder().add("password", password).build();
        JSONObject json = HTTPClient.getInstance().POST(URL_update_password, formBody);
        return extractStatus(json);

    }

    // 11 - add books
    public static int add_book(String book_id, String title, String author, String rating, String photo_url) {
        RequestBody formBody = new FormEncodingBuilder().add("book_id", book_id).add("title", title)
                .add("author", author).add("rating", rating).add("photo_url", photo_url).build();
        JSONObject json = HTTPClient.getInstance().POST(URL_add_book, formBody);
        return extractStatus(json);
    }

    // 12 - sign out ==>need get
    public static int sign_out() {
        JSONObject json = HTTPClient.getInstance().get(URL_sign_out);
        return extractStatus(json);
    }

    public static int update_location(double lat, double lng) {
        RequestBody formBody = new FormEncodingBuilder().add("lat", lat+"").add("lng", lng+"").build();
        JSONObject json = HTTPClient.getInstance().POST(URL_update_location, formBody);
        return extractStatus(json);
    }
    public static APIResponse get_location() {
        JSONObject json = HTTPClient.getInstance().get(URL_get_location);
        int status = extractStatus(json);
        ArrayList<Double> locs = null;
        if (status == 7) {
            try {

                JSONArray arr = json.getJSONArray("location");
                locs = new ArrayList<>(2);
                locs.add(arr.getDouble(0));
                locs.add(arr.getDouble(1));

            } catch (Exception e) {
            }
        }
        return new APIResponse<>(status, locs);
    }
}
