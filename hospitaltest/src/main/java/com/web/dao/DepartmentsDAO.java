package com.web.dao;

import com.web.model.Departments;
import com.web.model.Employees;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;

@Component
public class DepartmentsDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public DepartmentsDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Departments> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Departments p ", Departments.class)
                .getResultList();
    }


    /**
     * Retrieves a Department
     */
    @Transactional(readOnly =true)
    public Departments show(long id) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing Departments
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Departments  as p LEFT JOIN FETCH  p.employees WHERE p.id="+id);

      return (Departments) query.getResultList();



        //   return session.get(Departments.class, id);


    }

    @Transactional
    public void save(Departments departments) {
        Session session = sessionFactory.getCurrentSession();
        session.save(departments);
    }
    @Transactional
    public void update(long id, Departments updatedDepartments) {
        Session session = sessionFactory.getCurrentSession();
        Departments personToBeUpdated = session.get(Departments.class, id);

        personToBeUpdated.setName(updatedDepartments.getName());

    }

    @Transactional
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Departments as p LEFT JOIN FETCH  p.employees WHERE p.id="+id);

        // Retrieve record
        Departments departments=(Departments)query.getResultList();

        Set<Employees> employees=departments.getEmployees();


        // Delete employees
        session.delete(employees);

        // Delete associated employee
        for (Employees employee: employees) {
            session.delete(employee);
        }
    }


}
