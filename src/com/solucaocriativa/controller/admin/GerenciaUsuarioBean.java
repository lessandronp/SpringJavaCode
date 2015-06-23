package com.solucaocriativa.controller.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.solucaocriativa.entidade.Usuario;
import com.solucaocriativa.service.ServicoUsuario;

@Named("gerenciaUsuarioBean")
@Scope("view")
public class GerenciaUsuarioBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /* Servicos */
    private ServicoUsuario servicoUsuario;
    
    private List<Usuario> usuarios;

    @Inject
    public GerenciaUsuarioBean(ServicoUsuario servicoUsuario) {
	this.servicoUsuario = servicoUsuario;
    }
    
    public void init() {
	limpaUsuarios();
	this.usuarios = servicoUsuario.buscaTodos();
    }
    
    public void limpaUsuarios() {
	this.usuarios = new ArrayList<>();
	this.usuarios.clear();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
