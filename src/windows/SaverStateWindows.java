package windows;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SaverStateWindows
{
    private JInternalFrame[] _frames;

    public SaverStateWindows(JInternalFrame[] frames)
    {
        _frames = frames;
    }

    public void SaveStateWindows() throws IOException
    {
        File fileJSON = new File("positions.json");
        ArrayList<Window> windowList = new ArrayList<Window>();


        for (int i = 0; i < _frames.length; i++)
        {
            Window window = new Window();
            window.setWidth(_frames[i].getWidth());
            window.setHeight(_frames[i].getHeight());
            window.setX(_frames[i].getX());
            window.setY(_frames[i].getY());
            windowList.add(window);
        }

        var positionWindows = new WindowsPositions();
        positionWindows.setWindows(windowList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(fileJSON, positionWindows);
    }

    public void RestoreStateWindows() throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        WindowsPositions windowsPositions = mapper.readValue(new File("positions.json"), WindowsPositions.class);

        for (int i = 0; i < windowsPositions.getWindows().size(); i++)
        {
            Window window = windowsPositions.getWindows().get(i);
            _frames[i].setSize(window.getWidth(), window.getHeight());
            _frames[i].setLocation(window.getX(), window.getY());
        }
    }
}
