package controller;

import ch.qos.logback.core.model.Model;
import com.emily.demo.dao.DepartmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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

        model.addAttribute("department", departmentDAO.index());
       // model.addAttribute("department", departmentDAO.index());
        return "index";
    }


}
