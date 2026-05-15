package com.taskflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="tasks")
public class Task {
   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Title cannot be empty")
	private String title;
	
	@NotBlank(message = "Status is required")
    @Pattern(regexp = "TODO|IN_PROGRESS|DONE", message = "Status must be TODO, IN_PROGRESS, or DONE")
	private String status;// "Todo","In-progress","done"
	
	 @NotBlank(message = "Priority is required")
	   @Pattern(regexp = "LOW|MEDIUM|HIGH", message = "Priority must be LOW, MEDIUM, or HIGH")
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