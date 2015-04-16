import java.awt.*;
import java.net.*;
import javax.swing.*;

public class BannerPanel extends JPanel
{
    private final int BREDDE = 800;
    private final int HOYDE = 100;
    private ImageIcon bannerIkon;
    private Image banner;
    
    public BannerPanel()
    {
        URL bildeURL = null;
        bildeURL = Hovedside.class.getResource("/bilder/piano.jpg");
        if(bildeURL != null)
            bannerIkon = new ImageIcon(bildeURL);
        else
            bannerIkon = null;
        banner = bannerIkon.getImage();
        setPreferredSize(new Dimension(BREDDE, HOYDE));
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(banner, 0, 0, BREDDE, HOYDE, this);
    }
}//End of class BannerPanel
