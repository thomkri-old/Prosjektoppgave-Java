import java.util.*;
public class Kino extends Arrangement
{
    private String sjanger;
    private int lengde; //I sekunder
    
    public Kino(String n, String p, double bp, String[] dt, Calendar d, Kontaktperson kP, String sj, int l)
    {
        super(n, p, bp, dt, d, kP);
        sjanger = sj;
        lengde = l;
    }
}
