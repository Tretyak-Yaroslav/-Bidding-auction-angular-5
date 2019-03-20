package uk.co.afe.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author Sergey Teryoshin
 * 27.03.2018 15:33
 */
public abstract class BaseEvent<T> extends ApplicationEvent {

    public BaseEvent(T source) {
        super(source);
    }

    public T getSource() {
        return (T) super.getSource();
    }
}
