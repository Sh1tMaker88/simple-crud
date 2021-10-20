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
        List<Employee> employeeList = employeeJPADao.findAll();
        log.info("OUT: findAllEmployees - found {} employees", employeeList.size());
        return employeeList;
    }

    @Override
    public Employee findEmployeeById(Long id) {
        log.info("IN: findEmployeeById - search employee with ID={}", id);
        Optional<Employee> employeeOptional = employeeJPADao.findById(id);

        if (employeeOptional.isEmpty()) {
            log.error("findEmployeeById - no such employee with ID={}", id);
            throw new EmployeeServiceNotFoundException("No such employee with ID=" + id);
        }

        Employee employee = employeeOptional.get();
        log.info("OUT: findEmployeeById - found employee={}", employee);
        return employee;
    }

    @Override
    public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
        log.info("IN: findByFirstNameAndLastName - search employee with first name={} and last name={}", firstName, lastName);
        List<Employee> employeeList =
                employeeJPADao.findByFirstNameIgnoreCaseLikeAndLastNameIgnoreCaseLike(firstName, lastName);

//        if (optionalEmployee.isEmpty()) {
//            log.error("No such employee found");
//            throw new EmployeeServiceNotFoundException("No such employee with first name=" + firstName +
//                    " and last name=" + lastName);
//        }

        log.info("OUT: findByFirstNameAndLastName - found {} employee", employeeList.size());
        return employeeList;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        log.info("IN: createEmployee - creating {}", employee);
        Employee savedEmployee = employeeJPADao.save(employee);
        log.info("OUT: createEmployee - {}", savedEmployee);
        return savedEmployee;
    }

    @Override
    public Employee updateOrCreateEmployee(Employee employee) {
        log.info("IN: updateOrCreateEmployee - updating {}", employee);
        Employee savedEmployee = employeeJPADao.save(employee);
        log.info("OUT: updateOrCreateEmployee - successfully updated {}", savedEmployee);
        return employeeJPADao.save(employee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        log.info("IN: deleteEmployeeById - attempt to delete employee with ID={}", id);
        Optional<Employee> employeeOptional = employeeJPADao.findById(id);

        if (employeeOptional.isEmpty()) {
            log.error("deleteEmployeeById - no such employee with ID={}", id);
            throw new EmployeeServiceNotFoundException("No such employee with ID=" + id);
        }

        Employee employee = employeeOptional.get();
        employeeJPADao.delete(employee);
        log.info("OUT: deleteEmployeeById - successfully deleted {}", employee);
    }
}
