public class Billett
{
    private String fornavn, etternavn;
    private int plassNr, tlf, pris;
    private Lokale lokale;
    private Arrangement arr;
    
    public Billett(String fnavn, String enavn, int pN, int t, int p, Lokale l, Arrangement a)
    {
        fornavn = fnavn;
        etternavn = enavn;
        plassNr = pN;
        tlf = t;
        lokale = l;
        arr = a;
        pris = p;
    }
}
