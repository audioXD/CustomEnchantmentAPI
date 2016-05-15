package adx.audioxd.customenchantmentapi.enchantment;


import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEvent;
import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventHandler;
import adx.audioxd.customenchantmentapi.enchantment.event.EnchantmentEventPriority;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RegisteredListener implements Comparable<RegisteredListener> {
	private final Enchantment enchantment;
	private final Method method;

	// Constructor
	RegisteredListener(Enchantment listener, Method method) {
		this.enchantment = listener;
		this.method = method;
		method.setAccessible(true);

		if(method.getParameterCount() != 1) {
			throw new IllegalArgumentException(
					"Method must have a Event as the only parameter: " + this);
		}
	}

	public void fireEvent(EnchantmentEvent event) {
		try {
			method.invoke(enchantment, event);
		} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new EnchantmentEventException("Exception in: " + this + "(" + e.getMessage() + ")", e.getCause());
		}
	}

	@Override
	public String toString() {
		return enchantment.getClass().getName() + "::" + method.getName();
	}

	@Override
	public int compareTo(RegisteredListener other) {
		return other.getPriority().compareTo(getPriority());
	}

	public EnchantmentEventPriority getPriority() {
		return method.getAnnotation(EnchantmentEventHandler.class).priority();
	}

	@Override
	public int hashCode() {
		int result = enchantment.hashCode();
		result = 31 * result + method.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(o == null || getClass() != o.getClass()) return false;
		RegisteredListener other = (RegisteredListener) o;
		return enchantment.equals(other.enchantment) && method.equals(other.method);
	}

	// Getters
	public Class<?> getEventClass() {
		return method.getParameterTypes()[0];
	}

	public Enchantment getEnchantment() {
		return enchantment;
	}

	public Method getMethod() {
		return method;
	}

	public boolean isIgnoreCancelled() {
		return method.getAnnotation(EnchantmentEventHandler.class).ignoreCancelled();
	}
}
