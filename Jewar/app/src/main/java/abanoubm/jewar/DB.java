package abanoubm.jewar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {
    private static String DB_NAME = "jewar_db";
    private static final int DB_VERSION = 1;
    public static final int BOOK_STATUS_SEEKING = 1;
    public static final int BOOK_STATUS_OWNED = 0;

    private static final String TB_BOOK = "book_tb",
            BOOK_ID = "book_id",
            BOOK_STATUS = "book_status",
            BOOK_TITLE = "book_title",
            BOOK_RATING = "book_rating",
            BOOK_AUTHOR = "book_author",
            BOOK_PHOTO_URL = "book_photo_url";

    private static DB dbm;
    private SQLiteDatabase readableDB, writableDB;


    public static DB getInstant(Context context) {
        return dbm != null ? dbm : (dbm = new DB(context));
    }

    private DB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        readableDB = getReadableDatabase();
        writableDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TB_BOOK + " ( " +
                BOOK_ID + "  text, " +
                BOOK_STATUS + "  integer, " +
                BOOK_TITLE + "  text, " +
                BOOK_RATING + "  text, " +
                BOOK_AUTHOR + "  text, " +
                BOOK_PHOTO_URL + " text, " +
                "primary key (" + BOOK_ID + "))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {


    }

    public boolean isHoldingBooks() {
        boolean check = false;
        Cursor c = readableDB.query(TB_BOOK, new String[]{BOOK_TITLE}, null, null, null, null, null, "1");
        if (c.moveToNext())
            check = true;
        c.close();
        return check;
    }

//    public void insertBooksUpdateStatus(ArrayList<Book> books) {
//        writableDB.beginTransaction();
//        ContentValues values;
//        Book temp;
//        for (Book book : books) {
//            temp = getBook(book.getID());
//            if (temp == null) {
//                values = new ContentValues();
//                values.put(BOOK_ID, book.getID());
//                values.put(BOOK_STATUS, book.getStatus());
//                values.put(BOOK_TITLE, book.getTitle());
//                values.put(BOOK_RATING, book.getRating());
//                values.put(BOOK_AUTHOR, book.getAuthor());
//                values.put(BOOK_PHOTO_URL, book.getPhotoURL());
//                writableDB.insert(TB_BOOK, null, values);
//                book.setStatus(2);
//            } else{
//                book.setStatus(temp.getStatus());
//            }
//        }
//        writableDB.setTransactionSuccessful();
//        writableDB.endTransaction();
//    }

    public void updateStatusOfBooks(ArrayList<Book> books) {
        for (Book book : books)
            book.setStatus(getBookStatus(book.getID()));
    }
    public void insertBook(Book book) {
        Book temp = getBook(book.getID());
        if (temp == null) {
            ContentValues values;
            values = new ContentValues();
            values.put(BOOK_ID, book.getID());
            values.put(BOOK_STATUS, book.getStatus());
            values.put(BOOK_TITLE, book.getTitle());
            values.put(BOOK_RATING, book.getRating());
            values.put(BOOK_AUTHOR, book.getAuthor());
            values.put(BOOK_PHOTO_URL, book.getPhotoURL());
            writableDB.insert(TB_BOOK, null, values);
        } else if (temp.getStatus() != book.getStatus()) {
            updateBookStatus(book.getID(), book.getStatus());
        }
    }

    private void updateBookStatus(String bookID, int bookStatus) {
        ContentValues values;
        values = new ContentValues();
        values.put(BOOK_STATUS, bookStatus);
        writableDB.update(TB_BOOK, values, BOOK_ID + " = ?", new String[]{bookID});
    }

    public ArrayList<Book> getBooks(int bookStatus) {
        Cursor c = readableDB.query(TB_BOOK,
                new String[]{BOOK_ID,BOOK_STATUS, BOOK_TITLE, BOOK_RATING, BOOK_AUTHOR, BOOK_PHOTO_URL},
                BOOK_STATUS + " = ?", new String[]{bookStatus + ""}, null, null, BOOK_TITLE, null);

        ArrayList<Book> result = new ArrayList<>(c.getCount());

        if (c.moveToFirst()) {

            do {
                result.add(new Book(c.getString(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));

            } while (c.moveToNext());
        }
        c.close();

        return result;

    }

    public Book getBook(String bookID) {
        Cursor c = readableDB.query(TB_BOOK,
                new String[]{BOOK_ID,BOOK_STATUS, BOOK_TITLE, BOOK_RATING, BOOK_AUTHOR, BOOK_PHOTO_URL},
                BOOK_ID + " = ?", new String[]{bookID}, null, null, BOOK_TITLE, null);

        Book result = null;
        if (c.moveToFirst()) {

            result = new Book(c.getString(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));
        }
        c.close();

        return result;

    }

    public ArrayList<Book> getBooks() {
        Cursor c = readableDB.query(TB_BOOK,
                new String[]{BOOK_ID,BOOK_STATUS, BOOK_TITLE, BOOK_RATING, BOOK_AUTHOR, BOOK_PHOTO_URL},
                null, null, null, null, BOOK_TITLE, null);

        ArrayList<Book> result = new ArrayList<>(c.getCount());

        if (c.moveToFirst()) {

            do {
                result.add(new Book(c.getString(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));

            } while (c.moveToNext());
        }
        c.close();

        return result;

    }

    public int getBookStatus(String bookID) {
        Cursor c = readableDB.query(TB_BOOK,
                new String[]{BOOK_STATUS},
                BOOK_ID + " = ?", new String[]{bookID}, null, null, null, null);
        int result = 2;
        if (c.moveToFirst())
            result = c.getInt(0);
        c.close();

        return result;

    }


}
