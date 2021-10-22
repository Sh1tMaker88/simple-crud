package com.godel.simplecrud.dao.jpa;

import com.godel.simplecrud.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeJPADao extends JpaRepository<Employee, Long> {

    List<Employee> findByFirstNameIgnoreCaseLikeAndLastNameIgnoreCaseLike(String firstName, String lastName);
}
