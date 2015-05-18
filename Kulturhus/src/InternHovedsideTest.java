import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class InternHovedsideTest extends JFrame
{
    private static final int MENYBREDDE = 250;
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
    private static final int KONTAKTPERSON = 7;
    private static final int LOKALE = 8;
    
    private JPanel vindu, meny, hovedPanel, infoPanel, datoPanel, fraPanel, tilPanel, knapper;
    private JTable infoTabell;
    private AbstractTableModel tabellmodell;
    private JScrollPane tabellScroll, mainScroll;
    private String[] kolNavn;
    private String[] kolArr = {"Arrangement navn", "Dato", "Pris barn", "Pris Voksen", "Lokale", "Ledig plasser"};
    private String[] kolKPerson = {"Etternavn", "Fornavn", "Firma", "Telefon", "E-post", "Nettside"};
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
    private JMenuItem opprettValgK, opprettValgL, opprettValgA;
    private int type = ALLE;
    
    private DateTimeFormatter dtfDato = DateTimeFormatter.ofPattern("d-M-uuuu");
    private DateTimeFormatter dtfTid = DateTimeFormatter.ofPattern("d-M-uuuu-HH-mm");
    
    private Knappelytter knappelytter;
    private Labellytter labellytter;
    
    private Lokalregister lregister;
    private Kontaktpersonregister kpregister;
    
    public InternHovedsideTest(Lokalregister l, Kontaktpersonregister k)
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
        
        menylinje = new JMenuBar();
        setJMenuBar(menylinje);
        menylinje.add(opprettMeny);
        menylinje.add(statistikkMeny);
        
        vindu = new JPanel(new FlowLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        hovedPanel = new JPanel(new GridBagLayout());
        hovedPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        
        meny = new JPanel(new GridBagLayout());
        meny.setPreferredSize(new Dimension(MENYBREDDE, 425));
        
        infoPanel = new JPanel(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(700, 530));
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
        if(antRader < 28)
            antRader = 27;
        String[][] arrInfoArray = new String[antRader][kolNavn.length];
        for(int i = 0; i < arrListe.length; i++)
        {
            arrInfoArray[i][0] = arrListe[i].getNavn();
            arrInfoArray[i][1] = arrListe[i].getDatoString();
            arrInfoArray[i][2] = "" + arrListe[i].getBillettprisBarn();
            arrInfoArray[i][3] = "" + arrListe[i].getBillettprisVoksen();
            arrInfoArray[i][4] = arrListe[i].getLokaleNavn();
            arrInfoArray[i][5] = "" + arrListe[i].getLedigePlasser();
        }
        return arrInfoArray;
    }
    
    private String[][] lokaleInfoListe()
    {
        kolNavn = kolLokale;
        lokaleListe = lregister.getLokaler();
        if(lokaleListe == null)
            return null;
        
        Arrays.sort(lokaleListe, new Lokalesammenlikner());
        
        int antRader = lokaleListe.length;
        if(antRader < 28)
            antRader = 27;
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
    
    private String[][] kPersonInfoListe()
    {
        kolNavn = kolKPerson;
        kPersonListe = kpregister.getKontaktpersoner();
        if(kPersonListe == null)
            return null;
        
        Arrays.sort(kPersonListe, new KPersonsammenlikner());
        
        int antRader = kPersonListe.length;
        if(antRader < 28)
            antRader = 27;
        String[][] kPersonInfoArray = new String[antRader][kolNavn.length];
        for(int i = 0; i < kPersonListe.length; i++)
        {
            kPersonInfoArray[i][0] = kPersonListe[i].getEtternavn();
            kPersonInfoArray[i][1] = kPersonListe[i].getFornavn();
            kPersonInfoArray[i][2] = kPersonListe[i].getFirma();
            kPersonInfoArray[i][3] = "" + kPersonListe[i].getTlfNr();
            kPersonInfoArray[i][4] = kPersonListe[i].getEpost();
            kPersonInfoArray[i][5] = kPersonListe[i].getNettside();
        }
        return kPersonInfoArray;
    }
    
    private void visInfo()
    {
        if(infoTabell.getSelectedRow() >= arrListe.length)
        {
            visMelding("Du må velge et arrangement for å se info.");
            return;
        }
        JFrame infoVindu = new InfoArr(arrListe[infoTabell.getSelectedRow()], false);
        infoVindu.setLocationRelativeTo(this);
    }
    
    private void avlysArr()
    {
        if(infoTabell.getSelectedRow() >= arrListe.length)
        {
            visMelding("Du må velge et arrangement for å avlyset det.");
            return;
        }
        Arrangement a = arrListe[infoTabell.getSelectedRow()];
        String svar = JOptionPane.showInputDialog(this, "Du holder på å avlyse \"" + a.getNavn() + "\"\nHvis du er sikker på å avlyse arrangementet, skriv inn \"SLETT\" under:", "Avlys arrangement", JOptionPane.PLAIN_MESSAGE);
        if(svar == null)
            return;
        
        if(svar.equals("SLETT"))
        {
            if(!lregister.avlysArr(a))
            {
                visMelding("Arrangementet du prøver å slette eksisterer ikke i registeret.");
                return;
            }
            visMelding("Arrangementet \"" + a.getNavn() + "\" er avlyst.");
            oppdaterTabell(type, null);
        }
        else
        {
            visMelding("Feil kode skrevet inn. Arrangement ikke slettet.");
            avlysArr();
        }
    }
    
    private void oppdaterTabell(int t, JLabel knapp)
    {
        type = t;
        if(type == KONTAKTPERSON)
            infoListe = kPersonInfoListe();
        else if(type == LOKALE)
            infoListe = lokaleInfoListe();
        else
            infoListe = arrInfoListe();
        
        if(infoListe == null)
        {
            infoListe = new String[27][kolNavn.length];
            if(type == KONTAKTPERSON || type == LOKALE)
                visMelding("Det finnes ingen " + ((type == KONTAKTPERSON)?"kontaktpersoner":"lokaler") + " i systemet");
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
        if(type == KONTAKTPERSON)
        {
            infoTabell.getColumnModel().getColumn(0).setPreferredWidth(100);
            infoTabell.getColumnModel().getColumn(1).setPreferredWidth(100);
            infoTabell.getColumnModel().getColumn(2).setPreferredWidth(100);
            infoTabell.getColumnModel().getColumn(3).setPreferredWidth(100);
            infoTabell.getColumnModel().getColumn(4).setPreferredWidth(200);
            infoTabell.getColumnModel().getColumn(5).setPreferredWidth(200);
            fraFelt.setEnabled(false);
            tilFelt.setEnabled(false);
            avlysKnapp.setEnabled(false);
            infoKnapp.setEnabled(true);
        }
        else if(type == LOKALE)
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
    
    private void visRegVindu(int indeks)
    {
        JFrame regVindu = new RegistreringsVindu(lregister, kpregister, indeks);
        regVindu.setLocationRelativeTo(this);
    }
    
    private void visMelding(String melding)
    {
        JOptionPane.showMessageDialog(this, melding);
    }
    
    public class Tabellmodell extends AbstractTableModel
    {       
        public boolean isCellEditable(int rad, int kolonne)
        {  
            return false;  
        }
        
        public String getColumnName(int kolonne)
        {
            return kolNavn[ kolonne];
        }

        public int getRowCount()
        {
            return infoListe.length;
        }

        public int getColumnCount()
        {
            return infoListe[0].length;
        }

        public String getValueAt(int rad, int kolonne)
        {
            return infoListe[rad][kolonne];
        }
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
        }
    }
    
    private class Labellytter implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            if(e.getSource() == arrangementer)
                oppdaterTabell(ALLE, arrangementer);
            else if(e.getSource() == faglig)
                oppdaterTabell(FAGLIGE, faglig);
            else if(e.getSource() == underholdning)
                oppdaterTabell(UNDERHOLDNING, underholdning);
            else if(e.getSource() == debattkveld)
                oppdaterTabell(DEBATT, debattkveld);
            else if(e.getSource() == foredrag)
                oppdaterTabell(FOREDRAG, foredrag);
            else if(e.getSource() == politiskM)
                oppdaterTabell(POLITISK_MOTE, politiskM);
            else if(e.getSource() == bForestilling)
                oppdaterTabell(BARNE_FORESTILLING, bForestilling);
            else if(e.getSource() == kino)
                oppdaterTabell(KINO, kino);
            else if(e.getSource() == konsert)
                oppdaterTabell(KONSERT, konsert);
            else if(e.getSource() == teater)
                oppdaterTabell(TEATER, teater);
            else if(e.getSource() == kPersoner)
                oppdaterTabell(KONTAKTPERSON, kPersoner);
            else if(e.getSource() == lokaler)
                oppdaterTabell(LOKALE, lokaler);
        }

        public void mousePressed(MouseEvent e) { }

        public void mouseReleased(MouseEvent e) { }

        public void mouseEntered(MouseEvent e) { }

        public void mouseExited(MouseEvent e) { }
    }
}