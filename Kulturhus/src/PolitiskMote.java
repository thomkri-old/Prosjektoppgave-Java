import java.time.*;

public class PolitiskMote extends FagligArrangement
{
    public PolitiskMote(String n, String p, double bpB, double bpV, String[] dt, LocalDateTime d, Kontaktperson kP, String t)
    {
        super(n, p, bpB, bpV, dt, d, kP, t);
    }
    
    public String toString()
    {
        String tekst = super.toString();
        return tekst;
    }
} //End of class PolitiskMote