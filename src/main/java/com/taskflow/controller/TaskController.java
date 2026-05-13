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
import com.taskflow.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	/*In-memory list (A fake db for now)
	private List<Task> tasks=new ArrayList<>(List.of(new Task(1L,"Build HelloController","DONE","HIGH"),new Task(2L,"Connect MySQL","TODO","HIGH"),
			new Task(3L,"Write unit Tests","TODO","MEDIUM")));*/
	
	 private final TaskService taskService;

	    public TaskController(TaskService taskService) {
	        this.taskService = taskService;
	    }

	    @GetMapping
	    public List<Task> getAllTasks() {
	        return taskService.getAllTasks();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
	        return taskService.getTaskById(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @GetMapping("/filter")
	    public List<Task> filterByStatus(@RequestParam String status) {
	        return taskService.filterByStatus(status);
	    }

	    @PostMapping
	    public ResponseEntity<Task> createTask(@RequestBody Task task) {
	        return ResponseEntity.status(201).body(taskService.createTask(task));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updated) {
	        return taskService.updateTask(id, updated)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
	        if (taskService.deleteTask(id)) {
	            return ResponseEntity.ok("Task " + id + " deleted!");
	        }
	        return ResponseEntity.notFound().build();
	    }
   
}
