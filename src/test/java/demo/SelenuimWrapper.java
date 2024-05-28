package demo;

import org.openqa.selenium.WebElement;

public class SelenuimWrapper {
    public static void advanceSearch(WebElement About) throws InterruptedException{
        About.click();
        Thread.sleep(5000);
    }

    
}
