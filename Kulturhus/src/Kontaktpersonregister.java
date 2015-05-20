

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
            kontaktpersoner[i++] = registerIter.next();
        
        return kontaktpersoner;
    }
    
    //Metode som returnerer, som en array, kontaktpersoner med type lik variabelen type
    public Kontaktperson[] getKontaktpersoner(int type) 
    {
        if(register.isEmpty())
            return null;
        
        sorter();
        Iterator<Kontaktperson> registerIter = register.iterator();
        Iterator<Kontaktperson> registerIter2 = register.iterator();
        Kontaktperson[] kontaktpersoner = new Kontaktperson[antKPersoner(type, registerIter2)];
        int i = 0;
        while(registerIter.hasNext())
        {
            Kontaktperson k = registerIter.next();
            if(k.getType() == type)
                kontaktpersoner[i++] = k;
        }
        
        return kontaktpersoner;
    }
    
    /*Metode som returnerer antall kontaktpersoner av typen type, sendt med i paramteteret,
    ved hjelp av Itertator<Kontaktperson> objektet iter*/
    private int antKPersoner(int type, Iterator<Kontaktperson> iter)
    {
        if(register.isEmpty())
            return 0;
        
        int ant = 0;
        while(iter.hasNext())
        {
            if(iter.next().getType() == type)
                ant++;
        }
        return ant;
    }
} //End of class Kontakpersonregister