import java.time.*;
import javax.swing.*;


public abstract class FagligArrangement extends Arrangement
{
    String tema;
    
    public FagligArrangement(String n, String p, int t, double bpB, double bpV, String[] dt, LocalDateTime d, ImageIcon aB, Kontaktperson kP, String tm)
    {
        super(n, p, t, bpB, bpV, dt, d, aB, kP);
        tema = tm;
    }
    
    public String toString()
    {
        String tekst = super.toString();
        tekst += "\nTema: " + tema;
        return tekst;
    }
} //End of class FagligArrangement