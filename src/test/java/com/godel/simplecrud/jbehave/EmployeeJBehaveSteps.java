package com.godel.simplecrud.jbehave;

import com.godel.simplecrud.model.Employee;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
public class EmployeeJBehaveSteps {

    private static final String URL = "http://localhost:8080/employees";
    private List<Employee> employeeList = new ArrayList<>();

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
        assertEquals(numberOfEmployees, employeeList.size());
    }
}
