import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;
import javax.swing.*;
import javax.swing.text.*;

public class InfoArr extends JFrame
{
    private JPanel vindu, infoPanel, knappePanel, bildePanel, tekstInnholdPanel;
    private JTextPane arrInfo;
    private JScrollPane arrInfoScroll;
    private JButton kjopKnapp, lukkKnapp;
    
    private Kommandolytter knappelytter;
    
    private Arrangement arrangement;
    
    DecimalFormat krFormat;
    
    public InfoArr(Arrangement a)
    {
        super("Info om " + a.getNavn());
        
        knappelytter = new Kommandolytter();
        arrangement = a;
        krFormat = new DecimalFormat( "0.00" );
        
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
        lagInfoUtskrift("\t\t" + arrangement.getDatoString(), 14, false, false);
        lagInfoUtskrift("\nPris*:", 14, true, false);
        lagInfoUtskrift("\t\t" + krFormat.format(arrangement.getBillettprisBarn()) + ",- / " + krFormat.format(arrangement.getBillettprisVoksen()) + ",-", 14, false, false);
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
        
        knappePanel.add(kjopKnapp, gbcK);
        gbcK.gridx += 1;
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
    
    private void lukkVindu()
    {
        this.dispose();
    }
    
    public void kjopBillett()
    {
        JFrame kjopVindu = new Kjop(arrangement);
        kjopVindu.setLocationRelativeTo(this);
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
} //End of class InfoArr