/*Opprettet av: Sara Torp Myhre
Sist endret: 08.05.2015

Filen inneholder klassen Billett.*/

import java.io.*;

//Klassen er en klasse for alle billetter som implementerer Serializable.
public class Billett implements Serializable
{
    private String fornavn, etternavn, epost, arrNavn, lokaleNavn;
    private int plassNr, tlf;
    private double pris;
    
    /*Metoden er konstruktøren til klassen Billett. Paramtrenes betydning:
    fnavn = kjøperens fornavn, enavn = kjøperens etternavn, e = e-posten til kjøperen av billetten,
    aN = navnet på arrangementet detter er en billett til, lN = navnet på lokalet arrangementet er i, pN = hvilken plass billetten er til,
    t = telefonnummeret til kjøperen av billetten, p = prisen til billetten.*/
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
    
    public String getFornavn() //Get-metode for kjøperens fornavn
    {
        return fornavn;
    }
    
    public String getEtternavn() //Get-metode for kjøperens etternavn
    {
        return etternavn;
    }
    
    public String getEpost() //Get-metode for kjøperens e-post
    {
        return epost;
    }
    
    public int getTlf() //Get-metode for kjøperens telefonnummer
    {
        return tlf;
    }
    
    public double getPris() //Get-metode for billettens pris
    {
        return pris;
    }
} //End of class Billett