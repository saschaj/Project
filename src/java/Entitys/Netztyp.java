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
    
    @Column (name="HANDY_TYP")
    private boolean handyTyp;
    
    @Column (name="FESTNETZ_TYP")
    private boolean festnetzTyp;
    
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

    public boolean isHandyTyp() {
        return handyTyp;
    }

    public void setHandyTyp(boolean handyTyp) {
        this.handyTyp = handyTyp;
    }

    public boolean isFestnetzTyp() {
        return festnetzTyp;
    }

    public void setFestnetzTyp(boolean festnetzTyp) {
        this.festnetzTyp = festnetzTyp;
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
