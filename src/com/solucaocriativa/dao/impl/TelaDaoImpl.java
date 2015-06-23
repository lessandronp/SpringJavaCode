package com.solucaocriativa.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.solucaocriativa.dao.TelaDao;
import com.solucaocriativa.dao.generico.impl.DaoGenericoImpl;
import com.solucaocriativa.entidade.Tela;

@Repository
public class TelaDaoImpl extends DaoGenericoImpl<Tela, Long> implements TelaDao {

    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(TelaDaoImpl.class);

    @PersistenceContext//(unitName = "solucaoCriativaPU")
    private EntityManager entityManager;

    @PersistenceUnit//(unitName = "solucaoCriativaPU")
    private EntityManagerFactory factory;


}
