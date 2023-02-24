package com.web.dao;

import com.web.model.Departments;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly =true)
    public Departments show(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Departments.class, id);
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
        session.remove(session.get(Departments.class, id));
    }


}
