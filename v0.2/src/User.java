import java.util.ArrayList;
public class User extends Account {
    private Date joinedDate;
    private ArrayList<Media> bookmarks;

    User(String username, String email, String password) {
        super(username, email, password);
        this.bookmarks = new ArrayList<Media>();
    }

    public Date getJoinedDate() {return joinedDate;}
    public ArrayList<Media> getBookmarks() {return bookmarks;}

    public void submitReview(Media m, int rating, String comment){
        m.addReview(new Review(rating,comment, new Date()));
    }
    public void addBookmark(Media m){
        getBookmarks().add(m);
    }
    public void removeBookmark(Media m){
        getBookmarks().remove(m);
    }
}
