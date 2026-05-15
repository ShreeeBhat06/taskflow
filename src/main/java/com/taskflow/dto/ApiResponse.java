package com.taskflow.dto;

public class ApiResponse<T> {
	
	private boolean success;
	private String message;
	private T data;
	
	//Constructor for success with data
	public ApiResponse(boolean success,String message,T data)
	{
		this.success=success;
		this.message=message;
		this.data=data;
	}

	//Static factory helpers — cleaner to call
	public  static <T> ApiResponse<T> success(String mesage,T data)
	{
		return new ApiResponse<>(true, mesage, data);
	}
	
	public  static <T> ApiResponse<T> failure(String mesage)
	{
		return new ApiResponse<>(false, mesage, null);
	}

	//Getters
	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}
	
	
}
