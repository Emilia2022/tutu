package com.web.dao;

import com.web.model.Departments;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.web.model.Employees;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeesDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public EmployeesDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    /**
     * Retrieves all Employees
     */

    @Transactional(readOnly = true)
    public List<Employees> index(long departmentId) {
        Session session = sessionFactory.getCurrentSession();

       // return session.createQuery("select p from Employees p ", Employees.class)
                //.getResultList();

        Query query = session.createQuery("FROM Departments as p LEFT JOIN FETCH  p.employeesWHERE p.id="+departmentId);

       Departments departments=(Departments)query.getResultList();


        // Retrieve all
        return  new ArrayList<Employees>(departments.getEmployees());
    }

    @Transactional(readOnly =true)
    public Employees show(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Employees.class, id);
    }
    /**
     * Adds a new employee
     */


    @Transactional
    public void save(long departmentId,Employees employees) {
        Session session = sessionFactory.getCurrentSession();
        session.save(employees);

        // Add to departments as well
        // Retrieve existing departments via id
        Departments existingDepartment=(Departments)session.get(Departments.class,departmentId);

        // Assign updated values to this departments
        existingDepartment.getEmployees().add(employees);


        // Save updates
        session.save(existingDepartment);
    }
    @Transactional
    public void update(long id, Employees updatedEmployees) {
        Session session = sessionFactory.getCurrentSession();
        Employees employeesToBeUpdated = session.get(Employees.class, id);


        employeesToBeUpdated.setLastName(updatedEmployees.getLastName());
        employeesToBeUpdated.setSpecialization(updatedEmployees.getSpecialization());


    }

    @Transactional
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        // Delete reference to foreign key employee first
        // We need a SQL query instead of HQL query here to access the third table
        Query query = session.createSQLQuery("DELETE FROM DEPARTMENTS_EMPLOYEES " +
                "WHERE employees_ID="+id);

        query.executeUpdate();

        // Retrieve existing employee
        Employees employees=(Employees)session.get(Employees.class,id);


        // Delete
        session.delete(employees);


    }

}
