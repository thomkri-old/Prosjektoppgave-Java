import java.util.*;
import java.text.*;

public abstract class Arrangement
{
    private String navn, program;
    private int arrId, bilSolgt = 0;
    private static int nesteId = 0;
    private double billettprisBarn, billettprisVoksen;
    private int[] plasser;
    private String[] deltakere;
    private Calendar dato;
    private DateFormat df;
    private Kontaktperson kPerson;
    private ArrayList<Billett> billettListe;
    
    public Arrangement(String n, String p, double bpB, double bpV, String[] dt, Calendar d, Kontaktperson kP)
    {
        navn = n;
        program = p;
        billettprisBarn = bpB;
        billettprisVoksen = bpV;
        deltakere = dt;
        dato = d;
        df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
        kPerson = kP;
        arrId = ++ nesteId;
        billettListe = new ArrayList<>();
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
    
    public String toString()
    {
        String tekst = navn + "\n" + df.format(dato.getTime()) + "\nDeltakere:";
        for(int i = 0; i < deltakere.length; i++)
        {
            tekst += " " + deltakere[i];
            if(i != deltakere.length - 1)
                tekst += ",";
        }
        tekst += "\nLedige plasser: " + (plasser.length - bilSolgt)
                + "\nBillettpris voksen: " + billettprisVoksen + "\tBillettpris barn: " + billettprisBarn
                + "\n\n" + program + "\n\nKontaktperson: " + kPerson;
        return tekst;
    }
} //End of class Arrangement