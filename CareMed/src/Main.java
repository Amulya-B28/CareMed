import javax.swing.SwingUtilities;
import gui.LoginWindow;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Starting LoginWindow");
            LoginWindow login = new LoginWindow();
            login.setVisible(true);
        });
    }
}
