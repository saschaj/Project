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
    private Date geburtsdatum;
    
    private String vorname;
    private String nachname; 
    private String strasse;
    private String hausnummer;
    private String plz;
    private String ort;
    private String telefonnummer;

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

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }
    
    public String getPLZ() {
        return this.plz;
    }
    
    public void setPLZ(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
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
