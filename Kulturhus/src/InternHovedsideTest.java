import java.awt.*;
import java.awt.event.*;
import java.time.*;
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
    
    private JPanel vindu, meny, hovedPanel, infoPanel, knapper;
    private JTable infoTabell;
    private JScrollPane tabellScroll, mainScroll;
    private String[] kolNavn = {"Arrangement navn", "Dato", "Pris barn", "Pris Voksen", "Lokale", "Ledig plasser"};
    private String[][] infoListe;
    private JLabel menyOverskrift, arrangementer, underholdning, faglig, bForestilling, debattkveld, foredrag, kino, konsert, politiskM, teater, kPersoner, lokaler;
    private Arrangement[] arrListe;
    private JButton infoKnapp, slettKnapp;
    private int arrType = ALLE;
    
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
        
        vindu = new JPanel(new GridBagLayout());
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
        
        infoListe = arrInfoListe();
        
        infoTabell = new JTable(new Tabellmodell());
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
        slettKnapp = new JButton("Slett");
        slettKnapp.addActionListener(knappelytter);
        
        knapper = new JPanel(new BorderLayout());
        knapper.add(infoKnapp, BorderLayout.LINE_START);
        knapper.add(slettKnapp, BorderLayout.LINE_END);
        knapper.setBorder(BorderFactory.createEmptyBorder(0, 150, 5, 150));
        
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
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        vindu.add(hovedPanel, gbc);
        
        mainScroll = new JScrollPane(vindu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainScroll.getVerticalScrollBar().setUnitIncrement(SCROLLSPEED);
        
        add(hovedPanel);
        
        pack();
        setResizable(false);
        setVisible(true);
    }
    
    private String[][] arrInfoListe()
    {
        LocalDate[] l = new LocalDate[1];
        l[0] = LocalDate.now();
        arrListe = lregister.getArrangementer(arrType, l);
        int antRader = arrListe.length;
        if(antRader < 30)
            antRader = 29;
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
    
    private void visInfo()
    {
        if(infoTabell.getSelectedRow() > arrListe.length - 1)
        {
            visMelding("Du må velge et arrangement for å se info.");
            return;
        }
        JFrame infoVindu = new InfoArr(arrListe[infoTabell.getSelectedRow()], false);
        infoVindu.setLocationRelativeTo(this);
    }
    
    private void visMelding(String melding)
    {
        JOptionPane.showMessageDialog(this, melding);
    }
    
    public class Tabellmodell extends AbstractTableModel
    {       
        public boolean isCellEditable(int row, int column)
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
            else if(e.getSource() == slettKnapp)
            {
                if(arrListe != null)
                    return;
            }
        }
    }
    
    private class Labellytter implements MouseListener
    {
        public void mouseClicked(MouseEvent e)
        {
            /*if(e.getSource() == arrangementer)
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
            }*/
        }

        public void mousePressed(MouseEvent e) { }

        public void mouseReleased(MouseEvent e) { }

        public void mouseEntered(MouseEvent e) { }

        public void mouseExited(MouseEvent e) { }
    }
}