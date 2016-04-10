package com.todo.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.todo.model.TodoList;
import com.todo.printer.TodoListWriter;
import com.todo.service.TodoListService;

@Controller
@RequestMapping("/todolist/{id}")
public class ExportTodoListController {
	
	private static final String FILENAME_TEMPLATE = "attachment;filename=%s.txt";
	@Autowired
	private TodoListService todoListService;
	
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void export(@PathVariable(value = "id") int id, HttpServletResponse response) throws IOException {
		TodoList todoList = todoListService.getTodoListWithItems(id);
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", 
				String.format(FILENAME_TEMPLATE, todoList.getName()));
		ServletOutputStream out = response.getOutputStream();
		TodoListWriter writer = new TodoListWriter(out);
		writer.print(todoList);
	}
}
