import java.util.*;

public abstract class UnderholdningsArrangement extends Arrangement
{
    private String sjanger;
    
    public UnderholdningsArrangement(String n, String p, double bpB, double bpV, String[] dt, Calendar d, Kontaktperson kP, String sj)
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
} //End of class UnderholdningsArrangement