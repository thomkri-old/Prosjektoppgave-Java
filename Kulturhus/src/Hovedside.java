import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.URL;
import java.time.LocalDateTime;
import javax.swing.border.BevelBorder;

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
    
    private JPanel vindu, meny, hovedPanel, infoPanel, knapper;
    private JList<ImageIcon> infoFelt;
    private JScrollPane infoScroll, mainScroll;
    private JLabel menyOverskrift, arrangementer, underholdning, faglig, bForestilling, debattkveld, foredrag, kino, konsert, politiskM, teater;
    private JButton infoKnapp, kjopKnapp;
    
    private int arrType = ALLE;
    ImageIcon[] arrFrameListe;
    Arrangement[] arrListe;
    
    private Kontaktpersonregister kpregister;
    private Lokalregister lregister;
    
    private Knappelytter knappelytter;
    private Labellytter labellytter;
    
    public Hovedside(Lokalregister l, Kontaktperson kp, Lokale l1, Lokale l2, Lokale l3)
    {
        
        super("Målselv Kommune");
        
        lregister = l;
        knappelytter = new Knappelytter();
        labellytter = new Labellytter();
        
        URL bildeURL = Hovedside.class.getResource("/bilder/tammy.jpg");
        ImageIcon bildeIkon = new ImageIcon(bildeURL);
        
        URL bildeURL1 = Hovedside.class.getResource("/bilder/lfc.jpeg");
        ImageIcon bildeIkon1 = new ImageIcon(bildeURL1);
        
        String[] dArray = {"hnifof", "jfiow"};
        Arrangement a = new Foredrag("Ting og tang", "fjiowgwjiop wnmefo p mo", l1.getNavn(), 1, 125.5, 150, dArray, LocalDateTime.now(), bildeIkon, kp, "hgufoiho");
        Arrangement a1 = new Foredrag("Hallo", "fjiowgwjiop wnmefo p mo", l2.getNavn(), 1, 123, 115, dArray, LocalDateTime.now(), bildeIkon1, kp, "hgufoiho");
        Arrangement a2 = new Foredrag("Det jeg gjør", "fjiowgwjiop wnmefo p mo", l2.getNavn(), 1, 124.4, 123, dArray, LocalDateTime.now(), bildeIkon, kp, "hgufoiho");
        Arrangement a3 = new Foredrag("Fakta og sånt", "fjiowgwjiop wnmefo p mo", l3.getNavn(), 1, 123, 124, dArray, LocalDateTime.now(), bildeIkon, kp, "hgufoiho");
        Arrangement a4 = new Foredrag("Foredrag om foredrag", "fjiowgwjiop wnmefo p mo", l3.getNavn(), 1, 44.5, 98, dArray, LocalDateTime.now(), bildeIkon1, kp, "hgufoiho");
        
        l1.settInnArr(a);
        l2.settInnArr(a1);
        l3.settInnArr(a3);
        l2.settInnArr(a2);
        l3.settInnArr(a4);
        
        vindu = new JPanel(new GridBagLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        BannerPanel banner = new BannerPanel();
        banner.setMinimumSize(new Dimension(BANNERBREDDE, BANNERHOYDE));
        banner.setMaximumSize(new Dimension(BANNERBREDDE, BANNERHOYDE));
        
        meny = new JPanel(new GridBagLayout());
        meny.setPreferredSize(new Dimension(MENYBREDDE, 350));
        
        menyOverskrift = new JLabel("Meny");
        menyOverskrift.setFont(new Font(menyOverskrift.getFont().getName(), Font.BOLD, 20));
        arrangementer = new JLabel("Arrangement");
        arrangementer.setFont(new Font(menyOverskrift.getFont().getName(), Font.PLAIN, 18));
        arrangementer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        arrangementer.addMouseListener(labellytter);
        underholdning = new JLabel("Underholdning");
        underholdning.setFont(new Font(menyOverskrift.getFont().getName(), Font.PLAIN, 16));
        underholdning.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        underholdning.addMouseListener(labellytter);
        faglig = new JLabel("Faglige arrangement");
        faglig.setFont(new Font(menyOverskrift.getFont().getName(), Font.PLAIN, 16));
        faglig.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        faglig.addMouseListener(labellytter);
        bForestilling = new JLabel("Barne forestilling");
        bForestilling.setFont(new Font(menyOverskrift.getFont().getName(), Font.PLAIN, 14));
        bForestilling.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bForestilling.addMouseListener(labellytter);
        debattkveld = new JLabel("Debattkveld");
        debattkveld.setFont(new Font(menyOverskrift.getFont().getName(), Font.PLAIN, 14));
        debattkveld.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        debattkveld.addMouseListener(labellytter);
        foredrag = new JLabel("Foredrag");
        foredrag.setFont(new Font(menyOverskrift.getFont().getName(), Font.PLAIN, 14));
        foredrag.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        foredrag.addMouseListener(labellytter);
        kino = new JLabel("Kino");
        kino.setFont(new Font(menyOverskrift.getFont().getName(), Font.PLAIN, 14));
        kino.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        kino.addMouseListener(labellytter);
        konsert = new JLabel("Konsert");
        konsert.setFont(new Font(menyOverskrift.getFont().getName(), Font.PLAIN, 14));
        konsert.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        konsert.addMouseListener(labellytter);
        politiskM = new JLabel("Politisk møte");
        politiskM.setFont(new Font(menyOverskrift.getFont().getName(), Font.PLAIN, 14));
        politiskM.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        politiskM.addMouseListener(labellytter);
        teater = new JLabel("Teater");
        teater.setFont(new Font(menyOverskrift.getFont().getName(), Font.PLAIN, 14));
        teater.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        teater.addMouseListener(labellytter);
        
        infoPanel = new JPanel(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(535, 530));
        
        visArrangementer();
        
        infoScroll = new JScrollPane(infoFelt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        infoScroll.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        infoPanel.add(infoScroll, BorderLayout.CENTER);
        
        infoKnapp = new JButton("Info");
        infoKnapp.addActionListener(knappelytter);
        kjopKnapp = new JButton("Kjøp billett");
        kjopKnapp.addActionListener(knappelytter);
        
        knapper = new JPanel(new BorderLayout());
        knapper.add(infoKnapp, BorderLayout.LINE_START);
        knapper.add(kjopKnapp, BorderLayout.LINE_END);
        knapper.setBorder(BorderFactory.createEmptyBorder(0, 150, 5, 150));
        
        infoPanel.add(knapper, BorderLayout.PAGE_END);
        infoPanel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.lightGray));
        
        GridBagConstraints gbcM = new GridBagConstraints();
        gbcM.anchor = GridBagConstraints.FIRST_LINE_START;
        gbcM.insets = new Insets(5, 0, 5, 5);
        gbcM.gridx = 0;
        gbcM.gridy = 0;
        
        meny.add(menyOverskrift, gbcM);
        gbcM.gridy++;
        meny.add(arrangementer, gbcM);
        gbcM.gridy++;
        gbcM.insets = new Insets(5, 20, 5, 5);
        meny.add(faglig, gbcM);
        gbcM.gridy++;
        gbcM.insets = new Insets(5, 40, 5, 5);
        meny.add(debattkveld, gbcM);
        gbcM.gridy++;
        meny.add(foredrag, gbcM);
        gbcM.gridy++;
        meny.add(politiskM, gbcM);
        gbcM.gridy++;
        gbcM.insets = new Insets(5, 20, 5, 5);
        meny.add(underholdning, gbcM);
        gbcM.gridy++;
        gbcM.insets = new Insets(5, 40, 5, 5);
        meny.add(bForestilling, gbcM);
        gbcM.gridy++;
        meny.add(kino, gbcM);
        gbcM.gridy++;
        meny.add(konsert, gbcM);
        gbcM.gridy++;
        meny.add(teater, gbcM);
        
        hovedPanel = new JPanel(new GridBagLayout());
        hovedPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        
        GridBagConstraints gbcH = new GridBagConstraints();
        gbcH.anchor = GridBagConstraints.FIRST_LINE_START;
        gbcH.gridx = 0;
        gbcH.gridy = 0;
        
        hovedPanel.add(meny, gbcH);
        gbcH.gridx++;
        hovedPanel.add(infoPanel, gbcH);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;

        vindu.add(banner, gbc);
        gbc.gridy++;
        vindu.add(hovedPanel, gbc);
        
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
            
            ArrbildePanel plakat = new ArrbildePanel(arrListe[i].getArrBilde());
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
        infoFelt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        infoFelt.setSelectedIndex(0);
        infoFelt.setPreferredSize(new Dimension(500, infoFelt.getPreferredSize().height));
        
        DefaultListCellRenderer renderer = (DefaultListCellRenderer)infoFelt.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
    }
    
    public void visInfo()
    {
        JFrame infoVindu = new InfoArr(arrListe[infoFelt.getSelectedIndex()]);
        infoVindu.setLocationRelativeTo(this);
    }
    
    public void kjopBillett()
    {
        JFrame kjopVindu = new Kjop(arrListe[infoFelt.getSelectedIndex()]);
        kjopVindu.setLocationRelativeTo(this);
    }
    
    private class Knappelytter implements ActionListener //Kommandolytteren som bestemmer hvilken metode som blir utført utifra hvilken knapp det blir trykket på
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == infoKnapp)
                visInfo();
            else if(e.getSource() == kjopKnapp)
                kjopBillett();
        }
    }
    
    private class Labellytter implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            if(e.getSource() == arrangementer)
                System.out.println("Arrangement");
            else if(e.getSource() == faglig)
                System.out.println("Faglig");
            else if(e.getSource() == underholdning)
                System.out.println("Underholdning");
            else if(e.getSource() == debattkveld)
                System.out.println("Debattkveld");
            else if(e.getSource() == foredrag)
                System.out.println("Foredrag");
            else if(e.getSource() == politiskM)
                System.out.println("Politisk møte");
            else if(e.getSource() == bForestilling)
                System.out.println("Barne forestilling");
            else if(e.getSource() == kino)
                System.out.println("Kino");
            else if(e.getSource() == konsert)
                System.out.println("Konsert");
            else if(e.getSource() == teater)
                System.out.println("Teater");
        }

        public void mousePressed(MouseEvent e) { }

        public void mouseReleased(MouseEvent e) { }

        public void mouseEntered(MouseEvent e) { }

        public void mouseExited(MouseEvent e) { }
    }
} //End of class Hovedside
