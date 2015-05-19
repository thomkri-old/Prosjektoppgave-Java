/*Opprettet av: Thomas Kristiansen
Sist endret: 16.05.2015

Filen inneholder klassen Lokalesammenlikner.*/

import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

//Klassen implementerer interfacene Comparator<> og Serilizable, og brukes til sortering av Lokale objekter
public class Lokalesammenlikner implements Comparator<Lokale>, Serializable
{
    private String rekkefolge = "<\0<0<1<2<3<4<5<6<7<8<9<A,a<B,b<C,c<D,d<E,e<F,f<G,g<H,h<I,i<J,j<K,k<L,l<M,m<N,n<O,o<P,p<Q,q<R,r<S,s<T,t<U,u<V,v<W,w<X,x<Y,y<Z,z<Æ,æ<Ø,ø<Å=AA,å=aa;AA,aa";
    private RuleBasedCollator kollator;
    
    //Metoden er konstruktøren til klassen Lokalesammenlikner.
    public Lokalesammenlikner()
    {
        try
        {
            kollator = new RuleBasedCollator(rekkefolge);
        }
        catch(ParseException pe)
        {
            JOptionPane.showMessageDialog(null, "Feil i rekkefølge på kollatoren.");
        }
    }
    
    /*Metoden er implementert fra interfacet Comparator<>. Metoden tar to paramtere av typen Lokale.
    Metoden tar så å sammenlikner disse to objektene alfabetisk etter navn.*/
    public int compare(Lokale l1, Lokale l2)
    {
        String lNavn1 = l1.getNavn();
        String lNavn2 = l2.getNavn();
        return kollator.compare(lNavn1, lNavn2);
    }
}