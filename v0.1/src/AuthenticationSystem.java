import java.util.ArrayList;

public class AuthenticationSystem {
    private static final AuthenticationSystem SINGLETON = new AuthenticationSystem();
    private ArrayList<Account> accounts;

    private AuthenticationSystem(){
        this.accounts = new ArrayList<>();

    }

    public static AuthenticationSystem getSingleton(){return SINGLETON;}

    public boolean signUp(Account account){
        for(Account a: accounts){
           if(a.getUsername().equalsIgnoreCase(account.getUsername())){
               return false;
           }
        }
        accounts.add(account);
        return true;
    }

    public Account login(String username, String password){
        for(Account a : accounts){
            if(a.checkLoginDetails(username, password)){
                return a;
            }
        }
        return null;                //check login as a user
    }


    public boolean isVerifiedPublisher(Account account){            //account exists === is it a publisher???
        if(account instanceof Publisher){
            Publisher p = (Publisher) account;
            return p.isVerified();
        }
        return false;
    }
}
