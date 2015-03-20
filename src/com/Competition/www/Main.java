package com.Competition.www;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.sql.*;
public class Main {

	public static void main(String[] args) {
				
		List<Task> tasksInMain = new ArrayList<>();
		Task t1 = new Task();
		t1.setComplexity(Complexity.EASY);
		t1.setTaskArea("Science");
		t1.setTaskBody("Some Good question");
		
		Task t2 = new Task();
		t2.setComplexity(Complexity.PRO);
		t2.setTaskArea("Sport");
		t2.setTaskBody("Some other question");
		
		Task t3 = new Task();
		t3.setComplexity(Complexity.ADVANCED);
		t3.setTaskArea("Theater");
		t3.setTaskBody("abrakadabra");
		
		tasksInMain.add(t1);
		tasksInMain.add(t2);
		tasksInMain.add(t3);
		
		Competition c1 = new Competition();
		c1.setIdCompletition(2);
		c1.addTasksList(tasksInMain);
		c1.loadCompetitionFromXML(c1.getIdCompletition());
		c1.showCompetitionInfo();
	  //c1.saveCompetitionInXML();
		
		Competition c2 = new Competition();
		c2.setIdCompletition(2);
		c2.addTasksList(tasksInMain);
		c2.loadCompetitionFromXML(c2.getIdCompletition());
		//c2.showCompetitionInfo();
		//sql 
		try{
			// 1. get a connection to database
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CompetitionDB");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
