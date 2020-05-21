package events;

import java.util.ArrayList;
import java.util.List;

public class EventManager
{
    private List<Observer> observers;

    public EventManager()
    {
        observers = new ArrayList<>();
    }

    public void subscribe(Observer observer)
    {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer)
    {
        observers.remove(observer);
    }

    public void notifyObservers(Object o)
    {
        for (Observer ob : observers)
        {
            ob.update(o);
        }
    }
}
