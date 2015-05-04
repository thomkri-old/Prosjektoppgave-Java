import java.time.*;
import javax.swing.*;

public class Teater extends UnderholdningsArrangement
{
    public Teater(String n, String p, String lN, int t, double bpB, double bpV, String[] dt, LocalDateTime d, ImageIcon aB, Kontaktperson kP, String sj)
    {
        super(n, p, lN, t, bpB, bpV, dt, d, aB, kP, sj);
    }
    
    public String toString()
    {
        String tekst = super.toString();
        return tekst;
    }
} //End of class Teater