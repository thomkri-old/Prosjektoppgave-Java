import java.io.*;

public class Billett implements Serializable
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
    
    public String getFornavn()
    {
        return fornavn;
    }
    
    public String getEtternavn()
    {
        return etternavn;
    }
    
    public String getEpost()
    {
        return epost;
    }
    
    public int getTlf()
    {
        return tlf;
    }
    
    public double getPris()
    {
        return pris;
    }
} //End of class Billett