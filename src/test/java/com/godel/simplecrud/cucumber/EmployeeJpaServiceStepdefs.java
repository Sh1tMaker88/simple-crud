package com.godel.simplecrud.cucumber;

import com.godel.simplecrud.model.Employee;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeJpaServiceStepdefs {

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String URL = "http://localhost:8080/employees";
    private int statusCode;
    private List<Employee> employeeList = new ArrayList<>();
    private String exceptionMessage;

    @Given("database with employees")
    public void databaseWithEmployees() {
    }

    @When("user search employee by first name {string} and last name {string}")
    public void employeeServiceInvokeSearchByFirstNameAndLastName(String firstName, String lastName) {
        URI path = URI.create(URL + "?firstName=" + firstName + "&lastName=" + lastName);
        try {
            ResponseEntity<List<Employee>> responseEntity = restTemplate.exchange(path, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {});
            employeeList = responseEntity.getBody();
            statusCode = responseEntity.getStatusCodeValue();
        } catch (RestClientException exception) {
            exceptionMessage = exception.getMessage();
        }
    }

    @Then("return {int} employees")
    public void returnEmployees(int numberOfEmployees) {
        assertEquals(numberOfEmployees, employeeList.size());
    }

    @And("client receives status code {int}")
    public void clientReceivesStatusCode(int status) {
        assertEquals(status, statusCode);
    }

    @Then("program throws Exception")
    public void programThrowsException() {
        assertNotNull(exceptionMessage);
    }
}
