public class Review {
    private int rating;
    private String comment;
    private Date reviewDate;
    private User owner;
    private Media media;

    Review(int rating, String comment, Date reviewDate, User owner, Media media){
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.owner = owner;
        this.media = media;
    }

    public int getRating() {return rating;}
    public String getComment() {return comment;}
    public Date getReviewDate() {return reviewDate;}
    public User getOwner() {return owner;}

    public Media getMedia() {return media;}

    public void setRating(int rating){
        this.rating = rating;
    }
    public void setComment(String comment){
        this.comment = comment;
    }
    public void displayReviewForm(){
        System.out.println(this);
    }

    public boolean confirmSubmission(Media m){
        return m.getReviewList().contains(this);
    }

    @Override
    public String toString(){
        return "Rating: " + getRating() + "\nComment: " + getComment() + "\nReview Date: " + getReviewDate().toString();
    }
}
