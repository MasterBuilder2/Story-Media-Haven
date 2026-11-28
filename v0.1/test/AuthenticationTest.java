import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AuthenticationTest {
    private final AuthenticationSystem auth = AuthenticationSystem.getSingleton();

    @Test
    void signUpAcceptsNewUsername() {   //FR 3.1.5 regular sign up
        boolean result = auth.signUp(new User("new_reader", "new@example.com", "pass1234"));
        assertTrue(result);
    }

    @Test
    void signUpRejectsDuplicateUsername() {
        auth.signUp(new User("dup_user", "first@example.com", "pass1234"));

        boolean result = auth.signUp(new User("dup_user", "second@example.com", "other123"));

        assertFalse(result);
    }

    @Test
    void signUpDuplicateCheckIgnoresCapitalization() {
        auth.signUp(new User("CaseUser", "case1@example.com", "pass1234"));

        boolean result = auth.signUp(new User("caseuser", "case2@example.com", "pass1234"));

        assertFalse(result);
    }

    @Test
    void signUpAcceptsPublisher() {
        boolean result = auth.signUp(new Publisher("new_publisher", "pub@example.com", "pass1234", "Test Press"));
        assertTrue(result);
    }


    @Test       //FR 3.1.6
    void loginSucceedsWithCorrectDetails() {
        User reader = new User("login_ok", "ok@example.com", "pass1234");
        auth.signUp(reader);

        Account result = auth.login("login_ok", "pass1234");

        assertSame(reader, result);   // returns the exact account that signed up
    }

    @Test
    void loginFailsWithWrongPassword() {
        auth.signUp(new User("login_wrongpw", "wrong@example.com", "pass1234"));

        Account result = auth.login("login_wrongpw", "notMyPassword");

        assertNull(result);
    }

    @Test
    void loginFailsWithUnknownUsername() {
        Account result = auth.login("nobody_has_this_name", "pass1234");
        assertNull(result);
    }

    @Test       //ALTERNATIVE FLOW 3 : publisher verification
    void newPublisherIsNotVerified() {
        Publisher publisher = new Publisher("unverified_pub", "unv@example.com", "pass1234", "Test Press");
        auth.signUp(publisher);

        assertFalse(auth.isVerifiedPublisher(publisher));
    }

    @Test
    void verifiedPublisherPassesCheck() {
        Publisher publisher = new Publisher("verified_pub", "ver@example.com", "pass1234", "Test Press");
        publisher.setVerificationStatus(true);
        auth.signUp(publisher);

        assertTrue(auth.isVerifiedPublisher(publisher));
    }

    @Test
    void readerIsNeverAVerifiedPublisher() {
        User reader = new User("plain_reader", "reader@example.com", "pass1234");
        auth.signUp(reader);

        assertFalse(auth.isVerifiedPublisher(reader));
    }

    @Test
    void getSingletonAlwaysReturnsSameInstance() {
        assertSame(AuthenticationSystem.getSingleton(), AuthenticationSystem.getSingleton());
    }

}
