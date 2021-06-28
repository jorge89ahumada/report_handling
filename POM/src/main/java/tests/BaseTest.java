package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.jetty.client.api.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.SearchPage;
import pageObjects.SearchResultsPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class BaseTest {
	
    String driverPath = "C:\\browserdrivers\\chromedriver.exe";
    String searchString = "Amazon";
    WebDriver driver;
    SearchPage objSearchPage;
    SearchResultsPage objSearchResultsPage;
    ExtentHtmlReporter htmlReporter;
    ExtentReports Reports;
    ExtentTest logger;

   
    @BeforeTest
    public void setup(){
    	htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"\\src\\test\\Reports\\reports.html");
    	Reports = new ExtentReports();
    	Reports.attachReporter(htmlReporter);
    	System.setProperty("webdriver.chrome.driver", driverPath);      
        driver = new ChromeDriver();
        driver.get("https://www.bing.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    
   @Test(priority=0)
    public void openPageTest() throws InterruptedException
    {
    	logger = Reports.createTest("openPage");
    	objSearchPage = new SearchPage(driver);
    	objSearchResultsPage = objSearchPage.typeSearchItem(searchString);
    	logger.log(Status.PASS, "openPage");
    }
    
   @Test(priority=1)
   public void enterSearchTest()
    {
     	logger = Reports.createTest("enterSearch");
    	System.out.println("Search Result Stat::"+" "+objSearchResultsPage.returnSearchStat());
    	Assert.assertEquals(objSearchResultsPage.returnPageTitle(), searchString);
    	logger.log(Status.PASS, "enterSearch"); 
    }
    
    
    @Test(priority=2)
    public void openResultsTest() 
    {
    	logger = Reports.createTest("openResults");
    	Assert.assertEquals(objSearchResultsPage.clickURL(), searchString + " - Bing");
    	logger.log(Status.PASS, "openResults");
    }
    
    
    

    
    @AfterMethod()
    public void afterTest(ITestResult Result) throws IOException 
    {
    	if(ITestResult.FAILURE==Result.getStatus())	
    	{
    	TakesScreenshot scrsht = (TakesScreenshot)driver;
    	File SrcFile = scrsht.getScreenshotAs(OutputType.FILE);
    	String dest= System.getProperty("user.dir") +"\\src\\test\\Reports\\SCREENSHOT_"+ Result.getMethod().getMethodName()+".PNG";
    	File DestnFile = new File(dest);
    	FileUtils.copyFile(SrcFile, DestnFile);	  	
    	logger.log(Status.FAIL, Result.getThrowable());
    	logger.log(Status.FAIL, "Snapshot below: " + logger.addScreenCaptureFromPath(dest));
    }
    }
    
    
    @AfterTest
    public void tearDown() throws InterruptedException
    {
    	Reports.flush();
    	driver.quit();
    }
    
}
