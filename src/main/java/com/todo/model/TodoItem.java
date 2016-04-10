package com.todo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "todo_item")
public class TodoItem implements Identifiable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@NotEmpty
	@Length(max = 50)
	private String name;
	
	@Length(max = 200)
	private String description;
	
	private boolean isDone;

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

	public boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TodoItem) {
			return id == ((TodoItem)obj).id;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if(id == null) return 0;
		return id.hashCode();
	}
}
