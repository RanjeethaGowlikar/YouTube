package demo;

//import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
//import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    static ChromeDriver driver;
    public static WebDriverWait wait;

    @BeforeSuite
    public void createdriver() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).browserVersion("125.0.6422.61").setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
   

    public void TestCase01() throws InterruptedException {
        driver.get("https://www.youtube.com/");

        String currURL = driver.getCurrentUrl();
        Assert.assertEquals(currURL, "https://www.youtube.com/", "URL is incorrect");

        WebElement aboutSearch = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'About')]")));
        SelenuimWrapper.advanceSearch(aboutSearch);

        // Verify that the URL now contains "about"
        boolean AboutPage = wait.until(ExpectedConditions.urlContains("about"));
        Assert.assertTrue(AboutPage, "not on About page");
        
        // wait until youtbe text is visible

        WebElement AboutMsg1 = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'About YouTube')]")));

        WebElement AboutMsg2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//p[contains(text(), ' Our mission is to give everyone a voice and show them the world.')]")));

        String AboutText1 = AboutMsg1.getText();
        System.out.println("About Page Message" + AboutText1);

        String AboutText2 = AboutMsg2.getText();
        System.out.println("About Page Message" + AboutText2);
    }

    @Test
  
    public void TestCase02() throws InterruptedException {
        
        driver.get("https://www.youtube.com/");

        WebElement MoviesTab = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@title, 'Movies')]")));
        MoviesTab.click();

        wait.until(ExpectedConditions.urlContains("storefront"));
        Thread.sleep(6000);
        
        WebElement TopSelling1 = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Next']")));
      
        Actions actions = new Actions(driver);
            
            //scroll to the extreme right using right arrow
            for (int i = 0; i < 3; i++) {
                System.out.println("clicking next button iteration :"+(i+1));
                actions.moveToElement(TopSelling1).click().perform();
                Thread.sleep(2000);
            }
                
  // initializing the softAssert
  SoftAssert softAssert = new SoftAssert();
        WebElement  markedAMovie = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//p[contains(text(),'A')])[13] ")));
      //  markedA.click();
      boolean isMarkedA = markedAMovie != null;

        WebElement ComedyOrAnimation = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[contains(text(), 'Comedy')])[3]")));
        String comedyorannimation = ComedyOrAnimation.getText();
        boolean isComedyOrAnimation = comedyorannimation.contains("Comedy") || comedyorannimation.contains("Animation");
       
        softAssert.assertTrue(isComedyOrAnimation, "movie is neither comedy nor annimation");

        if (isMarkedA && isComedyOrAnimation) {
                System.out.println("The movie is marked A and  comedy type.");
            } else {
                System.out.println("The movie does not meet the expected criteria.");
            }

        
}

    


    @Test
 
    public void TestCase03() throws InterruptedException {
        
        driver.get("https://www.youtube.com/");
        Thread.sleep(3000);
        WebElement MusicTab = driver.findElement(By.xpath("(//a[contains(@title, 'Music')])[1]"));
        MusicTab.click();
        wait.until(ExpectedConditions.urlContains("channel"));
        Thread.sleep(5000);
        WebElement music1 = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[contains(@aria-hidden,'true')])[18]")));
        music1.click();
        WebElement music2 = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[contains(@aria-hidden,'true')])[18]")));
        music2.click();
        WebElement music3 = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[contains(@aria-hidden,'true')])[18]")));
        music3.click();

        WebElement lastPlaylist = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Bollywood Dance Hitlist')]")));
        String playlistName = lastPlaylist.getText();
        System.out.println("Playlist Name: " + playlistName);
        // initializing the softAssert
        SoftAssert softAssert = new SoftAssert();
        WebElement numberOfTracksElement = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("(//p[@class='style-scope ytd-compact-station-renderer'])[22]")));
        String numberOfTracksText = numberOfTracksElement.getText().split(" ")[0];
        int numberOfTracks = Integer.parseInt(numberOfTracksText);
        System.out.println("no. of tracks :" + numberOfTracks);
        softAssert.assertTrue(numberOfTracks <= 50, "Number is more than 50");
        softAssert.assertAll();
    }

    @Test
    
    public void TestCase04() throws InterruptedException {
        driver.get("https://www.youtube.com/");
        WebElement NewsTab = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//a[contains(@id,'endpoint')])[12]")));
        NewsTab.click();

        wait.until(ExpectedConditions.urlContains("channel"));

        // loop through first three latest news
        int totallikes = 0;
        for (int i = 1; i <= 3; i++) {
                Thread.sleep(5000);
            WebElement LikesElements = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//span[contains(@id,'vote-count-middle')]")));
            WebElement title = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@id,'author-text')]")));
            WebElement body = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id,'body')]")));
            String LikesElementsText = LikesElements.getText();

            String titleText = title.getText();

            String bodyText = body.getText();

            // convert likes to integer
            int likes = 0;
            if (!LikesElementsText.isEmpty()) {
                try {
                    likes = Integer.parseInt(LikesElementsText.replaceAll("[^0-9]", ""));
                } catch (Exception e) {
                    likes = 0;
                }
            }
            // print title, body,likes
            System.out.println("title" + i + ": " + bodyText);
            System.out.println("body" + i + ": " + titleText);
            System.out.println("Likes" + i + ": " + likes);

            // Add the total likes
            totallikes += likes;
            System.out.println("Total likes :" + totallikes);

        }
    }

    @AfterSuite
    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.quit();

    }

}