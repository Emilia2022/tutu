package com.emily.demo.dao;

import com.emily.demo.model.Department;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class DepartmentDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/Hospital";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "123";
    private static Connection connection;


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
}
