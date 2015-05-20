

/*Opprettet av: Sara Torp Myhre
Sist endret: 14.05.2015

Filen inneholder klassen Kjop.*/

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.text.*;

/*Klassen er en subklasse av JFrame. Klassen er et vindu som viser litt info om et bestemt arrangement
på et vindu der kunden kan kjøpe billett til dette arrangementet.*/ 
public class Kjop extends JFrame
{
    private static final int INFOSCROLLBREDDE = 325;
    private static final int INFOSCROLLHOYDE = 150;
    
    private JPanel vindu, infoPanel, hoyrePanel, kjopPanel, knappePanel, bildePanel, valgPanel;
    private JTextPane arrInfo;
    private JScrollPane infoScroll;
    private JButton kjopKnapp, lukkKnapp;
    
    private JLabel tittel, fornavn, etternavn, epost, tlf, antBilletter;
    private JTextField fornavnFelt, etternavnFelt, epostFelt, tlfFelt;
    private JComboBox<String> bBillettValg, vBillettValg;
    
    private Kommandolytter knappelytter;
    
    private Arrangement arrangement;
    
    DecimalFormat krFormat;
    private DateTimeFormatter dtf;
    
    /*Metoden er konstruktøren til klassen InfoArr.
    Konstruktøren oppretter og setter sammen objektene som utgjør utseendet til vinduet.
    Parametrenes betydning: a = Arrangement objektet det skal vises info om og kjøpes billett til.*/
    public Kjop(Arrangement a)
    {
        super("Kjøp billett for " + a.getNavn());
        
        arrangement = a;
        knappelytter = new Kommandolytter();
        krFormat = new DecimalFormat( "0.00" );
        dtf = DateTimeFormatter.ofPattern("d. MMMM uuuu  'kl.' HH:mm");
        
        vindu = new JPanel(new GridBagLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        hoyrePanel = new JPanel(new GridBagLayout());
        hoyrePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        bildePanel = new ArrbildePanel(arrangement.getArrBilde());
        
        arrInfo = new JTextPane();
        arrInfo.setBackground(this.getBackground());
        arrInfo.setEditable(false);
        
        lagInfoUtskrift(arrangement.getNavn() + "\n", 20, true, false);
        lagInfoUtskrift("\nDato:", 14, true, false);
        lagInfoUtskrift("\t\t" + dtf.format(arrangement.getDato()), 14, false, false);
        lagInfoUtskrift("\nPris*:", 14, true, false);
        lagInfoUtskrift("\t\t" + krFormat.format(arrangement.getBillettprisBarn()) + ",- / " + krFormat.format(arrangement.getBillettprisVoksen()) + ",-", 14, false, false);
        lagInfoUtskrift("\nLedige plasser:", 14, true, false);
        lagInfoUtskrift("\t" + arrangement.getLedigePlasser(), 14, false, false);
        lagInfoUtskrift("\n\n*Pris per billett skrevet på formen barn / voksen", 12, false, true);
        
        arrInfo.setCaretPosition(0);
        
        infoScroll = new JScrollPane(arrInfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        infoScroll.setPreferredSize(new Dimension(INFOSCROLLBREDDE, INFOSCROLLHOYDE));
        infoScroll.setBorder(BorderFactory.createEmptyBorder( 0, 0, 0, 0 ));
        
        kjopPanel = new JPanel(new GridBagLayout());
        
        tittel = new JLabel("Fyll inn info:");
        tittel.setFont(new Font(tittel.getFont().getName(), Font.PLAIN, 18));        
        fornavn = new JLabel("Fornavn:");
        etternavn = new JLabel("Etternavn:");
        epost = new JLabel("E-post:");
        tlf = new JLabel("Telefonnummer");
        antBilletter = new JLabel("Antall:");
        
        fornavnFelt = new JTextField(10);
        etternavnFelt = new JTextField(10);
        epostFelt = new JTextField(15);
        tlfFelt = new JTextField(8);
        
        bBillettValg = new JComboBox<>(tallArray("Barn", 0, 20));
        bBillettValg.setPreferredSize(new Dimension(70, 20));
        
        vBillettValg = new JComboBox<>(tallArray("Voksne", 0, 20));
        vBillettValg.setPreferredSize(new Dimension(70, 20));
        
        valgPanel = new JPanel(new FlowLayout());
        valgPanel.add(bBillettValg);
        valgPanel.add(vBillettValg);
        
        kjopKnapp = new JButton("Fullfør kjøp");
        kjopKnapp.addActionListener(knappelytter);
        lukkKnapp = new JButton("Avbryt kjøp");
        lukkKnapp.addActionListener(knappelytter);
        
        knappePanel = new JPanel(new GridBagLayout());
        knappePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        knappePanel.add(kjopKnapp, gbc);
        gbc.gridx++;
        knappePanel.add(lukkKnapp, gbc);
        
        GridBagConstraints gbcI = new GridBagConstraints();
        gbcI.anchor = GridBagConstraints.CENTER;
        gbcI.insets = new Insets(5, 5, 5, 5);
        gbcI.gridx = 0;
        gbcI.gridy = 0;
        
        infoPanel.add(bildePanel, gbcI);
        gbcI.gridy++;
        gbcI.anchor = GridBagConstraints.FIRST_LINE_START;
        infoPanel.add(infoScroll, gbcI);
        
        GridBagConstraints gbcK = new GridBagConstraints();
        gbcK.anchor = GridBagConstraints.LINE_START;
        gbcK.insets = new Insets(5, 5, 5, 5);
        gbcK.gridx = 0;
        gbcK.gridy = 0;
        
        kjopPanel.add(fornavn, gbcK);
        gbcK.gridy++;
        kjopPanel.add(etternavn, gbcK);
        gbcK.gridy++;
        kjopPanel.add(epost, gbcK);
        gbcK.gridy++;
        kjopPanel.add(tlf, gbcK);
        gbcK.gridy++;
        kjopPanel.add(antBilletter, gbcK);
        
        gbcK.gridy = 0;
        gbcK.gridx++;
        kjopPanel.add(fornavnFelt, gbcK);
        gbcK.gridy++;
        kjopPanel.add(etternavnFelt, gbcK);
        gbcK.gridy++;
        kjopPanel.add(epostFelt, gbcK);
        gbcK.gridy++;
        kjopPanel.add(tlfFelt, gbcK);
        gbcK.gridy++;
        kjopPanel.add(valgPanel, gbcK);
        
        GridBagConstraints gbcH = new GridBagConstraints();
        gbcH.anchor = GridBagConstraints.CENTER;
        gbcH.insets = new Insets(5, 5, 5, 5);
        gbcH.gridx = 0;
        gbcH.gridy = 0;
        
        hoyrePanel.add(tittel, gbcH);
        gbcH.gridy++;
        hoyrePanel.add(kjopPanel, gbcH);
        gbcH.gridy++;
        hoyrePanel.add(knappePanel, gbcH);
        
        GridBagConstraints gbcV = new GridBagConstraints();
        gbcV.anchor = GridBagConstraints.CENTER;
        gbcV.insets = new Insets(5, 5, 5, 5);
        gbcV.gridx = 0;
        gbcV.gridy = 0;
        
        vindu.add(infoPanel, gbcV);
        gbcV.gridx++;
        vindu.add(hoyrePanel, gbcV);
        
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
    
    /*Metode som tar all info som er ført inn i tekstboksene og det som er valgt i nedfallslistene,
    og lager et billett objekt ut ifra denne innførte videoen og variabelen arrangement,
    som blir satt inn i billettListen til Arrangement objektet arrangement.*/ 
    private void kjopBillett()
    {
        String fn = fornavnFelt.getText();
        String en = etternavnFelt.getText();
        String e = epostFelt.getText();
        if(fn.equals("") || en.equals("") || e.equals(""))
        {
            visMelding("Du må fylle ut info i alle feltene.");
            return;
        }
        if(!e.matches(".+@[a-zA-ZæÆøØåÅöÖäÄàÀèÈëËüÜûÛâÂêÊãÃõÕïÏ\\d]+\\.[a-zA-ZæÆøØåÅöÖäÄàÀèÈëËüÜûÛâÂêÊãÃõÕïÏ\\d]+"))
        {
            visMelding("Du har skrevet inn en ugyldig e-post adresse");
            return;
        }
        if(bBillettValg.getSelectedIndex() == 0 || vBillettValg.getSelectedIndex() == 0)
        {
            visMelding("Du må velge både antall baren billetter og antall voksen billetter");
            return;
        }
        if(bBillettValg.getSelectedIndex() == 1 && vBillettValg.getSelectedIndex() == 1)
        {
            visMelding("Du må velge minst èn billett");
            return;
        }
        try
        {
            int t = Integer.parseInt(tlfFelt.getText());
            int aBB = Integer.parseInt((String) bBillettValg.getSelectedItem());
            int aVB = Integer.parseInt((String) vBillettValg.getSelectedItem());
            
            if((aBB + aVB) > arrangement.getLedigePlasser())
            {
                visMelding("Det er ikke nok ledige plasser igjen.");
                return;
            }
            
            for(int i = 0; i < aBB; i++)
            {
                Billett b = new Billett(fn, en, e, arrangement.getNavn(), arrangement.getLokaleNavn(), arrangement.getPlassNr(), t, arrangement.getBillettprisBarn());
                arrangement.settInnBillett(b);
            }
            
            for(int i = 0; i < aVB; i++)
            {
                Billett b = new Billett(fn, en, e, arrangement.getNavn(), arrangement.getLokaleNavn(), arrangement.getPlassNr(), t, arrangement.getBillettprisVoksen());
                arrangement.settInnBillett(b);
            }
            visMelding("Billettene er bestilt!\nEn faktura på kroner " + (aBB * arrangement.getBillettprisBarn() + aVB * arrangement.getBillettprisVoksen()) + ",- er sendt til din e-post.");
            lukkVindu();
        }
        catch(NumberFormatException nfe)
        {
            visMelding("Feil i formatering av tall.");
        }
    }
    
    /*Metode som tar parametrene og lager en tekst Array. Parametrenes betydning:
    tittel = det første teks objektet i arrayen, start = starverdien til tallene i arrayen(vil være på indeks 1 i arrayen),
    slutt = sluttverdien til tallene i arrayen(vil være på siste indeksen i arrayen).
    Fra start til slutt i arrayen vil hver plass økes med en fra forrige tall, helt til man treffer sluttverdien.*/
    private String[] tallArray(String tittel, int start, int slutt)
    {
        String[] tall = new String[slutt - start + 2];
        tall[0] = tittel;
        for(int i = 1; i < tall.length; i++)
        {
            tall[i] = "" + start++;
        }
        return tall;
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
            if(e.getSource() == kjopKnapp)
                kjopBillett();
            else if(e.getSource() == lukkKnapp)
                lukkVindu();
        }
    }
} //End of class Kjop