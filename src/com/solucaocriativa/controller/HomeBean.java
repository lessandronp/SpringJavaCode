package com.solucaocriativa.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;

import com.solucaocriativa.entidade.Usuario;
import com.solucaocriativa.jsf.FacesUtils;
import com.solucaocriativa.service.ServicoUsuario;

@Named("homeBean")
@Scope("session")
public class HomeBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(HomeBean.class);
    private Usuario usuarioAutenticado;
    private ServicoUsuario servicoUsuario;

    @Inject
    public HomeBean(ServicoUsuario servicoUsuario) {
	this.servicoUsuario = servicoUsuario;
    }
    
    @PostConstruct
    public void init() {
	this.usuarioAutenticado = servicoUsuario.buscaUsuarioAutenticado();
    }

    public void logout() {
	try {
	    FacesUtils.getExternalContext().dispatch("/j_spring_security_logout");
	    FacesUtils.getContext().responseComplete();
	} catch (IOException e) {
	    log.error(e.getMessage());
	}
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void setUsuarioAutenticado(Usuario usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
    }
}
