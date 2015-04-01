public class Lokale
{
    private String navn;
    private int type, lokaleNr;
    private static int nesteNr = 0;
    private int[] plasser;
    private boolean harNrPlasser;
    
    public Lokale(String n, int t, int p, boolean hNP)
    {
        navn = n;
        type = t;
        harNrPlasser = hNP;
        lokaleNr = ++nesteNr;

        plasser = new int[p];
        for(int i = 0; i < p; i++)
        {
            plasser[i] = i+1;
        }
    }
}