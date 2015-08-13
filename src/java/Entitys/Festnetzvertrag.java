package Entitys;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
public class Festnetzvertrag extends Vertrag implements Serializable{
    
    @Column (name = "TARIFNAME")
    private String tarifname;
    
    @ManyToOne
    @JoinColumn (name="NETZTYP_ID")
    private Netztyp netztypp;
    
    @Column (name = "IST_ISDN", columnDefinition = "boolean default false")
    private boolean istISDN;
    
    @Column (name = "IST_VOIP", columnDefinition = "boolean default false")
    private boolean istVOIP;

    public Festnetzvertrag() {
    }

    public String getTarifname() {
        return tarifname;
    }

    public void setTarifname(String tarifname) {
        this.tarifname = tarifname;
    }

    public Netztyp getNetztypp() {
        return netztypp;
    }

    public void setNetztypp(Netztyp netztypp) {
        this.netztypp = netztypp;
    }

    public boolean isIstISDN() {
        return istISDN;
    }

    public void setIstISDN(boolean istISDN) {
        this.istISDN = istISDN;
    }

    public boolean isIstVOIP() {
        return istVOIP;
    }

    public void setIstVOIP(boolean istVOIP) {
        this.istVOIP = istVOIP;
    }
}
