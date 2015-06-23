package com.solucaocriativa.dao.generico.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.solucaocriativa.dao.generico.DaoGenerico;

@Repository
public abstract class DaoGenericoImpl<T, ID extends Serializable> implements
	DaoGenerico<T, ID>, Serializable {

    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(DaoGenericoImpl.class);
    private Class<T> persistentClass;

    @PersistenceContext//(unitName = "solucaoCriativaPU")
    private EntityManager entityManager;

    @PersistenceUnit//(unitName = "solucaoCriativaPU")
    private EntityManagerFactory factory;

    @SuppressWarnings("unchecked")
    public DaoGenericoImpl() {
	this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
		.getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T findById(ID id) throws Exception {
	try {
	    T entity = (T) this.entityManager.find(persistentClass, id);
	    return entity;
	} catch (Exception ex) {
	    log.error("Erro ao recuperar a entidade pelo Id "
		    .concat(String.valueOf(id))
		    .concat(" erro: ").concat(ex.getMessage()));
	    throw new Exception(ex);
	} finally {
	}
    }

    @Override
    public List<T> findAll() throws Exception {
	try {
	    CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
	    CriteriaQuery<T> query = cb.createQuery(persistentClass);
	    Root<T> from = query.from(persistentClass);
	    CriteriaQuery<T> select = query.select(from);
	    TypedQuery<T> typedQuery = this.entityManager.createQuery(select);
	    List<T> lista = typedQuery.getResultList();
	    return lista;
	} catch (Exception ex) {
	    log.error("Erro ao recuperar os dados da entidade "
		    .concat(persistentClass.getClass().getName())
		    .concat(" erro: ").concat(ex.getMessage()));
	    throw new Exception(ex);
	} finally {
	}
    }

    @Override
    public T save(T entity) throws PersistenceException, Exception {
	try {
	    this.entityManager.persist(entity);
//	    this.entityManager.flush();
	    return entity;
	} catch (PersistenceException e) {
		throw new PersistenceException(e.getMessage());
	} catch (Exception e) {
	    log.error("Erro ao persistir a entidade "
		    .concat(entity.getClass().getName())
		    .concat(" erro: ").concat(e.getMessage()));
	    throw new Exception(e);
	}
    }

    @Override
    public T update(T entity) throws PersistenceException, Exception {
	try {
	    this.entityManager.merge(entity);
//	    this.entityManager.flush();
	    return entity;
	} catch (PersistenceException e) {
		throw new PersistenceException(e.getMessage());
	} catch (Exception e) {
	    log.error("Erro ao atualizar a entidade "
		    .concat(entity.getClass().getName())
		    .concat(" erro: ").concat(e.getMessage()));
	    throw new Exception(e);
	}
    }

    @Override
    public void delete(T entity) throws Exception {
	try {
	    entity = this.entityManager.merge(entity);
	    this.entityManager.remove(entity);
//	    this.entityManager.flush();
	} catch (Exception ex) {
	    log.error("Erro ao remover a entidade "
		    .concat(entity.getClass().getName())
		    .concat(" erro: ").concat(ex.getMessage()));
	    throw new Exception(ex);
	} finally {
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findManyByCriterion(Criterion... criterion) throws Exception {
	try {
	    Session session = (Session) this.entityManager.getDelegate();
	    Criteria crit = session.createCriteria(persistentClass);
	    for (Criterion c : criterion) {
		crit.add(c);
	    }
	    List<T> list = crit.list();
	    return list;
	} catch (Exception ex) {
	    log.error("Erro ao recuperar os dados da entidade "
		    .concat(persistentClass.getClass().getName())
		    .concat(" erro: ").concat(ex.getMessage()));
	    throw new Exception(ex);
	} finally {
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findByCriterion(Criterion... criterion) throws Exception {
	try {
	    Session session = (Session) this.entityManager.getDelegate();
	    Criteria crit = session.createCriteria(persistentClass);
	    for (Criterion c : criterion) {
		crit.add(c);
	    }
	    T entity = (T) crit.uniqueResult();
	    return entity;
	} catch (Exception ex) {
	    log.error("Erro com criterio a entidade "
		    .concat(persistentClass.getClass().getName())
		    .concat(" erro: ").concat(ex.getMessage()));
	    throw new Exception(ex);
	} finally {
	}
    }
    
    @Override
    public T refresh(T entity) throws Exception {
	try {
	this.entityManager.merge(entity);
	this.entityManager.flush();
	return entity;
	} catch (Exception ex) {
	    log.error("Erro ao executar o merge da entidade "
		    .concat(entity.getClass().getName())
		    .concat(" erro: ").concat(ex.getMessage()));
	    throw new Exception(ex);
	}
    }
}
