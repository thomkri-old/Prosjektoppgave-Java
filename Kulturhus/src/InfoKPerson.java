import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class InfoKPerson extends JFrame
{
    private static final int SCROLLSPEED = 16;
    
    private JPanel vindu, tekstInnholdPanel;
    private JTextPane info;
    private JScrollPane infoScroll;
    private JButton lukkKnapp;
    
    private Kommandolytter knappelytter;
    
    private Kontaktperson kPerson;
    
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
        
        lagInfoUtskrift(kPerson.getNavn() + "\n", 20, true, false);
        lagInfoUtskrift("\nFirma:", 14, true, false);
        lagInfoUtskrift("\t" + kPerson.getFirma(), 14, false, false);
        lagInfoUtskrift("\nTelefon:", 14, true, false);
        lagInfoUtskrift("\t" + kPerson.getTlfNr(), 14, false, false);
        lagInfoUtskrift("\nE-post:", 14, true, false);
        lagInfoUtskrift("\t" + kPerson.getEpost(), 14, false, false);
        lagInfoUtskrift("\nNettside:", 14, true, false);
        lagInfoUtskrift("\t" + kPerson.getNettside(), 14, false, false);
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
            if(e.getSource() == lukkKnapp)
                lukkVindu();
        }
    }
}
