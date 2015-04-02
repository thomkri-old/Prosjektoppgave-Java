import java.util.*;

public class Debattkveld extends Arrangement
{
    private String tema;
    
    public Debattkveld(String n, String p, double bpB, double bpV, String[] dt, Calendar d, Kontaktperson kP, String t)
    {
        super(n, p, bpB, bpV, dt, d, kP);
        tema = t;        
    }
    
    public String toString()
    {
        String tekst = super.toString();
        tekst += "\nTema: " + tema;
        return tekst;
    }
}
