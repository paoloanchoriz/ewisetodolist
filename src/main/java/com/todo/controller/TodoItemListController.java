package com.todo.controller;

import java.util.List;

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
import com.todo.service.TodoListService;

@Controller
@RequestMapping("/todolist/{id}/item")
@RestController
public class TodoItemListController {

	@Autowired
	private TodoListService todoListService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public GenericRestResponse<List<TodoItem>> getAllItems(@PathVariable(value = "id") int parentId) {
		TodoList todoList = todoListService.getTodoListWithItems(parentId);
		return new GenericRestResponse<List<TodoItem>>(todoList.getItems());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public GenericRestResponse<TodoItem> addTodoItem(@Valid @RequestBody TodoItem todoItem, 
			@PathVariable(value = "id") int parentId) {
		todoListService.addTodoItem(parentId, todoItem);
		return new GenericRestResponse<TodoItem>(todoItem);
	}
}
