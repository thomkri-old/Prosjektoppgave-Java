import java.util.*;

public abstract class Arrangement
{
    private String navn, program;
    private int arrId, bilSolgt = 0;
    private static int nesteId = 0;
    private double billettpris;
    private String[] deltakere;
    private Calendar dato;
    private Lokale lokale;
    private Kontaktperson kPerson;
    
    public Arrangement(String n, String p, double bp, String[] dt, Calendar d, Lokale l, Kontaktperson kP)
    {
        navn = n;
        program = p;
        billettpris = bp;
        deltakere = dt;
        dato = d;
        lokale = l;
        kPerson = kP;
        arrId = ++ nesteId;
    }
}