package com.solucaocriativa.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.solucaocriativa.entidade.Departamento;
import com.solucaocriativa.service.ServicoDepartamento;

@Named("departamentoConverter")
@Scope("view")
@FacesConverter("departamentoConverter")
public class DepartamentoConverter implements Converter {

    private ServicoDepartamento servicoDepartamento;

    @Inject
    public DepartamentoConverter(ServicoDepartamento servicoDepartamento) {
	this.servicoDepartamento = servicoDepartamento;
    }
    
    public DepartamentoConverter() {
    }
    
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
	if (value != null && value.trim().length() > 0) {
	    try {
		Departamento departamento = servicoDepartamento.buscaPorId(Long.parseLong(value));
		return departamento;
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
	    return String.valueOf(((Departamento) object).getId());
	} else {
	    return null;
	}
    }
}
