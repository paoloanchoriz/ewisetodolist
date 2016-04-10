package com.todo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.dao.TodoListDAO;
import com.todo.model.TodoItem;
import com.todo.model.TodoList;

@Service(value="todoListService")
@Transactional
public class TodoListService extends AbstractService<TodoList, TodoListDAO> {

	@Autowired
	private TodoListDAO todoListDAO;

	@Override
	protected TodoListDAO getDataAccessObject() {
		return todoListDAO;
	}

	public void removeTodoItem(int id, TodoItem todoItem) {
		TodoList todoList = getTodoListWithItems(id);
		todoList.getItems().remove(todoItem);
		update(todoList);
	}
	
	public void addTodoItem(int id, TodoItem todoItem) {
		TodoList todoList = getTodoListWithItems(id);
		todoList.getItems().add(todoItem);
		update(todoList);
	}

	public TodoList getTodoListWithItems(int id) {
		TodoList todoList = get(id);
		if(todoList == null) throw new IllegalArgumentException("List not found.");
		todoListDAO.loadItems(todoList);
		return todoList;
	}

}
