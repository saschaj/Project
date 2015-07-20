package Entitys;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
@Table (name="INTERESSENGEBIET")
public class Interessengebiet implements Serializable{
    
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    @Column(name = "INTERESSENGEBIET_ID")
    private int interessengebietId;
    
    @Column (name="NAME")
    private String name;
    
    @Column (name="BESCHREIBUNG")
    private String beschreibung;

    
    public Interessengebiet() {
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

    public int getInteressengebietId() {
        return interessengebietId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.interessengebietId;
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
        final Interessengebiet other = (Interessengebiet) obj;
        if (this.interessengebietId != other.interessengebietId) {
            return false;
        }
        return true;
    }
    
    
}
