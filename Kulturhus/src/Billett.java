public class Billett
{
    private int plassNr, tlf;
    private Lokale lokale;
    private Arrangement arr;
    
    public Billett(int pN, int t, Lokale l, Arrangement a)
    {
        plassNr = pN;
        tlf = t;
        lokale = l;
        arr = a;
    }
}
