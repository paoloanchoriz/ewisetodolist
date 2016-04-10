package com.todo.dao;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.todo.model.TodoList;

@Repository
public class TodoListDAO extends AbstractDAO<TodoList> {

	public void loadItems(TodoList todoList) {
		Hibernate.initialize(todoList.getItems());
	}
	
	@Override
	protected void updateSpecific(TodoList persistedObj, TodoList updateObj) {
		persistedObj.setName(updateObj.getName());
		persistedObj.setDescription(updateObj.getDescription());
	}
	
	@Override
	protected Class<TodoList> getClassType() {
		return TodoList.class;
	}

	@Override
	protected String getListQuery() {
		return "from TodoList";
	}

}
