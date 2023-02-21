package com.web.controller;

import com.web.dao.EmployeeDAO;

import com.web.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Component
@RequestMapping("/employees")
public class ControllerEmployee {

    private final EmployeeDAO employeeDAO;

    @Autowired

    public ControllerEmployee(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }



    @GetMapping()
    public String index(Model model) {

        model.addAttribute("employees", employeeDAO.index());

        return "employees/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("employee", employeeDAO.show(id));
        return "employees/show";
    }

    @GetMapping("/new")
    public String newEmployee(@ModelAttribute("employees") Employee employee) {
        return "employees/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("employees") @Valid Employee employee,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "employees/new";
        employeeDAO.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("departments", employeeDAO.show(id));
        return "employees/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("departments") @Valid Employee employee, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "employees/edit";

        employeeDAO.update(id, employee);
        return "redirect:/employees/index";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        employeeDAO.delete(id);
        return "redirect:/employee";
    }

}
