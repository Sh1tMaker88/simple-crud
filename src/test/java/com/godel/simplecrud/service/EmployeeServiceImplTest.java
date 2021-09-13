package com.godel.simplecrud.service;

import com.godel.simplecrud.dao.EmployeeDao;
import com.godel.simplecrud.dao.EmployeeJDBCDao;
import com.godel.simplecrud.model.Employee;
import com.godel.simplecrud.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    EmployeeJDBCDao employeeDao;
    @InjectMocks
    EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllEmployeesTest() {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee();
        employees.add(employee);
        when(employeeDao.findAll()).thenReturn(employees);

        //when
        List<Employee> foundedEmployees = employeeService.findAllEmployees();

        //then
        assertEquals(1, foundedEmployees.size());
        verify(employeeDao).findAll();
    }

    @Test
    void findEmployeeByIdTest() {
        //given
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        when(employeeDao.findById(anyLong())).thenReturn(Optional.of(employee));

        //when
        Employee foundedEmployee = employeeService.findEmployeeById(1L);

        //then
        assertNotNull(foundedEmployee);
        verify(employeeDao).findById(anyLong());
    }

    @Test
    void findEmployeeById_ThrowRuntimeExceptionTest() {

        assertThrows(RuntimeException.class, () -> employeeService.findEmployeeById(1L));
        verify(employeeDao).findById(anyLong());
    }

    @Test
    void createEmployeeTest() {
        //given
        Employee employee = new Employee(1L, "fName", "lName", 5L,
                "jobTitle", Gender.MALE, LocalDate.of(1999, 5, 25));
        when(employeeDao.create(any())).thenReturn(employee.getEmployeeId());
        when(employeeDao.findById(anyLong())).thenReturn(Optional.of(employee));

        //when
        Employee foundedEmployee = employeeService.createEmployee(employee);

        //then
        assertEquals(1L, foundedEmployee.getEmployeeId());
        assertEquals("fName", foundedEmployee.getFirstName());
        assertEquals("lName", foundedEmployee.getLastName());
        assertEquals(5L, foundedEmployee.getDepartmentId());
        assertEquals("jobTitle", foundedEmployee.getJobTitle());
        assertEquals(Gender.MALE, foundedEmployee.getGender());
        assertEquals(LocalDate.of(1999, 5, 25), foundedEmployee.getDateOfBirth());
    }

    @Test
    void deleteEmployeeByIdTest() {
        //given
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        when(employeeDao.findById(anyLong())).thenReturn(Optional.of(employee));

        //when
        employeeService.deleteEmployeeById(1L);

        //then
        verify(employeeDao).deleteById(anyLong());
    }

    @Test
    void deleteEmployeeById_ThrowRuntimeExceptionTest() {
        when(employeeDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> employeeService.deleteEmployeeById(anyLong()));
    }
}