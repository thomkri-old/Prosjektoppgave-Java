import java.io.*;
import java.time.*;
import javax.swing.*;

public class Foredrag extends FagligArrangement implements Serializable
{    
    public Foredrag(String n, String p, String lN, int t, double bpB, double bpV, String[] dt, LocalDateTime d, ImageIcon aB, Kontaktperson kP, String tm)
    {
        super(n, p, lN, t, bpB, bpV, dt, d, aB, kP, tm);
    }
    
    public String toString()
    {
        String tekst = super.toString();
        return tekst;
    }
} //End of class Foredrag