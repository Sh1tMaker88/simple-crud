package com.godel.simplecrud.service;

import com.godel.simplecrud.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> findAllEmployees();

    Employee findEmployeeById(Long id);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    void deleteEmployeeById(Long Id);
}
