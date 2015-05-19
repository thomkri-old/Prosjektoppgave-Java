/*Opprettet av: Thomas Kristiansen
Sist endret: 15.05.2015

Filen inneholder klassen Teater.*/

import java.io.*;
import java.time.*;
import javax.swing.*;

//Klassen er en subklasse av klassen UnderholdningsArrangement og implementerer Serializable. Dette er en klasse for alle teatere
public class Teater extends UnderholdningsArrangement implements Serializable
{
    /*Metoden er konstruktøren til klassen Teater. Paramtrenes betydning:
    n = navn, p = program, lN = navnet på lokalet arrangementet holdes i, t = et heltatll som tilsier hvordan type arrangement det er(se Kulturhus.java),
    bpB = billettpris for barn, bpV = billettpris for voksne, dt = en array med navn over alle deltakere i arrangementet, d = Objekt med dato og tidspunkt for arrangementet,
    aB = ImageIcon som innneholder arrangemntets bilde(plakat), kP = Kontaktperson objekt av kontaktpersonen som er knyttet til arrangementet, sj = arrangementets sjanger*/
    public Teater(String n, String p, String lN, int t, double bpB, double bpV, String[] dt, LocalDateTime d, ImageIcon aB, Kontaktperson kP, String sj)
    {
        super(n, p, lN, t, bpB, bpV, dt, d, aB, kP, sj);
    }
    
    public String toString() //Klassens toString metode
    {
        String tekst = super.toString();
        return tekst;
    }
} //End of class Teater