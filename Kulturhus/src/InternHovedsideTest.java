import java.net.*;
import java.time.*;
import javax.swing.*;

public class InternHovedsideTest extends JFrame
{
    private static final int ALLE = -3;
    private static final int FAGLIGE = -2;
    private static final int UNDERHOLDNING = -1;
    private static final int DEBATT = 0;
    private static final int FOREDRAG = 1;
    private static final int POLITISK_MOTE = 2;
    private static final int BARNE_FORESTILLING = 3;
    private static final int KINO = 4;
    private static final int KONSERT = 5;
    private static final int TEATER = 6;
    
    private JTable arrTabell;
    private JScrollPane tabellScroll;
    private String[] kolNavn = {"Arrangement navn", "Dato", "Pris barn", "Pris Voksen", "Lokale", "Ledig plasser"};
    private Arrangement[] arrListe;
    private int arrType = ALLE;
    
    
    private Lokalregister lregister;
    
    public InternHovedsideTest(Lokalregister l)
    {
        super("Målselv Kommune - For ansatte");
        
        lregister = l;
        
        arrTabell = new JTable(arrInfoListe(), kolNavn);
        arrTabell.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        arrTabell.setRowSelectionAllowed(true);
        arrTabell.setColumnSelectionAllowed(false);
        
        tabellScroll = new JScrollPane(arrTabell, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        add(tabellScroll);
        pack();
        setVisible(true);
    }
    
    private String[][] arrInfoListe()
    {
        arrListe = lregister.getArrangementer(arrType);
        String[][] arrInfoArray = new String[arrListe.length][kolNavn.length];
        for(int i = 0; i < arrListe.length; i++)
        {
            arrInfoArray[i][0] = arrListe[i].getNavn();
            arrInfoArray[i][1] = arrListe[i].getDatoString();
            arrInfoArray[i][2] = "" + arrListe[i].getBillettprisBarn();
            arrInfoArray[i][3] = "" + arrListe[i].getBillettprisVoksen();
            arrInfoArray[i][4] = arrListe[i].getLokaleNavn();
            arrInfoArray[i][5] = "" + arrListe[i].getLedigePlasser();
        }
        return arrInfoArray;
    }
}