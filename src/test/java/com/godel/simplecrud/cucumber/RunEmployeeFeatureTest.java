package com.godel.simplecrud.cucumber;

import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasspathResource("src/test/resources/features")
public class RunEmployeeFeatureTest {

}
