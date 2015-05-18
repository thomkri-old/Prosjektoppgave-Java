public class Kontaktperson
{
    private String fornavn, etternavn, epost, nettside, firma, opplysninger;
    private int tlfNr, type, idNr;
    private static int nesteNr = 0;
    
    public Kontaktperson(String fn, String en, String e, String n, String f, String o, int tN)
    {
        fornavn = fn;
        etternavn = en;
        epost = e;
        nettside = n;
        firma = f;
        opplysninger = o;
        tlfNr = tN;
        idNr = ++nesteNr;
    }
    
    public String getFornavn()
    {
        return fornavn;
    }
    
    public String getEtternavn()
    {
        return etternavn;
    }
    
    public String getNavn()
    {
        return etternavn + ", " + fornavn;
    }
    
    public String getEpost()
    {
        return epost;
    }
    
    public String getNettside()
    {
        return nettside;
    }
    
    public String getFirma()
    {
        return firma;
    }
    
    public String getOpplysninger()
    {
        return opplysninger;
    }
    
    public int getTlfNr()
    {
        return tlfNr;
    }
    
    public void setType(int t)
    {
        type = t;
    }
    
    public String toString()
    {
        return "";
    }
} //End of class Kontaktperson