package config;

import javax.swing.*;
import java.awt.*;

public class UIConfig {
    private UIConfig() {
    }

    public static void aplicarEstilos() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("No se pudo aplicar Nimbus. Se usa estilo por defecto.");
        }

        // Colores base para conseguir una interfaz mas limpia y legible en clase.
        UIManager.put("control", new Color(245, 248, 252));
        UIManager.put("nimbusBase", new Color(52, 85, 122));
        UIManager.put("nimbusFocus", new Color(67, 126, 188));
        UIManager.put("nimbusSelectionBackground", new Color(67, 126, 188));

        UIManager.put("List.background", Color.WHITE);
        UIManager.put("List.foreground", new Color(32, 33, 36));
        UIManager.put("List.selectionBackground", new Color(67, 126, 188));
        UIManager.put("List.selectionForeground", Color.WHITE);

        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 14));
        UIManager.put("Label.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("TitledBorder.font", new Font("SansSerif", Font.BOLD, 13));
    }
}
