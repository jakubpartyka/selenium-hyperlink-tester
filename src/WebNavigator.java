import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class WebNavigator {
    private WebDriver driver;

    WebNavigator() {
        ChromeOptions options = new ChromeOptions();
        if(Main.HEADLESS)
            options.addArguments("--headless");
        options.addArguments("--incognito");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");


        driver = new ChromeDriver(options);
    }

    WebDriver getDriver() {
        return driver;
    }

    void end() {
        driver.close();
    }

    private static void log (String message){
        System.out.println(message);
    }
}
