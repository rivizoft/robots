package gui;

import events.Observer;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    private final GameVisualizer m_visualizer;

    public GameWindow(Observer ... listeners)
    {
        super("Игровое поле", true, true, true, true);

        m_visualizer = new GameVisualizer();

        for (Observer listener : listeners)
            m_visualizer.eventManager.subscribe(listener);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
