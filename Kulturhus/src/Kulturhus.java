

/*Opprettet av: Sara Torp Myhre
Sist endret: 18.05.2015

Filen inneholder klassen Kulturhus.*/

import java.awt.event.*;

//Klassen inneholder programmets main-metode og er klassen som starter opp hele programmet
public class Kulturhus
{
    public static final int ALLE = -3;
    public static final int FAGLIGE = -2;
    public static final int UNDERHOLDNING = -1;
    public static final int DEBATT = 0;
    public static final int FOREDRAG = 1;
    public static final int POLITISK_MOTE = 2;
    public static final int BARNE_FORESTILLING = 3;
    public static final int KINO = 4;
    public static final int KONSERT = 5;
    public static final int TEATER = 6;
    public static final int KONTAKTPERSON = 7;
    public static final int LOKALE = 8;
    public static final int DEBATTSAL = 11;
    public static final int FOREDRAGSSAL = 12;
    public static final int KINOSAL = 13;
    public static final int KONSERTSAL = 14;
    public static final int TEATERSAL = 15;
    
    /*Main-metoden som oppretter et Hovedside objekt og deretter kjører Hovedside objektet sin metode lesFraFil
    Det blir lagt til en vinduslytter til objektet som sier at når vinduet lukkes skal programmet først kjøre
    Hovedside objektet sin metode skrivTilFil, for deretter å avsluttet hele systemet. */
    public static void main(String[] args)
    {
        Hovedside h = new Hovedside();
        
        h.addWindowListener(
            new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    h.skrivTilFil();
                    System.exit(0);
                }
            }
	);
        h.setVisible(true);
        h.lesFraFil();
    }
} //End of class Kulturhus