import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistreringsVindu extends JFrame
{
    private final int DEBATT = 0;
    private final int FOREDRAG = 1;
    private final int POLITISK_MOTE = 2;
    private final int BARNE_FORESTILLING = 3;
    private final int KINO = 4;
    private final int KONSERT = 5;
    private final int TEATER = 6;
    private final int KONTAKT_PERSON = 7;
    private final int LOKALE = 8;
    private final int TEATERSAL = 1;
    private final int DEBATTSAL = 2;
    private final int FOREDRAGSSAL = 3;
    private final int KINOSAL = 4;
    private final int KONSERTSAL = 5;
    
    private int type;
    private JPanel vindu, utfylling, knapper;
    private JButton avbrytKnapp, regKnapp;
    private Kommandolytter knappelytter;
    
    private JLabel infoTekst, arrNavn, program, bPrisBarn, bPrisVoksen, deltakere, dato, kPerson, sjanger, aldersgrense, lengde;
    private JTextField navnFelt, bpBarnFelt, bpVoksenFelt, datoFelt, sjangerFelt, alderFelt, lengdeFelt;
    private JTextArea programFelt, deltakereFelt;
    private JScrollPane programScroll, deltakereScroll;
    private JComboBox<String> kPersonValg;
    
    private JLabel fornavn, etternavn, epost, nettside, firma, opplysninger, tlfNr;
    private JTextField fornavnFelt, etternavnFelt, epostFelt, nettsideFelt, firmaFelt, tlfNrFelt;
    private JTextArea opplysningerFelt;
    private JScrollPane opplysningerScroll;
    
    private JLabel lokaleNavn, lokaleType, antPlasser, harNrPlasser;
    private JTextField lokaleNavnFelt, antPlasserFelt;
    private JComboBox<String> lokaleTypeValg;
    private String[] lokaleTypeListe = {"", "Teatersal", "Debattsal", "Foredragssal", "Kinosal", "Konsertsal"};
    private ButtonGroup harNrPlasserGruppe;
    private JRadioButton radioJa, radioNei;
    private JPanel radioKnapper;
    private boolean harNrPVerdi, rKnappTrykket = false;
    
    private Kontaktpersonregister kpregister;
    private Lokalregister lregister;
    
    public RegistreringsVindu(String tittel, int t, Kontaktpersonregister k, Lokalregister l)
    {
        super(tittel);
        type = t;
        kpregister = k;
        lregister = l;
        
        vindu = new JPanel(new GridBagLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        utfylling = new JPanel(new GridBagLayout());
        utfylling.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        knappelytter = new Kommandolytter();
        
        if(type == KONTAKT_PERSON)
            opprettKpersonVindu();
        else if(type == LOKALE)
            opprettLokaleVindu();
        else
            opprettArrVindu();
        
        infoTekst = new JLabel("Fyll inn info:");
        
        avbrytKnapp = new JButton("Avbryt");
        avbrytKnapp.addActionListener(knappelytter);
        regKnapp = new JButton("Registrer");
        regKnapp.addActionListener(knappelytter);
        
        GridBagConstraints gbcK = new GridBagConstraints();
        gbcK.anchor = GridBagConstraints.LINE_START;
        gbcK.insets = new Insets(5, 5, 5, 5);
        gbcK.gridx = 0;
        gbcK.gridy = 0;
        
        knapper = new JPanel(new GridBagLayout());
        knapper.add(avbrytKnapp, gbcK);
        
        gbcK.gridx++;
        gbcK.anchor = GridBagConstraints.LINE_END;
        
        knapper.add(regKnapp, gbcK);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        vindu.add(infoTekst, gbc);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridy++;
        vindu.add(utfylling, gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy++;
        vindu.add(knapper, gbc);
        
        add(vindu);
        pack();
        setResizable(false);
        setVisible(true);
    }
    
    private void opprettArrVindu()
    {
        arrNavn = new JLabel("Arrangement navn:");
        program = new JLabel("Program:");
        bPrisBarn = new JLabel("Billettpris barn:");
        bPrisVoksen = new JLabel("Billettpris voksen:");
        deltakere = new JLabel("Deltakere (skilles med komma \",\"):");
        dato = new JLabel("Dato:");
        kPerson = new JLabel("Kontakt person:");
        aldersgrense = new JLabel("Aldersgrense:");
        lengde = new JLabel("Lengde (i minutter):");
        
        if(type == DEBATT || type == FOREDRAG || type == POLITISK_MOTE)
            sjanger = new JLabel("Tema:");
        else
            sjanger = new JLabel("Sjanger:");
        
        navnFelt = new JTextField(15);
        bpBarnFelt = new JTextField(4);
        bpVoksenFelt = new JTextField(4);
        datoFelt = new JTextField(8);
        sjangerFelt = new JTextField(10);
        alderFelt = new JTextField(5);
        lengdeFelt = new JTextField(10);
        
        programFelt = new JTextArea(5, 15);
        programFelt.setLineWrap(true);
        programFelt.setWrapStyleWord(true);
        
        programScroll = new JScrollPane(programFelt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        deltakereFelt = new JTextArea(2, 15);
        deltakereFelt.setLineWrap(true);
        deltakereFelt.setWrapStyleWord(true);
        
        deltakereScroll = new JScrollPane(deltakereFelt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        kPersonValg = new JComboBox<>();
        kPersonValg.setPreferredSize(new Dimension(200, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        utfylling.add(arrNavn, gbc);
        gbc.gridy++;
        utfylling.add(sjanger, gbc);
        gbc.gridy++;        
        utfylling.add(program, gbc);
        gbc.gridy++;
        utfylling.add(bPrisBarn, gbc);
        gbc.gridy++;
        utfylling.add(bPrisVoksen, gbc);
        
        if(type == KINO)
        {
            gbc.gridy++;
            utfylling.add(aldersgrense, gbc);
            gbc.gridy++;
            utfylling.add(lengde, gbc);
        }
        
        gbc.gridy++;
        utfylling.add(deltakere, gbc);
        gbc.gridy++;
        utfylling.add(dato, gbc);
        gbc.gridy++;
        utfylling.add(kPerson, gbc);
        
        gbc.gridx++;
        gbc.gridy = 0;
        utfylling.add(navnFelt, gbc);
        gbc.gridy++;
        utfylling.add(sjangerFelt, gbc);
        gbc.gridy++;
        utfylling.add(programScroll, gbc);
        gbc.gridy++;
        utfylling.add(bpBarnFelt, gbc);
        gbc.gridy++;
        utfylling.add(bpVoksenFelt, gbc);
        
         if(type == KINO)
        {
            gbc.gridy++;
            utfylling.add(alderFelt, gbc);
            gbc.gridy++;
            utfylling.add(lengdeFelt, gbc);
        }
        
        gbc.gridy++;
        utfylling.add(deltakereScroll, gbc);
        gbc.gridy++;
        utfylling.add(datoFelt, gbc);
        gbc.gridy++;
        utfylling.add(kPersonValg, gbc);
    }
    
    private void opprettKpersonVindu()
    {
        fornavn = new JLabel("Fornavn:");
        etternavn = new JLabel("Etternavn:");
        epost = new JLabel("E-post:");
        nettside = new JLabel("Nettside:");
        firma = new JLabel("Firma:");
        opplysninger = new JLabel("Opplysninger:");
        tlfNr = new JLabel("Telefon nr.:");
        
        fornavnFelt = new JTextField(10);
        etternavnFelt = new JTextField(10);
        epostFelt = new JTextField(15);
        nettsideFelt = new JTextField(15);
        firmaFelt = new JTextField(10);
        tlfNrFelt = new JTextField(8);
        
        opplysningerFelt = new JTextArea(5, 15);
        opplysningerFelt.setLineWrap(true);
        opplysningerFelt.setWrapStyleWord(true);
        
        opplysningerScroll = new JScrollPane(opplysningerFelt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        utfylling.add(fornavn, gbc);
        gbc.gridy++;
        utfylling.add(etternavn, gbc);
        gbc.gridy++;        
        utfylling.add(tlfNr, gbc);
        gbc.gridy++;
        utfylling.add(epost, gbc);
        gbc.gridy++;
        utfylling.add(nettside, gbc);
        gbc.gridy++;
        utfylling.add(firma, gbc);
        gbc.gridy++;
        utfylling.add(opplysninger, gbc);
        
        gbc.gridx++;
        gbc.gridy = 0;
        utfylling.add(fornavnFelt, gbc);
        gbc.gridy++;
        utfylling.add(etternavnFelt, gbc);
        gbc.gridy++;
        utfylling.add(tlfNrFelt, gbc);
        gbc.gridy++;
        utfylling.add(epostFelt, gbc);
        gbc.gridy++;
        utfylling.add(nettsideFelt, gbc);
        gbc.gridy++;
        utfylling.add(firmaFelt, gbc);
        gbc.gridy++;
        utfylling.add(opplysningerScroll, gbc);
    }
    
    private void opprettLokaleVindu()
    {
        lokaleNavn = new JLabel("Navn:");
        lokaleType = new JLabel("Type lokale:");
        antPlasser = new JLabel("Antall plasser:");
        harNrPlasser = new JLabel("Har lokalet nummererte plasser?:");
        
        lokaleNavnFelt = new JTextField(15);
        antPlasserFelt = new JTextField(5);
        
        lokaleTypeValg = new JComboBox<>(lokaleTypeListe);
        lokaleTypeValg.setPreferredSize(new Dimension(200, 20));
        
        radioJa = new JRadioButton("Ja", false);
        radioJa.addActionListener(knappelytter);
        radioNei = new JRadioButton("Nei", false);
        radioNei.addActionListener(knappelytter);
        
        harNrPlasserGruppe = new ButtonGroup();
        harNrPlasserGruppe.add(radioJa);
        harNrPlasserGruppe.add(radioNei);
        
        radioKnapper = new JPanel(new FlowLayout());
        radioKnapper.add(radioJa);
        radioKnapper.add(radioNei);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        utfylling.add(lokaleNavn, gbc);
        gbc.gridy++;
        utfylling.add(lokaleType, gbc);
        gbc.gridy++;        
        utfylling.add(antPlasser, gbc);
        gbc.gridy++;
        utfylling.add(harNrPlasser, gbc);
        
        gbc.gridx++;
        gbc.gridy = 0;
        utfylling.add(lokaleNavnFelt, gbc);
        gbc.gridy++;
        utfylling.add(lokaleTypeValg, gbc);
        gbc.gridy++;
        utfylling.add(antPlasserFelt, gbc);
        gbc.gridy++;
        utfylling.add(radioKnapper, gbc);
    }
    
    private void opprett()
    {
        if(type == KONTAKT_PERSON)
            opprettKontaktperson();
        else if(type == LOKALE)
            opprettLokale();
        else
            opprettArrangement();
    }
    
    private void opprettKontaktperson()
    {
                
    }
    
    private void opprettLokale()
    {
        String navn = lokaleNavnFelt.getText();
        int valgtIndex = lokaleTypeValg.getSelectedIndex();
        if(!rKnappTrykket)
        {
            visMelding("Du må velge om lokalet har nummererte plasser eller ikke.");
            return;
        }
        if(valgtIndex == 0)
        {
            visMelding("Du må velge type lokale.");
            return;
        }
        if(navn.equals(""))
        {
            visMelding("Du må fylle inn navn på lokalet.");
            return;
        }
        try
        {
            int antP = Integer.parseInt(antPlasserFelt.getText());
            Lokale l = new Lokale(navn, valgtIndex, antP, harNrPVerdi);
            lregister.settInn(l);
            visMelding("Lokalet opprettet.");
            lukkVindu();
        }
        catch(NumberFormatException nfe)
        {
            visMelding("Feil i formatering av tall.");
        }
    }
    
    private void opprettArrangement()
    {
        String navn = navnFelt.getText();
        String sjanger = sjangerFelt.getText();
        String program = programFelt.getText();
        String deltakere = deltakereFelt.getText();
        String dato = datoFelt.getText();
        if(navn.equals("") || sjanger.equals("") || program.equals("") || deltakere.equals("") || dato.equals(""))
        {
            visMelding("Du må fylle ut info i alle feltene");
            return;
        }
        try
        {
            int bpBarn = Integer.parseInt(bpBarnFelt.getText());
            int bpVoksen = Integer.parseInt(bpVoksenFelt.getText());        
        }
        catch(NumberFormatException nfe)
        {
            visMelding("Feil i formateringen av tall");
        }
    }
    
    private void visMelding(String melding)
    {
        JOptionPane.showMessageDialog(this, melding);
    }
    
    private void lukkVindu()
    {
        this.dispose();
    }
    
    private class Kommandolytter implements ActionListener //Kommandolytteren som bestemmer hvilken metode som blir utført utifra hvilken knapp det blir trykket på
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == avbrytKnapp)
                lukkVindu();
            else if(e.getSource() == regKnapp)
                opprett();
            else if(e.getSource() == radioJa)
            {
                harNrPVerdi = true;
                rKnappTrykket = true;
            }
            else if(e.getSource() == radioNei)
            {
                harNrPVerdi = false;
                rKnappTrykket = true;
            }
        }
    }
} //End of class RegistreringsVindu
