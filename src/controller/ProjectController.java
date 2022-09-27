/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;

import util.ConnectionFactory;

/**
 *
 * @author JUNIOR-PC
 */
public class ProjectController {

    public void save(Project project) throws SQLException {
        String sql = "INSERT INTO projects(name,"
                + "description,"
                + "createdAt, "
                + "updatedAt) "
                + "VALUES (?,?,?,?)";
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a Query
            statement = conn.prepareStatement(sql);

            //Setando os valores do statetment
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));

            //Executando a Query
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar  projeto " + e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }

    }

    public void update(Project project) throws SQLException {

        String sql = "UPDATE projects"
                + "SET name = ?,"
                + "description = ?,"
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE id  = ?";

        Connection conn = null;
        PreparedStatement statement = null;
        try {
            // Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a Query
            statement = conn.prepareStatement(sql);

            //Setando os valores do statetment
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());

            //Executando a Query
            statement.execute();

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao Atualizar  Projeto" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }

    }

    public void removeById(int projectId) throws SQLException {

        String sql = "DELETE FROM projects WHERE id = ? ";
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a Query
            statement = conn.prepareStatement(sql);

            //Setando os valores do statetment
            statement.setInt(1, projectId);

            //Executando a Query
            statement.execute();

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao deletar projeto " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn, statement);

        }

    }

    public List<Project> getAll() throws SQLException {
        String sql = "SELECT * FROM projects";

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Project> projects = new ArrayList<>();
        try {

            // Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a Query
            statement = conn.prepareStatement(sql);
            
            

            //Valor retornado pela execução da query
            resultSet = statement.executeQuery();

            //Emquanto houverem valores a serem percorridos pelo resultSet
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));

                projects.add(project);

            }

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar projetos " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn, statement, resultSet);

        }

        return projects;

    }

}
