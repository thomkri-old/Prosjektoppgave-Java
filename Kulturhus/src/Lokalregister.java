/*Opprettet av: Thomas Kristiansen
Sist endret: 18.05.2015

Filen inneholder klassen Lokaleregister.*/

import java.io.*;
import java.time.*;
import java.util.*;

//Klassen er et register for alle lokaler i systemet. Klassen implementerer Serializable
public class Lokalregister implements Serializable
{   
    private List<Lokale> register;
    
    //Metoden er konstruktøren til klassen Lokaleregister.
    public Lokalregister()
    {
        register = new LinkedList<>();
    }
    
    //Metode som setter parameteret l inn i registeret
    public void settInn(Lokale l)
    {
        register.add(l);
    }
    
    public void sorter() //Metode som sorterer registeret ved hjelp av KPersonsammenlikner klassen
    {
        Collections.sort(register, new Lokalesammenlikner());
    }
    
    /*Metode som returnerer en array av alle arrangementer fra alle lokaler i registeret av typen t,
    sendt med i parameteret, og som holdes på en av datoene i dArray, sendt med i parameteret*/
    public Arrangement[] getArrangementer(int type, LocalDate[] dArray)
    {
        if(register.isEmpty())
            return null;
        
        Iterator<Lokale> registerIter = register.iterator();
        Arrangement[][] multiArr = new Arrangement[register.size()][];
        int i = 0;
        while(registerIter.hasNext())
        {
            multiArr[i++] = registerIter.next().getArrangementer(type, dArray);
        }

        int antArr = 0;
        for(int y = 0; y < multiArr.length; y++)
        {
            if(multiArr[y] != null)
                antArr += multiArr[y].length;
        }
            
        if(antArr == 0)
            return null;
            
        Arrangement[] arrangementer = new Arrangement[antArr];
        int teller = 0;
        for(int x = 0; x < multiArr.length; x++)
            if(multiArr[x] != null)
                for(int z = 0; z < multiArr[x].length; z++)
                    arrangementer[teller++] = multiArr[x][z];

        return arrangementer;
    }
    
    //Metode som returnerer en array av de 10 arrangementene, fra alle lokalene i registeret, som har mest inntekt
    public Arrangement[] getMestInntektArr()
    {
        if(register.isEmpty())
            return null;
        
        Arrangement[] mestInntektArray = new Arrangement[10];
        Iterator<Lokale> registerIter = register.iterator();
        while(registerIter.hasNext())
        {
            Arrangement[] arrArray = registerIter.next().getMestInntektArr();
            if(arrArray != null)
            {
                for(int i = 0; i < arrArray.length; i++)
                {
                    Arrangement a = arrArray[i];
                    for(int y = 0; y < mestInntektArray.length; y++)
                    {
                        if(mestInntektArray[y] == null || (a != null && mestInntektArray[y].getTotalInntekt() < a.getTotalInntekt()))
                        {
                            for(int z = mestInntektArray.length - 1; z > y; z--)
                            {
                                mestInntektArray[z] = mestInntektArray[z-1];
                            }
                            mestInntektArray[y] = a;
                            break;
                        }
                    }
                }
            }
        }
        return mestInntektArray;
    }
    
    //Metode som returnerer alle lokaler i registeret i form av en array
    public Lokale[] getLokaler()
    {
        if(register.isEmpty())
            return null;
        
        sorter();
        Iterator<Lokale> registerIter = register.iterator();
        Lokale[] lokaler = new Lokale[register.size()];
        int i = 0;
        while(registerIter.hasNext())
            lokaler[i++] = registerIter.next();
        return lokaler;
    }
    
    //Metode som returnerer alle lokaler av typen type, sendt med i paramteteret, som en array
    public Lokale[] getLokaler(int type)
    {
        if(register.isEmpty())
            return null;
        
        sorter();
        Iterator<Lokale> registerIter = register.iterator();
        Lokale[] lokaler = new Lokale[antLokaler(type, registerIter)];
        int i = 0;
        while(registerIter.hasNext())
        {
            Lokale l = registerIter.next();
            if(l.getType() == type)
                lokaler[i++] = l;
        }
        return lokaler;
    }
    
    //Metode som returnerer antall lokaler av typen type, sendt med i paramteteret, ved hjelp av Itertator<Lokale> objektet iter
    private int antLokaler(int type, Iterator<Lokale> iter)
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
    
    /*Metode som fjerner Arrangementet avlysA, sendt med i parameteret, og returnerer true 
    hvis den finner avlysA ,i ett av arrangement listene til lokalene i registeret, og sletter det, ellers false*/
    public boolean avlysArr(Arrangement avlysA)
    {
        if(register.isEmpty())
            return false;
        
        Iterator<Lokale> registerIter = register.iterator();
        while(registerIter.hasNext())
        {
            Lokale l = registerIter.next();
            if(l.avlysArr(avlysA))
                return true;
        }
        return false;
    }
} //End of class Lokalregister