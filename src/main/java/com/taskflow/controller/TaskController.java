package com.taskflow.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.taskflow.dto.ApiResponse;
import com.taskflow.exception.TaskNotFoundException;
import com.taskflow.model.Task;
import com.taskflow.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	/*In-memory list (A fake db for now)
	private List<Task> tasks=new ArrayList<>(List.of(new Task(1L,"Build HelloController","DONE","HIGH"),new Task(2L,"Connect MySQL","TODO","HIGH"),
			new Task(3L,"Write unit Tests","TODO","MEDIUM")));*/
	
	private List<Task> tasks = new ArrayList<>(List.of(
            new Task(1L, "Build HelloController", "DONE", "HIGH"),
            new Task(2L, "Connect MySQL", "TODO", "HIGH"),
            new Task(3L, "Write Unit Tests", "TODO", "MEDIUM")
    ));

    @GetMapping
    public ResponseEntity<ApiResponse<List<Task>>> getAllTasks() {
        return ResponseEntity.ok(ApiResponse.success("Tasks fetched", tasks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> getTaskById(@PathVariable Long id) {
        Task task = tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException(id)); // throws → handler catches
        return ResponseEntity.ok(ApiResponse.success("Task found", task));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<Task>>> filterByStatus(@RequestParam String status) {
        List<Task> filtered = tasks.stream()
                .filter(t -> t.getStatus().equalsIgnoreCase(status))
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Filtered by: " + status, filtered));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Task>> createTask(@Valid @RequestBody Task task) {
        task.setId((long) (tasks.size() + 1));
        tasks.add(task);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Task created", task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> updateTask(@PathVariable Long id,
                                                         @Valid @RequestBody Task updated) {
        for (Task t : tasks) {
            if (t.getId().equals(id)) {
                t.setTitle(updated.getTitle());
                t.setStatus(updated.getStatus());
                t.setPriority(updated.getPriority());
                return ResponseEntity.ok(ApiResponse.success("Task updated", t));
            }
        }
        throw new TaskNotFoundException(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id) {
        boolean removed = tasks.removeIf(t -> t.getId().equals(id));
        if (!removed) throw new TaskNotFoundException(id);
        return ResponseEntity.ok(ApiResponse.success("Task " + id + " deleted", null));
    }
}