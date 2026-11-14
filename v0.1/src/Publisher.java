
public class Publisher extends Account {
    private String companyName;
    private Boolean verification;

    public Publisher(String username, String email, String password, String companyName){
        super(username,email,password);
        this.companyName = companyName;
        this.verification = false;      //publisher hasn't been verified yet
    }

    public void submitMediaRequest(String title,String creator,Date releaseDate,String synopsis){
        SubmissionPortal.getSingleton().submitRequest(this, title, creator, releaseDate, synopsis);
    }

}
