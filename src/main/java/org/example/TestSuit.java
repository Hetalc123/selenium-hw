package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestSuit {

    protected static WebDriver driver;

    public static void clickOnElement(By by) {
        driver.findElement(by).click();
    }

    public static void typeText(By by, String text) {
        driver.findElement(by).sendKeys(text);
    }

    public static String getTextFromElement(By by) {
        return driver.findElement(by).getText();
    }

    public static long timestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }

    public static long datestamp() {
        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy-hh:mm");
        return sd.hashCode();
    }

    @BeforeMethod
    public static void openBrowser() {
        //open the browser
        driver = new ChromeDriver();
        //open the url
        driver.get("https:/demo.nopcommerce.com/");
        //maximize the window
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @AfterMethod
    public static void closeBrowser() {
        //close the browser
        driver.close();
    }
    @Test
    public static void toVerifyUserShouldBeAbleToRegister() {
        String expectedResult = "Thanks For Registration";
        //click on register button
        clickOnElement(By.className("ico-register"));
        //type the firstname
        typeText(By.id("FirstName"), "MyFirstTest");
        //type the lastname
        typeText(By.id("LastName"), "Automation");
        //type the email
        typeText(By.name("Email"), "myfirstauto" + timestamp() + "@gmail.com");
        //type password
        typeText(By.name("Password"), "auto1234");
        //confirm password
        typeText(By.id("ConfirmPassword"), "auto1234");
        //click on register
        clickOnElement(By.id("register-button"));
        //get text and print
        String actualResult = getTextFromElement(By.xpath("//div[@class='result']"));
        System.out.println("Message on screen is =>" + (actualResult));
        //check if actual result is same as expected
        Assert.assertEquals(actualResult, expectedResult, "Registration not completed");
    }

    @Test
    public static void toVerifyNonRegisteredUserShouldNotBeAbleToVote() {
        String expectedResult = "Non Registered User Cannot Vote";
        //click on good button in community poll
        clickOnElement(By.xpath("//label[@for='pollanswers-2']"));
        //click on vote
        clickOnElement(By.xpath("//button[@class='button-2 vote-poll-button']"));
        //add wait time till print the message
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='poll-vote-error']")));
        //get and print the message
        String actualResul = driver.findElement(By.xpath("//div[@class='poll-vote-error']")).getText();
        System.out.println(actualResul);
        //check if actual result is same as expected
        Assert.assertEquals(actualResul, expectedResult, "Test Fail");
    }

    @Test
    public static void toVerifyProductsInCompareList() {
        String expectedResult = "No Product to Compare";
        //add Item 1 in compare list
        clickOnElement(By.xpath("(//button[@class='button-2 add-to-compare-list-button'])[3]"));
        //add wait time
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"bar-notification\"]/div/p/a")));
        //add another product to compare list
        clickOnElement(By.xpath("(//button[@class='button-2 add-to-compare-list-button'])[4]"));
        //go to compare list
        clickOnElement(By.xpath("//*[@id=\"bar-notification\"]/div/p/a"));
        //Print text of the product 1
        String name1 = getTextFromElement(By.partialLinkText("$25 Virtual Gift Card"));
        System.out.println("First Product => " + name1);
        //Print text of the product 2
        String name2 = getTextFromElement(By.partialLinkText("HTC One M8 Android L 5.0 Lollipop"));
        System.out.println("Second Product => " + name2);
        //clear compar list
        clickOnElement(By.className("clear-list"));
        //print message on page
        String actualResult = getTextFromElement(By.className("no-data"));
        System.out.println("Message is => " + actualResult);
        //check if actual result is same as expected
        Assert.assertEquals(actualResult, expectedResult, "No Product to compare");
    }

    @Test
    public static void toVerifyNonRegisteredUserShouldNotBeAbleToEmailAFriens() {
        String expectedResult = "only Registered user can refer a product to friend";
        //click on product
        clickOnElement(By.xpath("(//button[@class='button-2 product-box-add-to-cart-button'])[2]"));
        //click on send email
        clickOnElement(By.xpath("//button[@class='button-2 email-a-friend-button']"));
        //type friend's email
        typeText(By.className("friend-email"), "friends123@gmail.com");
        //type your email
        typeText(By.className("your-email"), "myemail@gmail.com");
        //click on send email
        clickOnElement(By.name("send-email"));
        //print error message
        String actualResult = getTextFromElement(By.xpath("//div[@class='message-error validation-summary-errors']/ul/li"));
        System.out.println("Error message is: " + actualResult);
        //check if actual result is same as expected
        Assert.assertEquals(actualResult, expectedResult, "Email sent to friend");

    }

    @Test
    public static void toVerifyProductInShoppingCart() {
        String expectedResult = "Leica T-Mirrorless Digital Camera";
        //go to category electronics
        clickOnElement(By.xpath("(//a[@title='Show products in category Electronics'])[1]"));
        //click on camera and photo
        clickOnElement(By.xpath("(//a[@title='Show products in category Camera & photo'])[1]"));
        //click on add to art
        clickOnElement(By.xpath("(//button[@class='button-2 product-box-add-to-cart-button'])[2]"));
        //print name of product
        String name1 = getTextFromElement(By.partialLinkText("Leica T Mirrorless Digital Camera"));
        System.out.println("Product added in cart is => " + name1);
        //go to shopping cart
        clickOnElement(By.className("cart-label"));
        //print product name which is in cart
        String actualResult = getTextFromElement(By.className("product-name"));
        System.out.println("Product in Cart is => " + actualResult);
        //check if actual result is same as expected
        Assert.assertEquals(actualResult, expectedResult, "Same Product in cart");
    }

    @Test
    public static void toVerifyRegisteredUserShouldBeAbleToReferAProductToAFriend() {
        String expectedResult = "Your message has been sent";
        //click on register button
        clickOnElement(By.className("ico-register"));
        //type the firstname
        typeText(By.id("FirstName"), "MyFirstTest");
        //type the lastname
        typeText(By.id("LastName"), "Automation");
        //type the email
        typeText(By.name("Email"), "myfirstauto@gmail.com");
        //type password
        typeText(By.name("Password"), "auto1234");
        //confirm password
        typeText(By.id("ConfirmPassword"), "auto1234");
        //click on register
        clickOnElement(By.id("register-button"));
        //click on login
        clickOnElement(By.className("ico-login"));
        //type email
        typeText(By.xpath("//input[@name='Email']"), "myfirstauto@gmail.com");
        //type password
        typeText(By.xpath("//input[@class='password']"), "auto1234");
        //click on login
        clickOnElement(By.xpath("//button[@class='button-1 login-button']"));
        //go to homepage
        clickOnElement(By.xpath("//img[@alt='nopCommerce demo store']"));
        //Open a Product
        clickOnElement(By.xpath("//a[@title='Show details for Apple MacBook Pro 13-inch']"));
        //click on email a friend
        clickOnElement(By.xpath("//button[@class='button-2 email-a-friend-button']"));
        //type friend's email
        typeText(By.xpath("//input[@class='friend-email']"), "hh123@gmail.com");
        //clear emai field
        WebElement email = driver.findElement(By.xpath("//input[@class='your-email']"));
        email.clear();
        //type email
        typeText(By.xpath("//input[@class='your-email']"), "myfirstauto@gmail.com");
        clickOnElement(By.xpath("(//button[@type='submit'])[2]"));
        //Get the message
        String actualResult = getTextFromElement(By.xpath("//div[@class='result']"));
        System.out.println(actualResult);
        //check if actual result is same as expected
        Assert.assertEquals(actualResult, expectedResult, "Refered a friend");
    }

    @Test
    public static void toVerifyRegisteredUserShouldBeAbleToVoteSuccesfully() {
        String expectedResult = "18 vote(s)";
        //click on register button
        clickOnElement(By.className("ico-register"));
        //type the firstname
        typeText(By.id("FirstName"), "MyFirstTest");
        //type the lastname
        typeText(By.id("LastName"), "Automation");
        //type the email
        typeText(By.name("Email"), "myfirst" + datestamp() + "auto@gmail.com");
        //type password
        typeText(By.name("Password"), "auto1234");
        //confirm password
        typeText(By.id("ConfirmPassword"), "auto1234");
        //click on register
        clickOnElement(By.id("register-button"));
        //click on login
        clickOnElement(By.className("ico-login"));
        //type email
        typeText(By.xpath("//input[@name='Email']"), "myfirst" + datestamp() + "auto@gmail.com");
        //type password
        typeText(By.xpath("//input[@class='password']"), "auto1234");
        //click on login
        clickOnElement(By.xpath("//button[@class='button-1 login-button']"));
        //go to homepage
        clickOnElement(By.xpath("//img[@alt='nopCommerce demo store']"));
        //click on good button in community poll
        clickOnElement(By.xpath("//label[@for='pollanswers-2']"));
        //click on vote
        clickOnElement(By.xpath("//button[@class='button-2 vote-poll-button']"));
        String actualResult = getTextFromElement(By.className("poll-total-votes"));
        System.out.println(actualResult);
        //check if actual result is same as expected
        Assert.assertEquals(actualResult, expectedResult, "Total votes");


    }

}
