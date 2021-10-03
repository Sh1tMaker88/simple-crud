package com.godel.simplecrud.dao.jpa;

import com.godel.simplecrud.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJPADao extends JpaRepository<Employee, Long> {

}
