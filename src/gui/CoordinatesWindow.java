package gui;

import events.Observer;

import javax.swing.*;
import java.awt.*;

public class CoordinatesWindow extends JInternalFrame implements Observer
{
    private static TextArea m_coordinates;
    private static int m_prevX = 0, m_prevY = 0;

    public CoordinatesWindow()
    {
        super("Координаты робота", true, true, true, true);

        m_coordinates = new TextArea("");
        m_coordinates.setSize(this.getWidth(), this.getHeight());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_coordinates, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void update(Object o)
    {
        Point point = (Point)o;
        if (point.x != m_prevX || point.y != m_prevY)
        {
            m_coordinates.append("X: " + point.x + " Y: " + point.y + "\n");
            m_coordinates.invalidate();
        }
        m_prevX = point.x;
        m_prevY = point.y;
    }
}
