package Entitys;

import java.util.Collection;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
@Table(name = "BENUTZER")
@Inheritance(strategy = javax.persistence.InheritanceType.JOINED)
public class Benutzer implements java.io.Serializable {

    @Id
    @GeneratedValue
    private int benutzerId;

    @ManyToOne
    private Benutzer_Status status;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "BENUTZER_RECHT_ZUORDNUNG")
    private Collection<Benutzer_Recht> rechte;

    private String email;
    private String passwort;

    public Benutzer() {
    }

    public int getBenutzerId() {
        return benutzerId;
    }

    public Benutzer_Status getStatus() {
        return status;
    }

    public void setStatus(Benutzer_Status status) {
        this.status = status;
    }

    public Collection<Benutzer_Recht> getRechte() {
        return rechte;
    }

    public void setRechte(Collection<Benutzer_Recht> rechte) {
        this.rechte = rechte;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.benutzerId;
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
        final Benutzer other = (Benutzer) obj;
        if (this.benutzerId != other.benutzerId) {
            return false;
        }
        return true;
    }
    
}
