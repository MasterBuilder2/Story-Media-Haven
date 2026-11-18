import javax.swing.*;
import java.awt.*;


public class LoginPanel extends JPanel {
    private static final Color NAVY = new Color(35,48,68);
    private static final Color BEIGE = new Color(242,243,237);
    private static final Color RED = new Color(255,0,0);    //the login error warning pop-up

    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JButton loginButton = new JButton("Log in");
    private final JLabel errorLabel = new JLabel(" ");  // blank until a login fails


    public LoginPanel(){
        setLayout(new GridBagLayout());         //keep dialog box in the center of the window
        add(buildForm());
    }

    private JPanel buildForm(){
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Log in to continue");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        errorLabel.setForeground(RED);

        loginButton.setBackground(NAVY);
        loginButton.setForeground(Color.WHITE);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);

        form.add(title);
        form.add(Box.createVerticalStrut(16));
        form.add(new JLabel("Username"));
        form.add(usernameField);
        form.add(Box.createVerticalStrut(12));
        form.add(new JLabel("Password"));
        form.add(passwordField);
        form.add(Box.createVerticalStrut(8));
        form.add(errorLabel);
        form.add(Box.createVerticalStrut(8));
        form.add(loginButton);

        for (Component c : form.getComponents()) {
            ((JComponent) c).setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        return form;

    }


    //temporary main for testing
    // Temporary: shows the panel on its own until the shared MainWindow exists
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Story Media Haven");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.add(new LoginPanel());
            frame.setLocationRelativeTo(null);   // open in the middle of the screen
            frame.setVisible(true);
        });
    }
}

