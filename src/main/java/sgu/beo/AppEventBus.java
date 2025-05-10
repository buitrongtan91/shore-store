package sgu.beo;

import com.google.common.eventbus.EventBus;

public class AppEventBus {
    // Tạo EventBus
    private static final EventBus eventBus = new EventBus();

    public static EventBus getInstance() {
        return eventBus;
    }
}