import java.time.*;

public class Barneforestilling extends UnderholdningsArrangement
{
    public Barneforestilling(String n, String p, double bpB , double bpV, String[] dt, LocalDateTime d, Kontaktperson kP, String sj)
    {
        super(n, p, bpB, bpV, dt, d, kP, sj);
    }
    
    public String toString()
    {
        String tekst = super.toString();
        return tekst;
    }
} //End of class Barneforestilling