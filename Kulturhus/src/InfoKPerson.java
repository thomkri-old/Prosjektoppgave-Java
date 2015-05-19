/*Opprettet av: Thomas Kristiansen
Sist endret: 16.05.2015

Filen inneholder klassen InfoKPerson.*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

//Klassen er en subklasse av JFrame. Klassen er Info vindu som viser info om en bestemt Kontaktperson
public class InfoKPerson extends JFrame
{
    private static final int SCROLLSPEED = 16;
    
    private JPanel vindu, tekstInnholdPanel;
    private JTextPane info;
    private JScrollPane infoScroll;
    private JButton lukkKnapp;
    
    private Kommandolytter knappelytter;
    
    private Kontaktperson kPerson;
    
    /*Metoden er konstruktøren til klassen InfoKPerson.
    Konstruktøren oppretter og setter sammen objektene som utgjør utseendet til vinduet.
    Parametrenes betydning: k = Kontaktperson objektet det skal vises info om.*/
    public InfoKPerson(Kontaktperson k)
    {
        super("Info om " + k.getNavn());
        
        knappelytter = new Kommandolytter();
        kPerson = k;
        
        vindu = new JPanel(new GridBagLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        info = new JTextPane();
        info.setBackground(this.getBackground());
        info.setEditable(false);
        
        lagInfoUtskrift(kPerson.getIdNr() + ". " + kPerson.getNavn() + "\n", 20, true, false);
        lagInfoUtskrift("\nFirma:", 14, true, false);
        lagInfoUtskrift("\t\t" + kPerson.getFirma(), 14, false, false);
        lagInfoUtskrift("\nTelefon:", 14, true, false);
        lagInfoUtskrift("\t\t" + kPerson.getTlfNr(), 14, false, false);
        lagInfoUtskrift("\nE-post:", 14, true, false);
        lagInfoUtskrift("\t\t" + kPerson.getEpost(), 14, false, false);
        lagInfoUtskrift("\nNettside:", 14, true, false);
        lagInfoUtskrift("\t\t" + kPerson.getNettside(), 14, false, false);
        lagInfoUtskrift("\nType arrangement:", 14, true, false);
        lagInfoUtskrift("\t" + kPerson.getTypeTekst(), 14, false, false);
        lagInfoUtskrift("\n\n" + kPerson.getOpplysninger(), 14, false, false);
        
        info.setCaretPosition(0);
        
        tekstInnholdPanel = new JPanel(new BorderLayout());
        tekstInnholdPanel.add(info, BorderLayout.CENTER);
        
        infoScroll = new JScrollPane(tekstInnholdPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        infoScroll.getVerticalScrollBar().setUnitIncrement(SCROLLSPEED);
        infoScroll.setPreferredSize(new Dimension(375, 250));
        infoScroll.setBorder(BorderFactory.createEmptyBorder( 0, 0, 0, 0 ));

        lukkKnapp = new JButton("Lukk");
        lukkKnapp.addActionListener(knappelytter);
        
        GridBagConstraints gbcV = new GridBagConstraints();
        gbcV.anchor = GridBagConstraints.CENTER;
        gbcV.insets = new Insets(5, 5, 5, 5);
        gbcV.gridx = 0;
        gbcV.gridy = 0;
        
        vindu.add(infoScroll, gbcV);        
        gbcV.gridy += 1;
        vindu.add(lukkKnapp, gbcV);
        
        add(vindu);
        pack();
        setResizable(false);
        setVisible(true);
    }
    
    /*Metode som tar parametrene og skriver tekst på JTextPane-objektet info. Parameterets betydning: 
    tekst = teksten som skal skrives, fontStr = størrelsen på skriften som skal skrives, fetSkrift = true hvis teksten skal
    ha fet skrift og false hvis ikke, kursivSkrift = true hvis teksten skal ha kursiv skrift og false hvis ikke*/
    private void lagInfoUtskrift(String tekst, int fontStr, boolean fetSkrift, boolean kursivSkrift)
    {
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setFontSize(style, fontStr);
        StyleConstants.setBold(style, fetSkrift);
        StyleConstants.setItalic(style, kursivSkrift);
        
        StyledDocument infoDokument = info.getStyledDocument();
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
            if(e.getSource() == lukkKnapp)
                lukkVindu();
        }
    }
} //End of class InfoKPerson