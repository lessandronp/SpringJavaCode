package com.solucaocriativa.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.solucaocriativa.dao.UsuarioDao;
import com.solucaocriativa.dao.generico.impl.DaoGenericoImpl;
import com.solucaocriativa.entidade.Usuario;

@Repository
public class UsuarioDaoImpl extends DaoGenericoImpl<Usuario, Long> implements UsuarioDao {

    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(UsuarioDaoImpl.class);

    @PersistenceContext//(unitName = "solucaoCriativaPU")
    private EntityManager entityManager;

    @PersistenceUnit//(unitName = "solucaoCriativaPU")
    private EntityManagerFactory factory;

    @Override
    public Usuario findUser(String loginEmail) {
	try {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
	    Root<Usuario> root = query.from(Usuario.class);
	    query.where(cb.equal(root.get("login"), loginEmail));
	    List<Usuario> users = entityManager.createQuery(query).getResultList();
	    return !users.isEmpty() ? users.get(0) : null;
	} catch (Exception ex) {
	    log.error(ex);
	} finally {
	}
	return null;
    }

    @Override
    public Usuario buscaUsuario(String loginEmail, String senha) {
	try {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
	    Root<Usuario> root = query.from(Usuario.class);
	    query.where(
		    cb.and(cb.equal(root.get("login"), loginEmail)),
		    cb.equal(root.get("senha"), senha));
	    List<Usuario> users = entityManager.createQuery(query).getResultList();
	    return !users.isEmpty() ? users.get(0) : null;
	} catch (Exception ex) {
	    log.error(ex);
	} finally {
	}
	return null;
    }

    @Override
    public int numeroUsuarios() {
	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	CriteriaQuery<Long> query = cb.createQuery(Long.class);
	Root<Usuario> root = query.from(Usuario.class);
	query.select(cb.count(root));
	return entityManager.createQuery(query).getSingleResult().intValue();
    }

    @Override
    public List<Usuario> buscaComPaginacao(int page, int rowsPerPage) {
	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
	Root<Usuario> root = query.from(Usuario.class);
	query.orderBy(cb.asc(root.get("login")));
	TypedQuery<Usuario> typedQuery = entityManager.createQuery(query);
	typedQuery.setFirstResult(page);
	typedQuery.setMaxResults(rowsPerPage);
	return typedQuery.getResultList();
    }

    @Override
    public Usuario buscaUsuario(String loginEmail, Long id) {
	try {
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
	    Root<Usuario> root = query.from(Usuario.class);
	    query.where(cb.and(
		    cb.or(cb.equal(root.get("login"), loginEmail),
			    cb.equal(root.get("senha"), loginEmail)), 
		    cb.equal(root.get("codigo"), id))
	    );
	    List<Usuario> users = entityManager.createQuery(query).getResultList();
	    return !users.isEmpty() ? users.get(0) : null;
	} catch (Exception ex) {
	    log.error(ex);
	} finally {
	}
	return null;
    }
}
