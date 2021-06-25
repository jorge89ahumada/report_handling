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
    String searchString = "Robert Rodriguez";
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
        driver.get("https://www.google.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    
    @Test(priority=0)
    public void testOne() throws InterruptedException
    {
    	logger = Reports.createTest("testOne");
    	objSearchPage = new SearchPage(driver);
    	objSearchResultsPage = objSearchPage.typeSearchItem(searchString);
    	logger.log(Status.PASS, "testOne");
    }
    
    @Test(priority=1)
    public void testTwo()
    {
    	logger = Reports.createTest("testTwo");
    	System.out.println("Search Result Stat::"+" "+objSearchResultsPage.returnSearchStat());
    	Assert.assertEquals(objSearchResultsPage.returnPageTitle(), searchString);
    	logger.log(Status.PASS, "testTwo");
    }
    
    
    @Test(priority=2)
    public void testThree()
    {
    	logger = Reports.createTest("testJorge");
    	Assert.assertEquals(objSearchResultsPage.returnPageTitle(), searchString + " - Google Searchh");
    	logger.log(Status.PASS, "testJorge");
    }
    
    
    
//    @Test(priority=0)
//    public void testOne() throws InterruptedException, IOException
//    {
//    	String SheetName="Sheet1";
//    	File file = new File("C:\\Users\\raunaq.nischal\\eclipse-workspace\\POM\\src\\main\\java\\SearchTerm.xlsx");
//    	FileInputStream ip = new FileInputStream(file);
//    	Workbook gwb= new XSSFWorkbook(ip);
//    	Sheet gsheet=gwb.getSheet(SheetName);
//    	int rowCount = gsheet.getLastRowNum()-gsheet.getFirstRowNum();
//    	for(int i=1;i<rowCount+1;i++)
//    	{
//    	Row row = gsheet.getRow(i);	
//    	int j=1;
//    	String Searchitem =row.getCell(j).getStringCellValue();
//    	
//    	objSearchPage = new SearchPage(driver);
//    	objSearchPage.typeSearchItem(Searchitem);
//    	objSearchResultsPage = new SearchResultsPage(driver);
//    	System.out.println("Search Result Stat::"+" "+objSearchResultsPage.returnSearchStat());
//    	driver.navigate().back();
//    	}
//    }
    
    
    
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
