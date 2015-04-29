import java.time.*;


public abstract class FagligArrangement extends Arrangement
{
    String tema;
    
    public FagligArrangement(String n, String p, int t, double bpB, double bpV, String[] dt, LocalDateTime d, Kontaktperson kP, String tm)
    {
        super(n, p, t, bpB, bpV, dt, d, kP);
        tema = tm;
    }
    
    public String toString()
    {
        String tekst = super.toString();
        tekst += "\nTema: " + tema;
        return tekst;
    }
} //End of class FagligArrangement