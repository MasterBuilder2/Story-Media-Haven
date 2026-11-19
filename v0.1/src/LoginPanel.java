import javax.swing.*;
import java.awt.*;


public class LoginPanel extends JPanel {
    private static final Color NAVY = new Color(35,48,68);
    private static final Color BEIGE = new Color(242,243,237);
    private static final Color RED = new Color(255,0,0);    //the login error warning pop-up
    private static final Color DARK_TEXT = new Color(0x33, 0x33, 0x33);

    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JButton loginButton = new JButton("Log in");
    private final JPanel errorBox = new JPanel();
    private final JLabel errorHeading = new JLabel();
    private final JLabel errorDetail = new JLabel();



    public LoginPanel(){
        setLayout(new GridBagLayout());         //keep dialog box in the center of the window
        add(buildForm());
    }

    private JPanel buildForm(){
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Log in to continue");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));


        loginButton.setBackground(NAVY);
        loginButton.setForeground(Color.WHITE);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);

        form.add(title);
        form.add(Box.createVerticalStrut(16));
        form.add(buildErrorBox());
        form.add(Box.createVerticalStrut(16));
        form.add(new JLabel("Username"));
        form.add(usernameField);
        form.add(Box.createVerticalStrut(12));
        form.add(new JLabel("Password"));
        form.add(passwordField);
        form.add(Box.createVerticalStrut(8));
        //form.add(errorLabel);
        form.add(Box.createVerticalStrut(8));
        form.add(loginButton);

        for (Component c : form.getComponents()) {
            ((JComponent) c).setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        return form;

    }


    private JPanel buildErrorBox() {
        errorBox.setLayout(new BoxLayout(errorBox, BoxLayout.X_AXIS));   // icon left, text right
        errorBox.setBackground(BEIGE);
        errorBox.setOpaque(true);
        errorBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1, true),     //outline
                BorderFactory.createEmptyBorder(12, 18, 12, 18)));    // padding inside the box

        errorHeading.setFont(errorHeading.getFont().deriveFont(Font.BOLD, 18f));
        errorDetail.setFont(errorDetail.getFont().deriveFont(Font.PLAIN, 16f));
        errorDetail.setForeground(DARK_TEXT);

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setOpaque(false);                                            // let the beige show through
        text.add(errorHeading);
        text.add(Box.createVerticalStrut(3));
        text.add(errorDetail);

        errorBox.add(new CircleIcon("!", NAVY));
        errorBox.add(Box.createHorizontalStrut(14));
        errorBox.add(text);

        errorBox.setVisible(false);                                       // doesn't show up until there is an error
        return errorBox;
    }

    void showError(String heading, String detail) {
        errorHeading.setText(heading);
        errorDetail.setText(detail);
        // Full form width, but only as tall as its content
        errorBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, errorBox.getPreferredSize().height));
        errorBox.setVisible(true);
        revalidate();
        repaint();
    }

    void clearError() {
        errorBox.setVisible(false);
        revalidate();
        repaint();
    }


    // Error exclamation point circle design
    //CLASS
    private static class CircleIcon extends JComponent {
        private final String symbol;
        private final Color color;

        CircleIcon(String symbol, Color color) {
            this.symbol = symbol;
            this.color = color;
            Dimension size = new Dimension(26, 26);
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  // smooth edges
            g2.setColor(color);
            g2.fillOval(0, 0, getWidth(), getHeight());

            g2.setColor(Color.WHITE);
            g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(symbol)) / 2;                 // center horizontally
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();      // center vertically
            g2.drawString(symbol, x, y);
            g2.dispose();
        }
    }
    //END OF CLASS

    //temporary main for testing until we do the actual Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Story Media Haven");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            LoginPanel panel = new LoginPanel();
            frame.add(panel);
            frame.setLocationRelativeTo(null);   // open in the middle of the screen
            frame.setVisible(true);

        });

    }
}

