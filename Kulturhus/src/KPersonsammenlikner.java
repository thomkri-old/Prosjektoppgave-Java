

/*Opprettet av: Thomas Kristiansen
Sist endret: 14.05.2015

Filen inneholder klassen KPersonsammenlikner.*/

import java.io.*;
import java.util.*;

//Klassen implementerer interfacene Comparator<> og Serilizable, og brukes til sortering av Kontaktperson objekter
public class KPersonsammenlikner implements Comparator<Kontaktperson>, Serializable
{    
    /*Metoden er implementert fra interfacet Comparator<>. Metoden tar to paramtere av typen Kontaktperson.
    Metoden tar så å sammenlikner disse to objektene etter ID-nummeret til kontaktpersonen*/
    public int compare(Kontaktperson k1, Kontaktperson k2)
    {
        Integer type1 = k1.getIdNr();
        Integer type2 = k2.getIdNr();
        return type1.compareTo(type2);
    }
}
