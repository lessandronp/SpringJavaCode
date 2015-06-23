package com.solucaocriativa.jsf;

//~--- JDK imports ------------------------------------------------------------
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.solucaocriativa.util.Constants;

public class FacesUtils {

    public static void addErrorMessageExcecao(Exception ex, String defaultMsg) {
	String msg = ex.getLocalizedMessage();

	if ((msg != null) && (msg.length() > 0)) {
	    addErrorMessage(msg);
	} else {
	    addErrorMessage(defaultMsg);
	}
    }

    public static void addErrorMessages(List<String> messages) {
	for (String message : messages) {
	    addErrorMessage(message);
	}
    }

    public static FacesMessage msgErroComponente(String idComponente, String msg) {
	FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
		getBundle().getString(msg), getBundle().getString(msg));
	FacesContext.getCurrentInstance().addMessage(idComponente, mensagem);
	return mensagem;
    }

    public static FacesMessage addErrorMessageWithoutInternacionalization(String idComponente, String msg) {
	FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
		msg, null);
	FacesContext.getCurrentInstance().addMessage(idComponente, mensagem);
	return mensagem;
    }

    public static void addErrorMessage(String msg) {
	msgErroComponente(null, msg);
    }

    public static void addErrorMessageWithAsterisk(String msg) {
	msg = getBundle().getString(msg);
	FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
		msg, msg);
	FacesContext.getCurrentInstance().addMessage(null, mensagem);
    }

    public static void addWarningMessageComponent(String idComponente, String msg) {
	FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_WARN, 
		getBundle().getString(msg), getBundle().getString(msg));
	FacesContext.getCurrentInstance().addMessage(idComponente, mensagem);
    }

    private static ResourceBundle getBundle() {
	Locale locale = getContext().getViewRoot().getLocale();
	ResourceBundle bundle = ResourceBundle.getBundle("com.solucaocriativa.resources.messages", 
		locale);
	return bundle;
    }

    public static void addWarningMessage(String msg) {
	addWarningMessageComponent(null, msg);
    }

    public static void msgSucessoComponente(String idComponente, String msg) {
	FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, 
		getBundle().getString(msg), getBundle().getString(msg));
	FacesContext.getCurrentInstance().addMessage(idComponente, mensagem);
    }

    public static void addSuccessMessage(String msg) {
	msgSucessoComponente(null, msg);
    }

    public static String getRequestParameter(final String param) {
	ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	return context.getRequestParameterMap().get(param);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter,
	    UIComponent component) {
	String theId = FacesUtils.getRequestParameter(requestParameterName);
	return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public static Object getMethod(Object obj, String name) throws Exception {
	Method createMethod = obj.getClass().getMethod(name, new Class[0]);
	return createMethod.invoke(obj, new Object[0]);
    }

    public static HttpServletRequest getServletRequest() {
	return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public static HttpServletResponse getServletResponse() {
	return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }

    public static ExternalContext getExternalContext() {
	return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static FacesContext getContext() {
	return FacesContext.getCurrentInstance();
    }

    public static Object getAttribute(String key) {
	Map<String, Object> map = getExternalContext().getSessionMap();
	Object obj = map.get(key);
	map.remove(key);
	return obj;
    }

    public static void setAttribute(String key, Object value) {
	getExternalContext().getSessionMap().put(key, value);
    }

    public static String getApplicationURI() {
	try {
	    String uri = getExternalContext().getRequestScheme().concat("://").concat(getExternalContext().getRequestServerName())
		    .concat(":").concat(String.valueOf(getExternalContext().getRequestServerPort()));
		    //.concat(getServletRequest().getContextPath());
	    return uri;
	} catch (Exception ex) {
	}
	return null;
    }

    public static String getApplicationPath() {
	try {
	    URI uri = new URI(getExternalContext().getRequestScheme(), null, getExternalContext()
		    .getRequestServerName(), getExternalContext().getRequestServerPort(), getExternalContext()
		    .getRequestContextPath(), null, null);
	    return uri.toASCIIString();
	} catch (Exception ex) {
	}
	return null;
    }

    /**
     * Recupera o caminho físico no servidor de aplicações.
     * @return Caminho físico no servidor de aplicações
     */
    public static String getRealPath() {
	ServletContext servletContext = (ServletContext) getExternalContext().getContext();
	return servletContext.getRealPath("/");
    }
    
    /**
     * Recupera o objeto da sessão.
     * @param key Chave
     * @return Objeto
     */
    public static Object getSessionMap(String key) {
	return getExternalContext().getSessionMap().get(key);
    }
    
    /**
     * Recupera o mapa da sessão.
     * @return Mapa da sessão
     */
    public static Map<String, Object> getSessionMap() {
	return getExternalContext().getSessionMap();
    }
    
    public static HttpServletRequest getRequest() throws Exception {
	return (HttpServletRequest) getExternalContext().getRequest();
    }
    
    /**
     * Limpa as mensagens.
     */
    public static void clearMessages() {
	Iterator<FacesMessage> it = getContext().getMessages();
	while ( it.hasNext() ) {
	    it.next();
	    it.remove();
	}
    }
}
