import javax.swing.*;
import java.awt.*;

public class RegistreringsVindu extends JFrame
{
    private JPanel vindu, utfylling;
    private JLabel infoTekst, arrNavn, program, bPrisBarn, bPrisVoksen, deltakere, dato, kPerson;
    private JTextField navnFelt, bpBarnFelt, bpVoksenFelt, kPersonFelt, datoFelt;
    private JTextArea programFelt, deltakereFelt;
    private JButton avbrytKnapp, regKnapp;
    
    public RegistreringsVindu(String tittel, int type)
    {
        super(tittel);
        
        vindu = new JPanel(new GridBagLayout());
        vindu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        utfylling = new JPanel(new GridBagLayout());
        utfylling.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        infoTekst = new JLabel("Fyll inn info:");
        arrNavn = new JLabel("Arrangement navn:");
        program = new JLabel("Program:");
        bPrisBarn = new JLabel("Billettpris barn:");
        bPrisVoksen = new JLabel("Billettpris voksen:");
        deltakere = new JLabel("Deltakere (skilles med komma \",\"):");
        dato = new JLabel("Dato:");
        kPerson = new JLabel("Kontakt person:");
        
        navnFelt = new JTextField(10);
        bpBarnFelt = new JTextField(4);
        bpVoksenFelt = new JTextField(4);
        kPersonFelt = new JTextField(10);
        datoFelt = new JTextField(8);
        
        programFelt = new JTextArea(5, 15);
        deltakereFelt = new JTextArea(2, 15);
        
        avbrytKnapp = new JButton("Avbryt");
        regKnapp = new JButton("Registrer");
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        utfylling.add(arrNavn, gbc);
        gbc.gridx++;
        utfylling.add(navnFelt, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        utfylling.add(program, gbc);
        gbc.gridx++;
        utfylling.add(programFelt, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        utfylling.add(bPrisBarn, gbc);
        gbc.gridx++;
        utfylling.add(bpBarnFelt, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        utfylling.add(bPrisVoksen, gbc);
        gbc.gridx++;
        utfylling.add(bpVoksenFelt, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        utfylling.add(deltakere, gbc);
        gbc.gridx++;
        utfylling.add(deltakereFelt, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        utfylling.add(dato, gbc);
        gbc.gridx++;
        utfylling.add(datoFelt, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        utfylling.add(kPerson, gbc);
        gbc.gridx++;
        utfylling.add(kPersonFelt, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        vindu.add(infoTekst, gbc);
        gbc.gridy++;
        vindu.add(utfylling, gbc);
        gbc.gridy++;
        vindu.add(avbrytKnapp, gbc);
        gbc.gridx++;
        vindu.add(regKnapp, gbc);
        
        add(vindu);
        pack();
        setResizable(false);
        setVisible(true);
    }
} //End of class RegistreringsVindu