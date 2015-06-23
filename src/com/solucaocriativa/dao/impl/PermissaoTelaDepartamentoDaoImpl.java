package com.solucaocriativa.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.solucaocriativa.dao.PermissaoTelaDepartamentoDao;
import com.solucaocriativa.dao.generico.impl.DaoGenericoImpl;
import com.solucaocriativa.entidade.PermissaoTelaDepartamento;

@Repository
public class PermissaoTelaDepartamentoDaoImpl extends DaoGenericoImpl<PermissaoTelaDepartamento, Long> implements
	PermissaoTelaDepartamentoDao {

    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(PermissaoTelaDepartamentoDaoImpl.class);

    @PersistenceContext//(unitName = "solucaoCriativaPU")
    private EntityManager entityManager;

    @PersistenceUnit//(unitName = "solucaoCriativaPU")
    private EntityManagerFactory factory;


}
