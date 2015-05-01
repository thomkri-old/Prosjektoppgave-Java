import java.util.*;
import java.time.*;
import java.time.format.*;
import javax.swing.*;

public abstract class Arrangement
{
    private String navn, program;
    private int arrId, bilSolgt = 0;
    private static int nesteId = 0;
    private int type;
    private double billettprisBarn, billettprisVoksen;
    private int[] plasser;
    private String[] deltakere;
    private LocalDateTime dato;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d. MMMM uuuu HH:mm");
    private Kontaktperson kPerson;
    private ImageIcon arrBilde;
    private Set<Billett> billettListe;
    
    public Arrangement(String n, String p, int t, double bpB, double bpV, String[] dt, LocalDateTime d, ImageIcon aB, Kontaktperson kP)
    {
        navn = n;
        program = p;
        billettprisBarn = bpB;
        billettprisVoksen = bpV;
        deltakere = dt;
        dato = d;
        kPerson = kP;
        arrBilde = aB;
        arrId = ++ nesteId;
        type = t;
        billettListe = new HashSet<>();
    }
        
    public String getNavn()
    {
        return navn;
    }
    
    public double getBillettprisBarn()
    {
        return billettprisBarn;
    }
    
    public double getBillettprisVoksen()
    {
        return billettprisVoksen;
    }
    
    public int getType()
    {
        return type;
    }
    
    public ImageIcon getArrBilde()
    {
        return arrBilde;
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
        String tekst = navn + "\n" + dtf.format(dato) + "\nDeltakere:";
        for(int i = 0; i < deltakere.length; i++)
        {
            tekst += " " + deltakere[i];
            if(i != deltakere.length - 1)
                tekst += ",";
        }
        tekst += "\nLedige plasser: " + (plasser.length - bilSolgt)
                + "\nBillettpris voksen: " + billettprisVoksen + "\tBillettpris barn: " + billettprisBarn
                + "\n\n" + program + "\n\nKontaktperson: " + kPerson.getNavn();
        return tekst;
    }
} //End of class Arrangement