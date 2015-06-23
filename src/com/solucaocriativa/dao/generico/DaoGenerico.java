package com.solucaocriativa.dao.generico;

import java.io.Serializable;
import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.criterion.Criterion;

public interface DaoGenerico<T, ID extends Serializable> {

    /**
     * Recupera pelo Id
     * @param id Identificador
     * @return Entidade
     * @throws Exception 
     */
    T findById(ID id) throws Exception;

    /**
     * Recupera todos os objetos.
     * @return Objetos
     * @throws Exception 
     */
    List<T> findAll() throws Exception;

    /**
     * Persiste a entidade
     * @param entity Entidade
     * @return Entidade
     * @throws Exception 
     */
    T save(T entity) throws PersistenceException, Exception;

    /**
     * Atualiza a entidade
     * @param entity Entidade
     * @return Entidade
     * @throws Exception 
     */
    T update(T entity) throws PersistenceException, Exception;

    /**
     * Remove a entidade
     * @param entity Entidade
     * @throws Exception 
     */
    void delete(T entity) throws Exception;

    /**
     * Recupera uma lista de entidades por critérios.
     * @param criterion Critérios
     * @return Entidades
     * @throws Exception 
     */
    List<T> findManyByCriterion(Criterion... criterion) throws Exception;

    /**
     * Recupera a entidade por critérios.
     * @param criterion Critérios
     * @return Entidade
     * @throws Exception 
     */
    T findByCriterion(Criterion... criterion) throws Exception;

    /**
     * Atualiza o objeto na memória com o banco.
     * @param entity Entidade
     * @return Entidade
     * @throws Exception 
     */
    T refresh(T entity) throws Exception;
}
