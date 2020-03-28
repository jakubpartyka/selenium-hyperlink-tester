import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
class Logger {
    
    static void log(String message, String identifier){
        if(Main.logToFile){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("[DD-MM-YYYY-HH:mm:ss]");
                String ts = sdf.format(Calendar.getInstance().getTime());
                FileWriter fileWriter = new FileWriter(Main.LOG_PATH,true);
                fileWriter.write(ts + "[" + identifier + "]:" + message + "\n");
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("an error occurred while logging to file...");
                e.printStackTrace();
                try {
                    FileWriter fw = new FileWriter(new File("/errorDump.txt"),true);
                    fw.write("an error occurred: " + e.getMessage());
                } catch (IOException ignored) {}
            }
        }
        if(Main.logToConsole)
            System.out.println(message);
    }
}
