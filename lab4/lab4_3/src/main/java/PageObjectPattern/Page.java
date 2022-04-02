package PageObjectPattern;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Page 
{
    @FindBy(id="username")
    private WebElement user_name;
    
    @FindBy(name="passsword")
    private WebElement user_password;
    
    @FindBy(className="h3")
    private WebElement label;
}
