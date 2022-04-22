package com.wolvesres.selenium.test_login_website;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestLoginLMS {
	public WebDriver driver;
	public UIMap uimap;
	public UIMap datafile;
	public String workingDir;
	// Declare An Excel Work Book
	HSSFWorkbook workbook;
	// Declare An Excel Work Sheet
	HSSFSheet sheet;
	// Declare A Map Object To Hold TestNG Results
	Map<String, Object[]> TestNGResults;

	@Test(description = "Opens the TestNG Demo Website for Login Test", priority = 1)
	public void LaunchWebsite() throws Exception {
		try {
			driver.get("http://dn-lms.poly.edu.vn/login.php");
			driver.manage().window().maximize();
			TestNGResults.put("2", new Object[] { 1d, "Navigate to demo website", "Site gets opened", "Pass" });
		} catch (Exception e) {
			TestNGResults.put("2", new Object[] { 1d, "Navigate to demo website", "Site gets opened", "Fail" });
			AssertJUnit.assertTrue(false);
			e.printStackTrace();
		}
	}

	@Test(description = "Fill the Login button", priority = 2)
	public void FillLoginButton() throws Exception {
		try {
			// Get the username element
			WebElement buttonsign = driver.findElement(uimap.getLocator("Button_sign"));
			buttonsign.click();
			Thread.sleep(1000);

			TestNGResults.put("3", new Object[] { 2d, "Fill Login form data button", "Login success", "Pass" });

		} catch (Exception e) {
			TestNGResults.put("3", new Object[] { 2d, "Fill Login form data button", "Login success", "Pass" });
			AssertJUnit.assertTrue(false);
			e.printStackTrace();
		}
	}

	@Test(description = "Fill the Login Details", priority = 3)
	public void FillLoginDetails() throws Exception {
		try {
			// Get the username element
			WebElement username = driver.findElement(uimap.getLocator("Username_field"));
			username.sendKeys(datafile.getData("username"));

			WebElement next = driver.findElement(uimap.getLocator("Next_button"));
			next.click();

			Thread.sleep(1000);

			// Get the password element
			WebElement password = driver.findElement(uimap.getLocator("Password_field"));
			password.sendKeys(datafile.getData("password"));

			WebElement nexttwo = driver.findElement(uimap.getLocator("Next_button_two"));
			nexttwo.click();

			Thread.sleep(1000);

			TestNGResults.put("4", new Object[] { 2d, "Fill Login form data (Username/Password)",
					"Login details gets filled", "Pass" });

		} catch (Exception e) {
			TestNGResults.put("4",
					new Object[] { 2d, "Fill Login form data (Username/Password)", "Login form gets filled", "Fail" });
			AssertJUnit.assertTrue(false);
			e.printStackTrace();
		}
	}

	@BeforeClass(alwaysRun = true)
	public void suiteSetUp() {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet("TestNG Result Sumary");
		TestNGResults = new LinkedHashMap<String, Object[]>();
		TestNGResults.put("1", new Object[] { "Test steep No", "Action", "Expected Output", "Actual Ouput" });
		try {
			workingDir = System.getProperty("user.dir");
			datafile = new UIMap(workingDir + "\\Resources\\datafile.properties");
			uimap = new UIMap(workingDir + "\\Resources\\locator.properties");
			System.setProperty("webdriver.chrome.driver", workingDir + "\\libs\\chromedriver\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("cant start the Chrome Web Driver", e);
		}
	}
}
