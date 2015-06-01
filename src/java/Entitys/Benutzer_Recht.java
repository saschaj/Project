package Entitys;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Collection;

/**
 *
 * @author René
 */
@Entity
public class Benutzer_Recht implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="BENUTZER_RECHT_ID")
    private int benutzerRechtId;
    
    @ManyToMany (mappedBy="rechte", cascade=CascadeType.PERSIST)
    private Collection<Benutzer> benutzer;
    
    private String name;
    private String beschreibung;

    public Benutzer_Recht() {
    }

    public int getBenutzerRechtId() {
        return benutzerRechtId;
    }

    public Collection<Benutzer> getBenutzer() {
        return benutzer;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.benutzerRechtId;
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
        final Benutzer_Recht other = (Benutzer_Recht) obj;
        if (this.benutzerRechtId != other.benutzerRechtId) {
            return false;
        }
        return true;
    }
    
}
