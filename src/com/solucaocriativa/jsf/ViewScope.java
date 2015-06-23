package com.solucaocriativa.jsf;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

/**
 * Implements the JSF View Scope for use by Spring. This class is registered as
 * a Spring bean with the CustomScopeConfigurer.
 */
public class ViewScope implements Scope {

    public static final String VIEW_SCOPE_CALLBACKS = "viewScope.callbacks";

    @Override
    public synchronized Object get(String name, ObjectFactory<?> objectFactory) {
        Object instance = getViewMap().get(name);
        if (instance == null) {
            instance = objectFactory.getObject();
            getViewMap().put(name, instance);
        }
        return instance;
    }

    @Override
    public Object remove(String name) {
        Object instance = getViewMap().remove(name);
        if (instance != null) {
            Map<String, Runnable> callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
            if (callbacks != null) {
                callbacks.remove(name);
            }
        }
        return instance;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable runnable) {
        Map<String, Runnable> callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
        if (callbacks != null) {
            callbacks.put(name, runnable);
        }
    }

    @Override
    public Object resolveContextualObject(String name) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
        return facesRequestAttributes.resolveReference(name);
    }

    @Override
    public String getConversationId() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
        return facesRequestAttributes.getSessionId() + "-" + facesContext.getViewRoot().getViewId();
    }

    private Map<String, Object> getViewMap() {
        return FacesContext.getCurrentInstance().getViewRoot().getViewMap();
    }
}
