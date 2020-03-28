import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

class WebNavigator implements Runnable {
    private WebDriver driver;
    private DomainList<Domain> domainsToScan;
    private static DomainList<Domain> scannedDomains;
    private DomainList<Domain> errorDomains;
    private String notFoundSourceCode;
    private Domain target;

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
        target = new Domain(Main.TARGET,Main.TARGET);
    }

    @Override
    public void run() {
        getNotFoundPageCode();

        driver.get(target.getDomain());

        //get hyperlinks from target domain
        List<WebElement> hyperlinks = driver.findElements(By.xpath("//*[@href]"));
        scannedDomains.add(target);

        //find links on main page
        for (WebElement hyperlink : hyperlinks) {
            Domain domain = new Domain(hyperlink.getAttribute("href"),driver.getCurrentUrl());
            if(!scannedDomains.contains(domain))
                domainsToScan.add(domain);
        }

        while (!domainsToScan.isEmpty()){
            scanNextDomain();
        }

        log("no more domains to scan. Printing results");
        printResults();

        //close web driver
        driver.close();
    }

    private void scanNextDomain() {
        Domain domainObject = domainsToScan.get(0);
        String domain = domainObject.getDomain();

        //check if domain already scanned
        if(scannedDomains.contains(domainObject)){
            domainsToScan.remove(domainObject);
            return;
        }

        //go to domain
        driver.get(domain);
        log("scanning " + domain);

        //check if 404 error occurred
        if(driver.getPageSource().equals(notFoundSourceCode)) {
            errorDomains.add(domainObject);
            domainsToScan.remove(domainObject);
            scannedDomains.add(domainObject);
            return;
        }

        //locate next links
        List<WebElement> elements = driver.findElements(By.xpath("//*[@href]"));
        for (WebElement hyperlink : elements) {
            Domain domainToScan = new Domain(hyperlink.getAttribute("href"),driver.getCurrentUrl());
            if(!scannedDomains.contains(domainToScan))
                domainsToScan.add(domainToScan);
        }

        domainsToScan.remove(domainObject);
        scannedDomains.add(domainObject);
    }

    private void getNotFoundPageCode() {
        driver.get(target.getDomain() + '/' + Math.random());
        notFoundSourceCode = driver.getPageSource();
    }

    private void printResults() {
        StringBuilder results = new StringBuilder("RESULTS:\n" +
                "domains scanned: " + scannedDomains.size() + "\n" +
                "invalid hyperlinks: " + errorDomains.size());

        if(errorDomains.size() > 0)
            results.append("\n\nfollowing hyperlinks are leading to 404 not found page:\n");

        for (Domain errorDomain : errorDomains) {
            results.append("INVALIND LINK: ").append(errorDomain.getDomain()).append("\nSOURCE: ").append(errorDomain.getSource()).append("\n\n");
        }

        log(results.toString());
    }

    private static void log (String message){
        Logger.log(message,"NAVG");
    }
}
