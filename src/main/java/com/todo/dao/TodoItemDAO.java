package com.todo.dao;

import org.springframework.stereotype.Repository;

import com.todo.model.TodoItem;

@Repository
public class TodoItemDAO extends AbstractDAO<TodoItem>{

	@Override
	protected void updateSpecific(TodoItem persistedObj, TodoItem updateObj) {
		persistedObj.setName(updateObj.getName());
		persistedObj.setDescription(updateObj.getDescription());
		if(!persistedObj.getIsDone()) {
			persistedObj.setIsDone(updateObj.getIsDone());
		}
	}

	@Override
	protected Class<TodoItem> getClassType() {
		return TodoItem.class;
	}

	@Override
	protected String getListQuery() {
		return "from TodoItem";
	}

}
