package com.godel.simplecrud.service;

import com.godel.simplecrud.dao.jpa.EmployeeJPADao;
import com.godel.simplecrud.exceptions.EmployeeServiceNotFoundException;
import com.godel.simplecrud.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Profile("JPA")
public class EmployeeJPAServiceImpl implements EmployeeService {

    private final EmployeeJPADao employeeJPADao;

    public EmployeeJPAServiceImpl(EmployeeJPADao employeeJPADao) {
        this.employeeJPADao = employeeJPADao;
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeJPADao.findAll();
    }

    @Override
    public Employee findEmployeeById(Long id) {
        Optional<Employee> employeeOptional = employeeJPADao.findById(id);

        if (employeeOptional.isEmpty()) {
            throw new EmployeeServiceNotFoundException("No such employee with ID=" + id);
        }

        return employeeOptional.get();
    }

    @Override
    public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
        return employeeJPADao.findByFirstNameIgnoreCaseLikeAndLastNameIgnoreCaseLike(firstName, lastName);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeJPADao.save(employee);
    }

    @Override
    public Employee updateOrCreateEmployee(Employee employee) {
        return employeeJPADao.save(employee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Optional<Employee> employeeOptional = employeeJPADao.findById(id);

        if (employeeOptional.isEmpty()) {
            throw new EmployeeServiceNotFoundException("No such employee with ID=" + id);
        }

        Employee employee = employeeOptional.get();
        employeeJPADao.delete(employee);
        log.info("deleteEmployeeById - successfully deleted {}", employee);
    }
}
