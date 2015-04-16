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
                    System.exit(0);
                }
            }
	);
        h.setVisible(true);
    }
} //End of class Kulturhus