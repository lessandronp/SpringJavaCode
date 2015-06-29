package com.solucaocriativa.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.password.Password;

import com.solucaocriativa.jsf.JSFUtils;

@FacesValidator(value = "confirmPasswordValidator")
public class ConfirmPasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component,
	    Object value) throws ValidatorException {
	
	UIComponent passwordComponent = JSFUtils.findComponent("password", component.getParent().getId());

	if (passwordComponent != null && passwordComponent instanceof Password) {
	    Password passwordSecret = (Password) passwordComponent;
	    if (passwordSecret.getValue() != null 
		    && !value.equals(passwordSecret.getValue().toString())) {
		throw new ValidatorException(new FacesMessage(
			"Confirm password is not the same as password"));
	    }
	}
    }
}
