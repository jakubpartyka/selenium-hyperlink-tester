import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class DomainList<E> extends ArrayList<E> {
    @Override
    public boolean add(Object o) {
        if(this.contains(o))
            return false;
        else
            return super.add((E) o);
    }
}
