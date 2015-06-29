package com.solucaocriativa.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

import com.solucaocriativa.jsf.FacesUtils;
import com.solucaocriativa.jsf.JSFUtils;

@FacesValidator(value = "dateRangeValidator")
public class DateRangeValidator implements Validator {
    
    private static Log log = LogFactory.getLog(DateRangeValidator.class);

    @Override
    public void validate(FacesContext context, UIComponent component,
	    Object value) {

	UIComponent beginDateComponent = JSFUtils.findComponent("beginDate", component.getParent().getId());
	UIComponent endDateComponent = JSFUtils.findComponent("endDate", component.getParent().getId());

	if (beginDateComponent != null && endDateComponent != null
		&& beginDateComponent instanceof HtmlInputText 
		&& endDateComponent instanceof HtmlInputText) {

	    HtmlInputText beginDate = (HtmlInputText) beginDateComponent;
	    HtmlInputText endDate = (HtmlInputText) endDateComponent;

	    DateTime beginDateFormatted = null;
	    DateTime endDateFormatted = null;

	    try {
		beginDateFormatted = beginDate.getValue() != null && !beginDate.getValue().toString().isEmpty() 
			? new DateTime(beginDate.getValue()) : null;
		endDateFormatted = endDate.getSubmittedValue() != null && !endDate.getSubmittedValue().toString().isEmpty()
			? new DateTime(endDate.getValue()) : null; 

	    } catch (Exception e) {
		log.error(e.getMessage());
	    }
	    
	    if (beginDateFormatted != null && endDateFormatted != null 
		    && beginDateFormatted.isAfter(endDateFormatted)) {
		FacesMessage msg = FacesUtils.addErrorMessageWithoutInternacionalization("endDate",
			"A data final deve ser maior ou igual à data inicial.");
		throw new ValidatorException(msg);
	    }
	    
	    if (endDateFormatted != null && endDateFormatted.isBeforeNow()) {
		FacesMessage msg = FacesUtils.addErrorMessageWithoutInternacionalization("endDate", 
			"* A data final deve ser maior ou igual à data atual.");

		throw new ValidatorException(msg);
	    }
	    
	    if (beginDateFormatted != null && beginDateFormatted.isBeforeNow()) {
		FacesMessage msg = FacesUtils.addErrorMessageWithoutInternacionalization("beginDate", 
			"* A data inicial deve ser maior ou igual à data atual.");
		throw new ValidatorException(msg);
	    }
	}
    }
}
