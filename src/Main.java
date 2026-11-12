
import com.formdev.flatlaf.FlatLightLaf;
import com.systempdv.database.Database;
import com.systempdv.service.ApplicationContext;
import com.systempdv.service.AuthService;
import com.systempdv.ui.Interface;
import com.systempdv.ui.LoginFrame;
import java.awt.Color;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author victo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> {
            Path dir = Paths.get(
                    System.getProperty(
                            "systempdv",
                            Paths.get(
                                    System.getProperty("user.home"),
                                    "SystemPDV-data"
                            ).toString()
                    )
            );

            try {
                ApplicationContext context = ApplicationContext.create(dir);

                String layout = context.getProperty("default.system");

                Database database = new Database(context);
                AuthService auth = new AuthService(database);

                switch (layout) {
                    case "ADMINISTRATIVO": {
                        LoginFrame login = new LoginFrame(context, auth, user -> {
                            if (user.isAdmin()) {
                                context.setUser(user);
                                initLayout(context, layout);
                            } else {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Usuário sem permissão\n",
                                        "Erro",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        });

                        login.setVisible(true);
                        break;
                    }
                    case "CAIXA": {
                        LoginFrame login = new LoginFrame(
                                context,
                                auth,
                                user -> {
                                    context.setUser(user);
                                    initLayout(context, layout);
                                }
                        );
                        login.setVisible(true);
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "Erro ao iniciar o sistema:\n"
                        + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                System.exit(1);
            }
        });
    }

    private static void initLayout(ApplicationContext context, String layout) {
        Interface ui = new Interface(context);
        ui.setTitle(context.getConfig().getStoreName());
               
        ui.getRootPane().putClientProperty(
                "JRootPane.titleBarBackground",
                new Color(20, 43, 74)
        );
        ui.getRootPane().putClientProperty(
                "JRootPane.titleBarForeground",
                Color.WHITE
        );

        ui.setVisible(true);
        ui.setLocationRelativeTo(null);
    }
}
