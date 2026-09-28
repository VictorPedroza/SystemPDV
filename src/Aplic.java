
import javax.swing.SwingUtilities;
import view.Interface;

/**
 *
 * @author victo
 */
public class Aplic {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interface().setVisible(true);
        });
    }
    
}
