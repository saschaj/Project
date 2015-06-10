package Entitys;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
public class Gasvertrag extends Vertrag implements Serializable {
    
    @Column (name="GASZAEHLER_NR")
    private String gaszaehlerNr;
    
    @Column (name="GASZAEHLER_STAND")
    private int gaszaehlerStand;
    
    @Column (name="PREIS_PRO_KWH")
    private float preisProKhw;
    
    @Column (name="VERBRAUCH_PRO_JAHR")
    private int verbrauchProJahr;

    public Gasvertrag() {
    }

    public String getGaszaehlerNr() {
        return gaszaehlerNr;
    }

    public void setGaszaehlerNr(String gaszaehlerNr) {
        this.gaszaehlerNr = gaszaehlerNr;
    }

    public int getGaszaehlerStand() {
        return gaszaehlerStand;
    }

    public void setGaszaehlerStand(int gaszaehlerStand) {
        this.gaszaehlerStand = gaszaehlerStand;
    }

    public float getPreisProKhw() {
        return preisProKhw;
    }

    public void setPreisProKhw(float preisProKhw) {
        this.preisProKhw = preisProKhw;
    }

    public int getVerbrauchProJahr() {
        return verbrauchProJahr;
    }

    public void setVerbrauchProJahr(int verbrauchProJahr) {
        this.verbrauchProJahr = verbrauchProJahr;
    }
    
}
