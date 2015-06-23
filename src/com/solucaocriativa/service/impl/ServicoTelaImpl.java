package com.solucaocriativa.service.impl;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.solucaocriativa.dao.TelaDao;
import com.solucaocriativa.entidade.Tela;
import com.solucaocriativa.service.ServicoTela;
import com.solucaocriativa.service.generico.impl.ServicoGenericoImpl;

@Service
public class ServicoTelaImpl extends ServicoGenericoImpl<Tela, Long>
	implements ServicoTela {

    private static final long serialVersionUID = 1L;
    private TelaDao telaDao;
    private static Log log = LogFactory.getLog(ServicoTelaImpl.class);

    public ServicoTelaImpl() {
    }

    @Inject
    public ServicoTelaImpl(TelaDao telaDao) {
	this.telaDao = telaDao;
	super.setGenericDAO(telaDao);
    }


}
