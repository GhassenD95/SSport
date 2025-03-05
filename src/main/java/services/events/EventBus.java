package services.events;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EventBus {

    private static final Map<String, List<Consumer<Object>>> singleListeners = new HashMap<>();
    private static final Map<String, List<BiConsumer<Object, Object>>> dualListeners = new HashMap<>();

    // Subscribe with a single parameter
    public static <T> void subscribe(String eventType, Consumer<T> listener) {
        singleListeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add((Consumer<Object>) listener);
    }

    // Subscribe with two parameters
    public static <T, U> void subscribe(String eventType, BiConsumer<T, U> listener) {
        dualListeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add((BiConsumer<Object, Object>) listener);
    }

    // Publish a single parameter event
    public static <T> void publish(String eventType, T message) {
        if (singleListeners.containsKey(eventType)) {
            for (Consumer<Object> listener : singleListeners.get(eventType)) {
                listener.accept(message);
            }
        }
    }

    // Publish an event with two parameters
    public static <T, U> void publish(String eventType, T message1, U message2) {
        if (dualListeners.containsKey(eventType)) {
            for (BiConsumer<Object, Object> listener : dualListeners.get(eventType)) {
                listener.accept(message1, message2);
            }
        }
    }
}
