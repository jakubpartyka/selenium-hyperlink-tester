import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

class WebNavigator implements Runnable {
    private WebDriver driver;
    private List<String> discoveredSubdomains;
    private List<String> checkedDomains;

    WebNavigator() {
        //initialize web driver
        ChromeOptions options = new ChromeOptions();
        if(Main.HEADLESS)
            options.addArguments("--headless");
        options.addArguments("--incognito","--disable-infobars","--disable-extensions");
        driver = new ChromeDriver(options);

        //initialize domains lists
        discoveredSubdomains = new DomainList<>();
        checkedDomains = new DomainList<>();
    }

    @Override
    public void run() {


        end();
    }

    WebDriver getDriver() {
        return driver;
    }

    void end() {
        driver.close();
    }

    private static void log (String message){
        Logger.log(message,"NAVG");
    }
}
