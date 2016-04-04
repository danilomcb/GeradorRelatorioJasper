package br.com.projetobase.arq.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.projetobase.arq.dao.Dao;
import br.com.projetobase.arq.dominio.AbstractEntity;

public abstract class HibernateDAO<T extends AbstractEntity> implements Dao<T>, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	protected EntityManager entityManager;
	
	protected Class<T> clazz;
	
	public HibernateDAO() {
		Class<T> clazz = inferGenericType();
		this.clazz = clazz;
	}
	
	@Override
	public T save(T entity) {
		entityManager.persist(entity);
		return entity;
	}
	
	@Override
	public T update(T entity) {
		entityManager.merge(entity);
		return entity;
	}
	
	@Override
	public T find(Long id) {
		return (T) entityManager.find(clazz, id);
	}

	@Override
	public List<T> all() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Root<T> entity = criteriaQuery.from(clazz);
		criteriaQuery.select(entity);
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
		List<T> all = query.getResultList();
		return all;
	} 
	
	public void remove(T entity) {
		entityManager.remove(entityManager.getReference(clazz, entity.getId()));
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> inferGenericType() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}
