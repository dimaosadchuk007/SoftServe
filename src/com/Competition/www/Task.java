package com.Competition.www;

public class Task {
	private Complexity complexity;
	private String taskArea;
	private String taskBody;
	Task(){
		complexity = null;
		taskArea = null;
		taskBody = null;
	}
	public void setComplexity(Complexity complexity) {
		this.complexity = complexity;
	}
	
	public Complexity getComplexity() {
		return complexity;
	}
	
	public void setTaskArea(String taskArea) {
		this.taskArea = taskArea;
	}
	
	public String getTaskArea() {
		return taskArea;
	}
	
	public void setTaskBody(String taskBody) {
		this.taskBody = taskBody;
	}
	
	public String getTaskBody() {
		return taskBody;
	}
	
	
}
