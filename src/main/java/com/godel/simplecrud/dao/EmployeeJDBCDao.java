package com.godel.simplecrud.dao;

import com.godel.simplecrud.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmployeeJDBCDao implements EmployeeDao {

    private final static String CREATE_SQL = "INSERT INTO employee (first_name, last_name, department_id, " +
            "job_title, gender, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
    private final static String UPDATE_SQL = "UPDATE employee SET first_name = ?, last_name = ?, department_id = ?, " +
            "job_title = ?, gender = ?, date_of_birth = ? WHERE employee_id = ?";
    private final static String GET_ALL_SQL = "SELECT * FROM employee";
    private final static String GET_BY_ID_SQL = "SELECT * FROM employee WHERE employee_id=?";
    private final static String DELETE_BY_ID_SQL = "DELETE FROM employee WHERE employee_id=?";
    private final static String EXISTS_SQL = "SELECT COUNT(*) FROM employee WHERE employee_id = ?";

    private final JdbcTemplate jdbcTemplate;

    public EmployeeJDBCDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query(GET_ALL_SQL, new EmployeeRowMapper());
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(GET_BY_ID_SQL, new EmployeeRowMapper(), id));
    }

    @Override
    public Long create(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS);
            setObjectsForCreateUpdateStatements(employee, statement);
            return statement;
        }, keyHolder);

        return Long.parseLong(String.valueOf(keyHolder.getKeys().get("employee_id")));
    }

    @Override
    public Long update(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL, Statement.RETURN_GENERATED_KEYS);
            setObjectsForCreateUpdateStatements(employee, statement);
            statement.setLong(7, employee.getEmployeeId());
            return statement;
        }, keyHolder);

        return Long.parseLong(String.valueOf(keyHolder.getKeys().get("employee_id")));
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id);
    }

    @Override
    public Integer exist(Long id) {
        return jdbcTemplate.queryForObject(EXISTS_SQL, Integer.class, id);
    }

    private void setObjectsForCreateUpdateStatements(Employee employee, PreparedStatement statement) throws SQLException {
        statement.setString(1, employee.getFirstName());
        statement.setString(2, employee.getLastName());
        statement.setLong(3, employee.getDepartmentId());
        statement.setString(4, employee.getJobTitle());
        statement.setObject(5, employee.getGender().toString(), Types.OTHER);
        statement.setDate(6, Date.valueOf(employee.getDateOfBirth()));
    }
}
