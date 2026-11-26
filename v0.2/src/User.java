import java.util.ArrayList;
public class User extends Account {
    private Date joinedDate;
    private ArrayList<Media> bookmarks;
    private ArrayList<Review> reviews;

    User(String username, String email, String password) {
        super(username, email, password);
        this.bookmarks = new ArrayList<Media>();
        this.reviews = new ArrayList<Review>();
    }

    public Date getJoinedDate() {return joinedDate;}
    public ArrayList<Media> getBookmarks() {return bookmarks;}

    public ArrayList<Review> getReviews() {return reviews;}

    public void submitReview(Media m, int rating, String comment){
        Review r = new Review(rating,comment, new Date(),this,m);
        m.addReview(r);
        addReviewToList(r);
    }
    public void addReviewToList(Review review){getReviews().add(review);}
    public void addBookmark(Media m){
        getBookmarks().add(m);
    }
    public void removeBookmark(Media m){
        getBookmarks().remove(m);
    }
}
