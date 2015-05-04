import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class Kjop extends JFrame
{
    private JPanel vindu, infoPanel, hoyrePanel, kjopPanel, knappePanel, bildePanel, valgPanel;
    private JTextPane arrInfo;
    private JScrollPane infoScroll;
    private JButton kjopKnapp, lukkKnapp;
    
    private JLabel fornavn, etternavn, epost, tlf, antBilletter;
    private JTextField fornavnFelt, etternavnFelt, epostFelt, tlfFelt;
    private JComboBox<String> bBillettValg, vBillettValg;
    
    private Kommandolytter knappelytter;
    
    private Arrangement arrangement;
    
    public Kjop(Arrangement a)
    {
        super("Kjøp billett for " + a.getNavn());
        
        arrangement = a;
        knappelytter = new Kommandolytter();
        
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
        lagInfoUtskrift("\t\t" + arrangement.getDatoString(), 14, false, false);
        lagInfoUtskrift("\nPris*:", 14, true, false);
        lagInfoUtskrift("\t\t" + arrangement.getBillettprisBarn() + ",- / " + arrangement.getBillettprisVoksen() + ",-", 14, false, false);
        lagInfoUtskrift("\nLedige plasser:", 14, true, false);
        lagInfoUtskrift("\t" + arrangement.getLedigePlasser(), 14, false, false);
        lagInfoUtskrift("\n\n*Pris per billett skrevet på formen barn/voksen", 12, false, true);
        
        arrInfo.setCaretPosition(0);
        
        infoScroll = new JScrollPane(arrInfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        infoScroll.setPreferredSize(new Dimension(300, 150));
        infoScroll.setBorder(BorderFactory.createEmptyBorder( 0, 0, 0, 0 ));
        
        kjopPanel = new JPanel(new GridBagLayout());
        
        fornavn = new JLabel("Fornavn:");
        etternavn = new JLabel("Etternavn:");
        epost = new JLabel("E-post:");
        tlf = new JLabel("Telefonnummer");
        antBilletter = new JLabel("Antall:");
        
        fornavnFelt = new JTextField(10);
        etternavnFelt = new JTextField(10);
        epostFelt = new JTextField(15);
        tlfFelt = new JTextField(8);
        
        bBillettValg = new JComboBox<>(tallArray("Barn", 1, 20));
        bBillettValg.setPreferredSize(new Dimension(70, 20));
        
        vBillettValg = new JComboBox<>(tallArray("Voksne", 1, 20));
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
        gbc.gridx += 1;
        knappePanel.add(lukkKnapp, gbc);
        
        GridBagConstraints gbcI = new GridBagConstraints();
        gbcI.anchor = GridBagConstraints.CENTER;
        gbcI.insets = new Insets(5, 5, 5, 5);
        gbcI.gridx = 0;
        gbcI.gridy = 0;
        
        infoPanel.add(bildePanel, gbcI);
        gbcI.gridy += 1;
        gbcI.anchor = GridBagConstraints.FIRST_LINE_START;
        infoPanel.add(infoScroll, gbcI);
        
        GridBagConstraints gbcK = new GridBagConstraints();
        gbcK.anchor = GridBagConstraints.FIRST_LINE_START;
        gbcK.insets = new Insets(5, 5, 5, 5);
        gbcK.gridx = 0;
        gbcK.gridy = 0;
        
        kjopPanel.add(fornavn, gbcK);
        gbcK.gridy += 1;
        kjopPanel.add(etternavn, gbcK);
        gbcK.gridy += 1;
        kjopPanel.add(epost, gbcK);
        gbcK.gridy += 1;
        kjopPanel.add(tlf, gbcK);
        gbcK.gridy += 1;
        kjopPanel.add(antBilletter, gbcK);
        
        gbcK.gridy = 0;
        gbcK.gridx += 1;
        kjopPanel.add(fornavnFelt, gbcK);
        gbcK.gridy += 1;
        kjopPanel.add(etternavnFelt, gbcK);
        gbcK.gridy += 1;
        kjopPanel.add(epostFelt, gbcK);
        gbcK.gridy += 1;
        kjopPanel.add(tlfFelt, gbcK);
        gbcK.gridy += 1;
        kjopPanel.add(valgPanel, gbcK);
        
        GridBagConstraints gbcH = new GridBagConstraints();
        gbcH.anchor = GridBagConstraints.CENTER;
        gbcH.insets = new Insets(5, 5, 5, 5);
        gbcH.gridx = 0;
        gbcH.gridy = 0;
        
        hoyrePanel.add(kjopPanel, gbcH);
        gbcH.gridy += 1;
        hoyrePanel.add(knappePanel, gbcH);
        
        GridBagConstraints gbcV = new GridBagConstraints();
        gbcV.anchor = GridBagConstraints.FIRST_LINE_START;
        gbcV.insets = new Insets(5, 5, 5, 5);
        gbcV.gridx = 0;
        gbcV.gridy = 0;
        
        vindu.add(infoPanel, gbcV);
        gbcV.gridx += 1;
        vindu.add(hoyrePanel, gbcV);
        
        add(vindu);
        pack();
        setResizable(false);
        setVisible(true);
    }
    
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
    
    private void kjopBillett()
    {
        
    }
    
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
    
    private void lukkVindu()
    {
        this.dispose();
    }
    
    private void visMelding(String melding)
    {
        JOptionPane.showMessageDialog(this, melding);
    }
    
    private class Kommandolytter implements ActionListener //Kommandolytteren som bestemmer hvilken metode som blir utført utifra hvilken knapp det blir trykket på
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