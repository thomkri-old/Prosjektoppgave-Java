import java.text.*;
import java.time.*;
import java.util.*;
import javax.swing.*;

public class ArrangementDatoSammenlikner implements Comparator<Arrangement>
{
    private String rekkefolge = "<\0<0<1<2<3<4<5<6<7<8<9<A,a<B,b<C,c<D,d<E,e<F,f<G,g<H,h<I,i<J,j<K,k<L,l<M,m<N,n<O,o<P,p<Q,q<R,r<S,s<T,t<U,u<V,v<W,w<X,x<Y,y<Z,z<Æ,æ<Ø,ø<Å=AA,å=aa;AA,aa";
    private RuleBasedCollator kollator;
    
    public ArrangementDatoSammenlikner()
    {
        try
        {
            kollator = new RuleBasedCollator(rekkefolge);
        }
        catch(ParseException pe)
        {
            JOptionPane.showMessageDialog(null, "Feil i rekkefølge på kollatoren.");
        }
    }
    
    public int compare(Arrangement a1, Arrangement a2)
    {
        LocalDateTime ldt1 = a1.getDato();
        LocalDateTime ldt2 = a2.getDato();
        String aNavn1 = a1.getNavn();
        String aNavn2 = a2.getNavn();
        
        int d = ldt1.compareTo(ldt2);
        if(d != 0)
            return d;
        return kollator.compare(aNavn1, aNavn2);
    }
}
