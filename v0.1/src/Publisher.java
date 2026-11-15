
public class Publisher extends Account {
    private String companyName;
    private boolean verificationStatus;

    public Publisher(String username, String email, String password, String companyName){
        super(username,email,password);
        this.companyName = companyName;
        this.verificationStatus = false;      //publisher hasn't been verified yet
    }

    public void submitMediaRequest(String title,String creator,Date releaseDate,String synopsis){
        SubmissionPortal.getSingleton().submitRequest(this, title, creator, releaseDate, synopsis);
    }

    public boolean isVerified(){
        return verificationStatus;
    }

    public String getCompanyName(){return this.companyName;}
    public boolean getVerification(){return this.verificationStatus;}

    public void setVerificationStatus(boolean verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

}
