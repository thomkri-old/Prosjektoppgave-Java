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
    
    public Lokale[] getLokaler()
    {
        Iterator<Lokale> registerIter = register.iterator();
        if(register.isEmpty())
            return null;
        
        Lokale[] lokaler = new Lokale[register.size()];
        int i = 0;
        while(registerIter.hasNext())
        {
            lokaler[i] = registerIter.next();
            i++;
        }
        return lokaler;
    }
} //End of class Lokalregister