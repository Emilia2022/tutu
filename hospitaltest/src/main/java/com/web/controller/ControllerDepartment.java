package com.web.controller;

import com.web.dao.DepartmentsDAO;
import com.web.dao.EmployeesDAO;
import com.web.dto.DepartmentsDTO;
import com.web.model.Departments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

        // Retrieve all departments
        List<Departments> departments = departmentsDAO.index();

        // Prepare model object
        List<DepartmentsDTO> departmentsDTO = new ArrayList<DepartmentsDTO>();

        for (Departments department: departments) {
            // Create new data transfer object
            DepartmentsDTO dto= new DepartmentsDTO();

           dto.setId(department.getId());
           dto.setName(department.getName());


            // Add to model list
            departmentsDTO.add(dto);

        }

        // Add to model
           model.addAttribute("department", departmentsDTO);


        // This will resolve to /WEB-INF/

        System.out.println(departmentsDTO);

        return "departments/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("department", departmentsDAO.show(id));
        return "departments/show";
    }

    @GetMapping("/new")
    public String newDepartment(@ModelAttribute("departmentsAttribute") Departments departments) {
        return "departments/new";
    }
    /**
     * Adds a new record
     */

    @PostMapping()
    public String create(@ModelAttribute("department") @Valid Departments departments,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "departments/new";

       departmentsDAO.save(departments);
        return "redirect:/hospital";
    }
    /**
     * Retrieves the "Edit Existing Department" page
     */

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long departmentId) {
        model.addAttribute("departments", departmentsDAO.show(departmentId));
        return "departments/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("department") @Valid Departments departments, BindingResult bindingResult,
                         @PathVariable("id") long departmentId) {
        if (bindingResult.hasErrors())
            return "departments/edit";

        departmentsDAO.update(departmentId, departments);
        return "redirect:/hospital";
    }
    /**
     * Deletes a record including all the associated employee
     */

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long departmentId) {
        departmentsDAO.delete(departmentId);
        return "redirect:/hospital";
    }


}
