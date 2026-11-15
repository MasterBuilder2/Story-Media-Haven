import java.util.Scanner;
public class Console {
    private static final Scanner scan = new Scanner(System.in);

    public static void signUp(Scanner scan){
        System.out.println("--- Publisher Sign Up ---");
        String username = readNonEmpty(scan,"Username: ");
        String email = readNonEmpty(scan, "Email: ");
        while (!isValidEmail(email)) {
            System.out.println("Please enter a valid email, like name@example.com.");
            email = readNonEmpty(scan, "Email: ");
        }
        String password = readNonEmpty(scan,"Password: ");
        String companyName = readNonEmpty(scan,"Company Name: ");
        Publisher publisher = new Publisher(username, email, password, companyName);

        if(AuthenticationSystem.getSingleton().signUp(publisher)){
            System.out.println("Account created successfully. Your publisher account is pending verification.");
        }else{
            System.out.println("Username already taken. Please choose another one.");
        }
    }

    public static Account login(Scanner input) {
        System.out.println("--- Log in ---");
        String username = readNonEmpty(input, "Username: ");
        String password = readNonEmpty(input, "Password: ");

        Account account = AuthenticationSystem.getSingleton().login(username, password);

        if (account == null) {
            System.out.println("Invalid username or password.");
            return null;
        }

        if (AuthenticationSystem.getSingleton().isVerifiedPublisher(account)) {
            System.out.println("Welcome, " + account.getUsername() + ". You are a verified publisher.");
        } else {
            System.out.println("Welcome, " + account.getUsername() + ". Your publisher account is not verified yet.");
        }
        return account;
    }

    public static String readNonEmpty(Scanner scan, String prompt){
        String value;
        do {
            System.out.print(prompt);
            value = scan.nextLine().trim();
            if (value.isEmpty()) {
                System.out.println("This field can't be empty.");
            }
        } while (value.isEmpty());
        return value;

    }

    private static boolean isValidEmail(String email) {
        return email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Publisher verified = new Publisher("northlight", "rights@northlight.com", "pass1234", "Northlight Press");
        verified.setVerificationStatus(true);
        AuthenticationSystem.getSingleton().signUp(verified);

        signUp(input);   // create an unverified publisher
        login(input);    // try: wrong password, then each account
        login(input);
    }


}
