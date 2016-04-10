package com.todo.controller;

import java.util.ArrayList;
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
@RequestMapping(value = "/todolist")
@RestController
public class TodoListController {

	@Autowired
	private TodoListService todoListService;

	@RequestMapping(method = RequestMethod.GET)
	public GenericRestResponse<List<TodoList>> getAllTodoList() {
		return new GenericRestResponse<List<TodoList>>(todoListService.getAll());
	}

	@RequestMapping(method = RequestMethod.POST)
	public GenericRestResponse<TodoList> addTodoList(
			@Valid @RequestBody TodoList todoList) {
		todoList.setItems(new ArrayList<TodoItem>());
		todoListService.add(todoList);
		return new GenericRestResponse<TodoList>(todoList);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public GenericRestResponse<String> deleteTodoList(
			@PathVariable(value = "id") int id) {
		todoListService.delete(id);
		return GenericRestResponse.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public GenericRestResponse<TodoList> updateTodoList(
			@Valid @RequestBody TodoList todoList,
			@PathVariable(value = "id") int id) {
		todoList.setId(id);
		todoListService.update(todoList);
		return new GenericRestResponse<TodoList>(todoList);
	}

}
