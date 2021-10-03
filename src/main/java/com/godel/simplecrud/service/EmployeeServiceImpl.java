package com.godel.simplecrud.service;

import com.godel.simplecrud.dao.jdbc.EmployeeJDBCDao;
import com.godel.simplecrud.exceptions.ResourceNotFoundException;
import com.godel.simplecrud.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Profile("JDBC")
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
            throw new ResourceNotFoundException("No such employee in database with ID:" + id);
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
    public Employee updateOrCreateEmployee(Employee employee) {
        Long employeeId;
        if (employeeJDBCDao.exist(employee.getEmployeeId()) > 0) {
            employeeId = employeeJDBCDao.update(employee);
            log.info("Updated employee with ID:{}", employeeId);
        } else {
            employeeId = employeeJDBCDao.create(employee);
            log.info("Create employee with ID:{}", employeeId);
        }

        return employeeJDBCDao.findById(employeeId).get();
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Optional<Employee> employeeOptional = employeeJDBCDao.findById(id);
        if (employeeOptional.isEmpty()) {
            log.info("No such employee with ID:{}", id);
            throw new ResourceNotFoundException("No such employee with ID:" + id);
        }
        employeeJDBCDao.deleteById(id);
        log.info("Deleted employee with ID:{}", id);
    }
}
