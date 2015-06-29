package com.solucaocriativa.validator;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.solucaocriativa.jsf.FacesUtils;
import com.solucaocriativa.jsf.JSFUtils;
import com.solucaocriativa.util.CurrencyUtil;

@FacesValidator(value = "valueRangeValidator")
public class ValueRangeValidator implements Validator {

    private static Log log = LogFactory.getLog(DateRangeValidator.class);

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {

	UIComponent grossValueComponent = JSFUtils.findComponent("grossValue", component.getParent().getId());
	UIComponent promotionalValueComponent = JSFUtils.findComponent("promotionalValue", component.getParent().getId());

	if (grossValueComponent != null && promotionalValueComponent != null
		&& grossValueComponent instanceof HtmlInputText
		&& promotionalValueComponent instanceof HtmlInputText) {

	    HtmlInputText grossValueInput = (HtmlInputText) grossValueComponent;
	    HtmlInputText promotionalValueInput = (HtmlInputText) promotionalValueComponent;

	    BigDecimal grossValueFormatted = grossValueInput.getValue() != null
		    && !grossValueInput.getValue().toString().isEmpty() 
		    ? new BigDecimal(grossValueInput.getValue().toString()) : BigDecimal.ZERO;
	    BigDecimal promotionalValueFormatted = promotionalValueInput.getSubmittedValue() != null
		    && !promotionalValueInput.getSubmittedValue().toString().isEmpty() 
		    ? CurrencyUtil.convertCurrency(promotionalValueInput.getSubmittedValue().toString()) 
			    : BigDecimal.ZERO;
	    if (promotionalValueFormatted.compareTo(grossValueFormatted) >= 0) {
		FacesMessage msg = FacesUtils.addErrorMessageWithoutInternacionalization("promotionalValue", 
			"* O valor com desconto deve ser menor que o valor sem desconto.");
		throw new ValidatorException(msg);
	    }
	}
    }
}
