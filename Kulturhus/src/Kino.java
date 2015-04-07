import java.util.*;
public class Kino extends UnderholdningsArrangement
{
    private int aldersgrense, lengde; //Lengde i sekunder
    
    public Kino(String n, String p, double bpB, double bpV, String[] dt, Calendar d, Kontaktperson kP, String sj, int l, int ag)
    {
        super(n, p, bpB, bpV, dt, d, kP, sj);
        aldersgrense = ag;
        lengde = l;
    }
    
    public String toString()
    {
        String tekst = super.toString();
        tekst += "\nLengde: " + lengde +"\nAldersgrense: " + aldersgrense;
        return tekst;
    }
}
