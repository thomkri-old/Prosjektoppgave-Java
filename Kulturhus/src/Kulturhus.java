import java.awt.event.*;

public class Kulturhus
{
    public static void main(String[] args)
    {
        Kontaktpersonregister kpregister = new Kontaktpersonregister();
        Lokalregister lregister = new Lokalregister();
        RegistreringsVindu r = new RegistreringsVindu("Opprett", 8, kpregister, lregister);
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