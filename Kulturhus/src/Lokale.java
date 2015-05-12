import java.util.*;

public class Lokale implements Comparable
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
    
    public Arrangement[] getArrangementer(int t)
    {
        if(arrListe.isEmpty())
            return null;
        
        if(t == ALLE)
            return getArrangementer();
        
        Iterator<Arrangement> arrIter = arrListe.iterator();
        Iterator<Arrangement> arrIter2 = arrListe.iterator();
        Arrangement[] arrangementer = new Arrangement[getAntArr(t, arrIter2)];
        int i = 0;
        while(arrIter.hasNext())
        {
            Arrangement a = arrIter.next();
            if(t == FAGLIGE)
            {
                if(a.getType() == DEBATT || a.getType() == FOREDRAG || a.getType() == POLITISK_MOTE)
                    arrangementer[i++] = a;
            }
                
            else if(t == UNDERHOLDNING)
            {
                if(a.getType() == BARNE_FORESTILLING || a.getType() == KINO || a.getType() == KONSERT || a.getType() == TEATER)
                    arrangementer[i++] = a;
            }
            
            else if(a.getType() == t)
                arrangementer[i++] = a;
        }
        return arrangementer;
    }
    
    private int getAntArr(int t, Iterator<Arrangement> iter)
    {
        if(arrListe.isEmpty())
            return 0;
        
        int ant = 0;
        while(iter.hasNext())
        {
            Arrangement a = iter.next();
            if(t == FAGLIGE)
            {
                if(a.getType() == DEBATT || a.getType() == FOREDRAG || a.getType() == POLITISK_MOTE)
                    ant++;
            }
                
            else if(t == UNDERHOLDNING)
            {
                if(a.getType() == BARNE_FORESTILLING || a.getType() == KINO || a.getType() == KONSERT || a.getType() == TEATER)
                    ant++;
            }
                
            else if(a.getType() == t)
                ant++;
        }
        return ant;
    }

    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
} //End of class Lokale
