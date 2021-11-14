package com.godel.simplecrud.cucumber;

import com.godel.simplecrud.SimpleCrudApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(
        classes = SimpleCrudApplication.class
        , webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
        , properties = "server.port=8080"
)
public class SpringIntegrationTest {

}
