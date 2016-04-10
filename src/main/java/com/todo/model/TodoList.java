package com.todo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="todo_list")
public class TodoList implements Identifiable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3000993110731917940L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@NotEmpty
	@Length(max = 50)
	private String name;
	
	@Length(max = 200)
	private String description;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private List<TodoItem> items;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TodoItem> getItems() {
		return items;
	}

	public void setItems(List<TodoItem> items) {
		this.items = items;
	}
	
}
