import java.text.*;
import java.util.*;
import javax.swing.*;

public class Lokalesammenlikner implements Comparator<Lokale>
{
    private String rekkefolge = "<\0<0<1<2<3<4<5<6<7<8<9<A,a<B,b<C,c<D,d<E,e<F,f<G,g<H,h<I,i<J,j<K,k<L,l<M,m<N,n<O,o<P,p<Q,q<R,r<S,s<T,t<U,u<V,v<W,w<X,x<Y,y<Z,z<Æ,æ<Ø,ø<Å=AA,å=aa;AA,aa";
    private RuleBasedCollator kollator;
    
    public Lokalesammenlikner()
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
    
    public int compare(Lokale l1, Lokale l2)
    {
        String lNavn1 = l1.getNavn();
        String lNavn2 = l2.getNavn();
        return kollator.compare(lNavn1, lNavn2);
    }
}