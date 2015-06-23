package com.solucaocriativa.dao;

import com.solucaocriativa.dao.generico.DaoGenerico;
import com.solucaocriativa.entidade.Departamento;

public interface DepartamentoDao extends DaoGenerico<Departamento, Long> {

	/**
	 * Find profile by name.
	 * @param name Name
	 * @return {@Profile}
	 */
	Departamento findByName(String name);
}
