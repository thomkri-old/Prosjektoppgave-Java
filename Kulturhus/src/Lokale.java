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
    
    public void settInnArr(Arrangement a)
    {
        a.setPlasser(antPlasser);
        arrListe.add(a);
    }
} //End of class Lokale