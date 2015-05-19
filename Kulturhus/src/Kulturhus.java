/*Opprettet av: Sara Torp Myhre
Sist endret: 18.05.2015

Filen inneholder klassen Kulturhus.*/

import java.awt.event.*;

//Klassen inneholder programmets main-metode og er klassen som starter opp hele programmet
public class Kulturhus
{
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