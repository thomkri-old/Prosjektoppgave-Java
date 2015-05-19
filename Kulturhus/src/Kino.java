/*Opprettet av: Sara Torp Myhre
Sist endret: 15.05.2015

Filen inneholder klassen Kino.*/

import java.io.*;
import java.time.*;
import javax.swing.*;

//Klassen er en subklasse av klassen UnderholdningsArrangement og implementerer Serializable. Dette er en klasse for alle kinoer
public class Kino extends UnderholdningsArrangement implements Serializable
{
    private int aldersgrense, lengde; //Lengde i minutter
    
    /*Metoden er konstruktøren til klassen Kino. Paramtrenes betydning:
    n = navn, p = program, lN = navnet på lokalet arrangementet holdes i, t = et heltatll som tilsier hvordan type arrangement det er(se Kulturhus.java),
    bpB = billettpris for barn, bpV = billettpris for voksne, dt = en array med navn over alle deltakere i arrangementet, d = Objekt med dato og tidspunkt for arrangementet,
    aB = ImageIcon som innneholder arrangemntets bilde(plakat), kP = Kontaktperson objekt av kontaktpersonen som er knyttet til arrangementet, sj = arrangementets sjanger,
    l = lengden på kinoen i minutter, ag = aldersgrensen på kinoen.*/
    public Kino(String n, String p, String lN, int t, double bpB, double bpV, String[] dt, LocalDateTime d, ImageIcon aB, Kontaktperson kP, String sj, int l, int ag)
    {
        super(n, p, lN, t, bpB, bpV, dt, d, aB, kP, sj);
        aldersgrense = ag;
        lengde = l;
    }
    
    public int getAldersgrense() //Get-metode for kinoens aldersgrense
    {
        return aldersgrense;
    }
    
    public int getLengde() //Get-metode for kinoens lengde
    {
        return lengde;
    }
    
    public String toString() //Klassens toString-metode. Skriver ut klassen som tekst
    {
        String tekst = super.toString();
        tekst += "\nLengde: " + lengde +"\nAldersgrense: " + aldersgrense;
        return tekst;
    }
} //End of class Kino
