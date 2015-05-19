/*Opprettet av: Sara Torp Myhre
Sist endret: 18.05.2015

Filen inneholder klassen StatistikkVindu.*/

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.text.*;

/*Klassen er en subklasse av JFrame. Klassen er et vindu hvor ansatte kan se
forskjellig statistikk for systemet.*/
public class StatistikkVindu extends JFrame
{
    private static final int BREDDE = 600;
    private static final int HOYDE = 300;
    
    private JPanel antArrPanel, mestInntektArrPanel, vindu, knappePanel;
    private JTabbedPane tabbedPane;
    private JTextPane aAPPane, mIAPPane;
    private JScrollPane aAPScroll, mIAPScroll;
    private JButton lukkAAPKnapp, lukkMIAPKnapp, oppdaterAAPKnapp, oppdaterMIAPKnapp;
    
    private Lokalregister lregister;
    private Kommandolytter knappelytter;
    
    private DecimalFormat krFormat = new DecimalFormat( "0.00" );
    
    /*Metoden er konstruktøren til klassen StatistikkVindu.
    Konstruktøren oppretter og setter sammen objektene som utgjør utseendet til vinduet.
    Parametrenes betydning: l = Lokalregister objektet som brukes felles for hele programmet,
    valgtFane = indeksen på fanen som skal være valgt når vinduet blir laget.*/
    public StatistikkVindu(Lokalregister l, int valgtFane)
    {
        super("Statistikk");
        
        lregister = l;
        knappelytter = new Kommandolytter();
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new BasicTabbedPaneUI());
        tabbedPane.setBackground(this.getBackground());
        
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
        antArrPanel = opprettAAPVindu();
        tabbedPane.addTab("Arr. per Lokale", null, antArrPanel, "Totalt antall arrangementer per lokale");
        mestInntektArrPanel = opprettMIAPVindu();
        tabbedPane.addTab("Arr. med høyest inntekt", null, mestInntektArrPanel, "De 10 mest inntektsbrinngende arrangementene");
    }
    
    /*Metode som oppretter alle objektene som skal vises i fanen for opprettelse av JPanel'en i fanen for
    Arr. per Lokale og returnerer et JPanel objekt med alle disse objektene i seg.*/
    private JPanel opprettAAPVindu()
    {
        vindu = new JPanel(new GridBagLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        Lokale[] lokaleArray = lregister.getLokaler();
        
        aAPPane = new JTextPane();
        aAPPane.setBackground(this.getBackground());
        aAPPane.setEditable(false);
        
        lagInfoUtskrift("Oversikt over antall arrangementer per lokale\n", 20, true, false, aAPPane);
        
        for(int i = 0; i < lokaleArray.length; i++)
        {
            lagInfoUtskrift("\n" + lokaleArray[i].getNavn() + ": " + lokaleArray[i].getAntArr() + " arrangementer", 16, false, false, aAPPane);
        }
        
        aAPScroll = new JScrollPane(aAPPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        aAPScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        aAPScroll.setPreferredSize(new Dimension(BREDDE, HOYDE));
        
        oppdaterAAPKnapp = new JButton("Oppdater");
        oppdaterAAPKnapp.addActionListener(knappelytter);
        lukkAAPKnapp = new JButton("Lukk");
        lukkAAPKnapp.addActionListener(knappelytter);
        
        knappePanel = new JPanel(new FlowLayout());
        knappePanel.add(oppdaterAAPKnapp);
        knappePanel.add(lukkAAPKnapp);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        vindu.add(aAPScroll, gbc);
        gbc.gridy++;
        vindu.add(knappePanel, gbc);
        
        return vindu;
    }
    
    /*Metode som oppretter alle objektene som skal vises i fanen for opprettelse av JPanel'en i fanen for
    Arr. med høyest inntekt og returnerer et JPanel objekt med alle disse objektene i seg.*/
    private JPanel opprettMIAPVindu()
    {
        vindu = new JPanel(new GridBagLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        Arrangement[] arrArray = lregister.getMestInntektArr();
        
        mIAPPane = new JTextPane();
        mIAPPane.setBackground(this.getBackground());
        mIAPPane.setEditable(false);
        
        lagInfoUtskrift("Oversikt over de 10 mest inntektsbringende arrangementene\n", 20, true, false, mIAPPane);
        
        for(int i = 0; i < arrArray.length; i++)
        {
            lagInfoUtskrift("\n" + (i+1) + ".\t", 16, true, false, mIAPPane);
            if(arrArray[i] != null)
                lagInfoUtskrift(arrArray[i].getNavn() + "   " + krFormat.format(arrArray[i].getTotalInntekt()) + ",-", 16, false, false, mIAPPane);
        }
        
        mIAPScroll = new JScrollPane(mIAPPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mIAPScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        mIAPScroll.setPreferredSize(new Dimension(BREDDE, HOYDE));
        
        oppdaterMIAPKnapp = new JButton("Oppdater");
        oppdaterMIAPKnapp.addActionListener(knappelytter);
        lukkMIAPKnapp = new JButton("Lukk");
        lukkMIAPKnapp.addActionListener(knappelytter);
        
        knappePanel = new JPanel(new FlowLayout());
        knappePanel.add(oppdaterMIAPKnapp);
        knappePanel.add(lukkMIAPKnapp);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        vindu.add(mIAPScroll, gbc);
        gbc.gridy++;
        vindu.add(knappePanel, gbc);
        
        return vindu;
    }
    
    /*Metode som tar parametrene og skriver tekst på JTextPane-objektet pane, gitt i parameteret. Parameterets betydning: 
    tekst = teksten som skal skrives, fontStr = størrelsen på skriften som skal skrives, fetSkrift = true hvis teksten skal
    ha fet skrift og false hvis ikke, kursivSkrift = true hvis teksten skal ha kursiv skrift og false hvis ikke,
    pane = JTextPane-objektet teksten skal skrives på.*/
    private void lagInfoUtskrift(String tekst, int fontStr, boolean fetSkrift, boolean kursivSkrift, JTextPane pane)
    {
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setFontSize(style, fontStr);
        StyleConstants.setBold(style, fetSkrift);
        StyleConstants.setItalic(style, kursivSkrift);
        
        StyledDocument infoDokument = pane.getStyledDocument();
        try
        {
            infoDokument.insertString(infoDokument.getLength(), tekst, style);
        }
        catch(BadLocationException ble)
        {
            visMelding("Her har det skjedd en feil.");
        }
    }
    
    private void lukkVindu() //Metode som lukker vinduet
    {
        this.dispose();
    }
    
    //Metode som tar parameteret og oppretter en popup-boks. Parameterets betydning: melding = teksten som skal skrives på popup-boksen.
    private void visMelding(String melding)
    {
        JOptionPane.showMessageDialog(this, melding);
    }
    
    //Klasse som bestemmer hvilken metode som blir utført utifra hvilken knapp det blir trykket på
    private class Kommandolytter implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == oppdaterAAPKnapp)
            {
                setTabbedPane();
                tabbedPane.setSelectedIndex(0);
            }
            else if(e.getSource() == oppdaterMIAPKnapp)
            {
                setTabbedPane();
                tabbedPane.setSelectedIndex(1);
            }
            else if(e.getSource() == lukkAAPKnapp)
                lukkVindu();
            else if(e.getSource() == lukkMIAPKnapp)
                lukkVindu();
        }
    }
}
