import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.filechooser.*;

public class RegistreringsVindu extends JFrame
{
    private static final int DEBATT = 0;
    private static final int FOREDRAG = 1;
    private static final int POLITISK_MOTE = 2;
    private static final int BARNE_FORESTILLING = 3;
    private static final int KINO = 4;
    private static final int KONSERT = 5;
    private static final int TEATER = 6;
    private static final int KONTAKT_PERSON = 7;
    private static final int LOKALE = 8;
    private static final int TEATERSAL = 1;
    private static final int DEBATTSAL = 2;
    private static final int FOREDRAGSSAL = 3;
    private static final int KINOSAL = 4;
    private static final int KONSERTSAL = 5;
    
    private final int type;
    private JPanel vindu, utfylling, knapper;
    private JButton avbrytKnapp, regKnapp;
    private Kommandolytter knappelytter;
    
    private JLabel infoTekst, arrNavn, program, bPrisBarn, bPrisVoksen, deltakere, dato, tid, kPerson, lokale, sjanger, aldersgrense, lengde, velgBilde, valgtBildeSjekk;
    private JTextField navnFelt, bpBarnFelt, bpVoksenFelt, sjangerFelt, alderFelt, lengdeFelt;
    private JTextArea programFelt, deltakereFelt;
    private JScrollPane programScroll, deltakereScroll;
    private JComboBox<String> dagValg, maanedValg, aarValg, timeValg, minuttValg;
    private JComboBox<String> kPersonValg, lokaleValg;
    private Lokale[] lokaleArray;
    private Kontaktperson[] kPersonArray;
    private JPanel datoPanel, tidPanel, bildeVelgerPanel;
    private JFileChooser filvelger;
    private JButton filvelgerKnapp;
    private int filvalgReturVerdi;
    private ImageIcon arrBilde;
    
    private JLabel fornavn, etternavn, epost, nettside, firma, opplysninger, tlfNr;
    private JTextField fornavnFelt, etternavnFelt, epostFelt, nettsideFelt, firmaFelt, tlfNrFelt;
    private JTextArea opplysningerFelt;
    private JScrollPane opplysningerScroll;
    
    private JLabel lokaleNavn, lokaleType, antPlasser, harNrPlasser;
    private JTextField lokaleNavnFelt, antPlasserFelt;
    private JComboBox<String> lokaleTypeValg;
    private final String[] lokaleTypeListe = {"", "Teatersal", "Debattsal", "Foredragssal", "Kinosal", "Konsertsal"};
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
        
        if(type != KONTAKT_PERSON && type != LOKALE)
        {
            if(lokaleArray == null)
            {
                visMelding("Du må opprette minst ett lokale\nfør du kan opprette et arrangement.");
                lukkVindu();
            }
            else if(kPersonArray == null)
            {
                visMelding("Du må opprette minst èn kontaktperson\nfør du kan opprette et arrangement.");
                lukkVindu();
            }
        }    
    }
    
    private void opprettArrVindu()
    {
        arrNavn = new JLabel("Arrangement navn:");
        program = new JLabel("Program:");
        bPrisBarn = new JLabel("Billettpris barn:");
        bPrisVoksen = new JLabel("Billettpris voksen:");
        deltakere = new JLabel("Deltakere (skilles med komma \",\"):");
        dato = new JLabel("Dato:");
        tid = new JLabel("Tidspunkt:");
        kPerson = new JLabel("Kontakt person:");
        lokale = new JLabel("Lokale:");
        aldersgrense = new JLabel("Aldersgrense:");
        lengde = new JLabel("Lengde (i minutter):");
        velgBilde = new JLabel("Velg et arrangement bilde:");
        valgtBildeSjekk = new JLabel("Bilde ikke valgt");
        valgtBildeSjekk.setForeground(Color.red);
        
        if(type == DEBATT || type == FOREDRAG || type == POLITISK_MOTE)
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
        
        lokaleArray = lregister.getLokaler();
        kPersonArray = kpregister.getKontaktpersoner();
        
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
        utfylling.add(tid, gbc);
        gbc.gridy++;
        utfylling.add(lokale, gbc);
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
        gbc.insets = new Insets(5, 0, 5, 5);
        utfylling.add(bildeVelgerPanel, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(5, 5, 5, 5);
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
        gbc.insets = new Insets(5, 0, 5, 5);
        utfylling.add(datoPanel, gbc);
        gbc.gridy++;
        utfylling.add(tidPanel, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(5, 5, 5, 5);
        utfylling.add(lokaleValg, gbc);
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
        tlfNr = new JLabel("Telefonnummer:");
        
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
        String fn = fornavnFelt.getText();
        String en = etternavnFelt.getText();
        String ep = epostFelt.getText();
        String ns = nettsideFelt.getText();
        String f = firma.getText();
        String o = opplysningerFelt.getText();
        if(fn.equals("") || en.equals("") || ep.equals("") || ns.equals("") || f.equals("") || o.equals(""))
        {
            visMelding("Du må fylle ut info i alle feltene.");
        }
        if(!ep.matches(".+@[a-zA-ZæÆøØåÅöÖäÄàÀèÈëËüÜûÛâÂêÊãÃõÕïÏ\\d]+\\.[a-zA-ZæÆøØåÅöÖäÄàÀèÈëËüÜûÛâÂêÊãÃõÕïÏ\\d]+"))
        {
            visMelding("Du har skrevet inn en ugyldig e-post adresse");
            return;
        }
        try
        {
            int tn = Integer.parseInt(tlfNrFelt.getText());
            Kontaktperson k = new Kontaktperson(fn, en, ep, ns, f, o, tn);
            kpregister.settInn(k);
            visMelding("Kontaktperson opprettet.");
            lukkVindu();
        }
        catch(NumberFormatException nfe)
        {
            visMelding("Feil i formatering av tall.");
        }
    }
    
    private void opprettLokale()
    {
        String n = lokaleNavnFelt.getText();
        int typeValgtIndeks = lokaleTypeValg.getSelectedIndex();
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
            lukkVindu();
        }
        catch(NumberFormatException nfe)
        {
            visMelding("Feil i formatering av tall.");
        }
    }
    
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
            if(type == KINO)
            {
                kinoL = Integer.parseInt(lengdeFelt.getText());
                kinoAg = Integer.parseInt(alderFelt.getText());
            }
            
            int dag = Integer.parseInt((String) dagValg.getSelectedItem());
            int maaned = Integer.parseInt((String) maanedValg.getSelectedItem());
            int aar = Integer.parseInt((String) aarValg.getSelectedItem());
            int time = Integer.parseInt((String) timeValg.getSelectedItem());
            int minutt = Integer.parseInt((String) maanedValg.getSelectedItem());
            String[] dArray = d.split("\\s*,\\s*");
            double bpBarn = Double.parseDouble(bpBarnFelt.getText());
            double bpVoksen = Double.parseDouble(bpVoksenFelt.getText());
            Kontaktperson kp = kPersonArray[kPersonValgtIndeks - 1];
            Lokale l = lokaleArray[lokaleValgtIndeks - 1];
            
            LocalDateTime ldt = LocalDateTime.of(aar, maaned, dag, time, minutt);
            
            Arrangement a;
            
            switch(type)
            {
                case BARNE_FORESTILLING:
                    a = new Barneforestilling(n, p, l.getNavn(), type, bpBarn, bpVoksen, dArray, ldt, arrBilde, kp, s);
                    break;
                case DEBATT:
                    a = new Debattkveld(n, p, l.getNavn(), type, bpBarn, bpVoksen, dArray, ldt, arrBilde, kp, s);
                    break;
                case FOREDRAG:
                    a = new Foredrag(n, p, l.getNavn(), type, bpBarn, bpVoksen, dArray, ldt, arrBilde, kp, s);
                    break;
                case KINO:
                    a = new Kino(n, p, l.getNavn(), type, bpBarn, bpVoksen, dArray, ldt, arrBilde, kp, s, kinoL, kinoAg);
                    break;
                case KONSERT:
                    a = new Konsert(n, p, l.getNavn(), type, bpBarn, bpVoksen, dArray, ldt, arrBilde, kp, s);
                    break;
                case POLITISK_MOTE:
                    a = new PolitiskMote(n, p, l.getNavn(), type, bpBarn, bpVoksen, dArray, ldt, arrBilde, kp, s);
                    break;
                default:
                    a = new Teater(n, p, l.getNavn(), type, bpBarn, bpVoksen, dArray, ldt, arrBilde, kp, s);
                    break;
            }
            
            l.settInnArr(a);
            visMelding("Arrangement opprettet.");
            lukkVindu();
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
    
    private String[] navnArray(Kontaktperson[] kpArray)
    {
        if(kpArray == null)
            return new String[0];
        String[] navn = new String[kpArray.length + 1];
        navn[0] = "";
        for(int i = 0; i < kpArray.length; i++)
            navn[i + 1] = kpArray[i].getNavn();
        return navn;
    }
    
    private void visMelding(String melding)
    {
        JOptionPane.showMessageDialog(this, melding);
    }
    
    private void lukkVindu()
    {
        this.dispose();
    }
    
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
            else if(e.getSource() == filvelgerKnapp)
                kjorBildevelger();
        }
    }
} //End of class RegistreringsVindu
