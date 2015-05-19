import java.io.*;
import java.time.*;
import javax.swing.*;

public abstract class UnderholdningsArrangement extends Arrangement implements Serializable
{
    private String sjanger;
    
    public UnderholdningsArrangement(String n, String p, String lN, int t, double bpB, double bpV, String[] dt, LocalDateTime d, ImageIcon aB, Kontaktperson kP, String sj)
    {
        super(n, p, lN, t, bpB, bpV, dt, d, aB, kP);
        sjanger = sj;
    }
    
    public String toString()
    {
        String tekst = super.toString();
        tekst += "\nSjanger: " + sjanger;
        return tekst;
    }
} //End of class UnderholdningsArrangement