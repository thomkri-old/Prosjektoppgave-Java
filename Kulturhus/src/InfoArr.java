import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.*;

public class InfoArr extends JFrame
{
    private JPanel vindu, infoPanel, knappePanel, bildePanel;
    private JTextPane arrInfo;
    private JScrollPane arrInfoScroll;
    private JButton kjopKnapp, lukkKnapp;
    
    private Kommandolytter knappelytter;
    
    private Arrangement arrangement;
    
    public InfoArr(Arrangement a)
    {
        super("Info om " + a.getNavn());
        
        knappelytter = new Kommandolytter();
        arrangement = a;
        
        vindu = new JPanel(new GridBagLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        bildePanel = new ArrbildePanel(arrangement.getArrBilde());
        
        arrInfo = new JTextPane();
        arrInfo.setBackground(this.getBackground());
        arrInfo.setEditable(false);
        arrInfo.setPreferredSize(new Dimension(300, 150));
        
        lagInfoUtskrift(arrangement.getNavn() + "\n", 20, true);
        lagInfoUtskrift("\nDato:", 14, true);
        lagInfoUtskrift("\t\t" + arrangement.getDatoString(), 14, false);
        lagInfoUtskrift("\nPris*:", 14, true);
        lagInfoUtskrift("\t\t" + arrangement.getBillettprisBarn() + ",- / " + arrangement.getBillettprisVoksen() + ",-", 14, false);
        lagInfoUtskrift("\nDeltakere:", 14, true);
        
        String[] deltakere = arrangement.getDeltakere();
        for(int i = 0; i < deltakere.length; i++)
            lagInfoUtskrift("\t" + ((i == 0)?"":"\t") + deltakere[i] + "\n", 14, false);
        
        lagInfoUtskrift("Ledige plasser:", 14, true);
        lagInfoUtskrift("\t" + arrangement.getLedigePlasser(), 14, false);
        lagInfoUtskrift("\n\n" + arrangement.getProgram(), 14, false);
        
        arrInfo.setCaretPosition(0);
        
        arrInfoScroll = new JScrollPane(arrInfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        arrInfoScroll.setPreferredSize(new Dimension(300, 150));
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
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        infoPanel.add(bildePanel, gbc);
        gbc.gridx += 1;        
        infoPanel.add(arrInfoScroll, gbc);
        
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
    
    private void lagInfoUtskrift(String tekst, int fontStr, boolean fetSkrift)
    {
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setFontSize(style, fontStr);
        StyleConstants.setBold(style, fetSkrift);
        
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