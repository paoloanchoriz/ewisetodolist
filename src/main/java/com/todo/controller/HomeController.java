package com.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todo.service.TodoListService;

@Controller
public class HomeController {
	
	@Autowired
	private TodoListService todoListService;
	
	@RequestMapping(value="/")
	public String mainPage(Model model) {
		return "home";
	}
	
	@RequestMapping(value="/index")
	public String indexPage(Model model) {
		return mainPage(model);
	}
}
