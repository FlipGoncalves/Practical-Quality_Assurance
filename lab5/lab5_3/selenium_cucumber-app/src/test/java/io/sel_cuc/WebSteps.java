package io.sel_cuc;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class WebSteps {

    private WebDriver driver;

    @When("I navigate to {string}")
    public void i_navigate_to(String url) {
        driver = WebDriverManager.firefoxdriver().create();
        driver.get(url);
    }

    @When("I choose {string} as the departure city")
    public void i_choose_as_the_departure_city(String string) {
        Select dropdown = new Select(driver.findElement(By.name("fromPort")));
        dropdown.selectByValue(string);
    }

    @When("I choose {string} as the destination city")
    public void i_choose_as_the_destination_city(String string) {
        Select dropdown = new Select(driver.findElement(By.name("toPort")));
        dropdown.selectByValue(string);
    }

    @When("I click Find Flights")
    public void i_click_find_flights() {
        driver.findElement(By.tagName("input")).click();
    }

    @Then("I should choose the flight number {int} with id {int}")
    public void i_should_choose_the_flight_number(Integer int1, Integer int2) {
        if (Integer.parseInt(driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr["+int1+"]/td[2]")).getText()) != int2)
            throw new io.cucumber.java.PendingException();
    }
}