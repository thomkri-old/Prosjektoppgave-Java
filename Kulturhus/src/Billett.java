public class Billett
{
    private int plassNr, tlf, pris;
    private Lokale lokale;
    private Arrangement arr;
    
    public Billett(int pN, int t, int p, Lokale l, Arrangement a)
    {
        plassNr = pN;
        tlf = t;
        lokale = l;
        arr = a;
        pris = p;
    }
}
