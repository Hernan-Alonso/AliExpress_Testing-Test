package hernan.stepFiles;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
/**
 * @class iphoneSearch
 */
public class iphoneSearch {
    WebDriver driver;

    /**
     * Before statement
     * @function void
     * sets chromedriver.exe file for use and initialize chrome driver.
     */
    @Before
    public void setup(){
        String path = System.getProperty("user.dir");
        System.out.println(path);
        System.setProperty("webdriver.chrome.driver", path+"\\resources\\chromedriver.exe");
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
    }
    /**
     * After statement
     * @function void
     * whenever driver is not null
     * Delete all cookies stored
     * Quits driver
     * @throws InterruptedException
     * Sets driver to null
     */
    @After
    public void tearDown() throws InterruptedException{
        if(this.driver != null){
            this.driver.manage().deleteAllCookies();
            this.driver.quit();
            this.driver = null;
        }
    }
    /**
     * Given Statement
     * @function void
     * @throws Throwable
     * Selenium WebDriver opens aliexpress.com website
     */
    @Given("^User navigates to Aliexpress website$")
    public void user_navigates_to_Aliexpress_webstie() throws Throwable{
        driver.get("http://aliexpress.com");
    }
    /**
     * And statement
     * @function void
     * @throws Throwable
     * Checks for popUp modal and close it. if not, test continues
     * Gets by xpath, searchbar and types iphone
     */
    @And("^User types in the searchbar iPhone$")
    public void user_types_in_the_searchbar_iphone() throws Throwable{
        Thread.sleep(4000);
        try {
            driver.findElement(By.xpath("//a[contains(@class,'close-layer')]")).click();
        } catch (Exception e){
            
        }
        WebElement searchbar = driver.findElement(By.id("search-key"));
        searchbar.sendKeys("iphone");
    }
    /**
     * When statement
     * @function void
     * @throws Throwable
     * Clicks in the search button
     */
    @When("^User clicks on the search button$")
    public void user_clicks_on_the_search_button() throws Throwable{
        Thread.sleep(2000);
        driver.findElement(By.className("search-button")).click();
    }
    /**
     * Then statement
     * @function void
     * @throws Throwable
     * Gets by cssSelector span of searching value.
     * Checks for if it's content is displayed.
     */
    @Then("^User should see iPhone search result page$")
    public void user_should_see_iPhone_search_result_page() throws Throwable{
        Thread.sleep(4000);
        try {
            driver.findElement(By.cssSelector(".activated.next-breadcrumb-text>span>span")).isDisplayed();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    /***
     * When statement
     * @function void
     * @throws Throwable
     * Gets by xpath. Check if exist popUp modal
     * if is displayed. Close it. if not continues
     * WebElement footer
     * ScrollIntoView
     * Waits for element "button Next" to appear
     * Gets element by cssSelector,
     * Moves towards it, if it's displayed, clicks on it
     */
    @When("^User click in the Next button$")
    public void user_click_in_the_next_button() throws Throwable{
        Thread.sleep(4000);
        try{
            WebElement modal = driver.findElement(By.xpath("//a[contains(@class,'next-dialog-close')]"));
            if(modal.isDisplayed())
                modal.click();
        }catch(Exception e){

        }
        WebElement footer = driver.findElement(By.xpath("//div[contains(@class,'site-footer')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", footer);
        Thread.sleep(500); 
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[text()='Next']")));
        WebElement nextButton = driver.findElement(By.xpath("//*[text()='Next']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(nextButton);
        actions.perform();
        if(nextButton.isDisplayed()){
            nextButton.click();
        }
    }
    /***
     * Then statement
     * @function void
     * @throws Throwable
     * String page, gets element by xpath. Checks if current page is "2"
     * If not @throws Exception
     */
    @Then("^User should see second page of iPhone list$")
    public void user_should_see_second_page_of_iphone_list() throws Throwable{
        String page = driver.findElement(By.xpath("//button[contains(@class, 'next-current')]")).getAttribute("innerHTML");
        if(page.compareTo("2") != 0){
            throw new Exception("User is not in the second page of list");
        }
    }
    /***
     * And statement
     * @function void
     * @throws Throwable
     * @Try get element by cssSelector with it's attribute
     * @String[] strItems. value attribute split by " "
     * @int itemsSold number value of @array
     * if conditional. Check if itemSold is bigger than 0
     * else @throws Exception
     */
    @And("^Second ad should have sold an item$")
    public void second_ad_should_have_sold_an_item() throws Throwable{
        Thread.sleep(2000);
        try{
            String soldItems = driver.findElement(By.cssSelector("[product-index='1'] .sale-value-link")).getAttribute("innerHTML");
            String[] strItems = soldItems.split(" ");
            int itemsSold = Integer.parseInt(strItems[0]);
            if(itemsSold > 0){
                System.out.print("We got items Sold! there are "+ itemsSold + " items sold in this Ad");
            }
        }catch(Exception e){
            throw new Exception("This Ad has not yet sell an item");
        } 
    }

}