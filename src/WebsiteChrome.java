import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;



public class WebsiteChrome {
    WebDriver driverChrome;
    ExtentReports report;
    ExtentTest test1, test2, test3;
    ExtentTest test4, test5, test6;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "D:\\selenium files\\chromedriver2\\chromedriver-win64\\chromedriver.exe");
        driverChrome = new ChromeDriver();
        driverChrome.get("http://the-internet.herokuapp.com/");

        Thread.sleep(2000);

        driverChrome.manage().window().maximize();

        report = new ExtentReports("TestReport.html");
        test1 = report.startTest("Cross Browser Testing");
        test1.log(LogStatus.PASS, "Chrome Browser Opened");

        wait = new WebDriverWait(driverChrome, Duration.ofSeconds(10)); // Initialize WebDriverWait with a timeout of 10 seconds
    }

    @AfterClass
    public void tearDown() {
        report.flush();
        driverChrome.quit();
    }

    @Test(priority = 1 , alwaysRun = true)
    public void addElementTest() throws InterruptedException {

        test2 = report.startTest("Add/Remove Element Test Case");

        driverChrome.findElement(By.xpath("//*[@id=\"content\"]/ul/li[2]/a")).click();
        Thread.sleep(2000);
        WebElement addButton = driverChrome.findElement(By.xpath("//*[@id=\"content\"]/div/button"));
        Thread.sleep(2000);
        if (addButton.isDisplayed() && addButton.isEnabled()) {
            test2.log(LogStatus.INFO, "Add Button is Enabled and Displayed."  );
        } else {
            test2.log(LogStatus.FAIL, "Add Button is Disabled and Not Displayed");
        }
        Thread.sleep(2000);

        driverChrome.findElement(By.xpath("//*[@id=\"content\"]/div/button")).click();
        Thread.sleep(2000);

        WebElement deleteButton = driverChrome.findElement(By.xpath("//*[@id=\"elements\"]/button"));
        if (deleteButton.isDisplayed() && deleteButton.isEnabled()) {
            test1.log(LogStatus.INFO, "Delete Button is Enabled and Displayed");
        } else {
            test1.log(LogStatus.FAIL, "Delete Button is Disabled and Not Displayed");
        }
        driverChrome.get("http://the-internet.herokuapp.com/");
        report.endTest(test1);

    }

    @Test(priority = 2 , alwaysRun = true)
    public void loginPageTest() throws InterruptedException, NoSuchElementException {

        test3 = report.startTest("Login Page Test Case");

        try {

            driverChrome.findElement(By.cssSelector("a[href='/login']"));

            Thread.sleep(2000);

            WebElement username = driverChrome.findElement(By.id("username"));
            WebElement password = driverChrome.findElement(By.id("password"));

            if (username.isDisplayed() && password.isDisplayed()) {
                test3.log(LogStatus.INFO, "Username and Password are Displayed");
            } else {
                test3.log(LogStatus.INFO, "Username and Password are not Displayed.");
            }

            if (username.isEnabled() && password.isEnabled()) {
                test3.log(LogStatus.INFO, "Username and Password are Enabled.");
            } else {
                test3.log(LogStatus.FAIL, "Username and Password are not Enabled");
            }
            username.sendKeys("tomsmith");

            Thread.sleep(2000);
            password.sendKeys("SuperSecretPassword!");
            Thread.sleep(2000);

            driverChrome.findElement(By.xpath("//*[@id=\"login\"]/button/i")).click();
            test3.log(LogStatus.INFO, "Secure Page Opened");
            Thread.sleep(3000);

            driverChrome.findElement(By.xpath("//*[@id=\"content\"]/div/a/i")).click();
            driverChrome.get("http://the-internet.herokuapp.com/");
        } catch (NoSuchElementException e) {
            System.out.println("Element not Found");
            test3.log(LogStatus.INFO, "Locator not found");
        }

        driverChrome.get("http://the-internet.herokuapp.com/");
        report.endTest(test3);
    }

    @Test(priority = 3 , alwaysRun = true)
    public void dropDown() throws InterruptedException {
        test4 = report.startTest("Dropdown Element Test Case");
        driverChrome.findElement(By.cssSelector("#content > ul > li:nth-child(11) > a")).click();
        WebElement a = driverChrome.findElement(By.xpath("//*[@id=\"dropdown\"]"));
        Select select = new Select(a);

        if (select.isMultiple()) {
            test4.log(LogStatus.PASS, "Multiple Values are Present");
        } else {
            test4.log(LogStatus.FAIL, "Multiple Values are not Present");
        }

        select.selectByVisibleText("Option 1");

        String selectedOption = select.getFirstSelectedOption().getText();
        Assert.assertEquals(selectedOption, "Option 1", "Selected option is not as expected");
        Thread.sleep(3000);

        select.selectByValue("2");
        String byvalue = select.getFirstSelectedOption().getText();
        Assert.assertEquals(byvalue, "Option 2", "Selected option is not as expected");
        Thread.sleep(3000);
        test4.log(LogStatus.PASS, "Selected Value is Correct");

        driverChrome.get("http://the-internet.herokuapp.com/");
        report.endTest(test4);

    }
    @Test(priority = 4 , alwaysRun = true)
    public void dynamicContent() throws InterruptedException, NoSuchElementException {

        test5 = report.startTest("Dynamic Content Test Case");
        driverChrome.findElement(By.xpath("//*[@id=\"content\"]/ul/li[12]/a")).click();
        Thread.sleep(5000);

        test5.log(LogStatus.PASS, "Test Case Passed");

        driverChrome.get("http://the-internet.herokuapp.com/");
        report.endTest(test5);

    }
    @Test(priority = 5 , alwaysRun = true)
    public void keyPresses() throws InterruptedException, NoSuchElementException {

        test5 = report.startTest("Key Presses Test Case");

        driverChrome.findElement(By.linkText("Key Presses")).click();
        driverChrome.findElement(By.id("target")).sendKeys(Keys.TAB);

        String Tab = driverChrome.findElement(By.id("result")).getText();
        if (Tab == "You entered: TAB") {
            test5.log(LogStatus.PASS, "Passed");
        } else {
            test5.log(LogStatus.FAIL, "Not passed");
        }
        driverChrome.findElement(By.id("target")).sendKeys(Keys.SHIFT);
        String shiftKey = driverChrome.findElement(By.id("result")).getText();

        if (shiftKey == "You entered: SHIFT") {
            test5.log(LogStatus.PASS, "Passed");
        }
        else
        {
            test5.log(LogStatus.FAIL, "Not passed");
        }
        String spaceBar = driverChrome.findElement(By.id("result")).getText();
        if(spaceBar == "You Entered: SPACEBAR"){
            test5.log(LogStatus.PASS , "Passed");
        }
        else{
            test5.log(LogStatus.FAIL , "Failed");
        }

        driverChrome.get("http://the-internet.herokuapp.com/");
        report.endTest(test5);
    }

    @Test(priority = 6 , alwaysRun = true)
    public void fileDownload() throws InterruptedException{

        test6 = report.startTest("File Download Test Case");

        driverChrome.findElement(By.linkText("File Download")).click();
        driverChrome.findElement(By.xpath("//*[@id=\"content\"]/div/a[6]")).click();
        Path textFile = Paths.get("C:\\Users\\aksha\\Downloads\\Assignment.txt");
        Thread.sleep(5000);

        if(Files.exists(textFile)){
            System.out.println("Text File is Found in the Download Directory");
            test6.log(LogStatus.PASS , "Text File is Downloaded");
        }
        else{
            System.out.println("Text File is not Found in the Download Directory");
            test6.log(LogStatus.FAIL , "Text File is not Downloaded");
        }

        if(textFile.endsWith(".txt")){
            System.out.println("The File has the correct Extension");
            test6.log(LogStatus.PASS , "Text File has the Correct Extension");
        }
        else{
            System.out.println("Wrong Extension");
            test6.log(LogStatus.FAIL , "Text File has the wrong Extension");
        }

        File fileName = new File("C:\\Users\\aksha\\Downloads\\test3.txt");
        long fileLength = fileName.length();
        System.out.println("The Size of Txt File is : "+ fileLength);

        if(fileName.length()>=0){
            test6.log(LogStatus.PASS , "The File Is Containing Data.");
        }
        else{
            test6.log(LogStatus.FAIL , "The File Is Not Containing Data.");
        }

        Path textFile2 = Paths.get("C:\\Users\\aksha\\Downloads\\orangehrm.json");

        if(Files.exists(textFile2)){
            System.out.println("JSON File is Present.");
            test6.log(LogStatus.PASS , "JSON Exists in the Directory");
        }
        else{
            System.out.println("JSON File is not present is the Directory");
            test6.log(LogStatus.FAIL , "JSON doesn't Exist.");
        }
        if(textFile2.endsWith(".json")){
            System.out.println("The File has the Correct Extension");
            test6.log(LogStatus.PASS , "The File has the Correct Extension");
        }
        else{
            System.out.println("The File's Extension is Wrong.");
            test6.log(LogStatus.FAIL , "The File's Extension is Wrong");
        }
        File fileName2 = new File("C:\\Users\\aksha\\Downloads\\orangehrm.json");
        long fileLength2 = fileName.length();

        System.out.println("The Size of Txt File is : "+ fileLength2);

        if(fileName2.length()>=0){
            test6.log(LogStatus.PASS , "The File Is Containing Data.");
        }
        else{
            test6.log(LogStatus.FAIL , "The File Is Not Containing Data.");
        }

        driverChrome.get("http://the-internet.herokuapp.com/");
    }
}