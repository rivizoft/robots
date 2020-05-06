package gui;

import javax.swing.*;
import java.awt.*;

public class ImageWindow extends JInternalFrame {
    public ImageWindow()
    {
        super("Картинка", true, true, true, true);

        JPanel panel = new JPanel(new BorderLayout());
        getContentPane().add(panel);
        pack();
    }
}
