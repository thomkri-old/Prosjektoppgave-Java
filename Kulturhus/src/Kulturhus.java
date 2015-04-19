import java.awt.event.*;
import javax.swing.JScrollPane;

public class Kulturhus
{
    public static void main(String[] args)
    {
        RegistreringsVindu r = new RegistreringsVindu("Opprett", 5);
        Hovedside h = new Hovedside();
        h.addWindowListener(
            new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    System.exit(0);
                }
            }
	);
        h.setVisible(true);
    }
} //End of class Kulturhus