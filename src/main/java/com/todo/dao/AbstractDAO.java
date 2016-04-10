package com.todo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.todo.model.Identifiable;

@Repository
@Transactional
public abstract class AbstractDAO<T extends Identifiable> {
	@Autowired
	private SessionFactory sessionFactory;
	
	protected abstract void updateSpecific(T updateObj, T persistedObj);
	protected abstract Class<T> getClassType();
	protected abstract String getListQuery();

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void update(T obj) {
		T persistedObj = get(obj.getId());
		updateSpecific(persistedObj, obj);
		getCurrentSession().update(persistedObj);
	}
	

	public void add(T team) {
		getCurrentSession().save(team);
	}
	
	public T get(int id) {
		@SuppressWarnings("unchecked")
		T obj = (T) getCurrentSession().get(getClassType(), id);
		return obj;
	}

	public void delete(int id) {
		T obj = get(id);
		if (obj != null)
			getCurrentSession().delete(obj);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return getCurrentSession().createQuery(getListQuery()).list();
	}
}
