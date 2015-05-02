import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.*;

public class InfoArr extends JFrame
{
    private JPanel vindu, infoPanel, knappePanel, bildePanel;
    private JTextPane arrInfo;
    private JButton kjopKnapp, lukkKnapp;
    
    private SimpleAttributeSet attributeSet;
    
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
        
        attributeSet = new SimpleAttributeSet();
        
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
        infoPanel.add(arrInfo, gbc);
        
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
    
    private void lagInfoUtskrift(int posisjon, int fontStr, boolean fetSkrift, boolean undertekst)
    {
        StyleContext infoContext = new StyleContext();
        StyledDocument infoDokument = new DefaultStyledDocument(infoContext);
        
        Style styleOverskrift = infoContext.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(styleOverskrift, posisjon);
        StyleConstants.setFontSize(styleOverskrift, fontStr);
        StyleConstants.setSpaceAbove(styleOverskrift, 4);
        StyleConstants.setSpaceBelow(styleOverskrift, 4);
        StyleConstants.setBold(styleOverskrift, fetSkrift);
        StyleConstants.setUnderline(styleOverskrift, undertekst);
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