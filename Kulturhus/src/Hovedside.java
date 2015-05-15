import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.URL;
import java.text.*;
import java.time.*;
import java.util.*;

public class Hovedside extends JFrame
{
    private static final int BANNERBREDDE = 800;
    private static final int BANNERHOYDE = 100;
    private static final int MENYBREDDE = 250;
    private static final int VALGHOYDE = 3;
    private static final int SCROLLSPEED = 16;
    
    private static final int ALLE = -3;
    private static final int FAGLIGE = -2;
    private static final int UNDERHOLDNING = -1;
    private static final int DEBATT = 0;
    private static final int FOREDRAG = 1;
    private static final int POLITISK_MOTE = 2;
    private static final int BARNE_FORESTILLING = 3;
    private static final int KINO = 4;
    private static final int KONSERT = 5;
    private static final int TEATER = 6;
    
    private JPanel vindu, meny, hovedPanel, infoPanel, datoPanel, fraPanel, tilPanel, knapper;
    private BannerPanel banner;
    private JList<ImageIcon> infoFelt;
    private JComboBox<String> fraDag, fraMaaned, fraAar, tilDag, tilMaaned, tilAar;
    private JScrollPane infoScroll, mainScroll;
    private JLabel menyOverskrift, arrangementer, underholdning, faglig, bForestilling, debattkveld, foredrag, kino, konsert, politiskM, teater, fra, til;
    private JButton infoKnapp, kjopKnapp;
    
    private int arrType = ALLE;
    private Arrangement[] arrListe;
    
    private Kontaktpersonregister kpregister;
    private Lokalregister lregister;
    
    private Knappelytter knappelytter;
    private Labellytter labellytter;
    
    DecimalFormat krFormat;
    
    public Hovedside(Lokalregister l, Kontaktperson kp, Lokale l1, Lokale l2, Lokale l3)
    {
        
        super("Målselv Kommune");
        
        lregister = l;
        knappelytter = new Knappelytter();
        labellytter = new Labellytter();
        krFormat = new DecimalFormat( "0.00" );
        
        URL bildeURL = Hovedside.class.getResource("/bilder/tammy.jpg");
        ImageIcon bildeIkon = new ImageIcon(bildeURL);
        
        URL bildeURL1 = Hovedside.class.getResource("/bilder/lfc.jpeg");
        ImageIcon bildeIkon1 = new ImageIcon(bildeURL1);
        
        String[] dArray = {"hnifof", "jfiow"};
        Arrangement a = new Foredrag("Ting og tang", "fjiowgwjiop wnmefo p mo", l1.getNavn(), 1, 125.5, 150, dArray, LocalDateTime.now(), bildeIkon, kp, "hgufoiho");
        Arrangement a1 = new PolitiskMote("Hallo", "fjiowgwjiop wnmefo p mo", l2.getNavn(), 2, 123, 115, dArray, LocalDateTime.now(), bildeIkon1, kp, "hgufoiho");
        Arrangement a2 = new Kino("Det jeg gjør", "fjiowgwjiop wnmefo p mo", l2.getNavn(), 4, 124.4, 123, dArray, LocalDateTime.now(), bildeIkon, kp, "hgufoiho", 128, 18);
        Arrangement a3 = new Konsert("Fakta og sånt", "fjiowgwjiop wnmefo p mo", l3.getNavn(), 5, 123, 124, dArray, LocalDateTime.now(), bildeIkon, kp, "hgufoiho");
        Arrangement a4 = new Teater("Foredrag om foredrag", "fjiowgwjiop wnmefo p mo", l3.getNavn(), 6, 44.5, 98, dArray, LocalDateTime.now(), bildeIkon1, kp, "hgufoiho");
        
        l1.settInnArr(a);
        l2.settInnArr(a1);
        l3.settInnArr(a3);
        l2.settInnArr(a2);
        l3.settInnArr(a4);
        
        vindu = new JPanel(new GridBagLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        hovedPanel = new JPanel(new GridBagLayout());
        hovedPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        
        banner = new BannerPanel();
        banner.setMinimumSize(new Dimension(BANNERBREDDE, BANNERHOYDE));
        banner.setMaximumSize(new Dimension(BANNERBREDDE, BANNERHOYDE));
        
        meny = new JPanel(new GridBagLayout());
        meny.setPreferredSize(new Dimension(MENYBREDDE, 350));
        
        infoPanel = new JPanel(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(535, 530));
        infoPanel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.lightGray));
                
        menyOverskrift = new JLabel("Meny");
        menyOverskrift.setFont(new Font(menyOverskrift.getFont().getName(), Font.BOLD, 20));
        menyOverskrift.setPreferredSize(new Dimension(MENYBREDDE - 60, menyOverskrift.getPreferredSize().height));
        arrangementer = new JLabel("Arrangement");
        arrangementer.setFont(new Font(arrangementer.getFont().getName(), Font.BOLD, 18));
        arrangementer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        arrangementer.addMouseListener(labellytter);
        underholdning = new JLabel("Underholdning");
        underholdning.setFont(new Font(underholdning.getFont().getName(), Font.PLAIN, 16));
        underholdning.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        underholdning.addMouseListener(labellytter);
        faglig = new JLabel("Faglig arrangement");
        faglig.setFont(new Font(faglig.getFont().getName(), Font.PLAIN, 16));
        faglig.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        faglig.addMouseListener(labellytter);
        bForestilling = new JLabel("Barneforestilling");
        bForestilling.setFont(new Font(bForestilling.getFont().getName(), Font.PLAIN, 14));
        bForestilling.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bForestilling.addMouseListener(labellytter);
        debattkveld = new JLabel("Debattkveld");
        debattkveld.setFont(new Font(debattkveld.getFont().getName(), Font.PLAIN, 14));
        debattkveld.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        debattkveld.addMouseListener(labellytter);
        foredrag = new JLabel("Foredrag");
        foredrag.setFont(new Font(foredrag.getFont().getName(), Font.PLAIN, 14));
        foredrag.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        foredrag.addMouseListener(labellytter);
        kino = new JLabel("Kino");
        kino.setFont(new Font(kino.getFont().getName(), Font.PLAIN, 14));
        kino.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        kino.addMouseListener(labellytter);
        konsert = new JLabel("Konsert");
        konsert.setFont(new Font(konsert.getFont().getName(), Font.PLAIN, 14));
        konsert.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        konsert.addMouseListener(labellytter);
        politiskM = new JLabel("Politisk møte");
        politiskM.setFont(new Font(politiskM.getFont().getName(), Font.PLAIN, 14));
        politiskM.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        politiskM.addMouseListener(labellytter);
        teater = new JLabel("Teater");
        teater.setFont(new Font(teater.getFont().getName(), Font.PLAIN, 14));
        teater.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        teater.addMouseListener(labellytter);
        
        fra = new JLabel("Fra: ");
        til = new JLabel("Til: ");
        
        int getDag = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int getMaaned = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int getAar = Calendar.getInstance().get(Calendar.YEAR);

        fraDag = new JComboBox<>(tallArray("Dag", 1, 31));
        fraDag.setSelectedIndex(getDag);
        fraDag.setPreferredSize(new Dimension(50, 20));

        fraMaaned = new JComboBox<>(tallArray("Måned", 1, 12));
        fraMaaned.setSelectedIndex(getMaaned);
        fraMaaned.setPreferredSize(new Dimension(70, 20));
        
        fraAar = new JComboBox<>(tallArray("År", getAar, getAar + 10));
        fraAar.setSelectedIndex(1);
        fraAar.setPreferredSize(new Dimension(65, 20));
        
        tilDag = new JComboBox<>(tallArray("Dag", 1, 31));
        tilDag.setSelectedIndex(getDag);
        tilDag.setPreferredSize(new Dimension(50, 20));
        
        tilMaaned = new JComboBox<>(tallArray("Måned", 1, 12));
        tilMaaned.setSelectedIndex(getMaaned);
        tilMaaned.setPreferredSize(new Dimension(70, 20));
        
        tilAar = new JComboBox<>(tallArray("År", getAar, getAar + 10));
        tilAar.setSelectedIndex(1);
        tilAar.setPreferredSize(new Dimension(65, 20));
        
        fraPanel = new JPanel(new FlowLayout());
        fraPanel.add(fra);
        fraPanel.add(fraDag);
        fraPanel.add(fraMaaned);
        fraPanel.add(fraAar);
        
        tilPanel = new JPanel(new FlowLayout());
        tilPanel.add(til);
        tilPanel.add(tilDag);
        tilPanel.add(tilMaaned);
        tilPanel.add(tilAar);
        
        datoPanel = new JPanel(new BorderLayout());
        datoPanel.add(fraPanel, BorderLayout.LINE_START);
        datoPanel.add(tilPanel, BorderLayout.LINE_END);        
        
        ImageIcon[] aL = arrangementerListe();
        if(aL == null)
        {
            aL = new ImageIcon[1];
            visMelding("Det finnes ingen arrangementer i systemet.\nProgrammet vil starte med en tom liste.");
        }
        
        infoFelt = new JList<>(aL);
        infoFelt.setVisibleRowCount(VALGHOYDE);
        infoFelt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        infoFelt.setSelectedIndex(0);
        infoFelt.setBackground(this.getBackground());

        DefaultListCellRenderer renderer = (DefaultListCellRenderer)infoFelt.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        
        infoScroll = new JScrollPane(infoFelt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        infoScroll.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        
        infoKnapp = new JButton("Info");
        infoKnapp.addActionListener(knappelytter);
        kjopKnapp = new JButton("Kjøp billett");
        kjopKnapp.addActionListener(knappelytter);
        
        knapper = new JPanel(new BorderLayout());
        knapper.add(infoKnapp, BorderLayout.LINE_START);
        knapper.add(kjopKnapp, BorderLayout.LINE_END);
        knapper.setBorder(BorderFactory.createEmptyBorder(0, 150, 5, 150));
        
        infoPanel.add(datoPanel, BorderLayout.PAGE_START);
        infoPanel.add(infoScroll, BorderLayout.CENTER);
        infoPanel.add(knapper, BorderLayout.PAGE_END);
        
        GridBagConstraints gbcM = new GridBagConstraints();
        gbcM.anchor = GridBagConstraints.LINE_START;
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
    
    private ImageIcon[] arrangementerListe()
    {
        LocalDate[] datoArray;
        try
        {
            int fDag = Integer.parseInt((String)fraDag.getSelectedItem());
            int fMaaned = Integer.parseInt((String)fraMaaned.getSelectedItem());
            int fAar = Integer.parseInt((String)fraAar.getSelectedItem());
            int tDag = Integer.parseInt((String)tilDag.getSelectedItem());
            int tMaaned = Integer.parseInt((String)tilMaaned.getSelectedItem());
            int tAar = Integer.parseInt((String)tilAar.getSelectedItem());
            
            LocalDateTime ldtFra = LocalDateTime.of(fAar, fMaaned, fDag, 00, 00);
            LocalDateTime ldtTil = LocalDateTime.of(tAar, tMaaned, tDag, 23, 59);
            int antD = (int)Duration.between(ldtFra, ldtTil).toDays();
            System.out.println(antD + "");
            
            if(antD <= 0 && ldtFra.getDayOfMonth() > ldtTil.getDayOfMonth())
            {
                visMelding("Til-dato må være større enn fra-dato.");
                return null;
            }
            
            datoArray = new LocalDate[antD + 1];
            datoArray[0] = ldtFra.toLocalDate();
            for(int i = 1; i < datoArray.length; i++)
            {
                ldtFra = ldtFra.plusDays(1);
                datoArray[i] = ldtFra.toLocalDate();
            }
        }
        catch(NumberFormatException nfe)
        {
            visMelding("Feil i formateringen av tall.");
            return null;
        }
        catch(DateTimeException dte)
        {
            visMelding("Du har valgt en ugyldig dato.");
            return null;
        }
        arrListe = lregister.getArrangementer(arrType, datoArray);
        if(arrListe == null)
            return null;
        
        Arrays.sort(arrListe, new ArrangementDatoSammenlikner());
        
        ImageIcon[] arrFrameListe = new ImageIcon[arrListe.length];
        for(int i = 0; i < arrFrameListe.length; i++)
        {
            JFrame ramme = new JFrame();
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel navn = new JLabel(arrListe[i].getNavn());
            navn.setFont(new Font(navn.getFont().getName(), Font.BOLD, 16));
            navn.setPreferredSize(new Dimension(200, navn.getSize().height + 25));
            JLabel pris = new JLabel("Pris: " + krFormat.format(arrListe[i].getBillettprisBarn()) + ",- / " + krFormat.format(arrListe[i].getBillettprisVoksen()) + ",-");
            JLabel dato = new JLabel("Dato: " + arrListe[i].getDatoString());
            
            ArrbildePanel plakat = new ArrbildePanel(arrListe[i].getArrBilde());
            panel.add(plakat);
            
            JPanel info = new JPanel(new GridLayout(3, 1, 5, 5));
            info.add(navn);
            info.add(pris);
            info.add(dato);
            
            panel.add(info);
            panel.setPreferredSize(new Dimension(375, 160));
            
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
        return arrFrameListe;
    }
    
    public void oppdaterArrangementer(int t)
    {
        arrType = t;
        ImageIcon[] aL = arrangementerListe();
        if(aL == null)
        {
            aL = new ImageIcon[1];
            visMelding("Det finnes ingen arrangementer i den valgte kategorien og perioden.");
            infoKnapp.setEnabled(false);
            kjopKnapp.setEnabled(false);
        }
        else
        {
            infoKnapp.setEnabled(true);
            kjopKnapp.setEnabled(true);
        }
        infoFelt.setListData(aL);
        infoFelt.setSelectedIndex(0);
    }
    
    private void visInfo()
    {
        JFrame infoVindu = new InfoArr(arrListe[infoFelt.getSelectedIndex()], true);
        infoVindu.setLocationRelativeTo(this);
    }
    
    private void kjopBillett()
    {
        JFrame kjopVindu = new Kjop(arrListe[infoFelt.getSelectedIndex()]);
        kjopVindu.setLocationRelativeTo(this);
    }
    
    private void visMelding(String melding)
    {
        JOptionPane.showMessageDialog(this, melding);
    }
    
    private String[] tallArray(String tittel, int start, int slutt)
    {
        String[] tall = new String[slutt - start + 2];
        tall[0] = tittel;
        for(int i = 1; i < tall.length; i++)
        {
            tall[i] = ((start < 10)?"" + 0:"") + start++;
        }
        return tall;
    }
    
    private void resetValgtKnapp()
    {
        arrangementer.setFont(new Font(arrangementer.getFont().getName(), Font.PLAIN, 18));
        underholdning.setFont(new Font(underholdning.getFont().getName(), Font.PLAIN, 16));
        faglig.setFont(new Font(faglig.getFont().getName(), Font.PLAIN, 16));
        bForestilling.setFont(new Font(bForestilling.getFont().getName(), Font.PLAIN, 14));
        debattkveld.setFont(new Font(debattkveld.getFont().getName(), Font.PLAIN, 14));
        foredrag.setFont(new Font(foredrag.getFont().getName(), Font.PLAIN, 14));
        kino.setFont(new Font(kino.getFont().getName(), Font.PLAIN, 14));
        konsert.setFont(new Font(konsert.getFont().getName(), Font.PLAIN, 14));
        politiskM.setFont(new Font(politiskM.getFont().getName(), Font.PLAIN, 14));
        teater.setFont(new Font(teater.getFont().getName(), Font.PLAIN, 14));
    }
    
    private class Knappelytter implements ActionListener //Kommandolytteren som bestemmer hvilken metode som blir utført utifra hvilken knapp det blir trykket på
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == infoKnapp)
            {
                if(arrListe != null)
                    visInfo();
            }
            else if(e.getSource() == kjopKnapp)
            {
                if(arrListe != null)
                    kjopBillett();
            }
        }
    }
    
    private class Labellytter implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            if(e.getSource() == arrangementer)
            {
                oppdaterArrangementer(ALLE);
                resetValgtKnapp();
                arrangementer.setFont(new Font(arrangementer.getFont().getName(), Font.BOLD, arrangementer.getFont().getSize()));
            }
            else if(e.getSource() == faglig)
            {
                oppdaterArrangementer(FAGLIGE);
                resetValgtKnapp();
                faglig.setFont(new Font(faglig.getFont().getName(), Font.BOLD, faglig.getFont().getSize()));
            }
            else if(e.getSource() == underholdning)
            {
                oppdaterArrangementer(UNDERHOLDNING);
                resetValgtKnapp();
                underholdning.setFont(new Font(underholdning.getFont().getName(), Font.BOLD, underholdning.getFont().getSize()));
            }
            else if(e.getSource() == debattkveld)
            {
                oppdaterArrangementer(DEBATT);
                resetValgtKnapp();
                debattkveld.setFont(new Font(debattkveld.getFont().getName(), Font.BOLD, debattkveld.getFont().getSize()));
            }
            else if(e.getSource() == foredrag)
            {
                oppdaterArrangementer(FOREDRAG);
                resetValgtKnapp();
                foredrag.setFont(new Font(foredrag.getFont().getName(), Font.BOLD, foredrag.getFont().getSize()));
            }
            else if(e.getSource() == politiskM)
            {
                oppdaterArrangementer(POLITISK_MOTE);
                resetValgtKnapp();
                politiskM.setFont(new Font(politiskM.getFont().getName(), Font.BOLD, politiskM.getFont().getSize()));
            }
            else if(e.getSource() == bForestilling)
            {
                oppdaterArrangementer(BARNE_FORESTILLING);
                resetValgtKnapp();
                bForestilling.setFont(new Font(bForestilling.getFont().getName(), Font.BOLD, bForestilling.getFont().getSize()));
            }
            else if(e.getSource() == kino)
            {
                oppdaterArrangementer(KINO);
                resetValgtKnapp();
                kino.setFont(new Font(kino.getFont().getName(), Font.BOLD, kino.getFont().getSize()));
            }
            else if(e.getSource() == konsert)
            {
                oppdaterArrangementer(KONSERT);
                resetValgtKnapp();
                konsert.setFont(new Font(konsert.getFont().getName(), Font.BOLD, konsert.getFont().getSize()));
            }
            else if(e.getSource() == teater)
            {
                oppdaterArrangementer(TEATER);
                resetValgtKnapp();
                teater.setFont(new Font(teater.getFont().getName(), Font.BOLD, teater.getFont().getSize()));
            }
        }

        public void mousePressed(MouseEvent e) { }

        public void mouseReleased(MouseEvent e) { }

        public void mouseEntered(MouseEvent e) { }

        public void mouseExited(MouseEvent e) { }
    }
} //End of class Hovedside