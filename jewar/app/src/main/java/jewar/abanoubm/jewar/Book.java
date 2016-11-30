package jewar.abanoubm.jewar;

/**
 * Created by bono on 11/20/2016.
 */
public class Book {
    String ID,title, rating,author,photoURL;

    public Book(String ID, String title, String rating, String author, String photoURL) {
        this.ID = ID;
        this.title = title;
        this.rating = rating;
        this.author = author;
        this.photoURL = photoURL;
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
