package com.web.dao;

import com.web.model.Departments;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.web.model.Employees;

import java.util.List;

@Component
public class EmployeesDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public EmployeesDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Employees> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Employees p ", Employees.class)
                .getResultList();
    }

    @Transactional(readOnly =true)
    public Employees show(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Employees.class, id);
    }

    @Transactional
    public void save(Employees employees) {
        Session session = sessionFactory.getCurrentSession();
        session.save(employees);
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
        session.remove(session.get(Employees.class, id));
    }

}
