import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class LoginPanel extends StackPane {
    private static final String NAVY = "#233044";
    private static final String BEIGE = "#F2F3ED";
    private static final String DARK_TEXT = "#333333";

    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button loginButton = new Button("Log in");
    private final HBox errorBox = new HBox(14);           // 14px gap between the icon and the text
    private final Label errorHeading = new Label();
    private final Label errorDetail = new Label();


    public LoginPanel(){
        getChildren().add(buildForm());                      // StackPane keeps the form in the center of the window
        loginButton.setOnAction(e -> handleLogin());         // makes the button work
        passwordField.setOnAction(e -> handleLogin());       // pressing Enter in the password field also logs in
    }

    private VBox buildForm(){
        VBox form = new VBox();                               // stacks items top to bottom, default left aligned
        form.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);   // don't stretch to fill the whole window

        Label title = new Label("Log in to continue");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        usernameField.setPrefColumnCount(20);
        passwordField.setPrefColumnCount(20);

        loginButton.setStyle("-fx-background-color: " + NAVY + "; -fx-text-fill: white;");

        form.getChildren().addAll(
                title, spacer(16),
                buildErrorBox(), new Label("Username"), usernameField, spacer(12),
                new Label("Password"), passwordField, spacer(16), loginButton);

        // Gap below the error box only while it's shown, so there's no empty space when it's hidden
        VBox.setMargin(errorBox, new Insets(0, 0, 16, 0));
        return form;
    }

    // Fixed vertical gap (replaces Swing's Box.createVerticalStrut)
    private static Region spacer(double height){
        Region gap = new Region();
        gap.setMinHeight(height);
        gap.setPrefHeight(height);
        return gap;
    }


    private HBox buildErrorBox() {
        errorBox.setAlignment(Pos.CENTER_LEFT);                     // icon left, text right, vertically centered
        errorBox.setPadding(new Insets(12, 18, 12, 18));            // padding inside the box
        errorBox.setStyle("-fx-background-color: " + BEIGE + ";"
                + "-fx-border-color: black; -fx-border-width: 1;"   // outline
                + "-fx-border-radius: 4; -fx-background-radius: 4;");

        errorHeading.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        errorDetail.setStyle("-fx-font-size: 16px; -fx-text-fill: " + DARK_TEXT + ";");

        VBox text = new VBox(3, errorHeading, errorDetail);         // 3px gap between the two lines

        errorBox.getChildren().addAll(new CircleIcon("!", NAVY), text);

        errorBox.setVisible(false);                                 // doesn't show up until there is an error
        errorBox.setManaged(false);                                 // and takes no space while hidden
        return errorBox;
    }

    void showError(String heading, String detail) {
        errorHeading.setText(heading);
        errorDetail.setText(detail);
        errorBox.setManaged(true);
        errorBox.setVisible(true);
    }

    void clearError() {
        errorBox.setVisible(false);
        errorBox.setManaged(false);
    }


    // Error exclamation point circle design
    private static class CircleIcon extends StackPane {
        CircleIcon(String symbol, String color) {
            Circle circle = new Circle(13, Color.web(color));        // 26px across
            Label mark = new Label(symbol);
            mark.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
            getChildren().addAll(circle, mark);                     // StackPane centers the "!" on the circle
        }
    }


    //actual implementation of login (making the button work)
    private void handleLogin(){
        clearError();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showError("Invalid information", "Please enter your username and password.");
            return;
        }

        Account account = AuthenticationSystem.getSingleton().login(username, password);
        if (account == null) {
            //security measure by not determining which one is wrong
            showError("Invalid username or password", "Check your details and try again.");
            passwordField.setText("");
            return;
        }

        if (!(account instanceof Publisher)) {
            // temporary until Main is built then switches to the reader's screen
            new Alert(Alert.AlertType.INFORMATION, "Welcome, " + account.getUsername() + ".").showAndWait();
            return;
        }

        if (!AuthenticationSystem.getSingleton().isVerifiedPublisher(account)) {
            //Alternative Flow 3 == publisher status verification fails
            showError("We couldn't verify your publisher account",
                    "Log in again to keep submitting your title.");
            return;
        }

        //temporary until Main is built,then switches to the submit-media screen
        new Alert(Alert.AlertType.INFORMATION,
                "Welcome, " + account.getUsername() + ". You are a verified publisher.").showAndWait();
    }



    //temporary main for testing until we do the actual Main
    public static void main(String[] args) {
        Application.launch(TestLauncher.class, args);
    }

    // JavaFX needs an Application class to open a window; this one only exists for testing
    public static class TestLauncher extends Application {
        @Override
        public void start(Stage stage) {
            stage.setTitle("Story Media Haven");
            stage.setScene(new Scene(new LoginPanel(), 1000, 700));
            stage.show();
        }
    }
}

