import java.util.*;

public class Kontaktpersonregister
{
    private Set<Kontaktperson> register;
    
    public Kontaktpersonregister()
    {
        register = new LinkedHashSet<>();
    }
    
    public void settInn(Kontaktperson k)
    {
        register.add(k);
    }
    
     public Kontaktperson[] getKontaktpersoner()
    {
        Iterator<Kontaktperson> registerIter = register.iterator();
        if(register.isEmpty())
            return null;
        
        Kontaktperson[] kontaktpersoner = new Kontaktperson[register.size()];
        int i = 0;
        while(registerIter.hasNext())
        {
            kontaktpersoner[i] = registerIter.next();
            i++;
        }
        return kontaktpersoner;
    }
} //End of class Kontakpersonregister