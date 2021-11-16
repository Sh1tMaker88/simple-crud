package com.godel.simplecrud.jbehave;

import com.godel.simplecrud.model.Employee;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
public class EmployeeJBehaveSteps {

    private static final String URL = "http://localhost:8080/employees";
    private int statusCode;
    private List<Employee> employeeList = new ArrayList<>();
    private String exceptionMessage;

    @Given("database with employees")
    public void databaseWIthEmployees() {
    }

    @When("user search employee by first name $firstName and last name $lastName")
    public void searchEmployeeByFirstNameAndLastName(String firstName, String lastName) {
        URI path = URI.create(URL + "?firstName=" + firstName + "&lastName=" + lastName);
        Response response = RestAssured.get(path);
        employeeList = response.getBody().as(List.class);
    }

    @Then("user receives response with $numberOfEmployees employee found")
    public void returnEmployees(int numberOfEmployees) {
        System.out.println("---assert---");
        assertEquals(numberOfEmployees, employeeList.size());
        System.out.println("---out assert---");
    }
}
