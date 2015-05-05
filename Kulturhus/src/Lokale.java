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
        
        Iterator<Arrangement> arrIter = arrListe.iterator();
        Iterator<Arrangement> arrIter2 = arrListe.iterator();
        Arrangement[] arrangementer = new Arrangement[getAntArr(t, arrIter2)];
        int i = 0;
        while(arrIter.hasNext())
        {
            Arrangement a = arrIter.next();
            if(a.getType() == t)
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
            if(iter.next().getType() == t)
                ant++;
        }
        return ant;
    }
} //End of class Lokale
