package com.solucaocriativa.service;

import java.util.List;

import com.solucaocriativa.entidade.Departamento;
import com.solucaocriativa.entidade.PermissaoTelaDepartamento;
import com.solucaocriativa.service.generico.ServicoGenerico;

public interface ServicoPermissaoTelaDepartamento extends
	ServicoGenerico<PermissaoTelaDepartamento, Long> {

    /**
     * Recupera as permissões da tela de um determinado departamento.
     * @param departamento Departamento
     * @return Permissões da tela de um determinado departamento
     */
    List<PermissaoTelaDepartamento> buscaPermissoes(Departamento departamento);

}
