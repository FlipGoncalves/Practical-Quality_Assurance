package PageObjectPattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Form extends Page {
  public Form(WebDriver driver) {
    super(driver);
  }
  public Form(WebDriver driver, int timeout) {
    super(driver);
    setTimeoutSec(timeout);
  }

  @FindBy(id = "inputName")
  WebElement name;
  @FindBy(id = "address")
  WebElement address;
  @FindBy(id = "city")
  WebElement city;
  @FindBy(id = "state")
  WebElement state;
  @FindBy(id = "zipCode")
  WebElement zipCode;
  @FindBy(id = "creditCardNumber")
  WebElement creditcard;
  @FindBy(id = "nameOnCard")
  WebElement namecard;
  @FindBy(id = "rememberMe")
  WebElement rememberme;
  @FindBy(css= ".btn-primary")
  WebElement confirm;

  public WebElement getInputName() {
    return name;
  }

  public WebElement getAddress() {
    return address;
  }

  public WebElement getCity() {
    return city;
  }

  public WebElement getState() {
    return state;
  }

  public WebElement getZipCode() {
    return zipCode;
  }

  public WebElement getCreditCardNumber() {
    return creditcard;
  }

  public WebElement getNameOnCard() {
    return namecard;
  }

  public WebElement getRememberMe() {
    return rememberme;
  }

  public WebElement getConfirmPurchaceBtn() {
    return confirm;
  }

  public void fillInputName( String inputName ) {
    this.name.sendKeys( inputName );
  }

  public void fillAddress( String address ) {
    this.address.sendKeys( address );
  }

  public void fillCity( String city ) {
    this.city.sendKeys(city);
  }

  public void fillState( String state ) {
    this.state.sendKeys(state);
  }

  public void fillZipCode( String zipCode ) {
    this.zipCode.sendKeys(zipCode);
  }

  public void fillCreditCardNumber( String creditCardNumber ) {
    this.creditcard.sendKeys(creditCardNumber);
  }

  public void fillNameOnCard( String nameOnCard ) {
    this.namecard.sendKeys(nameOnCard);
  }
  public String getText(WebElement e){
    return e.getAttribute( "value" );
  }
  public void setRememberMeBtn(){
    rememberme.click();
  }
  public void buy(){
    confirm.click();
  }
}

