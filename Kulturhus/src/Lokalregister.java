import java.util.*;

public class Lokalregister
{
    private Set<Lokale> register;
    
    public Lokalregister()
    {
        register = new HashSet<>();
    }
    
    public void settInn(Lokale l)
    {
        register.add(l);
    }
} //End of class Lokalregister