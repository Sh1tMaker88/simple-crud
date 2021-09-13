package com.godel.simplecrud.service;

import com.godel.simplecrud.dao.EmployeeJDBCDao;
import com.godel.simplecrud.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeJDBCDao employeeJDBCDao;

    public EmployeeServiceImpl(EmployeeJDBCDao employeeJDBCDao) {
        this.employeeJDBCDao = employeeJDBCDao;
    }


    @Override
    public List<Employee> findAllEmployees() {
        return employeeJDBCDao.findAll();
    }

    @Override
    public Employee findEmployeeById(Long id) {
        Optional<Employee> employeeOptional = employeeJDBCDao.findById(id);
        if (employeeOptional.isEmpty()) {
            log.error("No such employee in database with ID:{}", id);
            throw new RuntimeException("No such employee in database with ID:" + id);
        }
        return employeeOptional.get();
    }

    @Override
    @Transactional
    public Employee createEmployee(Employee employee) {
        Long employeeId = employeeJDBCDao.create(employee);
        log.info("Created employee with ID:{}", employeeId);

        return employeeJDBCDao.findById(employeeId).get();
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Long employeeId = employeeJDBCDao.update(employee);
        log.info("Updated employee with ID:{}", employeeId);

        return employeeJDBCDao.findById(employeeId).get();
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Optional<Employee> employeeOptional = employeeJDBCDao.findById(id);
        if (employeeOptional.isEmpty()) {
            log.info("No such employee with ID:{}", id);
            throw new RuntimeException("No such employee with ID:" + id);
        }
        employeeJDBCDao.deleteById(id);
        log.info("Deleted employee with ID:{}", id);
    }
}
