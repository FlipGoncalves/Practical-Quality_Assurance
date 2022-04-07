package PageObjectPattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.bonigarcia.seljup.Arguments;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class FirefoxHeadlessTest {

  @Test
  public void buyTicketDocker(@Arguments("--headless") FirefoxDriver driver) {

    driver.get("https://blazedemo.com/");
    Travel travelPointPick = new Travel(driver, 5);
    travelPointPick.chooseFromPort(1);
    travelPointPick.chooseToPort(2);
    travelPointPick.confirmFlight();
    PickFlight p = new PickFlight(driver);
    assertThat(p.getDepartCountry()).contains("Philadelphia");
    assertThat(p.getArrivesCountry()).contains("London");
    //String price = p.getFlightPrice();
    p.chooseFlight();

    Form i = new Form(driver, 10);

    try {
      TimeUnit.SECONDS.sleep(1l);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    i.fillInputName("Filipe Gonçalves");
    assertThat(i.getText(i.getInputName())).isEqualTo("Filipe Gonçalves");

    i.fillAddress("aveiro");
    assertThat(i.getText(i.getAddress())).isEqualTo("aveiro");

    i.fillCity("Europe");
    assertThat(i.getText(i.getCity())).isEqualTo("Europe");

    i.fillState("Aveiro");
    assertThat(i.getText(i.getState())).isEqualTo("Aveiro");

    i.fillZipCode("3800-510");
    assertThat(i.getText(i.getZipCode())).isEqualTo("3800-510");

    i.fillCreditCardNumber("123456789");
    assertThat(i.getText(i.getCreditCardNumber())).isEqualTo("123456789");

    i.fillNameOnCard("Filipe");
    assertThat(i.getText(i.getNameOnCard())).isEqualTo("Filipe");

    i.buy();
    try {
      TimeUnit.SECONDS.sleep(1l);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertThat(driver.getTitle()).isEqualTo("BlazeDemo Confirmation");
  }
}