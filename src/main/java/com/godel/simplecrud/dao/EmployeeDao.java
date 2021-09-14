package com.godel.simplecrud.dao;

import com.godel.simplecrud.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    List<Employee> findAll();

    Optional<Employee> findById(Long id);

    Long create(Employee employee);

    Long update(Employee employee);

    int deleteById(Long Id);

    Integer exist(Long id);
}
