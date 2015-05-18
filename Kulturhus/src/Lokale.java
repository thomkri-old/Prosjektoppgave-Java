import java.time.*;
import java.util.*;

public class Lokale
{
    private static final int ALLE = -3;
    private static final int FAGLIGE = -2;
    private static final int UNDERHOLDNING = -1;
    private static final int DEBATT = 0;
    private static final int FOREDRAG = 1;
    private static final int POLITISK_MOTE = 2;
    private static final int BARNE_FORESTILLING = 3;
    private static final int KINO = 4;
    private static final int KONSERT = 5;
    private static final int TEATER = 6;
    private static final int TEATERSAL = 11;
    private static final int DEBATTSAL = 12;
    private static final int FOREDRAGSSAL = 13;
    private static final int KINOSAL = 14;
    private static final int KONSERTSAL = 15;
    
    private String navn;
    private int type, lokaleNr, antPlasser;
    private static int nesteNr = 0;
    private boolean harNrPlasser;
    private Set<Arrangement> arrListe;
    
    public Lokale(String n, int t, int aP, boolean hNP)
    {
        navn = n;
        type = t;
        harNrPlasser = hNP;
        lokaleNr = ++nesteNr;
        antPlasser = aP;
        arrListe = new LinkedHashSet<>();
    }
    
    public String getNavn()
    {
        return navn;
    }
    
    public int getAntPlasser()
    {
        return antPlasser;
    }
    
    public int getAntArr()
    {
        return arrListe.size();
    }
    
    public int getType()
    {
        return type;
    }
    
    public String getTypeTekst()
    {
        if(type == TEATERSAL)
            return "Teatersal";
        else if(type == DEBATTSAL)
            return "Debattsal";
        else if(type == FOREDRAGSSAL)
            return "Foredragssal";
        else if(type == KINOSAL)
            return "Kinosal";
        else
            return "Konsertsal";
    }
    
    public boolean getHarNrPlasser()
    {
        return harNrPlasser;
    }
    
    public void settInnArr(Arrangement a)
    {
        a.setPlasser(antPlasser);
        arrListe.add(a);
    }
    
    public Arrangement[] getArrangementer()
    {
        if(arrListe.isEmpty())
            return null;
        
        Iterator<Arrangement> arrIter = arrListe.iterator();        
        Arrangement[] arrangementer = new Arrangement[arrListe.size()];
        int i = 0;
        while(arrIter.hasNext())
            arrangementer[i++] = arrIter.next();
        return arrangementer;
    }
    
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
            if(t == ALLE)
            {
                if(a.getType() == DEBATT || a.getType() == FOREDRAG || a.getType() == POLITISK_MOTE || a.getType() == BARNE_FORESTILLING || a.getType() == KINO || a.getType() == KONSERT || a.getType() == TEATER)
                {
                    for(int y = 0; y < dArray.length; y++)
                    {
                        if(a.getDato().toLocalDate().equals(dArray[y]))
                            arrangementer[i++] = a;
                    }
                }
            }
            
            if(t == FAGLIGE)
            {
                if(a.getType() == DEBATT || a.getType() == FOREDRAG || a.getType() == POLITISK_MOTE)
                {
                    for(int y = 0; y < dArray.length; y++)
                    {
                        if(a.getDato().toLocalDate().equals(dArray[y]))
                            arrangementer[i++] = a;
                    }
                }
            }
                
            else if(t == UNDERHOLDNING)
            {
                if(a.getType() == BARNE_FORESTILLING || a.getType() == KINO || a.getType() == KONSERT || a.getType() == TEATER)
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
    
    private int getAntArr(int t, LocalDate[] dArray, Iterator<Arrangement> iter)
    {
        if(arrListe.isEmpty())
            return 0;
        
        int ant = 0;
        while(iter.hasNext())
        {
            Arrangement a = iter.next();
            if(t == ALLE)
            {
                if(a.getType() == DEBATT || a.getType() == FOREDRAG || a.getType() == POLITISK_MOTE || a.getType() == BARNE_FORESTILLING || a.getType() == KINO || a.getType() == KONSERT || a.getType() == TEATER)
                {
                    for(int y = 0; y < dArray.length; y++)
                    {
                        if(a.getDato().toLocalDate().equals(dArray[y]))
                            ant++;
                    }
                }
            }
            
            if(t == FAGLIGE)
            {
                if(a.getType() == DEBATT || a.getType() == FOREDRAG || a.getType() == POLITISK_MOTE)
                {
                    for(int y = 0; y < dArray.length; y++)
                    {
                        if(a.getDato().toLocalDate().equals(dArray[y]))
                            ant++;
                    }
                }
            }
                
            else if(t == UNDERHOLDNING)
            {
                if(a.getType() == BARNE_FORESTILLING || a.getType() == KINO || a.getType() == KONSERT || a.getType() == TEATER)
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
