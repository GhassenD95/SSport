package services.events;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
//
public class EventBus {

    private static final Map<String, Consumer<?>> listeners = new HashMap<>();


    // Subscribe to an event with a specific type
    //listens to the event
    //melekher taatiha esm event testaneh w function kil event atheka yemchi
    public static <T> void subscribe(String eventType, Consumer<T> listener) {
        listeners.put(eventType, listener);
    }

    // Publish an event
    //ki t3ayet lel event, taatih esmou w data w hhowa ikhadem heka el function li hattha fil arraylist
    public static <T> void publish(String eventType, T message) {
        if (listeners.containsKey(eventType)) {
            // Call accept() on the listener to perform the action with the message
            ((Consumer<T>) listeners.get(eventType)).accept(message);
        }
    }



}
