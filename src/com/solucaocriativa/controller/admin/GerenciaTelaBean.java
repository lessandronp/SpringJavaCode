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

import com.solucaocriativa.entidade.Tela;
import com.solucaocriativa.jsf.FacesUtils;
import com.solucaocriativa.service.ServicoTela;
import com.solucaocriativa.vo.TelaVO;

@Named("gerenciaTelaBean")
@Scope("view")
public class GerenciaTelaBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(GerenciaTelaBean.class);

    /* Servicos */
    private ServicoTela servicoTela;

    private Tela tela;
    private List<Tela> telas;
    private boolean somenteLeitura;
    private List<TelaVO> telasDisponiveis;
    
    @Inject
    public GerenciaTelaBean(ServicoTela servicoTela) {
	this.servicoTela = servicoTela;
    }
    
    public void init() {
	this.tela = null;
	limpaTelas();
	preparaTelasDisponiveis();
	this.telas = servicoTela.buscaTodos();
    }
    
    public void novo() {
	this.tela = new Tela();
	setSomenteLeitura(Boolean.FALSE);
    }
    
    public void limpaTelas() {
	this.telas = new ArrayList<>();
	this.telas.clear();
    }
    
    public void visualizar(Tela tela) {
	preparaTelaSelecionada(tela);
	setSomenteLeitura(Boolean.TRUE);
    }

    public void editar(Tela tela) {
	preparaTelaSelecionada(tela);
	setSomenteLeitura(Boolean.FALSE);
    }

    private void preparaTelaSelecionada(Tela tela) {
	this.tela = tela;
    }
    
    private void preparaTelasDisponiveis() {
	telasDisponiveis = servicoTela.recuperaTelasDisponiveis(
		FacesUtils.getExternalContext().getRealPath("paginas"));
    }

    public void remover(Tela tela) {
	preparaTelaSelecionada(tela);
	FacesMessage message = null;
	try {
	    this.servicoTela.remove(tela);
	    message = FacesUtils.addSuccessMessage("msg.deleteScreen.success");
	    init();
	} catch (Exception e) {
	    log.error(e.getMessage());
	    message = FacesUtils.addErrorMessage("msg.deleteScreen.error");
	}
	RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
    
    private void aplicaTelaSelecionada() {
	for (TelaVO telaDisponivel : telasDisponiveis) {
	    if (telaDisponivel.selecionada) {
		this.tela.setCodigo(telaDisponivel.getCodigo());
	    }
	}
    }
    
    public void salvar() {
	FacesMessage message = null;
	try {
	    aplicaTelaSelecionada();
	    if (tela.getId() != null) {
		this.servicoTela.atualiza(tela);
		message = FacesUtils.addSuccessMessage("msg.updateScreen.success");
	    } else {
		this.servicoTela.salva(tela);
		message = FacesUtils.addSuccessMessage("msg.saveScreen.success");
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	    message = FacesUtils.addErrorMessage("msg.saveScreen.error");
	}
	RequestContext.getCurrentInstance().showMessageInDialog(message);
	init();
    }

    public Tela getTela() {
        return tela;
    }

    public void setTela(Tela tela) {
        this.tela = tela;
    }

    public List<Tela> getTelas() {
        return telas;
    }

    public void setTelas(List<Tela> telas) {
        this.telas = telas;
    }

    public boolean isSomenteLeitura() {
        return somenteLeitura;
    }

    public void setSomenteLeitura(boolean somenteLeitura) {
        this.somenteLeitura = somenteLeitura;
    }

    public List<TelaVO> getTelasDisponiveis() {
        return telasDisponiveis;
    }

    public void setTelasDisponiveis(List<TelaVO> telasDisponiveis) {
        this.telasDisponiveis = telasDisponiveis;
    }

}
