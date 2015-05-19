/*Opprettet av: Thomas Kristiansen
Sist endret: 15.05.2015

Filen inneholder en abstrakt superklasse Arrangementer.*/

import java.io.*;
import java.util.*;
import java.time.*;
import javax.swing.*;

//Klassen er en abstrakt superklasse for alle arrangementer, og den implementerer Serializable.
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

    /*Metoden er konstruktøren til klassen Arrangement. Paramtrenes betydning:
    n = navn, p = program, lN = navnet på lokalet arrangementet holdes i, t = et heltatll som tilsier hvordan type arrangement det er(se Kulturhus.java),
    bpB = billettpris for barn, bpV = billettpris for voksne, dt = en array med navn over alle deltakere i arrangementet, d = Objekt med dato og tidspunkt for arrangementet,
    aB = ImageIcon som innneholder arrangemntets bilde(plakat), kP = Kontaktperson objekt av kontaktpersonen som er knyttet til arrangementet.*/
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

    public String getNavn() //Get-metode for arrangementets navn
    {
        return navn;
    }

    public double getBillettprisBarn() //Get-metode for arrangementets billettpris for barn
    {
        return billettprisBarn;
    }

    public double getBillettprisVoksen() //Get-metode for arrangementets billettpris for voksne
    {
        return billettprisVoksen;
    }

    public String[] getDeltakere() //Get-metode for arrangementets deltakere
    {
        return deltakere;
    }
    
    public LocalDateTime getDato() //Get-metode for dato og tidspunkt arrangementet holdes
    {
        return dato;
    }

    public int getLedigePlasser() //Get-metode for ledige plasser til arrangementets
    {
        return plasser.length - bilSolgt;
    }

    public String getProgram() //Get-metode for arrangementets program
    {
        return program;
    }

    public int getType() //Get-metode for arrangementets type som heltall
    {
        return type;
    }

    public ImageIcon getArrBilde() //Get-metode for arrangementets bilde(plakat)
    {
        return arrBilde;
    }

    public String getLokaleNavn() //Get-metode for navnet på lokalet arrangementet holdes
    {
        return lokaleNavn;
    }

    //Get-metode som øker antall solgte billetter og returnerer plassnummeret billetten får
    public int getPlassNr()
    {
        return ++bilSolgt;
    }
    
    public static int getNesteId() //Statisk get-metode som returnerer verdien til variabelen nesteId
    {
        return nesteId;
    }
    
    public static void setNesteId(int nId) //Statisk set-metode som angir verdien til variabelen nesteId
    {
        nesteId = nId;
    }

    public void setPlasser(int antP) //Set-metode som angir antall plasser i arrangementet
    {
        plasser = new int[antP];
        for (int i = 0; i < antP; i++) {
            plasser[i] = i + 1;
        }
    }

    public void settInnBillett(Billett b) //Metode som setter inn en billett i arrangementets billettliste
    {
        billettListe.add(b);
    }
    
    //Metode som returnerer billettlisten til arrangementet i form av en array
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
    
    public double getTotalInntekt() //Metode som returnerer arrangementest totale inntekt
    {
        Iterator<Billett> billettIter = billettListe.iterator();
        double inntekt = 0;
        while(billettIter.hasNext())
        {
            inntekt += billettIter.next().getPris();
        }
        return inntekt;
    }

    public String toString() //Metodens toString-metode. Skriver ut klassen som tekst
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
