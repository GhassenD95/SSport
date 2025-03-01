package services.events;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EventBus {

    private static final Map<String, Consumer<?>> singleListeners = new HashMap<>();
    private static final Map<String, BiConsumer<?, ?>> dualListeners = new HashMap<>();

    // OLD: Subscribe with a single parameter
    public static <T> void subscribe(String eventType, Consumer<T> listener) {
        singleListeners.put(eventType, listener);
    }

    // NEW: Subscribe with two parameters
    public static <T, U> void subscribe(String eventType, BiConsumer<T, U> listener) {
        dualListeners.put(eventType, listener);
    }

    // OLD: Publish a single parameter event
    public static <T> void publish(String eventType, T message) {
        if (singleListeners.containsKey(eventType)) {
            ((Consumer<T>) singleListeners.get(eventType)).accept(message);
        }
    }

    // NEW: Publish an event with two parameters
    public static <T, U> void publish(String eventType, T message1, U message2) {
        if (dualListeners.containsKey(eventType)) {
            ((BiConsumer<T, U>) dualListeners.get(eventType)).accept(message1, message2);
        }
    }
}
