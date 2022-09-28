/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author JUNIOR-PC
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

public class TaskController {

    public void save(Task task) throws SQLException {
        String sql = "INSERT INTO tasks(idProject,"
                + "name,"
                + "description,"
                + "completed, "
                + "notes, "
                + "deadline, "
                + "createdAt, "
                + "updatedAt) "
                + "VALUES (?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a Query
            statement = conn.prepareStatement(sql);

            //Setando os valores do statetment
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));

            //Executando a Query
            statement.execute();

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao salvar  tarefa " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }

    }

    public void update(Task task) throws SQLException {

        String sql = "UPDATE tasks "
                + "SET idproject = ?, "
                + "name = ?, "
                + "description = ?, "
                + "completed = ?, "
                + "notes = ?, "
                + "deadline = ?, "
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE  id = ? ";

        Connection conn = null;
        PreparedStatement statement = null;
        try {
            // Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a Query
            statement = conn.prepareStatement(sql);

            //Setando os valores do statetment
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());

            //Executando a Query
            statement.execute();

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao Atualizar  tarefa" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }

    }

    public void removeById(int taskId) throws SQLException {

        String sql = "DELETE FROM tasks WHERE id = ? ";
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a Query
            statement = conn.prepareStatement(sql);

            //Setando os valores do statetment
            statement.setInt(1, taskId);

            //Executando a Query
            statement.execute();

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao deletar tarefa " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn, statement);

        }

    }

    public List<Task> getAll(int idProject) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE idProject = ?";

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Task> tasks = new ArrayList<>();
        try {
            // Estabelecendo a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();

            //Preparando a Query
            statement = conn.prepareStatement(sql);
            // Setando o valor que corresponde ao filtro de busca
            statement.setInt(1, idProject);

            //Valor retornado pela execução da query
            resultSet = statement.executeQuery();

            //Emquanto houverem valores a serem percorridos pelo resultSet
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idproject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setNotes(resultSet.getString("notes"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));

                tasks.add(task);

            }

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar tarefas " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(conn, statement, resultSet);

        }

        return tasks;

    }

}
