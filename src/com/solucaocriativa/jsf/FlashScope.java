package com.solucaocriativa.jsf;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class FlashScope implements Scope {

	private ExternalContext getContext() {
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		if (currentInstance != null)
			return currentInstance.getExternalContext();
		return null;
	}

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		ExternalContext context = getContext();
		if (context == null)
			return objectFactory.getObject();
		Flash flash = context.getFlash();
		Object object = flash.get(name);
		if (object == null) {
			object = objectFactory.getObject();
			flash.put(name, object);
		}
		return object;
	}

	@Override
	public Object remove(String name) {
		ExternalContext context = getContext();
		if (context != null) {
			Flash flash = context.getFlash();
			if (flash != null) {
				return flash.remove(name);
			}
		}
		return null;
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
	}

	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

	@Override
	public String getConversationId() {
		return null;
	}
}
