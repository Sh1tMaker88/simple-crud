Feature: search employees by first/last name (optional)
  if none of this specified return all of employees

  Scenario Outline: with first name and last name inserted
    Given database with employees
    When user search employee by first name <fName> and last name <lName>
    Then return <counter> employees
    And client receives status code <status>

    Examples:
    | fName | lName | counter | status |
    | "o"   | "o"   | 2       | 200    |
    | "o"   | ""    | 4       | 200    |
    | ""    | "o"   | 2       | 200    |
    | ""    | ""    | 8       | 200    |

  Scenario: user try to search name with numbers
    Given database with employees
    When user search employee by first name "s2" and last name "o"
    Then program throws Exception