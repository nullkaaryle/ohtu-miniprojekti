package ohtu;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Stepdefs {

    private WebDriver driver;
    
    @Before
    public void setUp() {
        FirefoxDriverManager.getInstance().setup();
        driver = new FirefoxDriver();
        driver.get("http://127.0.0.1:8080/");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    
    @After
    public void tearDown() {
        driver.quit();
    }
    
    @Given("^add references is selected$")
    public void add_references_is_selected() throws Throwable {    
        
        try {
            WebElement element = driver.findElement(By.id("addButton"));
            element.click();
        } catch (NoSuchElementException e) {
            fail("No such element");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }
    
    @Given("^export BibTex is selected$")
    public void export_bibtex_is_selected() throws Throwable {
        try {
            WebElement element = driver.findElement(By.id("exportButton"));
            element.click();
        } catch (NoSuchElementException e) {
            fail("No such element");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    @When("^valid reference information is entered$")
    public void enter_valid_information() throws Throwable {
        sendKeysToElement("gwt-uid-5", "anni puurunen");
        sendKeysToElement("gwt-uid-7", "otsikko");
        sendKeysToElement("gwt-uid-9", "journal");
        sendKeysToElement("gwt-uid-11", "2015");
        sendKeysToElement("gwt-uid-13", "vol 80");
        WebElement element = driver.findElement(By.id("addRef"));
        element.click();
    }
    
    @When("^invalid reference information is entered$")
    public void enter_invalid_book_information() throws Throwable {
        sendKeysToElement("gwt-uid-5", "anni puurunen");
        sendKeysToElement("gwt-uid-7", "otsikko");
        sendKeysToElement("gwt-uid-9", "journal");
        sendKeysToElement("gwt-uid-11", "2015");
        WebElement element = driver.findElement(By.id("addRef"));
        element.click();
    }

    private void sendKeysToElement(String elementId, String keys) {
        WebElement element = driver.findElement(By.id(elementId));
        element.sendKeys(keys);
    }

    @Then("^a new reference is added$")
    public void new_reference_is_added() throws Throwable {
        Thread.sleep(1000);
        pageHasContent("Reference saved!");
    }
    
    @Then("^reference is not added$")
    public void reference_is_not_added() throws Throwable {
        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("Failed to save a reference"));
    }
    
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }
}
