package com.todo.service;

import java.util.List;

import com.todo.dao.AbstractDAO;
import com.todo.model.Identifiable;

public abstract class AbstractService<T extends Identifiable, R extends AbstractDAO<T>> {
	
	protected abstract R getDataAccessObject();
	public void add(T obj) {
		getDataAccessObject().add(obj);
	}

	public void update(T obj) {
		getDataAccessObject().update(obj);
	}

	public T get(int id) {
		return getDataAccessObject().get(id);
	}

	public void delete(int id) {
		getDataAccessObject().delete(id);
	}

	public List<T> getAll() {
		return getDataAccessObject().getAll();
	}
	
}
