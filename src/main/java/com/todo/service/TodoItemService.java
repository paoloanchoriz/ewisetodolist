package com.todo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.dao.TodoItemDAO;
import com.todo.model.TodoItem;

@Service(value = "todoItemService")
@Transactional
public class TodoItemService extends AbstractService<TodoItem, TodoItemDAO> {

	@Autowired
	private TodoItemDAO todoItemDAO;
	
	@Override
	protected TodoItemDAO getDataAccessObject() {
		return todoItemDAO;
	}

	public void setItemAsDone(int id, boolean isDone) {
		TodoItem todoItem = get(id);
		
		todoItem.setIsDone(isDone);
		
		update(todoItem);
	}

}
