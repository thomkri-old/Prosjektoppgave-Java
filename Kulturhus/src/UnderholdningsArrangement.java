import java.time.*;

public abstract class UnderholdningsArrangement extends Arrangement
{
    private String sjanger;
    
    public UnderholdningsArrangement(String n, String p, int t, double bpB, double bpV, String[] dt, LocalDateTime d, Kontaktperson kP, String sj)
    {
        super(n, p, t, bpB, bpV, dt, d, kP);
        sjanger = sj;
    }
    
    public String toString()
    {
        String tekst = super.toString();
        tekst += "\nSjanger: " + sjanger;
        return tekst;
    }
} //End of class UnderholdningsArrangement