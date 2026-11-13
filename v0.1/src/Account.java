public class Account {
    private String username;
    private String email;
    private String password;
    Account(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername(){return username;}
    public String getEmail() {return email;}
    public String getPassword(){return password;}
    public boolean checkLoginDetails(String username, String password){
        if(this.username.equals(username) && this.password.equals(password)){
            return true;
        }
        else{
            return false;
        }
    }


}
