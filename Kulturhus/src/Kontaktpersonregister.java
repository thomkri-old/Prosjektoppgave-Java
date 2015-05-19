/*Opprettet av: Thomas Kristiansen
Sist endret: 15.05.2015

Filen inneholder klassen Kontaktpersonregister.*/

import java.io.*;
import java.util.*;

//Klassen er et register for alle kontaktpersonene i systemet. Klassen implementerer Serializable
public class Kontaktpersonregister implements Serializable
{
    private List<Kontaktperson> register;
    
    //Metoden er konstruktøren til klassen Kontaktpersonregister.
    public Kontaktpersonregister()
    {
        register = new LinkedList<>();
    }
    
    //Metode som setter parameteret k inn i registeret
    public void settInn(Kontaktperson k)
    {
        register.add(k);
    }
    
    public void sorter() //Metode som sorterer registeret ved hjelp av KPersonsammenlikner klassen
    {
        Collections.sort(register, new KPersonsammenlikner());
    }
    
    public Kontaktperson[] getKontaktpersoner() //Metode som returnerer registeret i form av en array
    {
        if(register.isEmpty())
            return null;
        
        sorter();
        Iterator<Kontaktperson> registerIter = register.iterator();
        Kontaktperson[] kontaktpersoner = new Kontaktperson[register.size()];
        int i = 0;
        while(registerIter.hasNext())
        {
            kontaktpersoner[i] = registerIter.next();
            i++;
        }
        return kontaktpersoner;
    }
} //End of class Kontakpersonregister