package com.solucaocriativa.service;

import java.util.List;

import com.solucaocriativa.entidade.Tela;
import com.solucaocriativa.service.generico.ServicoGenerico;
import com.solucaocriativa.vo.TelaVO;

public interface ServicoTela extends ServicoGenerico<Tela, Long> {

    /**
     * Recupera as telas disponíveis à partir do nome das páginas.
     * @param path Caminho das páginas no servidor
     * @return Nome das telas
     */
    List<TelaVO> recuperaTelasDisponiveis(String path);

}
