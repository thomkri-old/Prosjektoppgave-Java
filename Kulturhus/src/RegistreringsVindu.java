/*Opprettet av: Thomas Kristiansen
Sist endret: 18.05.2015

Filen inneholder klassen RegistreringsVindu.*/

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.plaf.basic.*;

/*Klassen er en subklasse av JFrame. Klassen er et vindu hvor ansatte kan oppprette
Kontaktpersoner, lokaler og arrangementer.*/
public class RegistreringsVindu extends JFrame
{    
    private int type;
    private JPanel kPPanel, lPanel, aPanel, vindu, utfylling, knapper;
    private JTabbedPane tabbedPane;
    private JButton avbrytKnappA, avbrytKnappKP, avbrytKnappL, regKnappA, regKnappKP, regKnappL;
    private Kommandolytter knappelytter;
    
    private JLabel infoTekst, arrNavn, program, bPrisBarn, bPrisVoksen, deltakere, dato, tid, kPerson, lokale, sjanger, aldersgrense, lengde, velgBilde, valgtBildeSjekk;
    private JTextField navnFelt, bpBarnFelt, bpVoksenFelt, sjangerFelt, alderFelt, lengdeFelt;
    private JTextArea programFelt, deltakereFelt;
    private JScrollPane programScroll, deltakereScroll;
    private JComboBox<String> dagValg, maanedValg, aarValg, timeValg, minuttValg;
    private JComboBox<String> kPersonValg, lokaleValg;
    private Lokale[] lokaleArray;
    private Kontaktperson[] kPersonArray;
    private JPanel datoPanel, tidPanel, bildeVelgerPanel, radioKnapperA;
    private JFileChooser filvelger;
    private JButton filvelgerKnapp;
    private int filvalgReturVerdi, radioValgtType = Kulturhus.DEBATT;
    private ImageIcon arrBilde;
    private ButtonGroup velgArrTypeGruppe;
    private JRadioButton radioDebatt, radioForedrag, radioPolitiskM, radioBarneF, radioKino, radioKonsert, radioTeater;
    
    private JLabel fornavn, etternavn, epost, nettside, firma, opplysninger, tlfNr, typeKP;
    private JTextField fornavnFelt, etternavnFelt, epostFelt, nettsideFelt, firmaFelt, tlfNrFelt;
    private JComboBox<String> typeKPValg;
    private final String[] typeKPListe = {"", "Debatt", "Foredrag", "Politisk møte", "Barneforestilling", "Kino", "Konsert", "Teater"};
    private JTextArea opplysningerFelt;
    private JScrollPane opplysningerScroll;
    
    private JLabel lokaleNavn, lokaleType, antPlasser, harNrPlasser;
    private JTextField lokaleNavnFelt, antPlasserFelt;
    private JComboBox<String> lokaleTypeValg;
    private final String[] lokaleTypeListe = {"", "Debattsal", "Foredragssal", "Kinosal", "Konsertsal", "Teatersal"};
    private ButtonGroup harNrPlasserGruppe;
    private JRadioButton radioJa, radioNei;
    private JPanel radioKnapperL;
    private boolean harNrPVerdi, rKnappTrykket = false;
    
    private Kontaktpersonregister kpregister;
    private Lokalregister lregister;
    
    /*Metoden er konstruktøren til klassen RegistreringsVindu.
    Konstruktøren oppretter og setter sammen objektene som utgjør utseendet til vinduet.
    Parametrenes betydning: l = Lokalregister objektet som brukes felles for hele programmet,
    kp = Kontaktpersonregister objektet som brukes felles for hele programmet,
    valgtFane = indeksen på fanen som skal være valgt når vinduet blir laget.*/
    public RegistreringsVindu(Lokalregister l, Kontaktpersonregister kp, int valgtFane)
    {
        super("Opprett");

        lregister = l;
        kpregister = kp;
        knappelytter = new Kommandolytter();
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new BasicTabbedPaneUI());
        tabbedPane.setBackground(this.getBackground());
        tabbedPane.setPreferredSize(new Dimension(825, 485));
        
        setTabbedPane();
        tabbedPane.setSelectedIndex(valgtFane);
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        
        add(tabbedPane, gbc);
        pack();
        setResizable(false);
        setVisible(true);
    }
    
    //Metode som resetter JTabbedPane objektet tabbedpane, og legger til alle JPanels til fanene på nytt
    private void setTabbedPane()
    {
        tabbedPane.removeAll();
        kPPanel = opprettVindu(Kulturhus.KONTAKTPERSON);
        tabbedPane.addTab("Kontaktperson", null, kPPanel, "Opprett kontaktperson");
        lPanel = opprettVindu(Kulturhus.LOKALE);
        tabbedPane.addTab("Lokale", null, lPanel, "Opprett lokale");
        aPanel = opprettVindu(radioValgtType);
        tabbedPane.addTab("Arrangement", null, aPanel, "Opprett arrangement");
    }
    
    //Metode som oppretter og returnerer et JPanel av typen t, sendt i parameteret(Se Kulturhus.java)
    private JPanel opprettVindu(int t)
    {
        type = t;
        vindu = new JPanel(new GridBagLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        utfylling = new JPanel(new GridBagLayout());
        utfylling.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        knapper = new JPanel(new GridBagLayout());
        
        if(type == Kulturhus.KONTAKTPERSON)
            opprettKpersonVindu();
        else if(type == Kulturhus.LOKALE)
            opprettLokaleVindu();
        else
            opprettArrVindu();
        
        infoTekst = new JLabel("Fyll inn info:");
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        if(type != Kulturhus.KONTAKTPERSON && type != Kulturhus.LOKALE)
        {
            vindu.add(radioKnapperA);
            gbc.gridy++;
        }
        vindu.add(infoTekst, gbc);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridy++;
        vindu.add(utfylling, gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy++;
        vindu.add(knapper, gbc);
        
        return vindu;
    }
    
    //Metode som oppretter alle objektene som skal vises i fanen for opprettelse av arrangementer
    private void opprettArrVindu()
    {
        arrNavn = new JLabel("Arrangement navn:");
        program = new JLabel("Program:");
        bPrisBarn = new JLabel("Billettpris barn:");
        bPrisVoksen = new JLabel("Billettpris voksen:");
        deltakere = new JLabel("Deltakere (skilles med komma \",\"):");
        dato = new JLabel("Dato:");
        tid = new JLabel("Tidspunkt:");
        kPerson = new JLabel("Kontaktperson:");
        lokale = new JLabel("Lokale:");
        aldersgrense = new JLabel("Aldersgrense:");
        lengde = new JLabel("Lengde (i minutter):");
        velgBilde = new JLabel("Velg et arrangement bilde:");
        valgtBildeSjekk = new JLabel("Bilde ikke valgt");
        valgtBildeSjekk.setForeground(Color.red);
        
        if(type == Kulturhus.DEBATT || type == Kulturhus.FOREDRAG || type == Kulturhus.POLITISK_MOTE)
            sjanger = new JLabel("Tema:");
        else
            sjanger = new JLabel("Sjanger:");
        
        navnFelt = new JTextField(15);
        bpBarnFelt = new JTextField(4);
        bpVoksenFelt = new JTextField(4);
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
        
        filvelgerKnapp = new JButton("Velg fil");
        filvelgerKnapp.addActionListener(knappelytter);
        
        bildeVelgerPanel = new JPanel(new FlowLayout());
        bildeVelgerPanel.add(filvelgerKnapp);
        bildeVelgerPanel.add(valgtBildeSjekk);
        
        switch(type)
        {
            case Kulturhus.BARNE_FORESTILLING:
                lokaleArray = lregister.getLokaler(Kulturhus.TEATERSAL);
                break;
            case Kulturhus.DEBATT:
                lokaleArray = lregister.getLokaler(Kulturhus.DEBATTSAL);
                break;
            case Kulturhus.FOREDRAG:
                lokaleArray = lregister.getLokaler(Kulturhus.FOREDRAGSSAL);
                break;
            case Kulturhus.KINO:
                lokaleArray = lregister.getLokaler(Kulturhus.KINOSAL);
                break;
            case Kulturhus.KONSERT:
                lokaleArray = lregister.getLokaler(Kulturhus.KONSERTSAL);
                break;
            case Kulturhus.POLITISK_MOTE:
                lokaleArray = lregister.getLokaler(Kulturhus.FOREDRAGSSAL);
                break;
            default:
                lokaleArray = lregister.getLokaler(Kulturhus.TEATERSAL);
                break;
        }
        kPersonArray = kpregister.getKontaktpersoner(type);
        
        dagValg = new JComboBox<>(tallArray("Dag", 1, 31));
        dagValg.setPreferredSize(new Dimension(50, 20));
        
        maanedValg = new JComboBox<>(tallArray("Måned", 1, 12));
        maanedValg.setPreferredSize(new Dimension(70, 20));
        
        int getAar = Calendar.getInstance().get(Calendar.YEAR);
        aarValg = new JComboBox<>(tallArray("År", getAar, getAar + 10));
        aarValg.setPreferredSize(new Dimension(65, 20));
        
        datoPanel = new JPanel(new FlowLayout());
        datoPanel.add(dagValg);
        datoPanel.add(maanedValg);
        datoPanel.add(aarValg);
        
        timeValg = new JComboBox<>(tallArray("Time", 0, 23));
        timeValg.setPreferredSize(new Dimension(75, 20));
        
        minuttValg = new JComboBox<>(tallArray("Minutt", 0, 59));
        minuttValg.setPreferredSize(new Dimension(75, 20));
        
        tidPanel = new JPanel(new FlowLayout());
        tidPanel.add(timeValg);
        tidPanel.add(minuttValg);
        
        lokaleValg = new JComboBox<>(navnArray(lokaleArray));
        lokaleValg.setPreferredSize(new Dimension(200, 20));
        
        kPersonValg = new JComboBox<>(navnArray(kPersonArray));
        kPersonValg.setPreferredSize(new Dimension(200, 20));
        
        radioDebatt = new JRadioButton("Debatt", (radioValgtType == Kulturhus.DEBATT)?true:false);
        radioDebatt.addActionListener(knappelytter);
        radioForedrag = new JRadioButton("Foredrag", (radioValgtType == Kulturhus.FOREDRAG)?true:false);
        radioForedrag.addActionListener(knappelytter);
        radioPolitiskM = new JRadioButton("Politisk møte", (radioValgtType == Kulturhus.POLITISK_MOTE)?true:false);
        radioPolitiskM.addActionListener(knappelytter);
        radioBarneF = new JRadioButton("Barneforestilling", (radioValgtType == Kulturhus.BARNE_FORESTILLING)?true:false);
        radioBarneF.addActionListener(knappelytter);
        radioKino = new JRadioButton("Kino", (radioValgtType == Kulturhus.KINO)?true:false);
        radioKino.addActionListener(knappelytter);
        radioKonsert = new JRadioButton("Konsert", (radioValgtType == Kulturhus.KONSERT)?true:false);
        radioKonsert.addActionListener(knappelytter);
        radioTeater = new JRadioButton("Teater", (radioValgtType == Kulturhus.TEATER)?true:false);
        radioTeater.addActionListener(knappelytter);
        
        velgArrTypeGruppe = new ButtonGroup();
        velgArrTypeGruppe.add(radioDebatt);
        velgArrTypeGruppe.add(radioForedrag);
        velgArrTypeGruppe.add(radioPolitiskM);
        velgArrTypeGruppe.add(radioBarneF);
        velgArrTypeGruppe.add(radioKino);
        velgArrTypeGruppe.add(radioKonsert);
        velgArrTypeGruppe.add(radioTeater);
        
        radioKnapperA = new JPanel(new FlowLayout());
        radioKnapperA.add(radioDebatt);
        radioKnapperA.add(radioForedrag);
        radioKnapperA.add(radioPolitiskM);
        radioKnapperA.add(radioBarneF);
        radioKnapperA.add(radioKino);
        radioKnapperA.add(radioKonsert);
        radioKnapperA.add(radioTeater);
        
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
        utfylling.add(velgBilde, gbc);
        gbc.gridy++;
        utfylling.add(bPrisBarn, gbc);
        gbc.gridy++;
        utfylling.add(bPrisVoksen, gbc);
        
        gbc.gridx++;
        gbc.gridy = 0;
        utfylling.add(navnFelt, gbc);
        gbc.gridy++;
        utfylling.add(sjangerFelt, gbc);
        gbc.gridy++;
        utfylling.add(programScroll, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 5);
        utfylling.add(bildeVelgerPanel, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(5, 5, 5, 5);
        utfylling.add(bpBarnFelt, gbc);
        gbc.gridy++;
        utfylling.add(bpVoksenFelt, gbc);
        
        gbc.gridx++;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        if(type == Kulturhus.KINO)
        {
            utfylling.add(aldersgrense, gbc);
            gbc.gridy++;
            utfylling.add(lengde, gbc);
            gbc.gridy++;
        }
        utfylling.add(deltakere, gbc);
        gbc.gridy++;
        utfylling.add(dato, gbc);
        gbc.gridy++;
        utfylling.add(tid, gbc);
        gbc.gridy++;
        utfylling.add(lokale, gbc);
        gbc.gridy++;
        utfylling.add(kPerson, gbc);
        
        gbc.gridx++;
        gbc.gridy = 0;
        if(type == Kulturhus.KINO)
        {
            utfylling.add(alderFelt, gbc);
            gbc.gridy++;
            utfylling.add(lengdeFelt, gbc);
            gbc.gridy++;
        }
        utfylling.add(deltakereScroll, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 5, 5);
        utfylling.add(datoPanel, gbc);
        gbc.gridy++;
        utfylling.add(tidPanel, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(5, 5, 5, 5);
        utfylling.add(lokaleValg, gbc);
        gbc.gridy++;
        utfylling.add(kPersonValg, gbc);
        
        avbrytKnappA = new JButton("Avbryt");
        avbrytKnappA.addActionListener(knappelytter);
        regKnappA = new JButton("Registrer");
        regKnappA.addActionListener(knappelytter);
        
        GridBagConstraints gbcK = new GridBagConstraints();
        gbcK.anchor = GridBagConstraints.LINE_START;
        gbcK.insets = new Insets(5, 5, 5, 5);
        gbcK.gridx = 0;
        gbcK.gridy = 0;
        
        knapper.add(regKnappA, gbcK);
        gbcK.gridx++;
        gbcK.anchor = GridBagConstraints.LINE_END;
        knapper.add(avbrytKnappA, gbcK);
    }
    
    //Metode som oppretter alle objektene som skal vises i fanen for opprettelse av kontaktpersoner
    private void opprettKpersonVindu()
    {
        fornavn = new JLabel("Fornavn:");
        etternavn = new JLabel("Etternavn:");
        epost = new JLabel("E-post:");
        nettside = new JLabel("Nettside:");
        firma = new JLabel("Firma:");
        opplysninger = new JLabel("Opplysninger:");
        tlfNr = new JLabel("Telefonnummer:");
        typeKP = new JLabel("Knyttet til arrangementtype:");
        
        fornavnFelt = new JTextField(10);
        etternavnFelt = new JTextField(10);
        epostFelt = new JTextField(15);
        nettsideFelt = new JTextField(15);
        firmaFelt = new JTextField(10);
        tlfNrFelt = new JTextField(8);
        
        typeKPValg = new JComboBox<>(typeKPListe);
        typeKPValg.setPreferredSize(new Dimension(200, 20));
        
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
        utfylling.add(typeKP, gbc);
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
        utfylling.add(typeKPValg, gbc);
        gbc.gridy++;
        utfylling.add(opplysningerScroll, gbc);
        
        avbrytKnappKP = new JButton("Avbryt");
        avbrytKnappKP.addActionListener(knappelytter);
        regKnappKP = new JButton("Registrer");
        regKnappKP.addActionListener(knappelytter);
        
        GridBagConstraints gbcK = new GridBagConstraints();
        gbcK.anchor = GridBagConstraints.LINE_START;
        gbcK.insets = new Insets(5, 5, 5, 5);
        gbcK.gridx = 0;
        gbcK.gridy = 0;
        
        knapper.add(regKnappKP, gbcK);
        gbcK.gridx++;
        gbcK.anchor = GridBagConstraints.LINE_END;
        knapper.add(avbrytKnappKP, gbcK);
    }
    
    //Metode som oppretter alle objektene som skal vises i fanen for opprettelse av lokaler
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
        
        radioKnapperL = new JPanel(new FlowLayout());
        radioKnapperL.add(radioJa);
        radioKnapperL.add(radioNei);
        
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
        utfylling.add(radioKnapperL, gbc);
        
        avbrytKnappL = new JButton("Avbryt");
        avbrytKnappL.addActionListener(knappelytter);
        regKnappL = new JButton("Registrer");
        regKnappL.addActionListener(knappelytter);
        
        GridBagConstraints gbcK = new GridBagConstraints();
        gbcK.anchor = GridBagConstraints.LINE_START;
        gbcK.insets = new Insets(5, 5, 5, 5);
        gbcK.gridx = 0;
        gbcK.gridy = 0;
        
        knapper.add(regKnappL, gbcK);
        gbcK.gridx++;
        gbcK.anchor = GridBagConstraints.LINE_END;
        knapper.add(avbrytKnappL, gbcK);
    }
    
    //Metode som ut ifra typen t, fra parameteret, kaller på riktig metode for å opprette riktig objekt 
    private void opprett(int t)
    {
        type = t;
        if(type == Kulturhus.KONTAKTPERSON)
            opprettKontaktperson();
        else if(type == Kulturhus.LOKALE)
            opprettLokale();
        else
            opprettArrangement();
    }
    
    //Metode som oppretter et Kontaktperson objekt ut ifra feltene i fanen for opprettelse av kontaktpersoner
    private void opprettKontaktperson()
    {
        String fn = fornavnFelt.getText();
        String en = etternavnFelt.getText();
        String ep = epostFelt.getText();
        String ns = nettsideFelt.getText();
        String f = firma.getText();
        String o = opplysningerFelt.getText();
        int typeValgtIndeks = typeKPValg.getSelectedIndex() - 1;
        if(fn.equals("") || en.equals("") || ep.equals("") || ns.equals("") || f.equals("") || o.equals(""))
        {
            visMelding("Du må fylle ut info i alle feltene.");
        }
        if(typeValgtIndeks == -1)
        {
            visMelding("Du må velge type arrangement kontaktpersonen er knyttet til.");
            return;
        }
        if(!ep.matches(".+@[a-zA-ZæÆøØåÅöÖäÄàÀèÈëËüÜûÛâÂêÊãÃõÕïÏ\\d]+\\.[a-zA-ZæÆøØåÅöÖäÄàÀèÈëËüÜûÛâÂêÊãÃõÕïÏ\\d]+"))
        {
            visMelding("Du har skrevet inn en ugyldig e-post adresse");
            return;
        }
        try
        {
            int tn = Integer.parseInt(tlfNrFelt.getText());
            Kontaktperson k = new Kontaktperson(fn, en, ep, ns, f, o, tn, typeValgtIndeks);
            kpregister.settInn(k);
            visMelding("Kontaktperson opprettet.");
            setTabbedPane();
            tabbedPane.setSelectedIndex(0);
        }
        catch(NumberFormatException nfe)
        {
            visMelding("Feil i formatering av tall.");
        }
    }
    
    //Metode som oppretter et Lokale objekt ut ifra feltene i fanen for opprettelse av lokaler
    private void opprettLokale()
    {
        String n = lokaleNavnFelt.getText();
        int typeValgtIndeks = lokaleTypeValg.getSelectedIndex() + 10;
        if(!rKnappTrykket)
        {
            visMelding("Du må velge om lokalet har nummererte plasser eller ikke.");
            return;
        }
        if(typeValgtIndeks == 0)
        {
            visMelding("Du må velge type lokale.");
            return;
        }
        if(n.equals(""))
        {
            visMelding("Du må fylle inn navn på lokalet.");
            return;
        }
        try
        {
            int antP = Integer.parseInt(antPlasserFelt.getText());
            Lokale l = new Lokale(n, typeValgtIndeks, antP, harNrPVerdi);
            lregister.settInn(l);
            visMelding("Lokalet opprettet.");
            setTabbedPane();
            tabbedPane.setSelectedIndex(1);
        }
        catch(NumberFormatException nfe)
        {
            visMelding("Feil i formatering av tall.");
        }
    }
    
    //Metode som oppretter et Arrangement objekt ut ifra feltene i fanen for opprettelse av arrangementer
    private void opprettArrangement()
    {
        String n = navnFelt.getText();
        String s = sjangerFelt.getText();
        String p = programFelt.getText();
        String d = deltakereFelt.getText();
        int lokaleValgtIndeks = lokaleValg.getSelectedIndex();
        int kPersonValgtIndeks = kPersonValg.getSelectedIndex();
        
        if(n.equals("") || s.equals("") || p.equals("") || d.equals(""))
        {
            visMelding("Du må fylle ut info i alle feltene.");
            return;
        }
        if(arrBilde == null)
        {
            visMelding("Du må velge et bilde til arrangementet");
            return;
        }
        if(!d.matches("([a-zA-ZæÆøØåÅöÖäÄàÀèÈëËüÜûÛâÂêÊãÃõÕïÏ\\s]+(,\\s*)?)+"))
        {
            visMelding("Deltakere må skilles med et komma(Ingen tall).\nF. eks.: Ola Nordmann, Jan Teigen, Fredrik Karlsen");
            return;
        }
        if(dagValg.getSelectedIndex() == 0 || maanedValg.getSelectedIndex() == 0 || aarValg.getSelectedIndex() == 0)
        {
            visMelding("Du må velge noe i alle felt på dato.");
            return;
        }
        if(timeValg.getSelectedIndex() == 0 || minuttValg.getSelectedIndex() == 0)
        {
            visMelding("Du må velge noe i alle felt på tidspunkt.");
            return;
        }
        if(lokaleValgtIndeks == 0)
        {
            visMelding("Du må velge et lokale.");
            return;
        }
        if(kPersonValgtIndeks == 0)
        {
            visMelding("Du må velge en kontaktperson.");
            return;
        }
        try
        {
            int kinoL = 0;
            int kinoAg = 0;
            if(type == Kulturhus.KINO)
            {
                kinoL = Integer.parseInt(lengdeFelt.getText());
                kinoAg = Integer.parseInt(alderFelt.getText());
            }
            
            int dag = Integer.parseInt((String) dagValg.getSelectedItem());
            int maaned = Integer.parseInt((String) maanedValg.getSelectedItem());
            int aar = Integer.parseInt((String) aarValg.getSelectedItem());
            int time = Integer.parseInt((String) timeValg.getSelectedItem());
            int minutt = Integer.parseInt((String) minuttValg.getSelectedItem());
            String[] dArray = d.split("\\s*,\\s*");
            double bpBarn = Double.parseDouble(bpBarnFelt.getText());
            double bpVoksen = Double.parseDouble(bpVoksenFelt.getText());
            Kontaktperson kp = kPersonArray[kPersonValgtIndeks - 1];
            Lokale l = lokaleArray[lokaleValgtIndeks - 1];
            
            LocalDateTime ldt = LocalDateTime.of(aar, maaned, dag, time, minutt);
            
            Arrangement a;
            
            switch(type)
            {
                case Kulturhus.BARNE_FORESTILLING:
                    a = new Barneforestilling(n, p, l.getNavn(), type, bpBarn, bpVoksen, dArray, ldt, arrBilde, kp, s);
                    break;
                case Kulturhus.DEBATT:
                    a = new Debattkveld(n, p, l.getNavn(), type, bpBarn, bpVoksen, dArray, ldt, arrBilde, kp, s);
                    break;
                case Kulturhus.FOREDRAG:
                    a = new Foredrag(n, p, l.getNavn(), type, bpBarn, bpVoksen, dArray, ldt, arrBilde, kp, s);
                    break;
                case Kulturhus.KINO:
                    a = new Kino(n, p, l.getNavn(), type, bpBarn, bpVoksen, dArray, ldt, arrBilde, kp, s, kinoL, kinoAg);
                    break;
                case Kulturhus.KONSERT:
                    a = new Konsert(n, p, l.getNavn(), type, bpBarn, bpVoksen, dArray, ldt, arrBilde, kp, s);
                    break;
                case Kulturhus.POLITISK_MOTE:
                    a = new PolitiskMote(n, p, l.getNavn(), type, bpBarn, bpVoksen, dArray, ldt, arrBilde, kp, s);
                    break;
                default:
                    a = new Teater(n, p, l.getNavn(), type, bpBarn, bpVoksen, dArray, ldt, arrBilde, kp, s);
                    break;
            }
            
            l.settInnArr(a);
            visMelding("Arrangement opprettet.");
            setTabbedPane();
            tabbedPane.setSelectedIndex(2);
        }
        catch(NumberFormatException nfe)
        {
            visMelding("Feil i formateringen av tall.");
        }
        catch(DateTimeException dte)
        {
            visMelding("Du har valgt en ugyldig dato.");
        }
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
    
    /*Metode som tar Lokale arrayen lArray, fra parameteret, og returnerer en String array med
    alle navnene til Lokale objektene i lArray*/
    private String[] navnArray(Lokale[] lArray)
    {
        if(lArray == null)
            return new String[0];
        String[] navn = new String[lArray.length + 1];
        navn[0] = "";
        for(int i = 0; i < lArray.length; i++)
            navn[i + 1] = lArray[i].getNavn();
        return navn;
    }
    
    /*Metode som tar Kontaktperson arrayen kpArray, fra parameteret, og returnerer en String array med
    alle navnene til Kontaktperson objektene i kpArray*/
    private String[] navnArray(Kontaktperson[] kpArray)
    {
        if(kpArray == null)
            return new String[0];
        String[] navn = new String[kpArray.length + 1];
        navn[0] = "";
        for(int i = 0; i < kpArray.length; i++)
            navn[i + 1] = kpArray[i].getIdNr() + ".  " + kpArray[i].getNavn();
        return navn;
    }
    
    //Metode som tar parameteret og oppretter en popup-boks. Parameterets betydning: melding = teksten som skal skrives på popup-boksen.
    private void visMelding(String melding)
    {
        JOptionPane.showMessageDialog(this, melding);
    }
    
    private void lukkVindu() //Metode som lukker vinduet
    {
        this.dispose();
    }
    
    /*Metode som starter et filvelger vindu, som gjør at brukeren kan velge et bilde fra PCen sin,
    som blir brukt som bilde(plakat) til arrangementet som skal opprettes.*/
    private void kjorBildevelger()
    {
        filvelger = new JFileChooser();
        FileFilter bildeFilter = new FileNameExtensionFilter("Bilde filer", ImageIO.getReaderFileSuffixes());
        filvelger.addChoosableFileFilter(bildeFilter);
        filvelger.setAcceptAllFileFilterUsed(false);
        filvalgReturVerdi = filvelger.showOpenDialog(this);
        
        if(filvalgReturVerdi == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                Image bilde = ImageIO.read(filvelger.getSelectedFile());
                arrBilde = new ImageIcon(bilde);
                valgtBildeSjekk.setText("Bilde valgt ✓");
                valgtBildeSjekk.setForeground(new Color(0, 153, 0));
            }
            catch(IOException ioe)
            {
                visMelding("Feil i formatering av bilde.\nPrøv en ny fil!");
            }
        }
    }
    
    //Klasse som bestemmer hvilken metode som blir utført utifra hvilken knapp det blir trykket på
    private class Kommandolytter implements ActionListener 
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == avbrytKnappA || e.getSource() == avbrytKnappKP || e.getSource() == avbrytKnappL)
                lukkVindu();
            else if(e.getSource() == regKnappA)
                opprett(radioValgtType);
            else if(e.getSource() == regKnappKP)
                opprett(Kulturhus.KONTAKTPERSON);
            else if(e.getSource() == regKnappL)
                opprett(Kulturhus.LOKALE);
            else if(e.getSource() == radioDebatt)
            {
                radioValgtType = Kulturhus.DEBATT;
                setTabbedPane();
                tabbedPane.setSelectedIndex(2);
            }
            else if(e.getSource() == radioForedrag)
            {
                radioValgtType = Kulturhus.FOREDRAG;
                setTabbedPane();
                tabbedPane.setSelectedIndex(2);
            }
            else if(e.getSource() == radioPolitiskM)
            {
                radioValgtType = Kulturhus.POLITISK_MOTE;
                setTabbedPane();
                tabbedPane.setSelectedIndex(2);
            }
            else if(e.getSource() == radioBarneF)
            {
                radioValgtType = Kulturhus.BARNE_FORESTILLING;
                setTabbedPane();
                tabbedPane.setSelectedIndex(2);
            }
            else if(e.getSource() == radioKino)
            {
                radioValgtType = Kulturhus.KINO;
                setTabbedPane();
                tabbedPane.setSelectedIndex(2);
            }
            else if(e.getSource() == radioKonsert)
            {
                radioValgtType = Kulturhus.KONSERT;
                setTabbedPane();
                tabbedPane.setSelectedIndex(2);
            }
            else if(e.getSource() == radioTeater)
            {
                radioValgtType = Kulturhus.TEATER;
                setTabbedPane();
                tabbedPane.setSelectedIndex(2);
            }
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
            else if(e.getSource() == filvelgerKnapp)
                kjorBildevelger();
        }
    }
} //End of class RegistreringsVindu