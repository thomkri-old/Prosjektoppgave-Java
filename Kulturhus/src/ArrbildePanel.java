/*Opprettet av: Sara Torp Myhre
Sist endret: 08.05.2015

Filen inneholder klassen ArrBildePanel.*/

import java.awt.*;
import javax.swing.*;

//Klassen er en subklasse av JPanel som brukes til å vise et arrangements bilde(plakat)
public class ArrbildePanel extends JPanel
{
    private final int BREDDE = 100;
    private final int HOYDE = 150;
    private ImageIcon bildeIkon;
    private Image bilde;
    
    /*Metoden er konstruktøren til klassen ArrbildePanel.
    Paramteret: bI = ImageIcon med av bilde som skal lagres på panelet.
    Konstruktøren tar ImageIcon objektet og gjør det om til et Image objekt*/
    public ArrbildePanel(ImageIcon bI)
    {
        bildeIkon = bI;
        
        bilde = bildeIkon.getImage();
        setPreferredSize(new Dimension(BREDDE, HOYDE));
    }
    
    /*Metode fra klassens superklasse JFrame.
    Metoden mottar et Graphics objekt og bruker det til å tegne bildet på panelet.*/
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(bilde, 0, 0, BREDDE, HOYDE, this);
    }
} //End of class ArrbildePanel