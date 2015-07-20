package Entitys;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
@Table (name="NETZTYP")
public class Netztyp implements Serializable {
    
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private int netztypId;
    
    @Column (name="NAME")
    private String name;
    
    @Column (name="BESCHREIBUNG")
    private String beschreibung;
    
    @Column (name="IST_HANDY_TYP")
    private boolean istHandyTyp;
    
    @Column (name="IST_FESTNETZ_TYP")
    private boolean istFestnetzTyp;
    
    public Netztyp() {
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getNetztypId() {
        return netztypId;
    }

    public boolean isIstHandyTyp() {
        return istHandyTyp;
    }

    public void setIstHandyTyp(boolean istHandyTyp) {
        this.istHandyTyp = istHandyTyp;
    }

    public boolean isIstFestnetzTyp() {
        return istFestnetzTyp;
    }

    public void setIstFestnetzTyp(boolean istFestnetzTyp) {
        this.istFestnetzTyp = istFestnetzTyp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.netztypId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Netztyp other = (Netztyp) obj;
        if (this.netztypId != other.netztypId) {
            return false;
        }
        return true;
    }
    
}
