import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class Hovedside extends JFrame
{
    private JPanel hovedPanel, vindu;
    private JLabel tekst;
    
    public Hovedside()
    {
        super("Målselv Kommune");
        
        vindu = new JPanel(new BorderLayout());
        hovedPanel = new JPanel(new GridBagLayout());
        BannerPanel banner = new BannerPanel();
        banner.setSize(800, 100);
        tekst = new JLabel("TEST TEST TEST TEST TESTioiohogojiohghioeohiegohighiogiohgeohioeih");
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        hovedPanel.add(banner, gbc);
        pack();
        gbc.gridy++;
        
        hovedPanel.add(tekst, gbc);
        hovedPanel.setSize(new Dimension(800, 700));
        vindu.add(banner, BorderLayout.CENTER);
        vindu.setSize(800, 700);
        vindu.setBackground(Color.red);
        add(vindu);
        setSize(800, 700);
    }
} //End of class Hovedside