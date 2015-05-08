import java.util.*;

public class Lokalregister
{   
    private Set<Lokale> register;
    
    public Lokalregister()
    {
        register = new HashSet<>();
    }
    
    public void settInn(Lokale l)
    {
        register.add(l);
    }
    
    public Arrangement[] getArrangementer(int type)
    {
        if(register.isEmpty())
            return null;
        
        Iterator<Lokale> registerIter = register.iterator();
        Arrangement[][] multiArr = new Arrangement[register.size()][];
        int i = 0;
        while(registerIter.hasNext())
        {
            Lokale l = registerIter.next();
            multiArr[i] = l.getArrangementer(type);
            i++;
        }

        int antArr = 0;
        for(int y = 0; y < multiArr.length; y++)
        {
            if(multiArr[y] != null)
                antArr += multiArr[y].length;
        }
            
        if(antArr == 0)
            return null;
            
        Arrangement[] arrangementer = new Arrangement[antArr];
        int teller = 0;
        for(int x = 0; x < multiArr.length; x++)
            if(multiArr[x] != null)
                for(int z = 0; z < multiArr[x].length; z++)
                    arrangementer[teller++] = multiArr[x][z];

        return arrangementer;
    }
    
    public Lokale[] getLokaler()
    {
        if(register.isEmpty())
            return null;
        
        Iterator<Lokale> registerIter = register.iterator();
        Lokale[] lokaler = new Lokale[register.size()];
        int i = 0;
        while(registerIter.hasNext())
            lokaler[i++] = registerIter.next();
        return lokaler;
    }
    
    public Lokale[] getLokaler(int type)
    {
        if(register.isEmpty())
            return null;
        
        Iterator<Lokale> registerIter = register.iterator();
        Lokale[] lokaler = new Lokale[antLokaler(type, registerIter)];
        int i = 0;
        while(registerIter.hasNext())
        {
            Lokale l = registerIter.next();
            if(l.getType() == type)
                lokaler[i++] = l;
        }
        return lokaler;
    }
    
    private int antLokaler(int type, Iterator<Lokale> iter)
    {
        if(register.isEmpty())
            return 0;
        
        int ant = 0;
        while(iter.hasNext())
        {
            if(iter.next().getType() == type)
                ant++;
        }
        return ant;
    }
} //End of class Lokalregister