package adx.audioxd.customenchantmentapi.enchantment;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent;
import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventHandler;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventBus {
	private final Map<Class<?>, HandlerList> handlers = new ConcurrentHashMap<>();

	// Constructor
	EventBus(Enchantment listener) {
		if(listener == null) throw new NullPointerException("Listener cannot be null!");

		for(Method method : listener.getClass().getMethods()) {
			if(!method.isAnnotationPresent(EnchantmentEventHandler.class)) continue;

			RegisteredListener rListener = new RegisteredListener(listener, method);
			handlers.computeIfAbsent(rListener.getEventClass(), (eventClass) -> new HandlerList())
					.registerListener(rListener);
		}
	}

	void fireEvent(EnchantmentEvent event, int lvl, boolean sync) {
		if(event == null) throw new NullPointerException("Event cannot be null");

		HandlerList handler = handlers.get(event.getClass());
		if(handler == null) return;

		if(sync) {
			synchronized(EventBus.class) {
				handler.fireEvent(event, lvl);
			}
		} else {
			handler.fireEvent(event, lvl);
		}
	}
}
