package com.solucaocriativa.dao;

import java.util.List;

import com.solucaocriativa.dao.generico.DaoGenerico;
import com.solucaocriativa.entidade.Departamento;
import com.solucaocriativa.entidade.PermissaoTelaDepartamento;

public interface PermissaoTelaDepartamentoDao extends
		DaoGenerico<PermissaoTelaDepartamento, Long> {

    /**
     * Recupera as permissões da tela de um determinado departamento.
     * @param departamento Departamento
     * @return Permissões da tela de um determinado departamento
     */
    List<PermissaoTelaDepartamento> buscaPermissoes(Departamento departamento);

}
