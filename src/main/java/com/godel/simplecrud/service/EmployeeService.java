package com.godel.simplecrud.service;

import com.godel.simplecrud.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAllEmployees();

    Employee findEmployeeById(Long id);

    Employee createEmployee(Employee employee);

    Employee updateOrCreateEmployee(Employee employee);

    void deleteEmployeeById(Long id);
}
