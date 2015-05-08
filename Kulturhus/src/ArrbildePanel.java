import java.awt.*;
import java.net.*;
import javax.swing.*;

public class ArrbildePanel extends JPanel
{
    private final int BREDDE = 100;
    private final int HOYDE = 150;
    private ImageIcon bildeIkon;
    private Image bilde;
    
    public ArrbildePanel(ImageIcon bI)
    {
        bildeIkon = bI;
        
        bilde = bildeIkon.getImage();
        setPreferredSize(new Dimension(BREDDE, HOYDE));
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(bilde, 0, 0, BREDDE, HOYDE, this);
    }
}
