

/*Opprettet av: Thomas Kristiansen
Sist endret: 18.05.2015

Filen inneholder klassen InternHovedside.*/

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/*Klassen er en subklasse av JFrame. Klassen er hovedvinduet for ansatte. Vinduet viser en liste over alle arrangementer
innenfor en valgt tidsperiode, samt valg for liste av alle kontaktpersoner og lokaler. Om arrangementene og kontaktpersonene kan
de ansatte få se mer info om hvert enkelt objekt, og for arrangementer kan de ansatte avlyse et bestemt objekt.
I en meny øverst på siden kan de ansatte også opprette arrangementer, kontaktpersoner og lokaler, samt se statistikk.*/
public class InternHovedside extends JFrame
{
    private static final int MENYBREDDE = 250;
    private static final int MENYHOYDE = 425;
    private static final int INFOPANELBREDDE = 700;
    private static final int INFOPANELHOYDE = 530;
    private static final int SCROLLSPEED = 16;
    private static final int VISANTRADER = 27;
    
    private JPanel vindu, meny, hovedPanel, infoPanel, datoPanel, fraPanel, tilPanel, knapper;
    private JTable infoTabell;
    private AbstractTableModel tabellmodell;
    private JScrollPane tabellScroll, mainScroll;
    private String[] kolNavn;
    private String[] kolArr = {"Arrangement navn", "Dato", "Pris barn", "Pris Voksen", "Lokale", "Ledig plasser"};
    private String[] kolKPerson = {"ID", "Etternavn", "Fornavn", "Firma", "Telefon", "E-post", "Nettside"};
    private String[] kolLokale = {"Navn", "Type lokale", "Antall plasser", "Nummererte plasser"};
    private String[][] infoListe;
    private JLabel menyOverskrift, arrangementer, underholdning, faglig, bForestilling, debattkveld, foredrag, kino, konsert, politiskM, teater, kPersoner, lokaler, fra, til;
    private JTextField fraFelt, tilFelt;
    private Arrangement[] arrListe;
    private Kontaktperson[] kPersonListe;
    private Lokale[] lokaleListe;
    private JButton infoKnapp, avlysKnapp;
    private JMenuBar menylinje;
    private JMenu opprettMeny, statistikkMeny;
    private JMenuItem opprettValgK, opprettValgL, opprettValgA, statistikkValgP, statistikkValgH;
    private int type = Kulturhus.ALLE;
    
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d. MMMM uuuu  'kl.' HH:mm");
    private DateTimeFormatter dtfDato = DateTimeFormatter.ofPattern("d-M-uuuu");
    private DateTimeFormatter dtfTid = DateTimeFormatter.ofPattern("d-M-uuuu-HH-mm");
    
    private Knappelytter knappelytter;
    private Labellytter labellytter;
    
    private Lokalregister lregister;
    private Kontaktpersonregister kpregister;
    
    /*Metoden er konstruktøren til klassen InternHovedside.
    Konstruktøren oppretter og setter sammen objektene som utgjør utseendet til vinduet.
    Parametrenes betydning: l = Lokalregister objektet som brukes felles for hele programmet,
    k = Kontaktpersonregister objektet som brukes felles for hele programmet.*/
    public InternHovedside(Lokalregister l, Kontaktpersonregister k)
    {
        super("Målselv Kommune - For ansatte");
        
        lregister = l;
        kpregister = k;
        knappelytter = new Knappelytter();
        labellytter = new Labellytter();
        
        opprettMeny = new JMenu("Opprett");
        opprettMeny.setMnemonic('O');
        
        opprettValgK = new JMenuItem("Kontaktperson");
        opprettValgK.setMnemonic('K');
        opprettValgK.addActionListener(knappelytter);
        opprettMeny.add(opprettValgK);
        
        opprettValgL = new JMenuItem("Lokale");
        opprettValgL.setMnemonic('L');
        opprettValgL.addActionListener(knappelytter);
        opprettMeny.add(opprettValgL);
        
        opprettValgA = new JMenuItem("Arrangement");
        opprettValgA.setMnemonic('A');
        opprettValgA.addActionListener(knappelytter);
        opprettMeny.add(opprettValgA);
        
        statistikkMeny = new JMenu("Statistikk");
        statistikkMeny.setMnemonic('S');
        
        statistikkValgP = new JMenuItem("Arr. per lokale");
        statistikkValgP.setMnemonic('P');
        statistikkValgP.addActionListener(knappelytter);
        statistikkMeny.add(statistikkValgP);
        
        statistikkValgH = new JMenuItem("Arr. med høyest inntekt");
        statistikkValgH.setMnemonic('H');
        statistikkValgH.addActionListener(knappelytter);
        statistikkMeny.add(statistikkValgH);
        
        menylinje = new JMenuBar();
        setJMenuBar(menylinje);
        menylinje.add(opprettMeny);
        menylinje.add(statistikkMeny);
        
        vindu = new JPanel(new FlowLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        hovedPanel = new JPanel(new GridBagLayout());
        hovedPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        
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
        kPersoner = new JLabel("Kontaktpersoner");
        kPersoner.setFont(new Font(kPersoner.getFont().getName(), Font.PLAIN, 18));
        kPersoner.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        kPersoner.addMouseListener(labellytter);
        lokaler = new JLabel("Lokaler");
        lokaler.setFont(new Font(lokaler.getFont().getName(), Font.PLAIN, 18));
        lokaler.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lokaler.addMouseListener(labellytter);
        
        fra = new JLabel("Fra: ");
        til = new JLabel("Til: ");
        
        fraFelt = new JTextField(10);
        fraFelt.setText(dtfDato.format(LocalDate.now()));
        tilFelt = new JTextField(10);
        tilFelt.setText(dtfDato.format(LocalDate.now()));
        
        fraPanel = new JPanel(new FlowLayout());
        fraPanel.add(fra);
        fraPanel.add(fraFelt);
        
        tilPanel = new JPanel(new FlowLayout());
        tilPanel.add(til);
        tilPanel.add(tilFelt);
        
        datoPanel = new JPanel(new FlowLayout());
        datoPanel.add(fraPanel);
        datoPanel.add(tilPanel);
        
        infoListe = arrInfoListe();
        if(infoListe == null)
        {
            infoListe = new String[VISANTRADER][kolNavn.length];
            visMelding("Det finnes ingen arrangementer i den valgte kategorien og perioden.");
        }
        
        tabellmodell = new Tabellmodell();
        infoTabell = new JTable(tabellmodell);
        infoTabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        infoTabell.setRowSelectionAllowed(true);
        infoTabell.setColumnSelectionAllowed(false);
        infoTabell.setRowSelectionInterval(0, 0);
        infoTabell.getTableHeader().setReorderingAllowed(false);
        infoTabell.getColumnModel().getColumn(0).setPreferredWidth(150);
        infoTabell.getColumnModel().getColumn(1).setPreferredWidth(200);
        infoTabell.getColumnModel().getColumn(2).setPreferredWidth(75);
        infoTabell.getColumnModel().getColumn(3).setPreferredWidth(75);
        infoTabell.getColumnModel().getColumn(4).setPreferredWidth(100);
        infoTabell.getColumnModel().getColumn(5).setPreferredWidth(100);
        
        tabellScroll = new JScrollPane(infoTabell, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabellScroll.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        
        infoKnapp = new JButton("Info");
        infoKnapp.addActionListener(knappelytter);
        avlysKnapp = new JButton("Avlys");
        avlysKnapp.addActionListener(knappelytter);
        
        knapper = new JPanel(new BorderLayout());
        knapper.add(infoKnapp, BorderLayout.LINE_START);
        knapper.add(avlysKnapp, BorderLayout.LINE_END);
        knapper.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 150));
        
        infoPanel.add(datoPanel, BorderLayout.PAGE_START);
        infoPanel.add(tabellScroll, BorderLayout.CENTER);
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
        gbcM.gridy++;
        gbcM.insets = new Insets(5, 0, 5, 5);
        meny.add(kPersoner, gbcM);
        gbcM.gridy++;
        meny.add(lokaler, gbcM);
        
        GridBagConstraints gbcH = new GridBagConstraints();
        gbcH.anchor = GridBagConstraints.FIRST_LINE_START;
        gbcH.gridx = 0;
        gbcH.gridy = 0;
        
        hovedPanel.add(meny, gbcH);
        gbcH.gridx++;
        hovedPanel.add(infoPanel, gbcH);
        
        vindu.add(hovedPanel);
        
        mainScroll = new JScrollPane(vindu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainScroll.getVerticalScrollBar().setUnitIncrement(SCROLLSPEED);
        
        add(hovedPanel);
        
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /*Metode som finner alle arrangementer innen for den valgt tidsperioden og typen ut ifra variabelen type,
    og returnerer en todimensjonal array av String objekter som inneholder info om hvert arrangement.*/
    private String[][] arrInfoListe()
    {
        kolNavn = kolArr;
        LocalDate[] datoArray;
        try
        {
            LocalDateTime ldtFra = LocalDateTime.parse(fraFelt.getText().replaceAll("\\s", "") + "-00-00", dtfTid);
            LocalDateTime ldtTil = LocalDateTime.parse(tilFelt.getText().replaceAll("\\s", "") + "-23-59", dtfTid);
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
        catch(DateTimeParseException dtpe)
        {
            visMelding("Du har valgt en ugyldig dato.");
            return null;
        }
        
        arrListe = lregister.getArrangementer(type, datoArray);
        if(arrListe == null)
            return null;
        
        Arrays.sort(arrListe, new ArrangementDatoSammenlikner());
        
        int antRader = arrListe.length;
        if(antRader < VISANTRADER + 1)
            antRader = VISANTRADER;
        String[][] arrInfoArray = new String[antRader][kolNavn.length];
        for(int i = 0; i < arrListe.length; i++)
        {
            arrInfoArray[i][0] = arrListe[i].getNavn();
            arrInfoArray[i][1] = dtf.format(arrListe[i].getDato());
            arrInfoArray[i][2] = "" + arrListe[i].getBillettprisBarn();
            arrInfoArray[i][3] = "" + arrListe[i].getBillettprisVoksen();
            arrInfoArray[i][4] = arrListe[i].getLokaleNavn();
            arrInfoArray[i][5] = "" + arrListe[i].getLedigePlasser();
        }
        return arrInfoArray;
    }
    
    //Metode som finner alle lokaler og returnerer en todimensjonal array av String objekter som inneholder info om hvert lokale.
    private String[][] lokaleInfoListe()
    {
        kolNavn = kolLokale;
        lokaleListe = lregister.getLokaler();
        if(lokaleListe == null)
            return null;
        
        Arrays.sort(lokaleListe, new Lokalesammenlikner());
        
        int antRader = lokaleListe.length;
        if(antRader < VISANTRADER + 1)
            antRader = VISANTRADER;
        String[][] lokaleInfoArray = new String[antRader][kolNavn.length];
        for(int i = 0; i < lokaleListe.length; i++)
        {
            lokaleInfoArray[i][0] = lokaleListe[i].getNavn();
            lokaleInfoArray[i][1] = lokaleListe[i].getTypeTekst();
            lokaleInfoArray[i][2] = "" + lokaleListe[i].getAntPlasser();
            lokaleInfoArray[i][3] = (lokaleListe[i].getHarNrPlasser() == true)?"Ja":"Nei";
        }
        return lokaleInfoArray;
    }
    
    //Metode som finner alle kontaktpersoner og returnerer en todimensjonal array av String objekter som inneholder info om hver kontaktperson.
    private String[][] kPersonInfoListe()
    {
        kolNavn = kolKPerson;
        kPersonListe = kpregister.getKontaktpersoner();
        if(kPersonListe == null)
            return null;
        
        Arrays.sort(kPersonListe, new KPersonsammenlikner());
        
        int antRader = kPersonListe.length;
        if(antRader < VISANTRADER +1)
            antRader = VISANTRADER;
        String[][] kPersonInfoArray = new String[antRader][kolNavn.length];
        for(int i = 0; i < kPersonListe.length; i++)
        {
            kPersonInfoArray[i][0] = kPersonListe[i].getIdNr() + ".";
            kPersonInfoArray[i][1] = kPersonListe[i].getEtternavn();
            kPersonInfoArray[i][2] = kPersonListe[i].getFornavn();
            kPersonInfoArray[i][3] = kPersonListe[i].getFirma();
            kPersonInfoArray[i][4] = "" + kPersonListe[i].getTlfNr();
            kPersonInfoArray[i][5] = kPersonListe[i].getEpost();
            kPersonInfoArray[i][6] = kPersonListe[i].getNettside();
        }
        return kPersonInfoArray;
    }
    
    //Metode som åpner et info vindu for enten arrangementet eller kontaktpersonen som er markert i listen
    private void visInfo()
    {
        JFrame infoVindu;
        if(type == Kulturhus.KONTAKTPERSON)
        {
            if(kPersonListe == null)
            {
                visMelding("Kontaktperson liste tom.");
                return;
            }
            if(infoTabell.getSelectedRow() >= kPersonListe.length)
            {
                visMelding("Du må velge en kontaktperson for å se info.");
                return;
            }
            infoVindu = new InfoKPerson(kPersonListe[infoTabell.getSelectedRow()]);
        }
        else
        {
            if(arrListe == null)
            {
                visMelding("Arrangement liste tom.");
                return;
            }   
            if(infoTabell.getSelectedRow() >= arrListe.length)
            {
                visMelding("Du må velge et arrangement for å se info.");
                return;
            }
            infoVindu = new InfoArr(arrListe[infoTabell.getSelectedRow()], false);
        }
        infoVindu.setLocationRelativeTo(this);
    }
    
    /*Metode som først spør den ansatte om å bekrefte at de vil slette det valgt arrangementet,
    for så å slette arrangementet og vise et vindu med info om alle som hadde kjøpt billetter til arrangementet.*/
    private void avlysArr()
    {
        if(infoTabell.getSelectedRow() >= arrListe.length)
        {
            visMelding("Du må velge et arrangement for å avlyset det.");
            return;
        }
        Arrangement a = arrListe[infoTabell.getSelectedRow()];
        String svar = JOptionPane.showInputDialog(this, "Du holder på å avlyse \"" + a.getNavn() + "\"\nHvis du er sikker på å avlyse arrangementet, skriv inn \"AVLYS\" under:", "Avlys arrangement", JOptionPane.PLAIN_MESSAGE);
        if(svar == null)
            return;
        
        if(svar.equals("AVLYS"))
        {
            if(!lregister.avlysArr(a))
            {
                visMelding("Arrangementet du prøver å slette eksisterer ikke i registeret.");
                return;
            }
            visMelding("Arrangementet \"" + a.getNavn() + "\" er avlyst.\nTrykk OK for info om kjøpte billetter");
            BilKjoperInfoVindu bkiVindu = new BilKjoperInfoVindu(a);
            bkiVindu.setLocationRelativeTo(this);
            oppdaterTabell(type, null);
        }
        else
        {
            visMelding("Feil kode skrevet inn. Arrangement ikke slettet.");
            avlysArr();
        }
    }
    
    /*Metode som oppdaterer tabellen ved hjelp av parametrene. Parametrenes betydning:
    t = hva slags type som skal vises i form av et heltall(se Kulturhus.java), knapp = JLabelen som er trykke på i menyen.*/
    private void oppdaterTabell(int t, JLabel knapp)
    {
        type = t;
        if(type == Kulturhus.KONTAKTPERSON)
            infoListe = kPersonInfoListe();
        else if(type == Kulturhus.LOKALE)
            infoListe = lokaleInfoListe();
        else
            infoListe = arrInfoListe();
        
        if(infoListe == null)
        {
            infoListe = new String[VISANTRADER][kolNavn.length];
            if(type == Kulturhus.KONTAKTPERSON || type == Kulturhus.LOKALE)
                visMelding("Det finnes ingen " + ((type == Kulturhus.KONTAKTPERSON)?"kontaktpersoner":"lokaler") + " i systemet");
            else
                visMelding("Det finnes ingen arrangementer i den valgte kategorien og perioden.");
            infoKnapp.setEnabled(false);
            avlysKnapp.setEnabled(false);
        }
        else
        {
            infoKnapp.setEnabled(true);
            avlysKnapp.setEnabled(true);
        }
        tabellmodell.fireTableDataChanged();
        tabellmodell.fireTableStructureChanged();
        if(type == Kulturhus.KONTAKTPERSON)
        {
            infoTabell.getColumnModel().getColumn(0).setPreferredWidth(20);
            infoTabell.getColumnModel().getColumn(1).setPreferredWidth(100);
            infoTabell.getColumnModel().getColumn(2).setPreferredWidth(100);
            infoTabell.getColumnModel().getColumn(3).setPreferredWidth(100);
            infoTabell.getColumnModel().getColumn(4).setPreferredWidth(100);
            infoTabell.getColumnModel().getColumn(5).setPreferredWidth(190);
            infoTabell.getColumnModel().getColumn(6).setPreferredWidth(190);
            fraFelt.setEnabled(false);
            tilFelt.setEnabled(false);
            avlysKnapp.setEnabled(false);
            infoKnapp.setEnabled(true);
        }
        else if(type == Kulturhus.LOKALE)
        {
            infoTabell.getColumnModel().getColumn(0).setPreferredWidth(200);
            infoTabell.getColumnModel().getColumn(1).setPreferredWidth(200);
            infoTabell.getColumnModel().getColumn(2).setPreferredWidth(100);
            infoTabell.getColumnModel().getColumn(3).setPreferredWidth(200);
            fraFelt.setEnabled(false);
            tilFelt.setEnabled(false);
            avlysKnapp.setEnabled(false);
            infoKnapp.setEnabled(false);
        }
        else
        {
            infoTabell.getColumnModel().getColumn(0).setPreferredWidth(150);
            infoTabell.getColumnModel().getColumn(1).setPreferredWidth(200);
            infoTabell.getColumnModel().getColumn(2).setPreferredWidth(75);
            infoTabell.getColumnModel().getColumn(3).setPreferredWidth(75);
            infoTabell.getColumnModel().getColumn(4).setPreferredWidth(100);
            infoTabell.getColumnModel().getColumn(5).setPreferredWidth(100);
            fraFelt.setEnabled(true);
            tilFelt.setEnabled(true);
            avlysKnapp.setEnabled(true);
            infoKnapp.setEnabled(true);
        }
        infoTabell.setRowSelectionInterval(0, 0);
        if(knapp != null)
            setValgtKnapp(knapp);
    }
    
    /*Metode som setter fonten til alle meny-knappene til PLAIN(vanlig)
    og setter fonten til JLabelen knapp, som blir sendt med som parameter, til BOLD(fet).*/
    private void setValgtKnapp(JLabel knapp)
    {
        arrangementer.setFont(new Font(arrangementer.getFont().getName(), Font.PLAIN, arrangementer.getFont().getSize()));
        underholdning.setFont(new Font(underholdning.getFont().getName(), Font.PLAIN, underholdning.getFont().getSize()));
        faglig.setFont(new Font(faglig.getFont().getName(), Font.PLAIN, faglig.getFont().getSize()));
        bForestilling.setFont(new Font(bForestilling.getFont().getName(), Font.PLAIN, bForestilling.getFont().getSize()));
        debattkveld.setFont(new Font(debattkveld.getFont().getName(), Font.PLAIN, debattkveld.getFont().getSize()));
        foredrag.setFont(new Font(foredrag.getFont().getName(), Font.PLAIN, foredrag.getFont().getSize()));
        kino.setFont(new Font(kino.getFont().getName(), Font.PLAIN, kino.getFont().getSize()));
        konsert.setFont(new Font(konsert.getFont().getName(), Font.PLAIN, konsert.getFont().getSize()));
        politiskM.setFont(new Font(politiskM.getFont().getName(), Font.PLAIN, politiskM.getFont().getSize()));
        teater.setFont(new Font(teater.getFont().getName(), Font.PLAIN, teater.getFont().getSize()));
        kPersoner.setFont(new Font(kPersoner.getFont().getName(), Font.PLAIN, kPersoner.getFont().getSize()));
        lokaler.setFont(new Font(lokaler.getFont().getName(), Font.PLAIN, lokaler.getFont().getSize()));
        
        knapp.setFont(new Font(knapp.getFont().getName(), Font.BOLD, knapp.getFont().getSize()));
    }
    
    //Metode som åpner et RegistreringsVindu objekt og setter den aktive fanen lik parameteren indeks.
    private void visRegVindu(int indeks)
    {
        JFrame regVindu = new RegistreringsVindu(lregister, kpregister, indeks);
        regVindu.setLocationRelativeTo(this);
    }
    
    //Metode som åpner et StatistikkVindu objekt og setter den aktive fanen lik parameteren indeks.
    private void visStatistikkVindu(int indeks)
    {
        JFrame statVindu = new StatistikkVindu(lregister, indeks);
        statVindu.setLocationRelativeTo(this);
    }
    
    //Metode som tar parameteret og oppretter en popup-boks. Parameterets betydning: melding = teksten som skal skrives på popup-boksen.
    private void visMelding(String melding)
    {
        JOptionPane.showMessageDialog(this, melding);
    }
    
    //Privat klasse som er tabellmodellen som blir brukt i tabellen infoTabell
    public class Tabellmodell extends AbstractTableModel
    {       
        public boolean isCellEditable(int rad, int kolonne) //Metode som gjørt at cellene i tabbellen ikke er editerbare
        {  
            return false;  
        }
        
        //Metode som returnerer kolonnenavnet ved hjelp av indeksen gitt ved parameteren kolonne 
        public String getColumnName(int kolonne)
        {
            return kolNavn[kolonne];
        }

        public int getRowCount() //Metode som returnerer antall rader
        {
            return infoListe.length;
        }

        public int getColumnCount() //Metode som returnerer antall kolonner
        {
            return infoListe[0].length;
        }

        //Metode som returnerer verdien til objektet i en bestem celle, bestemt ut ifra parametrene rad og kolonne
        public String getValueAt(int rad, int kolonne)
        {
            return infoListe[rad][kolonne];
        }
    }
    
    //Lasse som bestemmer hvilken metode som blir utført utifra hvilken knapp det blir trykket på
    private class Knappelytter implements ActionListener
    {       
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == infoKnapp)
            {
                visInfo();
            }
            else if(e.getSource() == avlysKnapp)
            {
                if(arrListe != null)
                   avlysArr();
            }
            else if(e.getSource() == opprettValgK)
                visRegVindu(0);
            else if(e.getSource() == opprettValgL)
                visRegVindu(1);
            else if(e.getSource() == opprettValgA)
                visRegVindu(2);
            else if(e.getSource() == statistikkValgP)
                visStatistikkVindu(0);
            else if(e.getSource() == statistikkValgH)
                visStatistikkVindu(1);
        }
    }
    
    //Klasse som bestemmer hvilken metode som blir utført utifra hvilken meny-knapp som blir trykket på.
    private class Labellytter implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            if(e.getSource() == arrangementer)
                oppdaterTabell(Kulturhus.ALLE, arrangementer);
            else if(e.getSource() == faglig)
                oppdaterTabell(Kulturhus.FAGLIGE, faglig);
            else if(e.getSource() == underholdning)
                oppdaterTabell(Kulturhus.UNDERHOLDNING, underholdning);
            else if(e.getSource() == debattkveld)
                oppdaterTabell(Kulturhus.DEBATT, debattkveld);
            else if(e.getSource() == foredrag)
                oppdaterTabell(Kulturhus.FOREDRAG, foredrag);
            else if(e.getSource() == politiskM)
                oppdaterTabell(Kulturhus.POLITISK_MOTE, politiskM);
            else if(e.getSource() == bForestilling)
                oppdaterTabell(Kulturhus.BARNE_FORESTILLING, bForestilling);
            else if(e.getSource() == kino)
                oppdaterTabell(Kulturhus.KINO, kino);
            else if(e.getSource() == konsert)
                oppdaterTabell(Kulturhus.KONSERT, konsert);
            else if(e.getSource() == teater)
                oppdaterTabell(Kulturhus.TEATER, teater);
            else if(e.getSource() == kPersoner)
                oppdaterTabell(Kulturhus.KONTAKTPERSON, kPersoner);
            else if(e.getSource() == lokaler)
                oppdaterTabell(Kulturhus.LOKALE, lokaler);
        }

        public void mousePressed(MouseEvent e) { }

        public void mouseReleased(MouseEvent e) { }

        public void mouseEntered(MouseEvent e) { }

        public void mouseExited(MouseEvent e) { }
    }
}