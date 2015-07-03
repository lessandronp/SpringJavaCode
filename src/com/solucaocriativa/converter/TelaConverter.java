package com.solucaocriativa.converter;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.solucaocriativa.entidade.Tela;
import com.solucaocriativa.service.ServicoTela;

@Named("telaConverter")
@Scope("view")
@FacesConverter("telaConverter")
public class TelaConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;
    private ServicoTela servicoTela;

    @Inject
    public TelaConverter(ServicoTela servicoTela) {
	this.servicoTela = servicoTela;
    }
    
    public TelaConverter() {
    }
    
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
	if (value != null && value.trim().length() > 0) {
	    try {
		Tela tela = servicoTela.buscaPorId(Long.parseLong(value));
		return tela;
	    } catch (NumberFormatException e) {
		throw new ConverterException(
			new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Conversion Error", "Not a valid theme."));
	    }
	} else {
	    return null;
	}
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	if (object != null) {
	    return String.valueOf(((Tela) object).getId());
	} else {
	    return null;
	}
    }
}
