import java.util.ArrayList;

public class Media {
    private static int ID = 0;
    private String title;
    private Date releaseDate;
    private String synopsis;
    private float averageRating;
    private int global_ID;
    private ArrayList<Review> reviewList;

    Media(String title, Date releaseDate, String synopsis) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.global_ID = ID;
        ID++;
        this.reviewList = new ArrayList<Review>();
    }

    public String getTitle() {
        return title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public int getGlobal_ID() {
        return global_ID;
    }

    public ArrayList<Review> getReviewList() {
        return reviewList;
    }

    public void addReview(Review r) {
        reviewList.add(r);
        updateRating();
    }

    public void viewDetails() {
        System.out.println("Title: " + getTitle());
        System.out.println("Release Date: " + getReleaseDate().toString());
        System.out.println("Synopsis: " + getSynopsis());
    }

    public void showRatings() {
        for (Review r : getReviewList()) {
            System.out.println(r);
        }
    }

    public void updateRating() {
        double average = 0;
        int sum = 0;
        if (getReviewList() != null && !getReviewList().isEmpty()) {
            for (Review r : getReviewList()) {
                sum += r.getRating();
            }
            average = (double) sum / reviewList.size();
            this.averageRating = (float) average;
        }
    }
    //adding toString for Search class
    @Override
    public String toString(){
        return getTitle() + ", " + getReleaseDate().toString() + ", " + getAverageRating();
    }
}
