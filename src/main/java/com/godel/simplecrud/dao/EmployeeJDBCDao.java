package com.godel.simplecrud.dao;

import com.godel.simplecrud.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmployeeJDBCDao implements EmployeeDao {

//    private final static String CREATE_SQL = "INSERT INTO employee (first_name, last_name, department_id, " +
//            "job_title, gender, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
    private final static String GET_ALL_SQL = "SELECT * FROM employee";
    private final static String GET_BY_ID_SQL = "SELECT * FROM employee WHERE employee_id=?";
    private final static String DELETE_BY_ID_SQL = "DELETE FROM employee WHERE employee_id=?";

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
        return Optional.ofNullable(jdbcTemplate.queryForObject(GET_BY_ID_SQL, new EmployeeRowMapper()));
    }

    @Override
    public Long create(Employee employee) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("employee").usingGeneratedKeyColumns("employee_id");
        Map<String, Object> parameters = new HashMap<>();
        createInsertParameters(employee, parameters);

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(CREATE_SQL,
//                employee.getFirstName(),
//                employee.getLastName(),
//                employee.getDepartmentId(),
//                employee.getJobTitle(),
//                employee.getGender(),
//                employee.getDateOfBirth());
//        keyHolder.getKey().longValue();
        return Long.parseLong(String.valueOf(id));
    }



    @Override
    public Long update(Employee employee) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("employee");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("employee_id", employee.getEmployeeId());
        createInsertParameters(employee, parameters);

        simpleJdbcInsert.execute(parameters);

        return employee.getEmployeeId();
    }

    @Override
    public int delete(Employee employee) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, employee.getEmployeeId());
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id);
    }

    private void createInsertParameters(Employee employee, Map<String, Object> parameters) {
        parameters.put("first_name", employee.getFirstName());
        parameters.put("last_name", employee.getLastName());
        parameters.put("department_id", employee.getDepartmentId());
        parameters.put("job_title", employee.getJobTitle());
        parameters.put("gender", employee.getGender());
        parameters.put("date_of_birth", employee.getDateOfBirth());
    }
}
