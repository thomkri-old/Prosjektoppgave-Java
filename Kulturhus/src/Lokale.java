

/*Opprettet av: Sara Torp Myhre
Sist endret: 15.05.2015

Filen inneholder klassen Lokale.*/

import java.io.*;
import java.time.*;
import java.util.*;

//Klassen er en klasse for alle lokaler, og den implementerer Serializable.
public class Lokale implements Serializable
{    
    private String navn;
    private int type, antPlasser;
    private boolean harNrPlasser;
    private String[] typeTekst = {"Debattsal", "Foredragssal", "Kinosal", "Konsertsal", "Teatersal"};
    private Set<Arrangement> arrListe;
    
    /*Metoden er konstruktøren til klassen Lokale. Paramtrenes betydning:
    n = lokalets navn, t = lokalets type i form av et heltall(se Kulturhus.java),
    aP = antall plasser i lokalet, hNP = variabel som angir om lokalet har
    nummererte plasser eller ikke(true hvis lokalet har det, false hvis ikke)*/
    public Lokale(String n, int t, int aP, boolean hNP)
    {
        navn = n;
        type = t;
        harNrPlasser = hNP;
        antPlasser = aP;
        arrListe = new LinkedHashSet<>();
    }
    
    public String getNavn() //Get-metode for lokalets navn
    {
        return navn;
    }
    
    public int getAntPlasser() //Get-metode for antall plasser i lokalet
    {
        return antPlasser;
    }
    
    //Get-metode for antall arrangementer i arrListe objektet som inneholder alle arrangementer koblet til lokalet
    public int getAntArr()
    {
        return arrListe.size();
    }
    
    public int getType() //Get-metode for hva slags type lokalet det er
    {
        return type;
    }
    
    public String getTypeTekst() //Get-metode for hva slags type lokale det er som et tekst objekt
    {
        return typeTekst[type - 11];
    }
    
    public boolean getHarNrPlasser() //Get-metode for om lokalet har nummererte plasser eller ikk
    {
        return harNrPlasser;
    }
    
    //Metode som setter inn arrangementet, gitt i parameteret, inn i lokalets arrangement liste
    public void settInnArr(Arrangement a)
    {
        a.setPlasser(antPlasser);
        arrListe.add(a);
    }
    
    /*Metode som returnerer en array av alle arrangementer fra lokalets arrangement liste av typen t,
    sendt med i parameteret, og som holdes på en av datoene i dArray, sendt med i parameteret*/
    public Arrangement[] getArrangementer(int t, LocalDate[] dArray)
    {
        if(arrListe.isEmpty())
            return null;
        
        Iterator<Arrangement> arrIter = arrListe.iterator();
        Iterator<Arrangement> arrIter2 = arrListe.iterator();
        Arrangement[] arrangementer = new Arrangement[getAntArr(t, dArray, arrIter2)];
        int i = 0;
        while(arrIter.hasNext())
        {
            Arrangement a = arrIter.next();
            if(t == Kulturhus.ALLE)
            {
                if(a.getType() == Kulturhus.DEBATT || a.getType() == Kulturhus.FOREDRAG || a.getType() == Kulturhus.POLITISK_MOTE || a.getType() == Kulturhus.BARNE_FORESTILLING || a.getType() == Kulturhus.KINO || a.getType() == Kulturhus.KONSERT || a.getType() == Kulturhus.TEATER)
                {
                    for(int y = 0; y < dArray.length; y++)
                    {
                        if(a.getDato().toLocalDate().equals(dArray[y]))
                            arrangementer[i++] = a;
                    }
                }
            }
            
            if(t == Kulturhus.FAGLIGE)
            {
                if(a.getType() == Kulturhus.DEBATT || a.getType() == Kulturhus.FOREDRAG || a.getType() == Kulturhus.POLITISK_MOTE)
                {
                    for(int y = 0; y < dArray.length; y++)
                    {
                        if(a.getDato().toLocalDate().equals(dArray[y]))
                            arrangementer[i++] = a;
                    }
                }
            }
                
            else if(t == Kulturhus.UNDERHOLDNING)
            {
                if(a.getType() == Kulturhus.BARNE_FORESTILLING || a.getType() == Kulturhus.KINO || a.getType() == Kulturhus.KONSERT || a.getType() == Kulturhus.TEATER)
                {
                    for(int y = 0; y < dArray.length; y++)
                    {
                        if(a.getDato().toLocalDate().equals(dArray[y]))
                            arrangementer[i++] = a;
                    }
                }
            }
            
            else if(a.getType() == t)
            {
                for(int y = 0; y < dArray.length; y++)
                {
                    if(a.getDato().toLocalDate().equals(dArray[y]))
                        arrangementer[i++] = a;
                }
            }
        }
        return arrangementer;
    }
    
    //Metode som returnerer en array av de 10 arrangementene, fra lokalets arrangement liste, som har mest inntekt
    public Arrangement[] getMestInntektArr()
    {
        if(arrListe.isEmpty())
            return null;
        
        Arrangement[] mestInntektArray = new Arrangement[10];
        Iterator<Arrangement> arrIter = arrListe.iterator();
        while(arrIter.hasNext())
        {
            Arrangement a = arrIter.next();
            for(int i = 0; i < mestInntektArray.length; i++)
            {
                if(mestInntektArray[i] == null || mestInntektArray[i].getTotalInntekt() < a.getTotalInntekt())
                {
                    for(int y = mestInntektArray.length - 1; y > i; y--)
                    {
                        mestInntektArray[y] = mestInntektArray[y-1];
                    }
                    mestInntektArray[i] = a;
                    break;
                }
            }
        }
        return mestInntektArray;
    }
    
    /*Metode som returnerer antall arrangementer fra lokalets arrangement liste av typen t,
    sendt med i parameteret, og som holdes på en av datoene i dArray, sendt med i parameteret,
    ved hjelp av Iterator<Arrangement> objektet iter, sendt med i parameteret.*/
    private int getAntArr(int t, LocalDate[] dArray, Iterator<Arrangement> iter)
    {
        if(arrListe.isEmpty())
            return 0;
        
        int ant = 0;
        while(iter.hasNext())
        {
            Arrangement a = iter.next();
            if(t == Kulturhus.ALLE)
            {
                if(a.getType() == Kulturhus.DEBATT || a.getType() == Kulturhus.FOREDRAG || a.getType() == Kulturhus.POLITISK_MOTE || a.getType() == Kulturhus.BARNE_FORESTILLING || a.getType() == Kulturhus.KINO || a.getType() == Kulturhus.KONSERT || a.getType() == Kulturhus.TEATER)
                {
                    for(int y = 0; y < dArray.length; y++)
                    {
                        if(a.getDato().toLocalDate().equals(dArray[y]))
                            ant++;
                    }
                }
            }
            
            if(t == Kulturhus.FAGLIGE)
            {
                if(a.getType() == Kulturhus.DEBATT || a.getType() == Kulturhus.FOREDRAG || a.getType() == Kulturhus.POLITISK_MOTE)
                {
                    for(int y = 0; y < dArray.length; y++)
                    {
                        if(a.getDato().toLocalDate().equals(dArray[y]))
                            ant++;
                    }
                }
            }
                
            else if(t == Kulturhus.UNDERHOLDNING)
            {
                if(a.getType() == Kulturhus.BARNE_FORESTILLING || a.getType() == Kulturhus.KINO || a.getType() == Kulturhus.KONSERT || a.getType() == Kulturhus.TEATER)
                {
                    for(int y = 0; y < dArray.length; y++)
                    {
                        if(a.getDato().toLocalDate().equals(dArray[y]))
                            ant++;
                    }
                }
            }
                
            else if(a.getType() == t)
            {
                for(int y = 0; y < dArray.length; y++)
                {
                    if(a.getDato().toLocalDate().equals(dArray[y]))
                        ant++;
                }
            }
        }
        return ant;
    }
    
    /*Metode som fjerner Arrangementet avlysA, sendt med i parameteret, og returnerer true 
    hvis den finner avlysA i lokalets arrangement liste og sletter det, ellers false*/
    public boolean avlysArr(Arrangement avlysA)
    {
        if(arrListe.isEmpty())
            return false;
        
        Iterator<Arrangement> arrIter = arrListe.iterator();
        while(arrIter.hasNext())
        {
            Arrangement a = arrIter.next();
            if(a.equals(avlysA))
            {
                arrListe.remove(a);
                return true;
            }
        }
        return false;
    }
} //End of class Lokale
