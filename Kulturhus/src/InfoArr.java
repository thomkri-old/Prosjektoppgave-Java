/*Opprettet av: Sara Torp Myhre
Sist endret: 16.05.2015

Filen inneholder klassen InfoArr.*/

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.text.*;

//Klassen er en subklasse av JFrame. Klassen er Info vindu som viser info om et bestemt arrangement
public class InfoArr extends JFrame
{
    private static final int SCROLLSPEED = 16;
    
    private JPanel vindu, infoPanel, knappePanel, bildePanel, tekstInnholdPanel;
    private JTextPane arrInfo;
    private JScrollPane arrInfoScroll;
    private JButton kjopKnapp, lukkKnapp;
    
    private Kommandolytter knappelytter;
    
    private Arrangement arrangement;
    
    private DecimalFormat krFormat = new DecimalFormat( "0.00" );
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d. MMMM uuuu  'kl.' HH:mm");
    private boolean erKunde;
    
    /*Metoden er konstruktøren til klassen InfoArr.
    Konstruktøren oppretter og setter sammen objektene som utgjør utseendet til vinduet.
    Parametrenes betydning: a = Arrangement objektet det skal vises info om,
    eK = variabel som tilsier om vinduet skal vises for kunder eller ansatte(hvis true så er det for kunder)*/
    public InfoArr(Arrangement a, boolean eK)
    {
        super("Info om " + a.getNavn());
        
        knappelytter = new Kommandolytter();
        arrangement = a;
        erKunde = eK;
        
        vindu = new JPanel(new GridBagLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        bildePanel = new ArrbildePanel(arrangement.getArrBilde());
        
        arrInfo = new JTextPane();
        arrInfo.setBackground(this.getBackground());
        arrInfo.setEditable(false);
        
        lagInfoUtskrift(arrangement.getNavn() + "\n", 20, true, false);
        lagInfoUtskrift("\nDato:", 14, true, false);
        lagInfoUtskrift("\t\t" + dtf.format(arrangement.getDato()), 14, false, false);
        lagInfoUtskrift("\nPris*:", 14, true, false);
        lagInfoUtskrift("\t\t" + krFormat.format(arrangement.getBillettprisBarn()) + ",- / " + krFormat.format(arrangement.getBillettprisVoksen()) + ",-", 14, false, false);
        if(arrangement.getType() == Kulturhus.KINO)
        {
            Kino k = (Kino)arrangement;
            int timer = k.getLengde() / 60;
            int minutter = k.getLengde() % 60;            
            
            lagInfoUtskrift("\nAldersgrense:", 14, true, false);
            lagInfoUtskrift("\t" + k.getAldersgrense() + " år", 14, false, false);
            lagInfoUtskrift("\nLengde:", 14, true, false);
            lagInfoUtskrift("\t\t" + timer + " timer og " + minutter + " minutter", 14, false, false);
        }
            
        lagInfoUtskrift("\nDeltakere:", 14, true, false);
        
        String[] deltakere = arrangement.getDeltakere();
        for(int i = 0; i < deltakere.length; i++)
            lagInfoUtskrift("\t" + ((i == 0)?"":"\t") + deltakere[i] + "\n", 14, false, false);
        
        lagInfoUtskrift("Ledige plasser:", 14, true, false);
        lagInfoUtskrift("\t" + arrangement.getLedigePlasser(), 14, false, false);
        lagInfoUtskrift("\n\n" + arrangement.getProgram(), 14, false, false);
        lagInfoUtskrift("\n\n*Pris per billett skrevet på formen barn / voksen", 12, false, true);
        
        arrInfo.setCaretPosition(0);
        
        tekstInnholdPanel = new JPanel(new BorderLayout());
        tekstInnholdPanel.add(arrInfo, BorderLayout.CENTER);
        
        arrInfoScroll = new JScrollPane(tekstInnholdPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        arrInfoScroll.getVerticalScrollBar().setUnitIncrement(SCROLLSPEED);
        arrInfoScroll.setPreferredSize(new Dimension(375, 275));
        arrInfoScroll.setBorder(BorderFactory.createEmptyBorder( 0, 0, 0, 0 ));
        
        knappePanel = new JPanel(new GridBagLayout());
        knappePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        kjopKnapp = new JButton("Kjøp");
        kjopKnapp.addActionListener(knappelytter);
        lukkKnapp = new JButton("Lukk");
        lukkKnapp.addActionListener(knappelytter);
        
        GridBagConstraints gbcK = new GridBagConstraints();
        gbcK.anchor = GridBagConstraints.CENTER;
        gbcK.insets = new Insets(5, 5, 5, 5);
        gbcK.gridx = 0;
        gbcK.gridy = 0;
        
        if(erKunde)
        {
            knappePanel.add(kjopKnapp, gbcK);
            gbcK.gridx += 1;
        }
        knappePanel.add(lukkKnapp, gbcK);
        
        GridBagConstraints gbcI = new GridBagConstraints();
        gbcI.anchor = GridBagConstraints.FIRST_LINE_START;
        gbcI.insets = new Insets(7, 5, 5, 5);
        gbcI.gridx = 0;
        gbcI.gridy = 0;
        
        infoPanel.add(bildePanel, gbcI);
        gbcI.gridx += 1;
        gbcI.insets = new Insets(0, 5, 5, 5);
        infoPanel.add(arrInfoScroll, gbcI);
        
        GridBagConstraints gbcV = new GridBagConstraints();
        gbcV.anchor = GridBagConstraints.CENTER;
        gbcV.insets = new Insets(5, 5, 5, 5);
        gbcV.gridx = 0;
        gbcV.gridy = 0;
        
        vindu.add(infoPanel, gbcV);        
        gbcV.gridy += 1;
        vindu.add(knappePanel, gbcV);
        
        add(vindu);
        pack();
        setResizable(false);
        setVisible(true);
    }
    
    /*Metode som tar parametrene og skriver tekst på JTextPane-objektet arrInfo. Parameterets betydning: 
    tekst = teksten som skal skrives, fontStr = størrelsen på skriften som skal skrives, fetSkrift = true hvis teksten skal
    ha fet skrift og false hvis ikke, kursivSkrift = true hvis teksten skal ha kursiv skrift og false hvis ikke*/
    private void lagInfoUtskrift(String tekst, int fontStr, boolean fetSkrift, boolean kursivSkrift)
    {
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setFontSize(style, fontStr);
        StyleConstants.setBold(style, fetSkrift);
        StyleConstants.setItalic(style, kursivSkrift);
        
        StyledDocument infoDokument = arrInfo.getStyledDocument();
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
    
    public void kjopBillett() //Metode som åpner et kjøp vindu for arrangementet som er sendt med til klassen
    {
        JFrame kjopVindu = new Kjop(arrangement);
        kjopVindu.setLocationRelativeTo(this);
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
            if(e.getSource() == kjopKnapp)
                kjopBillett();
            else if(e.getSource() == lukkKnapp)
                lukkVindu();
        }
    }
} //End of class InfoArr