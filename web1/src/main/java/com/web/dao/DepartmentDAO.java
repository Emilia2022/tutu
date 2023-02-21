package com.web.dao;

import com.web.model.Department;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentDAO {
    public static final String URL = "jdbc:postgresql://localhost:5432/Hospital";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "123";
    public static Connection connection;


    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public List<Department> index() {
        List<Department> departments = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM departments";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {

                Department department= new Department();
                department.setId(resultSet.getLong("id"));
                department.setName(resultSet.getString("name"));

                departments.add(department);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return departments;
    }
    public Department show(int id) {
        Department department=null;


        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM departments WHERE id=?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

             department= new Department();
            department.setId(resultSet.getLong("id"));
            department.setName(resultSet.getString("name"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return department;
    }
    public void save(Department department) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO departments VALUES(1,?)");

            preparedStatement.setString(1, department.getName());



            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(int id, Department updatedDepartment) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE departments SET name=? WHERE id=?");

            preparedStatement.setString(1, updatedDepartment.getName());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void delete(int id) {
        PreparedStatement preparedStatement =
                null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM departments WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
