import java.util.*;

public class Kontaktpersonregister
{
    private List<Kontaktperson> register;
    
    public Kontaktpersonregister()
    {
        register = new LinkedList<>();
    }
    
    public void settInn(Kontaktperson k)
    {
        register.add(k);
    }
    
    public void sorter()
    {
        Collections.sort(register, new KPersonsammenlikner());
    }
    
     public Kontaktperson[] getKontaktpersoner()
    {
        if(register.isEmpty())
            return null;
        
        sorter();
        Iterator<Kontaktperson> registerIter = register.iterator();
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