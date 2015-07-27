package Entitys;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
@Table(name = "BENUTZER_STATUS")
public class Benutzer_Status implements Serializable {

    @Id
    @Column (name="BENUTZER_STATUS_ID")
    private int benutzerStatusId;

//    @OneToMany(mappedBy = "status", cascade = CascadeType.PERSIST, 
//            fetch = FetchType.LAZY)
//    private Set<Benutzer> benutzer;

    private String name;
    private String beschreibung;

    public Benutzer_Status() {
    }

    public int getBenutzerStatusId() {
        return benutzerStatusId;
    }

//    public Set<Benutzer> getBenutzer() {
//        return benutzer;
//    }

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.benutzerStatusId;
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
        final Benutzer_Status other = (Benutzer_Status) obj;
        if (this.benutzerStatusId != other.benutzerStatusId) {
            return false;
        }
        return true;
    }
    
}
