import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class InternHovedside extends JFrame {
    
    private final int BANNERBREDDE = 800;
    private final int BANNERHOYDE = 100;
    private final int MENYBREDDE = 250;
    private final int VALGHOYDE = 3;
    private final int SCROLLSPEED = 16;
    private JPanel vindu, hovedPanel, meny, infoPanel, knapper;
    private JList<ImageIcon> infoFelt;
    private JScrollPane infoScroll, mainScroll;
    private JSplitPane splitter;
    private JButton infoKnapp, opprettKnapp, slettKnapp;
    
    public InternHovedside() {
        
        super("Målselv Kommune");
        
        vindu = new JPanel(new BorderLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        hovedPanel = new JPanel(new GridBagLayout());

        BannerPanel banner = new BannerPanel();
        banner.setMinimumSize(new Dimension(BANNERBREDDE, BANNERHOYDE));
        banner.setMaximumSize(new Dimension(BANNERBREDDE, BANNERHOYDE));
        
        meny = new JPanel();
        meny.setMinimumSize(new Dimension(MENYBREDDE, 0));
        infoPanel = new JPanel(new BorderLayout());
        
        ImageIcon[] arrListe = new ImageIcon[20];
        
        for(int i = 0; i < arrListe.length; i++) {
            
            JFrame ramme = new JFrame();
            JPanel panel = new JPanel();
            JLabel navn = new JLabel("Tittel: Tammy");
            JLabel lengde = new JLabel("Lengde: 1 time og 45 min");
            
            ArrbildePanel plakat = new ArrbildePanel();
            panel.add(plakat);
            
            JPanel info = new JPanel(new GridLayout(2, 1, 5, 5));
            info.add(navn);
            info.add(lengde);
            info.setBackground(Color.white);
            
            panel.add(info);
            panel.setBackground(Color.white);
            
            ramme.add(panel);
            ramme.pack();
            ramme.setVisible(false);
            
            BufferedImage bilde = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = bilde.createGraphics();
            panel.print(graphics);
            graphics.dispose();
            ramme.dispose();
            
            ImageIcon ikon = new ImageIcon(bilde);
            arrListe[i] = ikon;
        }
        
        infoFelt = new JList<>(arrListe);
        infoFelt.setVisibleRowCount(VALGHOYDE);
        infoFelt.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        infoFelt.setSelectedIndex(0);
        DefaultListCellRenderer renderer =  (DefaultListCellRenderer)infoFelt.getCellRenderer();  
        renderer.setHorizontalAlignment(JLabel.CENTER);
        renderer.setForeground(Color.gray);
        
        infoScroll = new JScrollPane(infoFelt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        infoScroll.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        infoPanel.add(infoScroll, BorderLayout.CENTER);
        
        infoKnapp = new JButton("Info");
        opprettKnapp = new JButton("Opprett");
        slettKnapp = new JButton("Slett");
        knapper = new JPanel(new BorderLayout());
        knapper.add(infoKnapp, BorderLayout.LINE_START);
        knapper.add(opprettKnapp); //, BorderLayout.LINE_END);
        knapper.add(slettKnapp, BorderLayout.LINE_END);
        knapper.setBorder(BorderFactory.createEmptyBorder(0, 150, 5, 150));
        
        infoPanel.add(knapper, BorderLayout.PAGE_END);
        
        splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, meny, infoPanel);
        splitter.setEnabled(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;

        hovedPanel.add(banner, gbc);

        gbc.gridy++;

        hovedPanel.add(splitter, gbc);
        vindu.add(hovedPanel, BorderLayout.CENTER);
        
        mainScroll = new JScrollPane(vindu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainScroll.getVerticalScrollBar().setUnitIncrement(SCROLLSPEED);
        
        setContentPane(mainScroll);
        
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);      
 
    }
    
} //End of class InternHovedside