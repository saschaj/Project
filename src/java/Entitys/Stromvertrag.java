package Entitys;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
public class Stromvertrag extends Vertrag implements Serializable{
    
    @Column (name="STROMZAEHLER_NR")
    private String stromzaehlerNr;
    @Column (name="STROMZAEHLER_STAND")
    private int stromzaehlerStand;
    @Column (name="VERBRAUCH_PRO_JAHR")
    private int verbrauchProJahr;
    @Column (name="PREIS_PRO_KWH")
    private float preisProKwh;

    public Stromvertrag() {
    }

    public String getStromzaehlerNr() {
        return stromzaehlerNr;
    }

    public void setStromzaehlerNr(String stromzaehlerNr) {
        this.stromzaehlerNr = stromzaehlerNr;
    }

    public int getStromzaehlerStand() {
        return stromzaehlerStand;
    }

    public void setStromzaehlerStand(int stromzaehlerStand) {
        this.stromzaehlerStand = stromzaehlerStand;
    }

    public int getVerbrauchProJahr() {
        return verbrauchProJahr;
    }

    public void setVerbrauchProJahr(int verbrauchProJahr) {
        this.verbrauchProJahr = verbrauchProJahr;
    }

    public float getPreisProKwh() {
        return preisProKwh;
    }

    public void setPreisProKwh(float preisProKwh) {
        this.preisProKwh = preisProKwh;
    }
    
}
