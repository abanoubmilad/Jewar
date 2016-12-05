
public class BookOwner {
    private String ID,name;
    private double lat,lng;
    private byte [] photo;

    public BookOwner(String ID, String name, double lat, double lng, byte[] photo) {
        this.ID = ID;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.photo = photo;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
