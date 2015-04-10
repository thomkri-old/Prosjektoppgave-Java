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
} //End of class Kontakpersonregister