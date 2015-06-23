package com.solucaocriativa.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.solucaocriativa.dao.DepartamentoDao;
import com.solucaocriativa.dao.generico.impl.DaoGenericoImpl;
import com.solucaocriativa.entidade.Departamento;

@Repository
public class DepartamentoDaoImpl extends DaoGenericoImpl<Departamento, Long>
	implements DepartamentoDao {

    private static final long serialVersionUID = 1L;

    @PersistenceContext//(unitName = "solucaoCriativaPU")
    private EntityManager entityManager;

    @PersistenceUnit//(unitName = "solucaoCriativaPU")
    private EntityManagerFactory factory;

    @Override
    public Departamento findByName(String name) {
	try {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Departamento> query = cb
		    .createQuery(Departamento.class);
	    Root<Departamento> root = query.from(Departamento.class);
	    query.where(cb.equal(cb.lower(root.<String> get("name")),
		    name.toLowerCase()));
	    return entityManager.createQuery(query).getSingleResult();
	} catch (Exception ex) {
	    return null;
	} finally {
	}
    }
}
