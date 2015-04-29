import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.net.URL;
import java.time.LocalDateTime;

public class Hovedside extends JFrame
{
    private final int BANNERBREDDE = 800;
    private final int BANNERHOYDE = 100;
    private final int MENYBREDDE = 250;
    private final int VALGHOYDE = 3;
    private final int SCROLLSPEED = 16;
    
    private static final int ALLE = -1;
    private static final int DEBATT = 0;
    private static final int FOREDRAG = 1;
    private static final int POLITISK_MOTE = 2;
    private static final int BARNE_FORESTILLING = 3;
    private static final int KINO = 4;
    private static final int KONSERT = 5;
    private static final int TEATER = 6;
    
    private JPanel vindu, hovedPanel, meny, infoPanel, knapper;
    private JList<ImageIcon> infoFelt;
    private JScrollPane infoScroll, mainScroll;
    private JSplitPane splitter;
    private JButton infoKnapp, kjopKnapp;
    
    private int arrType = FOREDRAG;
    ImageIcon[] arrFrameListe;
    Arrangement[] arrListe;
    
    private Kontaktpersonregister kpregister;
    private Lokalregister lregister;
    
    public Hovedside(Lokalregister l, Kontaktperson kp, Lokale l1, Lokale l2, Lokale l3)
    {
        
        super("Målselv Kommune");
        
        lregister = l;
        
        URL bildeURL = Hovedside.class.getResource("/bilder/tammy.jpg");
        ImageIcon bildeIkon = new ImageIcon(bildeURL);
        
        String[] dArray = {"hnifof", "jfiow"};
        Arrangement a = new Foredrag("Ting og tang", "fjiowgwjiop wnmefo p mo", 1, 125.5, 150, dArray, LocalDateTime.now(), bildeIkon, kp, "hgufoiho");
        Arrangement a1 = new Foredrag("Hallo", "fjiowgwjiop wnmefo p mo", 1, 123, 115, dArray, LocalDateTime.now(), bildeIkon, kp, "hgufoiho");
        Arrangement a2 = new Foredrag("Det jeg gjør", "fjiowgwjiop wnmefo p mo", 1, 124.4, 123, dArray, LocalDateTime.now(), bildeIkon, kp, "hgufoiho");
        Arrangement a3 = new Foredrag("Fakta og sånt", "fjiowgwjiop wnmefo p mo", 1, 123, 124, dArray, LocalDateTime.now(), bildeIkon, kp, "hgufoiho");
        Arrangement a4 = new Foredrag("Foredrag om foredrag", "fjiowgwjiop wnmefo p mo", 1, 44.5, 98, dArray, LocalDateTime.now(), bildeIkon, kp, "hgufoiho");
        
        l1.settInnArr(a);
        l2.settInnArr(a1);
        l3.settInnArr(a3);
        l2.settInnArr(a2);
        l3.settInnArr(a4);
        
        vindu = new JPanel(new BorderLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        hovedPanel = new JPanel(new GridBagLayout());

        BannerPanel banner = new BannerPanel();
        banner.setMinimumSize(new Dimension(BANNERBREDDE, BANNERHOYDE));
        banner.setMaximumSize(new Dimension(BANNERBREDDE, BANNERHOYDE));
        
        meny = new JPanel();
        meny.setMinimumSize(new Dimension(MENYBREDDE, 0));
        infoPanel = new JPanel(new BorderLayout());
        
        visArrangementer();
        
        infoScroll = new JScrollPane(infoFelt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        infoScroll.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        infoPanel.add(infoScroll, BorderLayout.CENTER);
        
        infoKnapp = new JButton("Info");
        kjopKnapp = new JButton("Kjøp billett");
        knapper = new JPanel(new BorderLayout());
        knapper.add(infoKnapp, BorderLayout.LINE_START);
        knapper.add(kjopKnapp, BorderLayout.LINE_END);
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
    
    private void visArrangementer()
    {
        if(arrType == ALLE)
            arrListe = lregister.getArrangementer(ALLE);
        arrListe = lregister.getArrangementer(arrType);
        if(arrListe == null)
            return;
        arrFrameListe = new ImageIcon[arrListe.length];
        for(int i = 0; i < arrFrameListe.length; i++)
        {
            JFrame ramme = new JFrame();
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel navn = new JLabel(arrListe[i].getNavn());
            JLabel pris = new JLabel("Pris: " + arrListe[i].getBillettprisBarn() + "/" + arrListe[i].getBillettprisVoksen());
            
            ArrbildePanel plakat = new ArrbildePanel();
            panel.add(plakat);
            
            JPanel info = new JPanel(new GridLayout(2, 1, 5, 5));
            info.add(navn);
            info.add(pris);
            info.setBackground(Color.white);
            
            panel.add(info);
            panel.setBackground(Color.white);
            panel.setPreferredSize(new Dimension(300, 160));
            
            ramme.add(panel);
            ramme.pack();
            ramme.setVisible(false);
            
            BufferedImage bilde = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = bilde.createGraphics();
            panel.print(graphics);
            graphics.dispose();
            ramme.dispose();
            
            ImageIcon ikon = new ImageIcon(bilde);
            arrFrameListe[i] = ikon;
        }
        infoFelt = new JList<>(arrFrameListe);
        infoFelt.setVisibleRowCount(VALGHOYDE);
        infoFelt.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        infoFelt.setSelectedIndex(0);
        
        DefaultListCellRenderer renderer = (DefaultListCellRenderer)infoFelt.getCellRenderer();  
        renderer.setHorizontalAlignment(JLabel.CENTER);
        renderer.setForeground(Color.gray);
    }
} //End of class Hovedside
