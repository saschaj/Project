package Entitys;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author René
 */
@Entity
@Table( name = "ADRESSE")
public class Adresse implements Serializable{
    
    @Id
    @GeneratedValue
    @Column (name = "ADRESS_ID")
    private int adressId;
    
    @Column (name = "STRASSE")
    private String strasse;
    
    @Column (name = "HAUSNR")
    private String hausNr;
    
    @Column (name = "PLZ")
    private String plz;
    
    @Column (name = "LAND")
    private String land;
    
    @Column (name = "ORT")
    private String ort;
    
    public Adresse() {
    }

    public int getAdressId() {
        return adressId;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getHausNr() {
        return hausNr;
    }

    public void setHausNr(String hausNr) {
        this.hausNr = hausNr;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }
    
}
