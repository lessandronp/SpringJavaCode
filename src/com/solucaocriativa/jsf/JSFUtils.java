package com.solucaocriativa.jsf;

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class JSFUtils {
    
    /**
     * Recupera um componente na árvore de componentes.
     * @param c Componente
     * @param id Identificador do componente
     * @return Componente
     */
    public static UIComponent findComponent(UIComponent c, String id) {
	if (id.equals(c.getId())) {
	    return c;
	}
	Iterator<UIComponent> kids = c.getFacetsAndChildren();
	while (kids.hasNext()) {
	    UIComponent found = findComponent(kids.next(), id);
	    if (found != null) {
		return found;
	    }
	}
	return null;
    }
    
    /**
     * Recupera o componente, caso não encontrar busca nos filhos.
     * @param c Componente
     * @param id Identificador do componente
     * @param parentId Identificador do componente pai
     * @return Componente
     */
    public static UIComponent findComponent(UIComponent c, String id, String parentId) {
	if (c.getId().equals(id) && c.getParent().getId().equals(parentId)) {
	    return c;
	}
	Iterator<UIComponent> kids = c.getFacetsAndChildren();
	while (kids.hasNext()) {
	    UIComponent found = findComponent(kids.next(), id, parentId);
	    if (found != null) {
		return found;
	    }
	}
	return null;
    }

    
    /**
     * Recupera um componente na árvore de componentes.
     * @param id Identificador do componente
     * @param parentId Identificador do componente pai
     * @return Componente
     */
    public static UIComponent findComponent(String id, String parentId) {
	FacesContext context = FacesContext.getCurrentInstance();
	UIViewRoot root = context.getViewRoot();
	return findComponent(root, id, parentId);
    }
    
    /**
     * Altera o valor de um componente da árvore de componentes.
     * @param id Identificador do componente
     * @param parentId Identificador do componente pai
     * @param value Valor
     */
    public static void setValueComponent(String id, String parentId, String value) {
	UIComponent component = findComponent(id, parentId);
	component.getAttributes().put("value", value);
    }
    
    /**
     * Altera o estilo de um componente da árvore de componentes.
     * @param id Identificador do componente
     * @param parentId Identificador do componente pai
     * @param styleClass Estilo
     */
    public static void setStyleClassComponent(String id, String parentId, 
	    String styleClass) {
	UIComponent component = findComponent(id, parentId);
	component.getAttributes().put("styleClass", styleClass);
    }
    
    /**
     * Aplica um valor e um estilo no componente da árvore de componentes.
     * @param id Identificador do componente
     * @param parentId Identificador do componente pai
     * @param value Valor
     * @param styleClass Estilo
     */
    public static void setComponent(String id, String parentId, String value, 
	    String styleClass) {
	UIComponent component = findComponent(id, parentId);
	component.getAttributes().put("styleClass", styleClass);
	component.getAttributes().put("value", value);
    }
}
