

package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

/**
 * @author arman
 *
 */
public class ProjectsApp {
	
	
	private Scanner scanner = new Scanner(System.in);
	
	private ProjectService projectService = new ProjectService();
	
	private Project curProject;
	
	
	//Formatter:off
	private List<String> operations = List.of(
			"1) add a project",
			"2) list projects",
			"3) select a project"
			);
	//@formatter:on
	private int getUserSelection() {
		
		printOperations();
		
		Integer input = getIntInput("Enter a menu selection");
		
		return Objects.isNull(input) ? -1 : input;
	}
	
	
	public static void main(String[] args) {
		
		new ProjectsApp().processUserSelections();
	
	}
	private void processUserSelections() {
		
		boolean done = false;
		
		while(!done) {
			try {
				int selection = getUserSelection();
			
				
				
				switch(selection ) {
				case -1:
					done = exitMenu();
					break;
				
				case 1:
					createProject();
					break;
					
				case 2: 
					listProjects();
					break;
					
				case 3:
					selectProject();
					break;
				
			
					
				default:
					System.out.println("\n" + selection + " is not a valid selection. Try again.");
					break;
					
				}
			}
			catch(Exception e) {
				System.out.println("\nError: " + e + " Try again.");
			}
			
		  }
			
		}
		
	
	

	private void selectProject() {
		// TODO Auto-generated method stub
		listProjects();
		Integer projectId = getIntInput("Enter a project ID to select a project");
		
		curProject = null;
		
		curProject = projectService.fetchProjectById(projectId);
	}


	private void listProjects() {
		// TODO Auto-generated method stub
		List<Project> projects = projectService.fetchAllProjects();
		
		System.out.println("\nProjects:");
		
		projects.forEach(project -> System.out.println("  " + project.getProjectId()+ ": " + project.getProjectName()));
	}


	private void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated the hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");
		
		Project project = new Project();
		
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
		
	}


	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			return new BigDecimal(input).setScale(2);
			
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
		}
		
		
	}
		
	
	

	private boolean exitMenu() {
		System.out.println("Exiting the menu.");
		return true;
	}


	private void printOperations() {
		
		
		
		
		System.out.println("\nThese are the available selections. Press the Enter key to quit:");
		operations.forEach(line -> System.out.println(" " + line));
		
		if(Objects.isNull(curProject)) {
			System.out.println("\nYou are not working with a project.");
			
		}
		else {
			System.out.println("\nYou are working with project: " + curProject);
		}
		
		
	}
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
		}
		try {
			return Integer.valueOf(input);
			
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}
		
		
	}
	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		
		String input = scanner.nextLine();
		
		return input.isBlank() ? null : input.trim();
	}
	
		
}	

