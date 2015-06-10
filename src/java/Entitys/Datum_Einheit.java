package Entitys;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
@Table (name="DATUM_EINHEIT")
public class Datum_Einheit implements Serializable {
    
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private int datumEinheitId;
    
    @Column (name="NAME")
    private String name;
    
    @Column (name="BESCHREIBUNG")
    private String beschreibung;
    
    /**
     * Standartkonstruktor.
     */
    public Datum_Einheit() {    
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

    public int getDatumEinheitId() {
        return datumEinheitId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.datumEinheitId;
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
        final Datum_Einheit other = (Datum_Einheit) obj;
        if (this.datumEinheitId != other.datumEinheitId) {
            return false;
        }
        return true;
    }
    
    
}
