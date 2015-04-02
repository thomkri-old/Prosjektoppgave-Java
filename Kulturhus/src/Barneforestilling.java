import java.util.*;

public class Barneforestilling extends Arrangement
{
    private String sjanger;
    
    public Barneforestilling(String n, String p, double bpB , double bpV, String[] dt, Calendar d, Kontaktperson kP, String sj)
    {
        super(n, p, bpB, bpV, dt, d, kP);
        sjanger = sj;
    }
    
    public String toString()
    {
        String tekst = super.toString();
        tekst += "\nSjanger: " + sjanger;
        return tekst;
    }
}
