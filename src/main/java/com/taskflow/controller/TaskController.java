package com.taskflow.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
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
import com.taskflow.repository.TaskRepository;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	/*In-memory list (A fake db for now)
	private List<Task> tasks=new ArrayList<>(List.of(new Task(1L,"Build HelloController","DONE","HIGH"),new Task(2L,"Connect MySQL","TODO","HIGH"),
			new Task(3L,"Write unit Tests","TODO","MEDIUM")));*/
	
	private final TaskRepository taskRepository;
	
	public TaskController(TaskRepository taskRepository)
	{
		this.taskRepository= taskRepository;
	}
	
   @GetMapping
   public List<Task> getAllTasks()
   {
	   return taskRepository.findAll();
   }
   
   @GetMapping("/{id}") //get tasks by id
   public ResponseEntity<Task> getTaskById(@PathVariable Long id)
   {
	   return taskRepository.findById(id)
			   .map(ResponseEntity::ok)
			   .orElse(ResponseEntity.notFound().build());
			   
   }
   
   @GetMapping("/filter") //filter tasks by their status
   public List<Task> filterByStatus(@RequestParam String status) 
   {
	   return taskRepository.findByStatusIgnoreCase(status);
   }
   
   @PostMapping  //To create a new Task
   public Task createTask(@RequestBody Task task)
   {
	   
	   return taskRepository.save(task);
   }
   
   @PutMapping("/{id}") //To update a Task through id
   public ResponseEntity<Task> updateTask(@PathVariable Long id,@RequestBody Task updated)
   {
	   return taskRepository.findById(id)
			   .map(existing->{
			   existing.setTitle(updated.getTitle());
			   existing.setStatus(updated.getStatus());
			   existing.setPriority(updated.getPriority());
			   return ResponseEntity.ok(taskRepository.save(existing));
		   })
	   .orElse(ResponseEntity.notFound().build());
	  
   }
   
   @DeleteMapping("/{id}") //To delete a task via id
   public ResponseEntity<String> deleteTask(@PathVariable Long id)
   {
	   if(!taskRepository.existsById(id))
	   {
		   return ResponseEntity.notFound().build();
	   }
	   taskRepository.deleteById(id);
	   return ResponseEntity.ok("Task " +id+" deleted!");
	   
   }
   
}
