package gui;

import log.Logger;
import windows.SaverStateWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private SaverStateWindows saverStateWindows;
    
    public MainApplicationFrame() throws IOException {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        CoordinatesWindow coordinatesWindow = new CoordinatesWindow();
        addWindow(coordinatesWindow);

        GameWindow gameWindow = new GameWindow(coordinatesWindow);
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        saverStateWindows = new SaverStateWindows(desktopPane.getAllFrames());
        saverStateWindows.restoreStateWindows();

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new ExitListener());
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Лог работает, кол-во хранимых сообщений: " + Logger.getAllowableSize());
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
    
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");
        
        {
            JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }

        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");
        
        {
            JMenuItem addLogMessageItem = new JMenuItem("Тестовое сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Тестовое сообщение");
            });
            JMenuItem addLogMessageItem2 = new JMenuItem("Сообщение - ошибка", KeyEvent.VK_S);
            addLogMessageItem2.addActionListener((event) -> {
                Logger.error("Тестовое сообщение об ошибке");
            });
            testMenu.add(addLogMessageItem2);
            testMenu.add(addLogMessageItem);
        }

        //Задание
        JMenu aboutMenu = new JMenu("О программе");
        aboutMenu.setMnemonic(KeyEvent.VK_O);
        aboutMenu.getAccessibleContext().setAccessibleDescription(
                "Информация о программе");
        JMenuItem aboutMenuItem = new JMenuItem("Выйти", KeyEvent.VK_S);
        aboutMenuItem.addActionListener(new ExitListener());
        aboutMenu.add(aboutMenuItem);


        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(aboutMenu);
        return menuBar;
    }
    
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }

    class ExitListener implements ActionListener, WindowListener
    {
        private void Exit()
        {
            Logger.debug("Нажата кнопка выхода.");
            UIManager.put("OptionPane.yesButtonText"   , "Да");
            UIManager.put("OptionPane.noButtonText"    , "Нет");

            int result = JOptionPane.showConfirmDialog(
                    new Component() {},
                    "Действительно закрыть?",
                    "Выход",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION)
                System.exit(0);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Exit();
        }

        @Override
        public void windowOpened(WindowEvent windowEvent) {

        }

        @Override
        public void windowClosing(WindowEvent windowEvent)
        {
            try {
                saverStateWindows.saveStateWindows();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Exit();
        }

        @Override
        public void windowClosed(WindowEvent windowEvent) {
        }

        @Override
        public void windowIconified(WindowEvent windowEvent) {

        }

        @Override
        public void windowDeiconified(WindowEvent windowEvent) {

        }

        @Override
        public void windowActivated(WindowEvent windowEvent) {

        }

        @Override
        public void windowDeactivated(WindowEvent windowEvent) {

        }
    }
}
