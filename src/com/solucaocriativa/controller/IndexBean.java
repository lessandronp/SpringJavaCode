package com.solucaocriativa.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;

import com.solucaocriativa.jsf.FacesUtils;

@Named("indexBean")
@Scope("view")
public class IndexBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(IndexBean.class);

    public IndexBean() {
    }
    
    /**
     * Autentica o usuario.
     */
    public void authenticate() {
	try {
	    FacesUtils.getExternalContext().dispatch("/j_spring_security_check");
	    FacesUtils.getContext().responseComplete();
	} catch (IOException e) {
	    log.error(e.getMessage());
	}
    }

}
