package PageObjectPattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class Travel extends Page {
  
  @FindBy(xpath = "/html/body/div[3]/form/select[1]")
  WebElement from;
  
  @FindBy(xpath = "/html/body/div[3]/form/select[2]")
  WebElement to;
  
  @FindBy(css = ".btn-primary")
  WebElement confirm;
  
  public Travel(WebDriver driver) {
    super(driver);
  }
  
  public Travel(WebDriver driver, int timeout) {
    super(driver);
    setTimeoutSec(timeout);
  }

  public void chooseFromPort(int i) {
    Select drop = new Select((WebElement) from);
    drop.selectByIndex(i);
  }
  
  public void chooseToPort(int i) {
    Select drop = new Select((WebElement) to);
    drop.selectByIndex(i);
  }
  
  public void confirmFlight() {
    confirm.click();
  }
  
}
