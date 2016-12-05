package abanoubm.jewar;

public class Book {
    private String ID;
    // BOOK_STATUS_SEEKING = 1;
    //  BOOK_STATUS_OWNED = 0;
    private int status;
    private String title, rating, author, photoURL;

    public Book(String ID, int status, String title, String rating, String author, String photoURL) {
        this.ID = ID;
        this.status = status;
        this.title = title;
        this.rating = rating;
        this.author = author;
        this.photoURL = photoURL;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
