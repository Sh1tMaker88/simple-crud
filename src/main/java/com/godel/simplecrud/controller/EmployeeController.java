package com.godel.simplecrud.controller;

import com.godel.simplecrud.model.Employee;
import com.godel.simplecrud.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
@Validated
@Tag(name = "Employee controller", description = "Give access to CRUD operation for employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(summary = "Show all employees", description = "Let see list of all employees")
    public ResponseEntity<List<Employee>> showAllEmployees() {
        return ResponseEntity.ok(employeeService.findAllEmployees());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Show specific employee by his ID", description = "Let see specific employee by his ID as path variable")
    public ResponseEntity<Employee> showEmployee(@PathVariable @Parameter(description = "Employee ID") Long id) {
        return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }

    @PostMapping
    @Operation(summary = "Create new employee", description = "Let create new employee with given parameters")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employee));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Create or update employee", description = "Let create new employee with given parameters or update if he already exists")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
                                                   @RequestBody Employee employee) {
        employee.setEmployeeId(id);

        return ResponseEntity.ok(employeeService.updateOrCreateEmployee(employee));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee", description = "Let delete employee by his ID")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable @Parameter(description = "Employee ID") Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
