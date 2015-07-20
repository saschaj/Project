package Entitys;

import java.util.Collection;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
@Table ( name = "KUNDE" )
public class Kunde extends Benutzer{
    
    @OneToMany (mappedBy="kunde")
    private Collection<Vertrag> vertraege;
    
    @Temporal(TemporalType.DATE)
    @Column (name = "GEBURTSDATUM")
    private Date geburtsdatum;
    
    @Column (name = "VORNAME")
    private String vorname;
    
    @Column (name = "NACHNAME")
    private String nachname;
    
    @Column (name = "TELEFONNUMMER")
    private String telefonnummer;
    
    @ManyToOne
    @JoinColumn (name = "ADRESSE")
    private Adresse adresse;

    public Kunde() {
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public Collection<Vertrag> getVertraege() {
        return vertraege;
    } 
}
