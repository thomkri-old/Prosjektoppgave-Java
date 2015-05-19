/*Opprettet av: Sara Torp Myhre
Sist endret: 12.05.2015

Filen inneholder klassen BannerPanel.*/

import java.awt.*;
import java.net.*;
import javax.swing.*;

//Klassen er en subklasse av JPanel som brukes til å vise hovedsidens banner
public class BannerPanel extends JPanel
{
    private ImageIcon bannerIkon;
    private Image banner;
    
    /*Metoden er konstruktøren til klassen ArrbildePanel.
    Konstruktøren tar et bilde fra en url (i dette tilfellet lokalt) og gjør det om til et Image objekt.*/
    public BannerPanel()
    {
        URL bildeURL = null;
        bildeURL = Hovedside.class.getResource("/bilder/banner.png");
        if(bildeURL != null)
            bannerIkon = new ImageIcon(bildeURL);
        else
            bannerIkon = null;
        banner = bannerIkon.getImage();
        setPreferredSize(new Dimension(banner.getWidth(this), banner.getHeight(this)));
    }
    
    /*Metode fra klassens superklasse JFrame.
    Metoden mottar et Graphics objekt og bruker det til å tegne bildet på panelet.*/
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(banner, 0, 0, banner.getWidth(this), banner.getHeight(this), this);
    }
}//End of class BannerPanel
