/*Opprettet av: Sara Torp Myhre
Sist endret: 15.05.2015

Filen inneholder klassen Kontaktperson.*/

import java.io.*;

//Klassen er en klasse for alle kontaktpersoner, og den implementerer Serializable.
public class Kontaktperson implements Serializable
{
    private String fornavn, etternavn, epost, nettside, firma, opplysninger;
    private int tlfNr, type, idNr;
    private static int nesteNr = 0;
    
    /*Metoden er konstruktøren til klassen Kontaktperson. Paramtrenes betydning:
    fn = kontaktpersonens fornavn, en = kontaktpersonens etternavn, e = kontaktpersonenes e-post, n = kontaktpersonens nettside
    f = firmaet kontaktpersonen er knyttet til, o = ekstra opplysninger om kontaktpersonnen, tN = kontaktpersonens telefonnummer.*/
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
    
    public String getFornavn() //Get-metode for kontaktpertsonens fornavn
    {
        return fornavn;
    }
    
    public String getEtternavn() //Get-metode for kontaktpersonens etternavn
    {
        return etternavn;
    }
    
    public String getNavn() //Get-metode for kontaktpersonens navn satt sammen av etternavn og fornavn
    {
        return etternavn + ", " + fornavn;
    }
    
    public String getEpost() //Get-metode for kontaktpersonens e-post
    {
        return epost;
    }
    
    public String getNettside() //Get-metode for kontaktpersonens nettside
    {
        return nettside;
    }
    
    public String getFirma() //Get-metode for firmaet kontaktpersonen er knyttet til
    {
        return firma;
    }
    
    public String getOpplysninger() //Get-metode for ekstra opplysninger om kontaktpersonen
    {
        return opplysninger;
    }
    
    public int getTlfNr() //Get-metode for kontaktpersonens telefonnummer
    {
        return tlfNr;
    }
    
    public void setType(int t) //Set-metode for kontaktpersonens type
    {
        type = t;
    }
    
    public static int getNesteNr() //Statisk get-metode som returnerer verdien til variabelen nesteId
    {
        return nesteNr;
    }
    
    public static void setNesteNr(int nNr) //Statisk set-metode som angir verdien til variabelen nesteId
    {
        nesteNr = nNr;
    }
    
    public String toString() //Klassens toString metode
    {
        return getNavn() + "\nE-post: " + epost + "\nTlf. nr.: " + tlfNr
               + "\nNettside: " + nettside + "\nFirma: " + firma + "\n\n" + opplysninger;
    }
} //End of class Kontaktperson