package gui;

import log.LogEntry;

import javax.swing.*;
import java.awt.*;

public class CoordinatesWindow extends JInternalFrame
{
    private static TextArea m_coordinates;
    private static int m_prevX = 0, m_prevY = 0;

    public CoordinatesWindow(GameVisualizer visualizer)
    {
        super("Координаты робота", true, true, true, true);

        m_coordinates = new TextArea("");
        m_coordinates.setSize(this.getWidth(), this.getHeight());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_coordinates, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public static void updateCoordinates(int x, int y)
    {
        if (x != m_prevX || y != m_prevY)
        {
            m_coordinates.append("X: " + x + " Y: " + y + "\n");
            m_coordinates.invalidate();
        }
        m_prevX = x;
        m_prevY = y;
    }
}
