public class UserSession {
    private static final UserSession SINGLETON = new UserSession();
    private User currentUser;
    private UserSession(){
        this.currentUser = null;
    }
    public static UserSession getSINGLETON() {return SINGLETON;}

    public void setCurrentUser(User currentUser) {this.currentUser = currentUser;}
    public User getCurrentUser() {return currentUser;}
    public void clearSession() {currentUser = null;} // logging out
}
