import java.time.*;

public class Kino extends UnderholdningsArrangement
{
    private int aldersgrense, lengde; //Lengde i minutter
    
    public Kino(String n, String p, int t, double bpB, double bpV, String[] dt, LocalDateTime d, Kontaktperson kP, String sj, int l, int ag)
    {
        super(n, p, t, bpB, bpV, dt, d, kP, sj);
        aldersgrense = ag;
        lengde = l;
    }
    
    public String toString()
    {
        String tekst = super.toString();
        tekst += "\nLengde: " + lengde +"\nAldersgrense: " + aldersgrense;
        return tekst;
    }
} //End of class Kino
