package log;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Что починить:
 * 1. Этот класс порождает утечку ресурсов (связанные слушатели оказываются
 * удерживаемыми в памяти)
 */

public class LogWindowSource
{
    private int m_iQueueLength;
    
    private volatile ArrayDeque<LogEntry> m_messages;
    private final ArrayList<LogChangeListener> m_listeners;
    private volatile LogChangeListener[] m_activeListeners;
    
    public LogWindowSource(int iQueueLength) 
    {
        m_iQueueLength = iQueueLength;
        m_messages = new ArrayDeque<LogEntry>(iQueueLength);
        m_listeners = new ArrayList<LogChangeListener>();
    }
    
    public void registerListener(LogChangeListener listener)
    {
        synchronized(m_listeners)
        {
            m_listeners.add(listener);
            m_activeListeners = null;
        }
    }

    public LogEntry getLastLogMessage()
    {
        return m_messages.getLast();
    }

    public LogEntry getFirstLogMessage()
    {
        return m_messages.getFirst();
    }

    public int getAllowableSize()
    {
        return m_iQueueLength;
    }
    
    public void unregisterListener(LogChangeListener listener)
    {
        synchronized(m_listeners)
        {
            m_listeners.remove(listener);
            m_activeListeners = null;
        }
    }
    
    public void append(LogLevel logLevel, String strMessage)
    {
        if (m_messages.size() > m_iQueueLength)
        {
            synchronized (m_messages)
            {
                System.out.println("Последняя запись была удалена!");
                m_messages.removeLast();
            }
        }

        LogEntry entry = new LogEntry(logLevel, strMessage);
        synchronized (m_messages)
        {
            m_messages.push(entry);
        }

        LogChangeListener [] activeListeners = m_activeListeners;

        if (activeListeners == null)
        {
            synchronized (m_listeners)
            {
                if (m_activeListeners == null)
                {
                    activeListeners = m_listeners.toArray(new LogChangeListener [0]);
                    m_activeListeners = activeListeners;
                }
            }
        }
        for (LogChangeListener listener : activeListeners)
        {
            listener.onLogChanged();
        }
    }
    
    public int size()
    {
        return m_messages.size();
    }

    public Iterable<LogEntry> range(int startFrom, int count)
    {
        if (startFrom < 0 || startFrom >= m_messages.size())
        {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, m_messages.size());
        //return m_messages.subList(startFrom, indexTo);
        return null;
    }

    public Iterable<LogEntry> all()
    {
        return m_messages;
    }
}
