package PageObjectPattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LastPage extends Page {
  
  public LastPage(WebDriver driver) {
    super(driver);
  }
  
  @FindBy(xpath = "/html/body/div[2]/div/pre") WebElement element;
  
  public String getTexte(){
    return element.getText();
  }
}