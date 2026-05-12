package com.taskflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tasks")
public class Task {
   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String status;// "Todo","In-progress","done"
	private String priority;//"low","medium","high"


public Task(Long id,String title,String status,String priority)
{
	this.id=id;
	this.title=title;
	this.status=status;
	this.priority=priority;
}

public Task()
{
	
}


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public String getTitle() {
	return title;
}


public void setTitle(String title) {
	this.title = title;
}


public String getStatus() {
	return status;
}


public void setStatus(String status) {
	this.status = status;
}


public String getPriority() {
	return priority;
}


public void setPriority(String priority) {
	this.priority = priority;
}
   
}