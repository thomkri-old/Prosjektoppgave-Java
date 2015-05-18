import java.time.*;
import java.util.*;

public class Lokalregister
{   
    private List<Lokale> register;
    
    public Lokalregister()
    {
        register = new LinkedList<>();
    }
    
    public void settInn(Lokale l)
    {
        register.add(l);
    }
    
    public void sorter()
    {
        Collections.sort(register, new Lokalesammenlikner());
    }
    
    public Arrangement[] getArrangementer(int type, LocalDate[] dArray)
    {
        if(register.isEmpty())
            return null;
        
        Iterator<Lokale> registerIter = register.iterator();
        Arrangement[][] multiArr = new Arrangement[register.size()][];
        int i = 0;
        while(registerIter.hasNext())
        {
            multiArr[i++] = registerIter.next().getArrangementer(type, dArray);
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
    
    public Arrangement[] getMestInntektArr()
    {
        if(register.isEmpty())
            return null;
        
        Arrangement[] mestInntektArray = new Arrangement[10];
        Iterator<Lokale> registerIter = register.iterator();
        while(registerIter.hasNext())
        {
            Arrangement[] arrArray = registerIter.next().getMestInntektArr();
            if(arrArray != null)
            {
                for(int i = 0; i < arrArray.length; i++)
                {
                    Arrangement a = arrArray[i];
                    for(int y = 0; y < mestInntektArray.length; y++)
                    {
                        if(mestInntektArray[y] == null || (a != null && mestInntektArray[y].getTotalInntekt() < a.getTotalInntekt()))
                        {
                            for(int z = mestInntektArray.length - 1; z > y; z--)
                            {
                                mestInntektArray[z] = mestInntektArray[z-1];
                            }
                            mestInntektArray[y] = a;
                            break;
                        }
                    }
                }
            }
        }
        return mestInntektArray;
    }
    
    public Lokale[] getLokaler()
    {
        if(register.isEmpty())
            return null;
        
        sorter();
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
        
        sorter();
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
    
    public boolean avlysArr(Arrangement avlysA)
    {
        if(register.isEmpty())
            return false;
        
        Iterator<Lokale> registerIter = register.iterator();
        while(registerIter.hasNext())
        {
            Lokale l = registerIter.next();
            if(l.avlysArr(avlysA))
                return true;
        }
        return false;
    }
} //End of class Lokalregister