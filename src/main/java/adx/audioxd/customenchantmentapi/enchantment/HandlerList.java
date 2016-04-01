package adx.audioxd.customenchantmentapi.enchantment;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HandlerList {
	private final Set<RegisteredListener> listeners;
	private volatile RegisteredListener[] backedListeners;

	HandlerList() {
		listeners = new HashSet<>();
	}

	public void fireEvent(EnchantmentEvent event) {
		for(RegisteredListener listener : bake()) {
			listener.fireEvent(event);
		}
	}

	private RegisteredListener[] bake() {
		RegisteredListener[] baked = backedListeners;
		if(baked == null) {
			// Set -> array
			synchronized(this) {
				if((baked = backedListeners) == null) {
					baked = listeners.toArray(new RegisteredListener[listeners.size()]);
					Arrays.sort(baked);
					backedListeners = baked;
				}
			}
		}
		return baked;
	}

	public void registerListener(RegisteredListener listener) {
		if(listeners.add(listener)) {
			backedListeners = null;
		}
	}

	public void unregisterListener(RegisteredListener listener) {
		if(listeners.remove(listener)) {
			backedListeners = null;
		}
	}
}
