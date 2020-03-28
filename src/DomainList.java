import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class DomainList<Domain> extends ArrayList<Domain> {
    @Override
    public boolean add(Object o) {
        String domain = o.toString();

        //check if not a file
        if (domain.endsWith(".css") ||
            domain.endsWith(".js")  ||
            domain.endsWith(".ico")  ||
            domain.endsWith(".png")  ||
            domain.endsWith(".jpeg")  ||
            domain.endsWith(".jpg")  ||
            domain.endsWith(".JPG")
        )
            return false;

        //check if not a mailto or tel
        if(domain.startsWith("mailto:") ||
                domain.startsWith("tel:"))
            return false;

        //check if in target service
        if(!domain.contains(Main.TARGET))
            return false;

        //check if unique
        if(this.contains(o))
            return false;
        else {
            if(super.add((Domain) o)){
                log("added new domain: " + o);
                return true;
            }
            else
                return false;
        }
    }

    private static void log (String message){
        Logger.log(message,"DMLS");
    }
}
