package com.web.controller;


import com.web.dao.EmployeesDAO;
import com.web.model.Departments;
import com.web.model.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller

@RequestMapping("/employees")
public class ControllerEmployee {
    private final EmployeesDAO employeesDAO;
    @Autowired
    public ControllerEmployee(EmployeesDAO employeesDAO) {
        this.employeesDAO = employeesDAO;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("employee", employeesDAO.index());
        return "employees/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("employee", employeesDAO.show(id));
        return "employees/show";
    }

    @GetMapping("/new")
    public String  newEmployee(@ModelAttribute("employee") Employees employees) {
        return "employees/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("employee") @Valid Employees employees,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "employees/new";

        employeesDAO.save(employees);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("employee", employeesDAO.show(id));
        return "employees/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Employees employees , BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "employees/edit";

        employeesDAO.update(id, employees);
        return "redirect:/employees";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        employeesDAO.delete(id);
        return "redirect:/employees";
    }


}
