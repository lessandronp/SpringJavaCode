package com.solucaocriativa.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.solucaocriativa.dao.PermissaoTelaDepartamentoDao;
import com.solucaocriativa.dao.generico.impl.DaoGenericoImpl;
import com.solucaocriativa.entidade.Departamento;
import com.solucaocriativa.entidade.PermissaoTelaDepartamento;

@Repository
public class PermissaoTelaDepartamentoDaoImpl extends DaoGenericoImpl<PermissaoTelaDepartamento, Long> implements
	PermissaoTelaDepartamentoDao {

    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(PermissaoTelaDepartamentoDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @PersistenceUnit
    private EntityManagerFactory factory;

    @Override
    public List<PermissaoTelaDepartamento> buscaPermissoes(
	    Departamento departamento) {
	try {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<PermissaoTelaDepartamento> query = cb.createQuery(PermissaoTelaDepartamento.class);
	    Root<PermissaoTelaDepartamento> root = query.from(PermissaoTelaDepartamento.class);
	    query.where(cb.equal(root.get("departamento"), departamento));
	    List<PermissaoTelaDepartamento> permissoesTelaDepartamento = 
		    entityManager.createQuery(query).getResultList();
	    return permissoesTelaDepartamento;
	} catch (Exception ex) {
	    log.error(ex);
	}
	return null;
    }
}
