public class Billett
{
    private String fornavn, etternavn, epost, arrNavn, lokaleNavn;
    private int plassNr, tlf;
    private double pris;
    
    public Billett(String fnavn, String enavn, String e, String aN, String lN, int pN, int t, double p)
    {
        fornavn = fnavn;
        etternavn = enavn;
        epost = e;
        arrNavn = aN;
        lokaleNavn = lN;
        plassNr = pN;
        tlf = t;
        pris = p;
    }
} //End of class Billett