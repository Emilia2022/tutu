package com.web.controller;

import com.web.dao.DepartmentsDAO;
import com.web.model.Departments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller


@RequestMapping("/hospital")
public class ControllerDepartment {
    private final DepartmentsDAO departmentsDAO;

    @Autowired
    public ControllerDepartment(DepartmentsDAO departmentsDAO) {
        this.departmentsDAO = departmentsDAO;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("department", departmentsDAO.index());
        return "departments/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("department", departmentsDAO.show(id));
        return "departments/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("departments") Departments departments) {
        return "departments/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Departments departments,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "departments/new";

       departmentsDAO.save(departments);
        return "redirect:/hospital";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("departments", departmentsDAO.show(id));
        return "departments/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("department") @Valid Departments departments, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "departments/edit";

        departmentsDAO.update(id, departments);
        return "redirect:/hospital";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        departmentsDAO.delete(id);
        return "redirect:/hospital";
    }


}
