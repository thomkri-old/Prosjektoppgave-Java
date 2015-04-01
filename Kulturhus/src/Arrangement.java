import java.util.*;

public abstract class Arrangement
{
    private String navn, program;
    private int arrId, bilSolgt = 0;
    private static int nesteId = 0;
    private double billettpris;
    private int[] plasser;
    private String[] deltakere;
    private Calendar dato;
    private Kontaktperson kPerson;
    private ArrayList<Billett> billettListe = new ArrayList<>();
    
    public Arrangement(String n, String p, double bp, String[] dt, Calendar d, Kontaktperson kP)
    {
        navn = n;
        program = p;
        billettpris = bp;
        deltakere = dt;
        dato = d;
        kPerson = kP;
        arrId = ++ nesteId;
    }
    
    public void setPlasser(int antP)
    {
        plasser = new int[antP];
        for(int i = 0; i < antP; i++)
        {
            plasser[i] = i+1;
        }
    }
    
    public void settInnBillett(Billett b)
    {
        billettListe.add(b);
    }
}