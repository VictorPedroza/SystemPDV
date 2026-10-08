
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import javax.swing.SwingUtilities;
import view.Interface;

/**
 *
 * @author victo
 */
public class Aplic {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            Interface frame = new Interface();
            frame.getRootPane().putClientProperty(
                    "JRootPane.titleBarBackground",
                    new Color(20, 43, 74)
            );
            frame.getRootPane().putClientProperty(
                    "JRootPane.titleBarForeground",
                    Color.WHITE
            );
            frame.setVisible(true);
        });
    }

}
