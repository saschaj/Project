package Entitys;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
@Table (name="Zeit"
        + "_EINHEIT")
public class Zeit_Einheit implements Serializable {
    
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private int zeitEinheitId;
    
    @Column (name="NAME")
    private String name;
    
    @Column (name="BESCHREIBUNG")
    private String beschreibung;
    
    /**
     * Standartkonstruktor.
     */
    public Zeit_Einheit() {    
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

    public int getZeitEinheitId() {
        return zeitEinheitId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.zeitEinheitId;
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
        final Zeit_Einheit other = (Zeit_Einheit) obj;
        if (this.zeitEinheitId != other.zeitEinheitId) {
            return false;
        }
        return true;
    }

    
    
    
}
