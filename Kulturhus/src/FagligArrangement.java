import java.util.*;

public abstract class FagligArrangement extends Arrangement
{
    String tema;
    
    public FagligArrangement(String n, String p, double bpB, double bpV, String[] dt, Calendar d, Kontaktperson kP, String t)
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
} //End of class FagligArrangement