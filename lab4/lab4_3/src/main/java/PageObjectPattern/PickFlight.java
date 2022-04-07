package PageObjectPattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PickFlight extends Page {
  
  
  @FindBy(xpath = "/html/body/div[2]/table/tbody/tr[1]/td[1]/input")
  WebElement flight;
  @FindBy(xpath = "/html/body/div[2]/table/tbody/tr[1]/td[6]")
  WebElement price;
  
  @FindBy(xpath = "/html/body/div[2]/table/thead/tr/th[4]")
  WebElement origin;
  @FindBy(xpath = "/html/body/div[2]/table/thead/tr/th[5]")
  WebElement destination;
  
  public PickFlight(WebDriver driver) {
    super(driver);
  }
  public PickFlight(WebDriver driver, int timeout) {
    super(driver);
    setTimeoutSec(timeout);
  }
  
  public void chooseFlight(){
    flight.click();
  }
  
  public String getDepartCountry(){
    return this.origin.getText();
  }
  public String getArrivesCountry(){
    return this.destination.getText();
  }
  
  public String getFlightPrice(){
    return this.price.getText();
  }
}
