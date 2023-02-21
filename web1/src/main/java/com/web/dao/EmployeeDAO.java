package com.web.dao;

import com.web.model.Department;
import com.web.model.Employee;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.web.dao.DepartmentDAO.*;

@Component
public class EmployeeDAO {
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
    public List<Employee> index() {
        List<Employee> employees = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM employees";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {

                Employee employee= new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setLastName(resultSet.getString("lastname"));
                employee.setSpecialization(resultSet.getString("specialization"));
                employee.setDep_id(resultSet.getLong("dep_id"));



                employees.add(employee);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return employees;
    }
    public Employee show(int id) {
        Employee employee=null;


        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM employees WHERE id=?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            employee=new Employee();
            employee.setId(resultSet.getLong("id"));
            employee.setLastName(resultSet.getString("lastname"));
            employee.setSpecialization(resultSet.getString("specialization"));
            employee.setDep_id(resultSet.getLong("dep_id"));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return employee;
    }
    public void save(Employee employee) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO employees VALUES(1,?,?,?)");

            preparedStatement.setString(1, employee.getLastName());
            preparedStatement.setString(2, employee.getSpecialization());
            preparedStatement.setLong(3,employee.getDep_id());


            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(int id, Employee updatedEmployee) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE employees SET lastname=?,specialization=?,dep_id=? WHERE id=?");

            preparedStatement.setString(1, updatedEmployee.getLastName());
            preparedStatement.setString(2, updatedEmployee.getSpecialization());
            preparedStatement.setLong(3,updatedEmployee.getDep_id());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void delete(int id) {
        PreparedStatement preparedStatement =
                null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM employees WHERE id=?");

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


}
