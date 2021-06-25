package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class SearchResultsPage {
  private final WebDriver driver;
  
  
//Page Object constructor which passes the driver context forward
  public SearchResultsPage(WebDriver driver) {
      this.driver = driver;
  }
  
  
  By searchResultStat = By.xpath("//div[@id='result-stats']");


  public String returnSearchStat() {
	return driver.findElement(searchResultStat).getText();
  }
  
  public String returnPageTitle() {
		return driver.getTitle();
	  }
  
}
