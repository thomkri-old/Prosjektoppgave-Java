

/*Opprettet av: Sara Torp Myhre
Sist endret: 18.05.2015

Filen inneholder klassen Hovedside.*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

/*Klassen er en subklasse av JFrame. Klassen er hovedvinduet for kundene. Vinduet viser en liste over alle arrangementer
innenfor en valgt tidsperiode, hvor kunden kan se mer info om arrangementene eller kjøpe billetter til de.
I menyen øverst på siden kan også ansatte logge inn for å komme til hovedsiden for ansatte.*/
public class Hovedside extends JFrame implements Serializable
{
    private static final int BANNERBREDDE = 800;
    private static final int BANNERHOYDE = 100;
    private static final int MENYBREDDE = 250;
    private static final int MENYHOYDE = 350;
    private static final int INFOPANELBREDDE = 535;
    private static final int INFOPANELHOYDE = 530;
    private static final int ARRINFOBREDDE = 375;
    private static final int ARRINFOHOYDE = 160;
    private static final int VALGHOYDE = 3;
    private static final int SCROLLSPEED = 16;
    
    private JPanel vindu, meny, hovedPanel, infoPanel, datoPanel, fraPanel, tilPanel, knapper;
    private BannerPanel banner;
    private JList<ImageIcon> infoFelt;
    private JComboBox<String> fraDag, fraMaaned, fraAar, tilDag, tilMaaned, tilAar;
    private JScrollPane infoScroll, mainScroll;
    private JLabel menyOverskrift, arrangementer, underholdning, faglig, bForestilling, debattkveld, foredrag, kino, konsert, politiskM, teater, fra, til;
    private JButton infoKnapp, kjopKnapp;
    private JMenuBar menylinje;
    private JMenu ansattMeny;
    private JMenuItem loggInnValg;
    
    private int arrType = Kulturhus.ALLE;
    private String filnavn = "Registre.data";
    private Arrangement[] arrListe;
    
    private Kontaktpersonregister kpregister;
    private Lokalregister lregister;
    
    private Knappelytter knappelytter;
    private Labellytter labellytter;
    
    private DecimalFormat krFormat;
    private DateTimeFormatter dtf;
    
    /*Metoden er konstruktøren til klassen Hovedside.
    Konstruktøren oppretter og setter sammen objektene som utgjør utseendet til vinduet.*/
    public Hovedside()
    {
        super("Målselv Kommune");
        
        knappelytter = new Knappelytter();
        labellytter = new Labellytter();
        krFormat = new DecimalFormat( "0.00" );
        dtf = DateTimeFormatter.ofPattern("d. MMMM uuuu  'kl.' HH:mm");
        
        ansattMeny = new JMenu("Ansatt");
        ansattMeny.setMnemonic('A');
        
        loggInnValg = new JMenuItem("Logg inn");
        loggInnValg.setMnemonic('L');
        loggInnValg.addActionListener(knappelytter);
        ansattMeny.add(loggInnValg);
        
        menylinje = new JMenuBar();
        setJMenuBar(menylinje);
        menylinje.add(ansattMeny);
        
        vindu = new JPanel(new GridBagLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        hovedPanel = new JPanel(new GridBagLayout());
        hovedPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        
        banner = new BannerPanel();
        banner.setMinimumSize(new Dimension(BANNERBREDDE, BANNERHOYDE));
        banner.setMaximumSize(new Dimension(BANNERBREDDE, BANNERHOYDE));
        
        meny = new JPanel(new GridBagLayout());
        meny.setPreferredSize(new Dimension(MENYBREDDE, MENYHOYDE));
        
        infoPanel = new JPanel(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(INFOPANELBREDDE, INFOPANELHOYDE));
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
        
        knapper = new JPanel(new FlowLayout());
        knapper.add(infoKnapp);
        knapper.add(kjopKnapp);
        
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
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    /*Metode som finner alle arrangementer innen for den valgt tidsperioden og typen ut ifra variabelen arrType,
    og returnerer en array av ImageIcon objekter som er "screeshots" av JPanels med info om hvert arrangement.*/
    private ImageIcon[] arrangementerListe()
    {
        if(lregister == null)
            return null;
        
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
            
            if(antD <= 0 && ldtFra.isAfter(ldtTil))
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
        for(int i = 0; i < arrListe.length; i++)  //Er i denne for-løkka ImageIcon objektene blir opprettet
        {
            JFrame ramme = new JFrame();
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel navn = new JLabel(arrListe[i].getNavn());
            navn.setFont(new Font(navn.getFont().getName(), Font.BOLD, 16));
            navn.setPreferredSize(new Dimension(200, navn.getSize().height + 25));
            JLabel pris = new JLabel("Pris: " + krFormat.format(arrListe[i].getBillettprisBarn()) + ",- / " + krFormat.format(arrListe[i].getBillettprisVoksen()) + ",-");
            JLabel dato = new JLabel("Dato: " + dtf.format(arrListe[i].getDato()));

            ArrbildePanel plakat = new ArrbildePanel(arrListe[i].getArrBilde());
            panel.add(plakat);

            JPanel info = new JPanel(new GridLayout(3, 1, 5, 5));
            info.add(navn);
            info.add(pris);
            info.add(dato);

            panel.add(info);
            panel.setPreferredSize(new Dimension(ARRINFOBREDDE, ARRINFOHOYDE));

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
    
    //Metode som oppdaterer arrangement listen som vises i vinduet
    public void oppdaterArrangementer(int t, JLabel knapp)
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
        setValgtKnapp(knapp);
    }
    
    public void skrivTilFil() //Metode som skriver registrene til en fil med navn lik variabelen filnavn
    {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filnavn)))
        {
            output.writeObject(lregister);
            output.writeObject(kpregister);
            output.writeInt(Arrangement.getNesteId());
            output.writeInt(Kontaktperson.getNesteNr());
        }
        catch (NotSerializableException nse)
        {
            visMelding("En eller flere av objektene er ikke serialiserbare!\nIngen registrering på fil!");
        }
        catch (IOException ioe)
        {
            visMelding("Det oppstod en feil ved skriving til fil.");
        }
    }

    public void lesFraFil() //Metode som leser registrene fra en fil med navn lik variabelen filnavn
    {
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filnavn)))
        {
            lregister = (Lokalregister) input.readObject();
            kpregister = (Kontaktpersonregister) input.readObject();
            Arrangement.setNesteId(input.readInt());
            Kontaktperson.setNesteNr(input.readInt());
        }
        catch(ClassNotFoundException cnfe)
        {
            visMelding("Fant ikke definisjon av objektene.\nOppretter tomme registre.");
            lregister = new Lokalregister();
            kpregister = new Kontaktpersonregister();
        }
        catch(FileNotFoundException fnfe)
        {
            visMelding("Finner ikke angitt fil.\nOppretter tomme registre.");
            lregister = new Lokalregister();
            kpregister = new Kontaktpersonregister();
        }
        catch(IOException ioe)
        {
            visMelding("Fikk ikke lest fra fila.\nOppretter tomme registre.");
            lregister = new Lokalregister();
            kpregister = new Kontaktpersonregister();
        }
        oppdaterArrangementer(Kulturhus.ALLE, arrangementer);
    }
    
    private void visInfo() //Metode som åpner et info vindu for arrangementet som er markert i listen
    {
        JFrame infoVindu = new InfoArr(arrListe[infoFelt.getSelectedIndex()], true);
        infoVindu.setLocationRelativeTo(this);
    }
    
    private void kjopBillett() //Metode som åpner et kjøp vindu for arrangementet som er markert i listen
    {
        JFrame kjopVindu = new Kjop(arrListe[infoFelt.getSelectedIndex()]);
        kjopVindu.setLocationRelativeTo(this);
    }
    
    //Metode som åpner et pop-up vindu der ansatte kan logge inn for å få opp hovedsiden for ansatte
    private void loggInn()
    {
        JPanel loggInnInfo = new JPanel(new FlowLayout());
        JLabel brukernavnLabel = new JLabel("Brukernavn(admin): ");
        JLabel passordLabel = new JLabel("Passord(admin123): ");
        JTextField brukernavnFelt = new JTextField(10);
        JPasswordField passordFelt = new JPasswordField(10);
        
        loggInnInfo.add(brukernavnLabel);
        loggInnInfo.add(brukernavnFelt);
        loggInnInfo.add(passordLabel);
        loggInnInfo.add(passordFelt);
        
        int svar = JOptionPane.showConfirmDialog(this, loggInnInfo, "Logg inn", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(svar == JOptionPane.OK_OPTION)
        {
            String passord = new String(passordFelt.getPassword());
            if(brukernavnFelt.getText().equals("admin") && passord.equals("admin123"))
            {
                InternHovedside internHVindu = new InternHovedside(lregister, kpregister);
                internHVindu.setLocationRelativeTo(this);
            }
            else
            {
                visMelding("Du har skrevet inn et feil brukernavn eller passord.\nTrykk OK for å prøve igjen.");
                loggInn();
            }
        }
    }
    
    //Metode som tar parameteret og oppretter en popup-boks. Parameterets betydning: melding = teksten som skal skrives på popup-boksen.
    private void visMelding(String melding)
    {
        JOptionPane.showMessageDialog(this, melding);
    }
    
    /*Metode som tar parametrene og lager en tekst Array. Parametrenes betydning:
    tittel = det første teks objektet i arrayen, start = starverdien til tallene i arrayen(vil være på indeks 1 i arrayen),
    slutt = sluttverdien til tallene i arrayen(vil være på siste indeksen i arrayen).
    Fra start til slutt i arrayen vil vær plass økes med en fra forrige tall, helt til man treffer sluttverdien.*/
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
    
    /*Metode som setter fonten til alle meny-knappene til PLAIN(vanlig)
    og setter fonten til JLabelen knapp, som blir sendt med som parameter, til BOLD(fet).*/
    private void setValgtKnapp(JLabel knapp)
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
        
        knapp.setFont(new Font(knapp.getFont().getName(), Font.BOLD, knapp.getFont().getSize()));
    }
    
    //Klasse som bestemmer hvilken metode som blir utført utifra hvilken knapp det blir trykket på
    private class Knappelytter implements ActionListener
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
            else if(e.getSource() == loggInnValg)
                loggInn();
        }
    }
    
    //Klasse som bestemmer hvilken metode som blir utført utifra hvilken meny-knapp som blir trykket på.
    private class Labellytter implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            if(e.getSource() == arrangementer)
                oppdaterArrangementer(Kulturhus.ALLE, arrangementer);
            else if(e.getSource() == faglig)
                oppdaterArrangementer(Kulturhus.FAGLIGE, faglig);
            else if(e.getSource() == underholdning)
                oppdaterArrangementer(Kulturhus.UNDERHOLDNING, underholdning);
            else if(e.getSource() == debattkveld)
                oppdaterArrangementer(Kulturhus.DEBATT, debattkveld);
            else if(e.getSource() == foredrag)
                oppdaterArrangementer(Kulturhus.FOREDRAG, foredrag);
            else if(e.getSource() == politiskM)
                oppdaterArrangementer(Kulturhus.POLITISK_MOTE, politiskM);
            else if(e.getSource() == bForestilling)
                oppdaterArrangementer(Kulturhus.BARNE_FORESTILLING, bForestilling);
            else if(e.getSource() == kino)
                oppdaterArrangementer(Kulturhus.KINO, kino);
            else if(e.getSource() == konsert)
                oppdaterArrangementer(Kulturhus.KONSERT, konsert);
            else if(e.getSource() == teater)
                oppdaterArrangementer(Kulturhus.TEATER, teater);
        }

        public void mousePressed(MouseEvent e) { }

        public void mouseReleased(MouseEvent e) { }

        public void mouseEntered(MouseEvent e) { }

        public void mouseExited(MouseEvent e) { }
    }
} //End of class Hovedside