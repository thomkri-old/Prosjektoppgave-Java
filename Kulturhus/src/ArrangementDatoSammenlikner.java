import java.time.*;
import java.util.*;

public class ArrangementDatoSammenlikner implements Comparator<Arrangement>
{
    public int compare(Arrangement a1, Arrangement a2)
    {
        LocalDateTime ldt1 = a1.getDato();
        LocalDateTime ldt2 = a2.getDato();
        return ldt1.compareTo(ldt2);
    }
}
