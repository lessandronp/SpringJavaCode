package com.solucaocriativa.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.solucaocriativa.dao.TelaDao;
import com.solucaocriativa.entidade.Tela;
import com.solucaocriativa.service.ServicoTela;
import com.solucaocriativa.service.generico.impl.ServicoGenericoImpl;
import com.solucaocriativa.vo.TelaVO;

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
    
    @Override
    public List<TelaVO> recuperaTelasDisponiveis(String path) {
	List<TelaVO> telasDisponiveis = new ArrayList<>();
	for (File pagina : new File(path).listFiles()) {
	    if (pagina.isDirectory()) {
		for (File paginaGerenciada : pagina.listFiles()) {
		    TelaVO telaVO = new TelaVO();
		    telaVO.setCodigo(FilenameUtils.getBaseName(paginaGerenciada.getName()));
		    telasDisponiveis.add(telaVO);
		}
	    }
	}
	return telasDisponiveis;
    }


}
