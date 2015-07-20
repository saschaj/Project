package Entitys;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
public class Stromvertrag extends Vertrag implements Serializable {

    @Column(name = "STROMZAEHLER_NR")
    private String stromzaehlerNr;

    @Column(name = "STROMZAEHLER_STAND")
    private int stromzaehlerStand;

    @Column(name = "VERBRAUCH_PRO_JAHR")
    private int verbrauchProJahr;

    @Column(name = "PREIS_PRO_KWH")
    private float preisProKwh;

    @Column(name = "ANZ_PERSONEN_HAUSHALT")
    private int anzPersonenHaushalt;
    
    @Column (name = "GRUNDPREIS_MONAT")
    private float grundpreisMonat;

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

    public int getAnzPersonenHaushalt() {
        return anzPersonenHaushalt;
    }

    public void setAnzPersonenHaushalt(int anzPersonenHaushalt) {
        this.anzPersonenHaushalt = anzPersonenHaushalt;
    }

    public float getGrundpreisMonat() {
        return grundpreisMonat;
    }

    public void setGrundpreisMonat(float grundpreisMonat) {
        this.grundpreisMonat = grundpreisMonat;
    }

}
