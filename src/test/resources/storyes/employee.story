Feature: search in database

Scenario: user want to create a new employee

Given database with employees
When user search employee by first name o and last name o
Then user receives response with 2 employee found