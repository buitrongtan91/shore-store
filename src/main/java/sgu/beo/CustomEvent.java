package sgu.beo;

import javafx.event.Event;
import javafx.event.EventType;

public class CustomEvent extends Event {
    public static final EventType<CustomEvent> CUSTOM_EVENT = new EventType<>(Event.ANY, "CUSTOM_EVENT");

    private String message;

    public CustomEvent(String message) {
        super(CUSTOM_EVENT);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}