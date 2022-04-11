Feature: BlazeDemo Confirmation

  Scenario: Successful
    When I navigate to "https://blazedemo.com/"
    And I choose "Boston" as the departure city
    And I choose "Rome" as the destination city
    And I click Find Flights
    Then I should choose the flight number 3 with id 9696