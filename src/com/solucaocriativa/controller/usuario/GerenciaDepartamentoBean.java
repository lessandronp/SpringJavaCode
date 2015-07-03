package com.solucaocriativa.controller.usuario;

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
import com.solucaocriativa.jsf.FacesUtils;
import com.solucaocriativa.service.ServicoDepartamento;

@Named("gerenciaDepartamentoBean")
@Scope("view")
public class GerenciaDepartamentoBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(GerenciaDepartamentoBean.class);

    /* Servicos */
    private ServicoDepartamento servicoDepartamento;

    private Departamento departamento;
    private List<Departamento> departamentos;
    private boolean somenteLeitura;

    @Inject
    public GerenciaDepartamentoBean(ServicoDepartamento servicoDepartamento) {
	this.servicoDepartamento = servicoDepartamento;
    }
    
    public void init() {
	this.departamento = null;
	limpaDepartamentos();
	this.departamentos = servicoDepartamento.buscaTodos();
    }
    
    public void novo() {
	this.departamento = new Departamento();
	setSomenteLeitura(Boolean.FALSE);
    }
    
    public void limpaDepartamentos() {
	this.departamentos = new ArrayList<>();
	this.departamentos.clear();
    }
    
    public void visualizar(Departamento departamento) {
	preparaDepartamentoSelecionado(departamento);
	setSomenteLeitura(Boolean.TRUE);
    }

    public void editar(Departamento departamento) {
	preparaDepartamentoSelecionado(departamento);
	setSomenteLeitura(Boolean.FALSE);
    }

    private void preparaDepartamentoSelecionado(Departamento departamento) {
	this.departamento = departamento;
    }

    public void remover(Departamento departamento) {
	preparaDepartamentoSelecionado(departamento);
	FacesMessage message = null;
	try {
	    this.servicoDepartamento.remove(departamento);
	    message = FacesUtils.addSuccessMessage("msg.deleteDepartment.success");
	    init();
	} catch (Exception e) {
	    log.error(e.getMessage());
	    message = FacesUtils.addErrorMessage("msg.deleteDepartment.error");
	}
	RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
    
    public void salvar() {
	FacesMessage message = null;
	try {
	    if (departamento.getId() != null) {
		this.servicoDepartamento.atualiza(departamento);
		message = FacesUtils.addSuccessMessage("msg.updateDepartment.success");
	    } else {
		this.servicoDepartamento.salva(departamento);
		message = FacesUtils.addSuccessMessage("msg.saveDepartment.success");
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	    message = FacesUtils.addErrorMessage("msg.saveDepartment.error");
	}
	RequestContext.getCurrentInstance().showMessageInDialog(message);
	init();
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public boolean isSomenteLeitura() {
        return somenteLeitura;
    }

    public void setSomenteLeitura(boolean somenteLeitura) {
        this.somenteLeitura = somenteLeitura;
    }


}
