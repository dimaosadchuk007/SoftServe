package com.Competition.www;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Competition {
	private Integer idCompletition;
	private Calendar startCalendarDate;
	private List<Task> tasksList = new ArrayList<>();
	Competition(){
		idCompletition =0;
		startCalendarDate = null;
		tasksList = null;
	}
	
	public Integer getIdCompletition() {
		
		return idCompletition;
	}
	
	public void setIdCompletition(Integer idCompletition) {
		this.idCompletition = idCompletition;
	}
	
	public Calendar getStartCalendarDate() {
		return startCalendarDate;
	}
	
	public void setStartCalendarDate(Calendar startCalendarDate) {
		this.startCalendarDate = startCalendarDate;
	}

	public void addTasksList(List<Task> tasksList) {
		this.tasksList = tasksList;
	}
	
	public List<Task> getTasktsList() {
		return tasksList;
	}
	
	public void loadCompetitionFromXML(int id) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("competitions.xml");
			NodeList nodeCompetitionList = doc.getElementsByTagName("competition");		// вибираю всі елементи типу competition
			
			for (int s = 0;s<nodeCompetitionList.getLength();s++) {
				Node currentCompetition = nodeCompetitionList.item(s);
				
				if (currentCompetition.getNodeType()==Node.ELEMENT_NODE) {
					Element competition = (Element)currentCompetition;
					 int checkIdCompetition = Integer.parseInt(competition.getAttribute("id"));
					 
					 if (checkIdCompetition == id) {// якщо знайшов потрібну олімпіаду проходжусь по її child-ах
						 idCompletition = checkIdCompetition;// зразу присвоюю id нашому майбутньому об'єкту Competition
						 String checkStartCalendarDate = competition.getAttribute("startDate");
						 String[] stringForDate = checkStartCalendarDate.split(",");
						 Calendar c = new  GregorianCalendar(Integer.parseInt(stringForDate[0]), Integer.parseInt(stringForDate[1]), Integer.parseInt(stringForDate[2]));
						 setStartCalendarDate(c);	 // зараз ми обробили всі необхідні елементи, які необхідні для олімпіади. наступним кроком буде обробка завдань з олімпіади
						 NodeList nodeTasksList = competition.getChildNodes();
						 
						 for (int i = 0;i<nodeTasksList.getLength();i++){
							 Task tmpTask = new Task();
							 Node taskInfo =  (Node)nodeTasksList.item(i);
							 
							   if (taskInfo.getNodeType()==Node.ELEMENT_NODE){ 
								   Element someTask = (Element)taskInfo;
								   tmpTask.setTaskArea(someTask.getElementsByTagName("taskArea").item(0).getChildNodes().item(0).getNodeValue()); 
								   tmpTask.setTaskBody(someTask.getElementsByTagName("taskBody").item(0).getChildNodes().item(0).getNodeValue());
								   tmpTask.setComplexity(Complexity.valueOf(someTask.getAttribute("complexity")));
								   tasksList.add(tmpTask);
							}
						   }
						
					 }
					
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveCompetitionInXML() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();// створили пустий документ
			
			Element allCompetitionsEl = document.createElement("AllCompetitions");//кореневий документ
			document.appendChild(allCompetitionsEl);// в документі призв'язуємо тег competition
			Element competitionEl = document.createElement("competition");
				Attr attrCompetitionId = document.createAttribute("id");
					attrCompetitionId.setValue(idCompletition.toString());
					competitionEl.setAttributeNode(attrCompetitionId);
				Attr attrCompetitionStartDate = document.createAttribute("startDate");
			    int year = startCalendarDate.get(Calendar.YEAR);
			    int month = startCalendarDate.get(Calendar.MONTH);
			    int day = startCalendarDate.get(Calendar.DAY_OF_MONTH);
				String finealDate = year+","+month+","+day;
				attrCompetitionStartDate.setValue(finealDate);
					competitionEl.setAttributeNode(attrCompetitionStartDate);
			allCompetitionsEl.appendChild(competitionEl);
			for(int i=0;i<tasksList.size();i++){
				Element taskEl = document.createElement("task");
				Attr taskComplexity = document.createAttribute("complexity");
					taskComplexity.setValue(tasksList.get(i).getComplexity().toString());
					taskEl.setAttributeNode(taskComplexity);
			competitionEl.appendChild(taskEl);
			
			Element taskAreaEl = document.createElement("taskArea");
			taskAreaEl.appendChild(document.createTextNode(tasksList.get(i).getTaskArea()));
			taskEl.appendChild(taskAreaEl);
			Element taskBodyEl = document.createElement("taskBody"); 
			taskBodyEl.appendChild(document.createTextNode(tasksList.get(i).getTaskBody()));
			taskEl.appendChild(taskBodyEl); 	
			}
	
			 TransformerFactory factoryTr = TransformerFactory.newInstance();
			 Transformer transformer = factoryTr.newTransformer();
			 DOMSource domSource = new DOMSource(document);
			 StreamResult streamFile = new StreamResult(new File("OutputCompetition.xml"));
			 transformer.transform(domSource, streamFile);
			 System.out.println("document is saved");
		} catch (ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void showCompetitionInfo(){
		System.out.println("\n"+ "id Competition is: #"+this.idCompletition + " Start Calendar Date is :\t"+ this.startCalendarDate.getTime());
		for(int i=0;i<this.tasksList.size();i++){
		System.out.println("Task # " + i + "\t Task complexity is :"+this.tasksList.get(i).getComplexity()+"\t Task area is: \t "+this.tasksList.get(i).getTaskArea()+"  "+ " \t Task body is: \t "+this.tasksList.get(i).getTaskBody());
		}
	}
}
