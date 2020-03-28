import java.util.List;

public class Main {
    private static final String TARGET    = "https://www.2020concept.com";
    static final boolean HEADLESS = true;
    private static List<String> discoveredSubdomains = new DomainList<>();
    private static List<String> checkedDomains = new DomainList<>();

    public static void main(String[] args) {
        WebNavigator navigator = new WebNavigator();
        navigator.getDriver().get(TARGET);


        navigator.end();
    }

    private static void log (String message){
        System.out.println(message);
    }
}
