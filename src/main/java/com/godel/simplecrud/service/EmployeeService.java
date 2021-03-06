package com.godel.simplecrud.service;

import com.godel.simplecrud.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> findAllEmployees();

    Employee findEmployeeById(Long id);

    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    Employee createEmployee(Employee employee);

    Employee updateOrCreateEmployee(Employee employee);

    void deleteEmployeeById(Long id);
}
