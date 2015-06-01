package Entitys;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
public class Vertrag_Status implements Serializable {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int vertragStatusId;
    
    private String name;
    private String beschreibung;

    public Vertrag_Status() {
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

    public int getVertragStatusId() {
        return vertragStatusId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.vertragStatusId;
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
        final Vertrag_Status other = (Vertrag_Status) obj;
        if (this.vertragStatusId != other.vertragStatusId) {
            return false;
        }
        return true;
    }
    
    
}
