package PageObjectPattern;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public class Page {
  
  static final Logger log = Logger.getLogger(Page.class.toString());
  
  WebDriver driver;
  WebDriverWait wait;
  int timeout = 6;
  
  public Page(WebDriver driver) {
    this.driver = driver;
    wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    PageFactory.initElements(driver, this);
  }
  
  public void setTimeoutSec(int timeout) {
    this.timeout = timeout;
  }
  
  public void visit(String url) {
    driver.get(url);
  }
  
  public WebElement find(By e) {
    return driver.findElement(e);
  }
  
  public void click(By e) {
    find(e).click();
  }
  
  public void type(By e, String str) {
    find(e).sendKeys(str);
  }
  
  public boolean isDisplayed(By loc) {
    try {
      wait.until( ExpectedConditions.visibilityOfElementLocated(loc));
    } catch (TimeoutException e) {
      return false;
    }
    return true;
  }
  
}