import java.time.*;

public class PolitiskMote extends FagligArrangement
{
    public PolitiskMote(String n, String p, int t, double bpB, double bpV, String[] dt, LocalDateTime d, Kontaktperson kP, String tm)
    {
        super(n, p, t, bpB, bpV, dt, d, kP, tm);
    }
    
    public String toString()
    {
        String tekst = super.toString();
        return tekst;
    }
} //End of class PolitiskMote