package com.taskflow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.taskflow.model.Task;
import com.taskflow.repository.TaskRepository;

@Service
public class TaskService {

	private final TaskRepository taskRepository;
	public TaskService(TaskRepository taskRepository)
	{
		this.taskRepository=taskRepository;
	}
	
	public List<Task> getAllTasks()
	{
		return taskRepository.findAll();
	}
	
	public Optional<Task> getTaskById(Long id)
	{
		return taskRepository.findById(id);
	}
	
	public List<Task> filterByStatus(String status)
	{
		return taskRepository.findByStatusIgnoreCase(status);
	}
	
	public Task createTask(Task task)
	{
		//Default status--->TODO if not provided
		if(task.getStatus()==null || task.getStatus().isBlank())
		{
			task.setStatus("TODO");
		}
		//Default priority--->MEDIUM if not provided
		if(task.getPriority()==null || task.getPriority().isBlank())
		{
			task.setPriority("MEDIUM");
		}
		return taskRepository.save(task);
	
	}
	
	public Optional<Task> updateTask(Long id,Task updated)
	{
		return taskRepository.findById(id)
				.map(existing ->{
					 existing.setTitle(updated.getTitle());
					   existing.setStatus(updated.getStatus());
					   existing.setPriority(updated.getPriority());
					   return taskRepository.save(existing);
				});
	}
	
	public boolean deleteTask(Long id)
	{
		if(!taskRepository.existsById(id))
		{
			return false;
		}
		taskRepository.deleteById(id);
		return true;
	}
	
	
}
