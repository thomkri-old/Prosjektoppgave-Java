import java.time.*;

public class Foredrag extends FagligArrangement
{    
    public Foredrag(String n, String p, double bpB, double bpV, String[] dt, LocalDateTime d, Kontaktperson kP, String t)
    {
        super(n, p, bpB, bpV, dt, d, kP, t);
    }
    
    public String toString()
    {
        String tekst = super.toString();
        return tekst;
    }
} //End of class Foredrag