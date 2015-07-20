package Entitys;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
public class Festnetzvertrag extends Vertrag implements Serializable{
    
    private String tarifname;
    
    @ManyToOne
    @JoinColumn (name="NETZTYP_ID")
    private Netztyp netztypp;

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

}
