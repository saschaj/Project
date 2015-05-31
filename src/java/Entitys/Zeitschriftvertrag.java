package Entitys;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
public class Zeitschriftvertrag extends Vertrag implements Serializable {
    
    @Column (name="ZEITSCHRIFT_NAME")
    private String zeitschriftName;
    private String lieferintervall;
    private String interessengebiet;

    public Zeitschriftvertrag() {
    }

    public String getZeitschriftName() {
        return zeitschriftName;
    }

    public void setZeitschriftName(String zeitschriftName) {
        this.zeitschriftName = zeitschriftName;
    }

    public String getLieferintervall() {
        return lieferintervall;
    }

    public void setLieferintervall(String lieferintervall) {
        this.lieferintervall = lieferintervall;
    }

    public String getInteressengebiet() {
        return interessengebiet;
    }

    public void setInteressengebiet(String interessengebiet) {
        this.interessengebiet = interessengebiet;
    }
}
