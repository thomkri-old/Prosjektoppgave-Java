import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class Hovedside extends JFrame
{
    private final int BANNERBREDDE = 800;
    private final int MENYBREDDE = 250;
    private final int VALGBREDDE = 46;
    private final int VALGHOYDE = 40;
    private JPanel vindu, hovedPanel, meny, infoPanel;
    private JTextArea infoFelt;
    private JScrollPane infoScroll;
    
    public Hovedside()
    {
        super("Målselv Kommune");
        
        vindu = new JPanel();
        vindu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        vindu.setLayout(new BorderLayout());
        hovedPanel = new JPanel();
        hovedPanel.setLayout(new GridBagLayout());

        BannerPanel banner = new BannerPanel();
        banner.setMinimumSize(new Dimension(BANNERBREDDE, 0));
        banner.setMaximumSize(new Dimension(BANNERBREDDE, 0));
        
        meny = new JPanel();
        meny.setMinimumSize(new Dimension(MENYBREDDE, 0));
        infoPanel = new JPanel();
        
        infoFelt = new JTextArea(VALGHOYDE, VALGBREDDE);
        infoScroll = new JScrollPane(infoFelt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        infoPanel.add(infoScroll);
        JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, meny, infoPanel);
        splitter.setEnabled(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;


        hovedPanel.add(banner, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy++;

        hovedPanel.add(splitter, gbc);

        vindu.add(hovedPanel, BorderLayout.CENTER);
        
        JScrollPane scroll = new JScrollPane(vindu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setContentPane(scroll);
        
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);        
    }
} //End of class Hovedside