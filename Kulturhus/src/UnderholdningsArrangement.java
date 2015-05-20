

/*Opprettet av: Sara Torp Myhre
Sist endret: 15.05.2015

Filen inneholder en abstrakt superklasse UnderholdningsArrangement.*/

import java.io.*;
import java.time.*;
import javax.swing.*;

//Klassen er en abstrakt superklasse for alle underholdnings arrangementer, er også en subklasse av Arrangementer og den implementerer Serializable.
public abstract class UnderholdningsArrangement extends Arrangement implements Serializable
{
    private String sjanger;
    
    /*Metoden er konstruktøren til klassen UnderholdningsArrangement. Paramtrenes betydning:
    n = navn, p = program, lN = navnet på lokalet arrangementet holdes i, t = et heltatll som tilsier hvordan type arrangement det er(se Kulturhus.java),
    bpB = billettpris for barn, bpV = billettpris for voksne, dt = en array med navn over alle deltakere i arrangementet, d = Objekt med dato og tidspunkt for arrangementet,
    aB = ImageIcon som innneholder arrangemntets bilde(plakat), kP = Kontaktperson objekt av kontaktpersonen som er knyttet til arrangementet, sj = arrangementets sjanger*/
    public UnderholdningsArrangement(String n, String p, String lN, int t, double bpB, double bpV, String[] dt, LocalDateTime d, ImageIcon aB, Kontaktperson kP, String sj)
    {
        super(n, p, lN, t, bpB, bpV, dt, d, aB, kP);
        sjanger = sj;
    }
    
    public String toString() //Klassen toString metode
    {
        String tekst = super.toString();
        tekst += "\nSjanger: " + sjanger;
        return tekst;
    }
} //End of class UnderholdningsArrangement