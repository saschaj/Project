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
    
    @Column (name="LIEFERINTERVALL")
    private int lieferintervall;
    
    @ManyToOne
    @JoinColumn (name="LIEFERINTERVALL_EINHEIT")
    private Zeit_Einheit lieferintervallEinheit;
    
    @ManyToOne
    @JoinColumn (name="INTERESSENGEBIET_ID")
    private Interessengebiet interessengebiet;

    public Zeitschriftvertrag() {
    }

    public String getZeitschriftName() {
        return zeitschriftName;
    }

    public void setZeitschriftName(String zeitschriftName) {
        this.zeitschriftName = zeitschriftName;
    }

    public int getLieferintervall() {
        return lieferintervall;
    }

    public void setLieferintervall(int lieferintervall) {
        this.lieferintervall = lieferintervall;
    }

    public Zeit_Einheit getLieferintervallEinheit() {
        return lieferintervallEinheit;
    }

    public void setLieferintervallEinheit(Zeit_Einheit lieferintervallEinheit) {
        this.lieferintervallEinheit = lieferintervallEinheit;
    }

    public Interessengebiet getInteressengebiet() {
        return interessengebiet;
    }

    public void setInteressengebiet(Interessengebiet interessengebiet) {
        this.interessengebiet = interessengebiet;
    }
    
    
}
