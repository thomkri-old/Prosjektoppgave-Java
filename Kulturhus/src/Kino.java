import java.util.*;
public class Kino extends Arrangement
{
    private String sjanger;
    private int aldersgrense, lengde; //Lengde i sekunder
    
    public Kino(String n, String p, double bpB, double bpV, String[] dt, Calendar d, Kontaktperson kP, String sj, int l, int ag)
    {
        super(n, p, bpB, bpV, dt, d, kP);
        sjanger = sj;
        aldersgrense = ag;
        lengde = l;
    }
    
    public String toString()
    {
        String tekst = super.toString();
        tekst += "\nSjanger: " + sjanger + "\nLengde: " + lengde +"\nAldersgrense: " + aldersgrense;
        return tekst;
    }
}
