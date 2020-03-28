public class Main {
    //settings
    static final String TARGET    = "https://www.2020concept.com";
    static final boolean HEADLESS = true;
    static final boolean logToFile = true;
    static final boolean logToConsole = true;
    static final String  LOG_PATH = "log.txt";

    public static void main(String[] args) {
        log("Hyperlink test begin. Target domain: " + TARGET);
        Thread thread = new Thread(new WebNavigator());
        thread.run();
        log("Hyperlink test end");
    }

    private static void log (String message){
        Logger.log(message,"MAIN");
    }
}
