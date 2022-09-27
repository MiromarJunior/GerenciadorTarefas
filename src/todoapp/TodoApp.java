/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todoapp;

import controller.ProjectController;
/**
 *
 * @author JUNIOR-PC
 */
import java.sql.SQLException;
import java.util.List;
import model.Project;

public class TodoApp {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        
        ProjectController pc = new ProjectController();
        Project project = new Project();
        
        
        project.setName("Projeto Teste");
        project.setDescription("Descrição teste");
        pc.save(project);
        
        List<Project> ps = pc.getAll();
        System.out.println("Total "+ps.size());
    }
    
}
