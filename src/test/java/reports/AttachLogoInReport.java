package reports;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class AttachLogoInReport {
	
	@Test
	public void attachLogo() throws IOException {
		ExtentReports extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("report/index.html");
		spark.loadXMLConfig(new File("extent_config.xml"));
		extent.attachReporter(spark);
		
		ExtentTest test = extent.createTest("First Test");
		test.pass("Test started");
		test.pass("test finished");
		
		extent.flush();
		Desktop.getDesktop().browse(new File("report/index.html").toURI());
	}

}
