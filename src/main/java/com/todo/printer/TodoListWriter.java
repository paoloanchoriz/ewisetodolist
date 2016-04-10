package com.todo.printer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import com.todo.model.TodoItem;
import com.todo.model.TodoList;

public class TodoListWriter {
	
	private static final String NAME_LABEL = "Name: ";
	private static final String DESCRIPTION_LABEL = "Description: ";
	private static final String THREE_TAB = "\t\t\t";
	private static final String TWO_TAB = "\t\t";
	private static final String DONE_LABEL = "Done";
	private static final String NOT_DONE_LABEL = "Not-Done";
	
	private ServletOutputStream out;
	public TodoListWriter(ServletOutputStream out) {
		this.out = out;
	}
	
	public void print(TodoList todoList) throws IOException {
		print(NAME_LABEL).print(todoList.getName())
			.printNewLine()
			.print(DESCRIPTION_LABEL).print(todoList.getDescription());
		
		if(todoList.getItems() != null) {
			printItems(todoList.getItems());
		}
		
		flushAndClose();
	}
	
	private void flushAndClose() throws IOException {
		out.flush();
		out.close();
	}
	
	private void printItems(List<TodoItem> items) throws IOException {
		printNewLine().printNewLine();
		for(TodoItem item:items) {
			printItem(item);
		}
	}
	
	private void printRow(String status, String name, String description, String tab) throws IOException {
		print(status).print(tab)
			.print(name)
			.printNewLine()
			.print(THREE_TAB)
			.print(description)
			.printNewLine()
			.printNewLine();
	}

	private void printItem(TodoItem item) throws IOException {
		String status = item.getIsDone() ? DONE_LABEL:NOT_DONE_LABEL;
		String tab = item.getIsDone() ? THREE_TAB:TWO_TAB;
		printRow(status, item.getName(), item.getDescription(), tab);
	}
	

	private TodoListWriter print(String printStr) throws IOException {
		out.print(printStr);
		return this;
	}
	
	private TodoListWriter printNewLine() throws IOException {
		out.println();
		return this;
	}
}
