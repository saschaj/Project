package Manager;

import Entitys.Vertrag;
import Entitys.Zeit_Einheit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author René
 */
@Stateless
public class SystemManager {
    
    private Calendar now;
    private DatenZugriffsObjekt dao;
    private ArrayList<Vertrag> alleVertraege;
    
    
    private void pruefeAufAblaufendeFristen() {
        now = Calendar.getInstance();
        dao = new DatenZugriffsObjekt();
        alleVertraege = new ArrayList(dao.getAllVertraege());
        for (Vertrag v : alleVertraege) {
            Calendar benachrichtigungsDatum = Calendar.getInstance();
            benachrichtigungsDatum.setTime(v.getVertragEnde());
            
            int benachrichtigungsFrist = v.getBenachrichtigungsfrist();
            int kuendigungsFrist = v.getKuendigungsfrist();
            Zeit_Einheit bfEinheit = v.getBenachrichtigungsfristEinheit();
            Zeit_Einheit kfEinheit = v.getKuendigungsfristEinheit();
            
            datumAnpassen(benachrichtigungsDatum, kfEinheit, -kuendigungsFrist);
            datumAnpassen(benachrichtigungsDatum, bfEinheit, -benachrichtigungsFrist);
            
            if (benachrichtigungsDatum.before(now)) {
                
            }
        }
    }
    
    private void datumAnpassen(Calendar cal, Zeit_Einheit ze, int frist) {        
        switch (ze.getName()){
                case "Tag(e)": cal.add(Calendar.DAY_OF_YEAR, frist);
                    break;
                case "Woche(n)": cal.add(Calendar.WEEK_OF_YEAR, frist);
                     break;
                case "Monat(e)": cal.add(Calendar.MONTH, frist);
                     break;
                case "Jahr(e)": cal.add(Calendar.YEAR, frist);
                     break;
            }        
    }
}
