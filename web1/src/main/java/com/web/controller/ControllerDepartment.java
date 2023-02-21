package com.web.controller;

import com.web.dao.DepartmentDAO;
import com.web.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller


@RequestMapping("/hospital")
public class ControllerDepartment {

    private final DepartmentDAO departmentDAO;
    @Autowired

    public ControllerDepartment(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    @GetMapping()
    public String index(Model model) {

        model.addAttribute("departments", departmentDAO.index());

        return "departments/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("departments", departmentDAO.show(id));
        return "departments/show";
    }

    @GetMapping("/new")
    public String newDepartment(@ModelAttribute("departments") Department department) {
        return "departments/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("departments") @Valid Department department,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "departments/new";
departmentDAO.save(department);
        return "redirect:/hospital";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("departments", departmentDAO.show(id));
        return "departments/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("departments") @Valid Department department, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "departments/edit";

        departmentDAO.update(id, department);
        return "redirect:/hospital";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        departmentDAO.delete(id);
        return "redirect:/hospital";
    }

}
