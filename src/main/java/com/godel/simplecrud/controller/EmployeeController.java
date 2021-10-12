package com.godel.simplecrud.controller;

import com.godel.simplecrud.exceptions.EmployeeControllerIllegalArgumentException;
import com.godel.simplecrud.model.Employee;
import com.godel.simplecrud.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Slf4j
@Validated
@Tag(name = "Employee controller", description = "Give access to CRUD operation for employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(summary = "Show all employees", description = "Let see list of all employees")
    public List<Employee> showAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Show specific employee by his ID", description = "Let see specific employee by his ID as path variable")
    public Employee showEmployee(@PathVariable @Parameter(description = "Employee ID") Long id) {
        if (id <= 0) {
            throw new EmployeeControllerIllegalArgumentException("ID cannot be less or equal 0");
        }

        return employeeService.findEmployeeById(id);
    }

    @GetMapping("{first_name}/{last_name}")
    public Employee showEmployeeByFirstNameAndLastName(@PathVariable String first_name,
                                                       @PathVariable String last_name) {
        String pattern = "\\D{3,}";
        if (!first_name.matches(pattern) || !last_name.matches(pattern)) {
            throw new EmployeeControllerIllegalArgumentException("First name and last name must have length 3+ letters and contains no numbers");
        }

        return employeeService.findByFirstNameAndLastName(StringUtils.capitalize(first_name), StringUtils.capitalize(last_name));
    }

    @PostMapping
    @Operation(summary = "Create new employee", description = "Let create new employee with given parameters")
    public Employee createEmployee(@RequestBody Employee employee) {

        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Create or update employee", description = "Let create new employee with given parameters or update if he already exists")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        if (id <= 0) {
            throw new EmployeeControllerIllegalArgumentException("ID cannot be less or equal 0");
        }
        employee.setEmployeeId(id);

        return employeeService.updateOrCreateEmployee(employee);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee", description = "Let delete employee by his ID")
    public void deleteEmployee(@PathVariable @Parameter(description = "Employee ID") Long id) {
        if (id <= 0) {
            throw new EmployeeControllerIllegalArgumentException("ID cannot be less or equal 0");
        }

        employeeService.deleteEmployeeById(id);
    }
}
