import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

class WebNavigator implements Runnable {
    private WebDriver driver;
    private DomainList<Domain> domainsToScan;
    private DomainList<Domain> scannedDomains;
    private DomainList<Domain> errorDomains;
    private String notFoundSourceCode;
    private String target;

    WebNavigator() {
        //initialize web driver
        ChromeOptions options = new ChromeOptions();
        if(Main.HEADLESS)
            options.addArguments("--headless");
        options.addArguments("--incognito","--disable-infobars","--disable-extensions");
        driver = new ChromeDriver(options);

        //initialize domains lists
        domainsToScan = new DomainList<>();
        scannedDomains = new DomainList<>();
        errorDomains = new DomainList<>();

        //set target domain address
        target = Main.TARGET;
    }

    @Override
    public void run() {
        getNotFoundPageCode();

        driver.get(target);

        //get hyperlinks from target domain
        List<WebElement> hyperlinks = driver.findElements(By.xpath("//*[@href]"));
        scannedDomains.add(target);
        for (WebElement hyperlink : hyperlinks) {
            domainsToScan.add(new Domain(hyperlink.getAttribute("href"),driver.getCurrentUrl()));
        }

        while (!domainsToScan.isEmpty()){
            scanNextDomain();
        }

        log("no more domains to scan. Printing results");
        printResults();

        //close web driver
        driver.close();
    }

    private void printResults() {
        String results = "RESULTS:\n" +
                "domains scanned: " + scannedDomains.size() + "\n" +
                "invalid hyperlinks: " + errorDomains.size();

        log(results);
    }

    private void scanNextDomain() {
        Domain domainObject = domainsToScan.get(0);
        String domain = domainObject.getDomain();

        driver.get(domain);
        log("scanning " + domain);

        //check if error appeared
        if(driver.getPageSource().equals(notFoundSourceCode)) {
            errorDomains.add(domain);
            return;
        }

        List<WebElement> elements = driver.findElements(By.xpath("//*[@href]"));
        for (WebElement hyperlink : elements) {
            domainsToScan.add(new Domain(hyperlink.getAttribute("href"),driver.getCurrentUrl()));
        }

        domainsToScan.remove(domainObject);
        scannedDomains.add(domainObject);
        log("finished scanning " + domain);
    }

    private void getNotFoundPageCode() {
        driver.get(target + '/' + Math.random());
        notFoundSourceCode = driver.getPageSource();
    }

    WebDriver getDriver() {
        return driver;
    }

    private static void log (String message){
        Logger.log(message,"NAVG");
    }
}
