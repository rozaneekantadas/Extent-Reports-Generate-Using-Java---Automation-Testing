package reports;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class ExtentReportGenerate {
	
	@Test
	public void extentTest() throws IOException {
		ExtentReports extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("report/index.html").viewConfigurer().viewOrder().as(new ViewName[] {ViewName.DASHBOARD, ViewName.TEST, ViewName.CATEGORY}).apply();
		ExtentSparkReporter failed_spark = new ExtentSparkReporter("report/failed_tests_index.html").filter().statusFilter().as(new Status[] {Status.FAIL}).apply();
		failed_spark.config().setDocumentTitle("Failed Tests");
		
		
		final File CONF = new File("extent_config.json");
		spark.loadJSONConfig(CONF);
		
//		final File CONF = new File("extent_config.xml");
//		spark.loadXMLConfig(CONF);
		
//		spark.config().setTheme(Theme.DARK);
//		spark.config().setDocumentTitle("MyReport");
		extent.attachReporter(spark, failed_spark);
		
		ExtentTest test = extent.createTest("Login Tests").assignAuthor("Rozanee").assignCategory("Smoke").assignCategory("Regression").assignDevice("Chrome 96");
		test.pass("Login Test Start Successfully");
		test.info("URL is loaded");
		test.info("Values Entered");
		test.pass("Login Test Completed Successfully");
		//Arrays.asList(new String[] {"Selenium", "Appium", "Rest Assured"}).forEach(test::pass);
		test.pass(MarkupHelper.createOrderedList(Arrays.asList(new String[] {"Selenium", "Appium", "Rest Assured"})).getMarkup());
		test.pass(MarkupHelper.createLabel("Login Test Completed Successfully", ExtentColor.GREEN));
		
		ExtentTest test1 = extent.createTest("HomePage Test").assignAuthor("Supto").assignCategory("Regression").assignDevice("Firefox 60");
		test1.pass("Homepage Test Start Successfully");
		test1.info("URL is loaded");
		test1.info("Values Entered");
		test1.fail("Homepage Test failed");
		test1.fail(MarkupHelper.createLabel("Homepage Test failed", ExtentColor.RED));
		
		extent.flush();
		Desktop.getDesktop().browse(new File("report/index.html").toURI());
		Desktop.getDesktop().browse(new File("report/failed_tests_index.html").toURI());
	}
}
