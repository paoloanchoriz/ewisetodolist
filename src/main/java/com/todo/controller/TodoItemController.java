package com.todo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todo.model.TodoItem;
import com.todo.model.TodoList;
import com.todo.service.TodoItemService;
import com.todo.service.TodoListService;

@Controller
@RequestMapping("/todolist/{parentId}/item")
@RestController
public class TodoItemController {

	@Autowired
	private TodoItemService todoItemService;
	
	@Autowired
	private TodoListService todoListService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public GenericRestResponse<TodoItem> getTodoItem(@PathVariable(value = "id")int id, 
			@PathVariable(value = "parentId") int parentId) {
		validateParent(parentId);
		
		return new GenericRestResponse<TodoItem>(todoItemService.get(id));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public GenericRestResponse<TodoItem> updateTodoItem(@Valid @RequestBody TodoItem todoItem, 
			@PathVariable(value = "id")int id, @PathVariable(value = "parentId") int parentId) {
		validateParent(parentId);
		todoItem.setId(id);
		todoItemService.update(todoItem);
		return new GenericRestResponse<TodoItem>(todoItem);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public GenericRestResponse<String> deleteTodoItem(@PathVariable(value = "id")int id,
			@PathVariable(value = "parentId") int parentId) {
		validateParent(parentId);
		TodoItem todoItem = todoItemService.get(id);
		if(todoItem == null) throw new IllegalArgumentException("Item does not exist");
		todoListService.removeTodoItem(parentId, todoItem);
		todoItemService.delete(id);
		return GenericRestResponse.SUCCESS;
	}
	
	@RequestMapping(value = "/{id}/setdone", method = RequestMethod.PUT)
	public GenericRestResponse<String> setToDone(@PathVariable(value = "id")int id,
			@PathVariable(value = "parentId") int parentId) {
		validateParent(parentId);
		todoItemService.setItemAsDone(id, true);
		return GenericRestResponse.SUCCESS;
	}
	
	@RequestMapping(value = "/{id}/setundone", method = RequestMethod.PUT)
	public GenericRestResponse<String> setToNotDone(@PathVariable(value = "id")int id,
			@PathVariable(value = "parentId") int parentId) {
		validateParent(parentId);
		todoItemService.setItemAsDone(id, false);
		return GenericRestResponse.SUCCESS;
	}
	
	private void validateParent(int parentId) {
		TodoList todoList = todoListService.get(parentId);
		if(todoList == null) throw new IllegalArgumentException("Parent List does not exist");
	}
}
