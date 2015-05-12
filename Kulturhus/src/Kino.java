import java.time.*;
import javax.swing.*;

public class Kino extends UnderholdningsArrangement
{
    private int aldersgrense, lengde; //Lengde i minutter
    
    public Kino(String n, String p, String lN, int t, double bpB, double bpV, String[] dt, LocalDateTime d, ImageIcon aB, Kontaktperson kP, String sj, int l, int ag)
    {
        super(n, p, lN, t, bpB, bpV, dt, d, aB, kP, sj);
        aldersgrense = ag;
        lengde = l;
    }
    
    public int getAldersgrense()
    {
        return aldersgrense;
    }
    
    public int getLengde()
    {
        return lengde;
    }
    
    public String toString()
    {
        String tekst = super.toString();
        tekst += "\nLengde: " + lengde +"\nAldersgrense: " + aldersgrense;
        return tekst;
    }
} //End of class Kino
