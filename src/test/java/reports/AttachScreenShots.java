package reports;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AttachScreenShots {
	
	public static ExtentReports extent;
	public static WebDriver driver;
	
	@BeforeSuite
	public void setUp() throws IOException {
		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("report/index.html");
		spark.loadXMLConfig(new File("extent_config.xml"));
		extent.attachReporter(spark);
	}
	
	@AfterSuite
	public void tearDown() throws IOException {
		extent.flush();
		Desktop.getDesktop().browse(new File("report/index.html").toURI());
	}
	
	@Test
	public void attachScreenShot() throws IOException {
		
		ExtentTest test = extent.createTest("First Test");
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		test.pass("Browser opened");
		driver.get("https://google.com");
		driver.manage().window().maximize();
		driver.findElement(By.name("q")).sendKeys("Automation", Keys.ENTER);
		test.pass("Value entered", MediaEntityBuilder.createScreenCaptureFromBase64String(getScreenshotAsBase64()).build());
		
		test.pass("test finished");
		
	}
	
	public String getScreenshotPath() throws IOException {
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/Screenshots/image.png";
		FileUtils.copyFile(source, new File(path));
		return path;
	}
	
	public String getScreenshotAsBase64() throws IOException {
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/Screenshots/image.png";
		FileUtils.copyFile(source, new File(path));
		byte[] imageBytes = IOUtils.toByteArray(new FileInputStream(path));
		return Base64.getEncoder().encodeToString(imageBytes);
	}
	

}
