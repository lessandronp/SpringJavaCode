package com.solucaocriativa.controller.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;

import com.solucaocriativa.entidade.Departamento;
import com.solucaocriativa.entidade.Usuario;
import com.solucaocriativa.jsf.FacesUtils;
import com.solucaocriativa.service.ServicoDepartamento;
import com.solucaocriativa.service.ServicoUsuario;

@Named("gerenciaUsuarioBean")
@Scope("view")
public class GerenciaUsuarioBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(GerenciaUsuarioBean.class);

    /* Servicos */
    private ServicoUsuario servicoUsuario;
    private ServicoDepartamento servicoDepartamento;
    
    private List<Usuario> usuarios;
    private Usuario usuario;
    private boolean somenteLeitura = Boolean.FALSE; 
    private List<Departamento> departamentos;
    private Departamento departamento;

    @Inject
    public GerenciaUsuarioBean(ServicoUsuario servicoUsuario, 
	    ServicoDepartamento servicoDepartamento) {
	this.servicoUsuario = servicoUsuario;
	this.servicoDepartamento = servicoDepartamento;
    }
    
    public void init() {
	this.usuario = null;
	limpaUsuarios();
	this.usuarios = servicoUsuario.buscaTodos();
	this.departamentos = servicoDepartamento.buscaTodos();
    }
    
    public void novo() {
	this.usuario = new Usuario();
	setSomenteLeitura(Boolean.FALSE);
    }
    
    public void limpaUsuarios() {
	this.usuarios = new ArrayList<>();
	this.usuarios.clear();
    }
    
    public void visualizar(Usuario usuario) {
	preparaUsuarioSelecionado(usuario);
	setSomenteLeitura(Boolean.TRUE);
    }

    public void editar(Usuario usuario) {
	preparaUsuarioSelecionado(usuario);
	setSomenteLeitura(Boolean.FALSE);
    }

    private void preparaUsuarioSelecionado(Usuario usuario) {
	this.usuario = usuario;
    }

    public void remover(Usuario usuario) {
	preparaUsuarioSelecionado(usuario);
	FacesMessage message = null;
	try {
	    this.servicoUsuario.remove(usuario);
	    message = FacesUtils.addSuccessMessage("msg.deleteUser.success");
	} catch (Exception e) {
	    log.error(e.getMessage());
	    message = FacesUtils.addErrorMessage("msg.deleteUser.error");
	}
	RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
    
    public void salvar() {
	FacesMessage message = null;
	try {
	    if (usuario.getId() != null) {
		this.servicoUsuario.atualizaUsuario(usuario);
		message = FacesUtils.addSuccessMessage("msg.updateUser.success");
	    } else {
		this.servicoUsuario.saveUser(usuario);
		message = FacesUtils.addSuccessMessage("msg.saveUser.success");
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	    message = FacesUtils.addErrorMessage("msg.saveUser.error");
	}
	RequestContext.getCurrentInstance().showMessageInDialog(message);
	init();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isSomenteLeitura() {
        return somenteLeitura;
    }

    public void setSomenteLeitura(boolean somenteLeitura) {
        this.somenteLeitura = somenteLeitura;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

}
