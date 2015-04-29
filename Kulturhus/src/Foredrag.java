import java.time.*;
import javax.swing.*;

public class Foredrag extends FagligArrangement
{    
    public Foredrag(String n, String p, int t, double bpB, double bpV, String[] dt, LocalDateTime d, ImageIcon aB, Kontaktperson kP, String tm)
    {
        super(n, p, t, bpB, bpV, dt, d, aB, kP, tm);
    }
    
    public String toString()
    {
        String tekst = super.toString();
        return tekst;
    }
} //End of class Foredrag