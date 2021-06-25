package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {
  private final WebDriver driver;
  Actions action;
  WebDriverWait wait;
  
//Page Object constructor which passes the driver context forward
  public SearchPage(WebDriver driver) {
      this.driver = driver;
      action = new Actions(driver);
      wait = new WebDriverWait(driver,30);
      if (!driver.getTitle().equals("Google")) {
          throw new IllegalStateException("This is not the correct page," +" current page is: " + driver.getCurrentUrl());
      }
  }
  
  By searchBox = By.xpath("//input[@name='q']");


  public SearchResultsPage typeSearchItem(String searchItem) throws InterruptedException {
	wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox)).isEnabled();
	driver.findElement(searchBox).sendKeys(searchItem);
	action.sendKeys(Keys.ENTER).build().perform();
	Thread.sleep(5000);
	return new SearchResultsPage(driver); 
  }
  
}