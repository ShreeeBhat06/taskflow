package com.taskflow.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.model.Task;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	//In-memory list (A fake db for now)
	private List<Task> tasks=new ArrayList<>(List.of(new Task(1L,"Build HelloController","DONE","HIGH"),new Task(2L,"Connect MySQL","TODO","HIGH"),
			new Task(3L,"Write unit Tests","TODO","MEDIUM")));
	
   @GetMapping
   public List<Task> getAllTasks()
   {
	   return tasks;
   }
   
   @GetMapping("/{id}") //get tasks by id
   public Task getTaskById(@PathVariable Long id)
   {
	   return tasks.stream()
			   .filter(t -> t.getId().equals(id))
			   .findFirst()
			   .orElse(null);
			   
   }
   
   @GetMapping("/filter") //filter tasks by their status
   public List<Task> filterByStatus(@RequestParam String status) 
   {
	   return tasks.stream()
			   .filter(t-> t.getStatus().equalsIgnoreCase(status))
			   .toList();
   }
   
   @PostMapping  //To create a new Task
   public Task createTask(@RequestBody Task task)
   {
	   task.setId((long) (tasks.size()+1));
	   tasks.add(task);
	   return task;
   }
   
   @PutMapping("/{id}") //To update a Task through id
   public Task updateTask(@PathVariable Long id,@RequestBody Task updated)
   {
	   for(Task t:tasks)
	   {
		   if(t.getId().equals(id)) {
			   t.setTitle(updated.getTitle());
			   t.setStatus(updated.getStatus());
			   t.setPriority(updated.getPriority());
			   return t;
		   }
	   }
	   return null;
   }
   
   @DeleteMapping("/{id}") //To delete a task via id
   public String deleteTask(@PathVariable Long id)
   {
	   tasks.removeIf(t->t.getId().equals(id));
	   return "Task " + id + " deleted!";
   }
   
}
