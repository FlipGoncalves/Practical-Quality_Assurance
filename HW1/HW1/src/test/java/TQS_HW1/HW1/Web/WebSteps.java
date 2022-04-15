package TQS_HW1.HW1.Web;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class WebSteps {

    private WebDriver driver;

    @When("I navigate to {string}")
    public void navigate_to(String url) {
        driver = WebDriverManager.firefoxdriver().create();
        driver.get(url);
    }

    @When("I choose {string} as the Location")
    public void choose_country(String string) {
        Select dropdown = new Select(driver.findElement(By.name("country")));
        dropdown.selectByValue(string);

        assertEquals(dropdown.getAllSelectedOptions().size(), 1);
    }

    @When("I click on Submit")
    public void submit_button() {
        driver.findElement(By.tagName("button")).click();
    }

    @Then("I should see the country {string} as the title of the table")
    public void table_title(String string) {
        assertEquals(driver.findElement(By.xpath("/html/body/div/div/h1")).getAttribute("innerHTML"), string);
    }

    @Then("I should look at Critical Cases in the first line and see its {int}")
    public void look_at_table(Integer int1) {
        assertEquals(Integer.parseInt(driver.findElement(By.xpath("/html/body/div/div/div[1]/table/tbody/tr/td[4]")).getText()), int1);
    }
}