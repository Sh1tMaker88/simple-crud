package com.godel.simplecrud.controller;

import com.godel.simplecrud.exceptions.EmployeeControllerIllegalArgumentException;
import com.godel.simplecrud.exceptions.ErrorMessage;
import com.godel.simplecrud.model.Employee;
import com.godel.simplecrud.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
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
    @Operation(summary = "Show all employees. Can be filtered by first name and last name",
            description = "Let see list of all employees")
    @ApiResponse(responseCode = "200", description = "Found employees", content = {
            @Content(mediaType = "application/json", array =
            @ArraySchema(schema = @Schema(implementation = Employee.class))
            )
    })
    public List<Employee> showAllEmployees(
            @Pattern(regexp = "[A-Za-z.]*", message = "First name can contain letters only and '.'")
                    @RequestParam(required = false, defaultValue = "") String firstName,
            @Pattern(regexp = "[A-Za-z.]*", message = "Last name can contain letters only and '.'")
                    @RequestParam(required = false, defaultValue = "") String lastName,
            HttpServletRequest request
    ) {
        log.info("IN: showAllEmployees - Request: [method:{}] URI: {}, params:{}",
                request.getMethod(), request.getRequestURI(), request.getQueryString());

        List<Employee> employeeList = employeeService.findByFirstNameAndLastName(firstName + "%", lastName + "%");

        log.info("OUT: showAllEmployees - found {} employees", employeeList.size());
        return employeeList;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Show specific employee by his ID", description = "Let see specific employee by his ID as path variable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee founded", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))
            }),
            @ApiResponse(responseCode = "400", description = "Incorrect parameter - ID cannot be negative or equal 0", content = {
                    @Content(mediaType = "plain/text", schema = @Schema(implementation = ErrorMessage.class))
            }),
            @ApiResponse(responseCode = "404", description = "No such employee in data base", content = {
                    @Content(mediaType = "plain/text", schema = @Schema(implementation = ErrorMessage.class))
            })
    })
    public Employee showEmployee(@PathVariable @Min(value = 1, message = "Employee ID cannot be less or equal 0") Long id,
                                 HttpServletRequest request) {
        log.info("IN: showEmployee - Request: [method:{}] URI: {}", request.getMethod(), request.getRequestURI());

        Employee employee = employeeService.findEmployeeById(id);

        log.info("OUT: showEmployee - found {}", employee);
        return employee;
    }

    @PostMapping
    @Operation(summary = "Create new employee", description = "Let create new employee with given parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))
            }),
            @ApiResponse(responseCode = "400", description = "Incorrect data input", content = {
                    @Content(mediaType = "plain/text", schema = @Schema(implementation = ErrorMessage.class))
            })
    })
    public Employee createEmployee(@RequestBody Employee employee, HttpServletRequest request) {
        log.info("IN: createEmployee - Request: [method:{}] URI: {}", request.getMethod(), request.getRequestURI());

        Employee createdEmployee = employeeService.createEmployee(employee);

        log.info("OUT: createEmployee - found {}", createdEmployee);
        return createdEmployee;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Create or update employee", description = "Let create new employee with given parameters or update if he already exists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee saved", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))
            }),
            @ApiResponse(responseCode = "400", description = "Incorrect data input", content = {
                    @Content(mediaType = "plain/text", schema = @Schema(implementation = ErrorMessage.class))
            })
    })
    public Employee updateEmployee(@PathVariable @Min(value = 1, message = "Employee ID cannot be less or equal 0") Long id,
                                   @Valid @RequestBody Employee employee, HttpServletRequest request) {
        log.info("IN: updateEmployee - Request: [method:{}] URI: {}", request.getMethod(), request.getRequestURI());

        employee.setEmployeeId(id);
        Employee updatedEmployee = employeeService.updateOrCreateEmployee(employee);

        log.info("OUT: updateEmployee - found {}", updatedEmployee);
        return updatedEmployee;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee", description = "Let delete employee by his ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee deleted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))
            }),
            @ApiResponse(responseCode = "400", description = "Incorrect parameter - ID cannot be negative or equal 0", content = {
                    @Content(mediaType = "plain/text", schema = @Schema(implementation = ErrorMessage.class))
            }),
            @ApiResponse(responseCode = "404", description = "No such employee in data base", content = {
                    @Content(mediaType = "plain/text", schema = @Schema(implementation = ErrorMessage.class))
            })
    })
    public void deleteEmployee(@PathVariable @Min(value = 1, message = "Employee ID cannot be less or equal 0") Long id,
                               HttpServletRequest request) {
        log.info("IN: deleteEmployee - Request: [method:{}] URI: {}", request.getMethod(), request.getRequestURI());

        employeeService.deleteEmployeeById(id);

        log.info("OUT deleteEmployee - success");
    }
}
