import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

public abstract class Arrangement implements Serializable
{

    private String navn, program, lokaleNavn;
    private int arrId, bilSolgt = 0;
    private static int nesteId = 0;
    private int type;
    private double billettprisBarn, billettprisVoksen;
    private int[] plasser;
    private String[] deltakere;
    private LocalDateTime dato;
    private Kontaktperson kPerson;
    private ImageIcon arrBilde;
    private Set<Billett> billettListe;

    public Arrangement(String n, String p, String lN, int t, double bpB, double bpV, String[] dt, LocalDateTime d, ImageIcon aB, Kontaktperson kP)
    {
        navn = n;
        program = p;
        lokaleNavn = lN;
        billettprisBarn = bpB;
        billettprisVoksen = bpV;
        deltakere = dt;
        dato = d;
        kPerson = kP;
        arrBilde = aB;
        arrId = ++nesteId;
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

    public String[] getDeltakere()
    {
        return deltakere;
    }
    
    public LocalDateTime getDato()
    {
        return dato;
    }

    public int getLedigePlasser()
    {
        return plasser.length - bilSolgt;
    }

    public String getProgram()
    {
        return program;
    }

    public int getType()
    {
        return type;
    }

    public ImageIcon getArrBilde()
    {
        return arrBilde;
    }

    public String getLokaleNavn()
    {
        return lokaleNavn;
    }

    public int getPlassNr()
    {
        return ++bilSolgt;
    }
    
    public static int getNesteId()
    {
        return nesteId;
    }
    
    public static void setNesteId(int nId)
    {
        nesteId = nId;
    }

    public void setPlasser(int antP)
    {
        plasser = new int[antP];
        for (int i = 0; i < antP; i++) {
            plasser[i] = i + 1;
        }
    }

    public void settInnBillett(Billett b)
    {
        billettListe.add(b);
    }
    
    public Billett[] getBilettListe()
    {
        if(billettListe.isEmpty())
            return null;
        
        Iterator<Billett> billettIter = billettListe.iterator();
        Billett[] billettArray = new Billett[billettListe.size()];
        int i = 0;
        while(billettIter.hasNext())
        {
            billettArray[i++] = billettIter.next();
        }
        return billettArray;
    }
    
    public double getTotalInntekt()
    {
        Iterator<Billett> billettIter = billettListe.iterator();
        double inntekt = 0;
        while(billettIter.hasNext())
        {
            inntekt += billettIter.next().getPris();
        }
        return inntekt;
    }

    public String toString()
    {
        String tekst = navn + "\n" + dato.toString() + "\nDeltakere:";
        for (int i = 0; i < deltakere.length; i++) {
            tekst += " " + deltakere[i];
            if (i != deltakere.length - 1) {
                tekst += ",";
            }
        }
        tekst += "\nLedige plasser: " + (getLedigePlasser())
                + "\nBillettpris voksen: " + billettprisVoksen + "\tBillettpris barn: " + billettprisBarn
                + "\n\n" + program + "\n\nKontaktperson: " + kPerson.getNavn();
        return tekst;
    }
} //End of class Arrangement
