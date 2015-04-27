import java.util.*;

public class Lokale
{
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
    
    public int getType()
    {
        return type;
    }
    
    public int getAntArr()
    {
        return arrListe.size();
    }
    
    public void settInnArr(Arrangement a)
    {
        a.setPlasser(antPlasser);
        arrListe.add(a);
    }
    
    public Arrangement[] getArrangementer()
    {
        Iterator<Arrangement> arrIter = arrListe.iterator();
        if(arrListe.isEmpty())
            return null;
        
        Arrangement[] arrangementer = new Arrangement[arrListe.size()];
        int i = 0;
        while(arrIter.hasNext())
        {
            arrangementer[i] = arrIter.next();
            i++;
        }
        return arrangementer;
    }
    
    public Arrangement[] getArrangementer(int type)
    {
        Iterator<Arrangement> arrIter = arrListe.iterator();
        if(arrListe.isEmpty())
            return null;
        
        Arrangement[] arrangementer = new Arrangement[arrListe.size()];
        int i = 0;
        while(arrIter.hasNext())
        {
            Arrangement a = arrIter.next();
            if(a.getType() == type)
            {
                arrangementer[i] = a;
                i++;
            }
        }
        return arrangementer;
    }
    
    private int getAntArr(int type)
    {
        Iterator<Arrangement> arrIter = arrListe.iterator();
        if(arrListe.isEmpty())
            return null;
        
        int ant = 0;
        while(arrIter.hasNext())
        {
            Arrangement a = arrIter.next();
            if(arrIter.harsN.getType() == type;
                ant++;
        }
        return ant;
    }
} //End of class Lokale
