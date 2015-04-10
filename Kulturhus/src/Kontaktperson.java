public class Kontaktperson
{
    private String fornavn, etternavn, epost, nettside, firma, opplysninger;
    private int tlfNr, type, idNr;
    private static int nesteNr = 0;
    
    public Kontaktperson(String fn, String en, String e, String n, String f, String o, int tN, int t)
    {
        fornavn = fn;
        etternavn = en;
        epost = e;
        nettside = n;
        firma = f;
        opplysninger = o;
        tlfNr = tN;
        type = t;
        idNr = ++nesteNr;
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