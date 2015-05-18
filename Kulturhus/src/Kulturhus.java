import java.awt.event.*;

public class Kulturhus
{
    public static void main(String[] args)
    {

        Kontaktpersonregister kpregister = new Kontaktpersonregister();
        Lokalregister lregister = new Lokalregister();
        
        Lokale l1 = new Lokale("Lokale1", 3, 235, false);
        Lokale l2 = new Lokale("Lokale2", 1, 115, false);
        Lokale l3 = new Lokale("Lokale3", 5, 290, true);
        Lokale l4 = new Lokale("Lokale4", 4, 230, false);
        Lokale l5 = new Lokale("Lokale5", 2, 524, true);
        
        lregister.settInn(l1);
        lregister.settInn(l2);
        lregister.settInn(l3);
        lregister.settInn(l4);
        lregister.settInn(l5);
        
        Kontaktperson kp1 = new Kontaktperson("Olav", "Elnan", "juventus4ever@hotmail.com", "www.olav-juve-fan.com", "The Firm", "Stooor juventus fan", 12345678);
        Kontaktperson kp2 = new Kontaktperson("Rudi", "Yu", "juventus4ever@hotmail.com", "www.olav-juve-fan.com", "The Firm", "Stooor juventus fan", 12345678);
        Kontaktperson kp3 = new Kontaktperson("Audun", "Brustad", "juventus4ever@hotmail.com", "www.olav-juve-fan.com", "The Firm", "Stooor juventus fan", 12345678);
        
        kpregister.settInn(kp1);
        kpregister.settInn(kp2);
        kpregister.settInn(kp3);
        
        Hovedside h = new Hovedside(lregister, kp1, l1, l2, l5);
        InternHovedsideTest h2 = new InternHovedsideTest(lregister, kpregister);
        
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