package com.solucaocriativa.controller.admin;

import java.io.IOException;
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
import com.solucaocriativa.entidade.PermissaoTelaDepartamento;
import com.solucaocriativa.entidade.Tela;
import com.solucaocriativa.jsf.FacesUtils;
import com.solucaocriativa.service.ServicoDepartamento;
import com.solucaocriativa.service.ServicoPermissaoTelaDepartamento;
import com.solucaocriativa.service.ServicoTela;
import com.solucaocriativa.util.MessageUtil;

@Named("gerenciaPermissaoTelaDeptoBean")
@Scope("view")
public class GerenciaPermissaoTelaDeptoBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(GerenciaPermissaoTelaDeptoBean.class);

    /* Servicos */
    private ServicoTela servicoTela;
    private ServicoDepartamento servicoDepartamento;
    private ServicoPermissaoTelaDepartamento servicoPermissaoTelaDepartamento;

    private List<Tela> telas;
    private List<Departamento> departamentos;
    private PermissaoTelaDepartamento permissaoTelaDepartamento;
    private List<PermissaoTelaDepartamento> permissoesTelaDepartamento;
    private boolean somenteLeitura;

    @Inject
    public GerenciaPermissaoTelaDeptoBean(ServicoPermissaoTelaDepartamento servicoPermissaoTelaDepartamento,
	    ServicoDepartamento servicoDepartamento, ServicoTela servicoTela) {
	this.servicoTela = servicoTela;
	this.servicoDepartamento = servicoDepartamento;
	this.servicoPermissaoTelaDepartamento = servicoPermissaoTelaDepartamento;
    }
    
    public void init() {
	this.permissaoTelaDepartamento = null;
	limpaPermissoesTelaDepartamento();
	this.permissoesTelaDepartamento = servicoPermissaoTelaDepartamento.buscaTodos();
	preparaTelas();
	preparaDepartamentos();
    }
    
    public void novo() {
	this.permissaoTelaDepartamento = new PermissaoTelaDepartamento();
	setSomenteLeitura(Boolean.FALSE);
    }
    
    public void limpaPermissoesTelaDepartamento() {
	this.permissoesTelaDepartamento = new ArrayList<>();
	this.permissoesTelaDepartamento.clear();
    }

    public void preparaTelas() {
	this.telas = new ArrayList<>();
	this.telas.clear();
	this.telas = servicoTela.buscaTodos();
    }
    
    public void preparaDepartamentos() {
	this.departamentos = new ArrayList<>();
	this.departamentos.clear();
	this.departamentos = servicoDepartamento.buscaTodos();
    }

    public void visualizar(PermissaoTelaDepartamento permissaoTelaDepartamento) {
	preparaPermissaoTelaDepartamento(permissaoTelaDepartamento);
	setSomenteLeitura(Boolean.TRUE);
    }

    public void editar(PermissaoTelaDepartamento permissaoTelaDepartamento) {
	preparaPermissaoTelaDepartamento(permissaoTelaDepartamento);
	setSomenteLeitura(Boolean.FALSE);
    }
    
    public String exibePermissao(boolean permite) {
	try {
	    return permite ? MessageUtil.getMessage("statusQuestionType.yes", new Object()) 
	    	: MessageUtil.getMessage("statusQuestionType.no", new Object());
	} catch (IOException e) {
	    log.error(e.getMessage());
	}
	return new String();
    }

    private void preparaPermissaoTelaDepartamento(PermissaoTelaDepartamento permissaoTelaDepartamento) {
	this.permissaoTelaDepartamento = permissaoTelaDepartamento;
    }

    public void remover(PermissaoTelaDepartamento permissaoTelaDepartamento) {
	preparaPermissaoTelaDepartamento(permissaoTelaDepartamento);
	FacesMessage message = null;
	try {
	    this.servicoPermissaoTelaDepartamento.remove(permissaoTelaDepartamento);
	    message = FacesUtils.addSuccessMessage("msg.deletePermissionScreenDepartment.success");
	    init();
	} catch (Exception e) {
	    log.error(e.getMessage());
	    message = FacesUtils.addErrorMessage("msg.deletePermissionScreenDepartment.error");
	}
	RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
    
    public void salvar() {
	FacesMessage message = null;
	try {
	    if (permissaoTelaDepartamento.getId() != null) {
		this.servicoPermissaoTelaDepartamento.atualiza(permissaoTelaDepartamento);
		message = FacesUtils.addSuccessMessage("msg.updatePermissionScreenDepartment.success");
	    } else {
		this.servicoPermissaoTelaDepartamento.salva(permissaoTelaDepartamento);
		message = FacesUtils.addSuccessMessage("msg.savePermissionScreenDepartment.success");
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	    message = FacesUtils.addErrorMessage("msg.savePermissionScreenDepartment.error");
	}
	RequestContext.getCurrentInstance().showMessageInDialog(message);
	init();
    }

    public List<Tela> getTelas() {
        return telas;
    }

    public void setTelas(List<Tela> telas) {
        this.telas = telas;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public PermissaoTelaDepartamento getPermissaoTelaDepartamento() {
        return permissaoTelaDepartamento;
    }

    public void setPermissaoTelaDepartamento(
    	PermissaoTelaDepartamento permissaoTelaDepartamento) {
        this.permissaoTelaDepartamento = permissaoTelaDepartamento;
    }

    public List<PermissaoTelaDepartamento> getPermissoesTelaDepartamento() {
        return permissoesTelaDepartamento;
    }

    public void setPermissoesTelaDepartamento(
    	List<PermissaoTelaDepartamento> permissoesTelaDepartamento) {
        this.permissoesTelaDepartamento = permissoesTelaDepartamento;
    }

    public boolean isSomenteLeitura() {
        return somenteLeitura;
    }

    public void setSomenteLeitura(boolean somenteLeitura) {
        this.somenteLeitura = somenteLeitura;
    }


}
