package com.solucaocriativa.service.impl;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.solucaocriativa.dao.DepartamentoDao;
import com.solucaocriativa.entidade.Departamento;
import com.solucaocriativa.service.ServicoDepartamento;
import com.solucaocriativa.service.generico.impl.ServicoGenericoImpl;

@Service
public class ServicoDepartamentoImpl extends ServicoGenericoImpl<Departamento, Long> 
	implements ServicoDepartamento {

    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(ServicoDepartamentoImpl.class);
    
    public DepartamentoDao departamentoDao;

    public ServicoDepartamentoImpl() {
    }

    @Inject
    public ServicoDepartamentoImpl(DepartamentoDao departamentoDao) {
	super.setGenericDAO(departamentoDao);
    }
}
