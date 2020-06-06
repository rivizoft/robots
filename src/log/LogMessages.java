package log;

import java.util.*;

public class LogMessages<T> implements Iterable<T>
{
    private int _count;
    private volatile LinkedList<T> _messages;

    LogMessages(int maxCount)
    {
        _count = maxCount;
        _messages = new LinkedList<>();
    }

    public synchronized void pushMessage(T message)
    {
        if (_messages.size() == _count)
            _messages.removeFirst();

        _messages.add(message);
    }

    public T getLastMessage()
    {
        return _messages.getLast();
    }

    public T getFirstMessage()
    {
        return _messages.getFirst();
    }

    public int size()
    {
        return _messages.size();
    }

    public synchronized Iterable<T> subList(int from, int to)
    {
        LinkedList<T> newList = new LinkedList<>();

        for (int i = from; i < to; i++)
            newList.add(0, _messages.get(i));

        return newList;
    }

    @Override
    public Iterator<T> iterator()
    {
        return _messages.descendingIterator();
    }
}
