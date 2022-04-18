Feature: HW1 Web Selenium Test

  Scenario: Successful
    When I navigate to "http://localhost:8080/"
    And I choose "Portugal" as the Location
    And I click on Submit
    Then I should see the country "Portugal" as the title of the table
    And I should look at Critical Cases in the first line and see its "not zero"

  Scenario: No Data
    When I navigate to "http://localhost:8080/"
    And I choose "DRC" as the Location
    And I click on Submit
    Then I should see the country "DRC" as the title of the table
    But I should look at Critical Cases in the first line and see its "zero"