import java.awt.event.*;

public class Kulturhus
{
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