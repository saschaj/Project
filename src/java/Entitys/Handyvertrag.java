package Entitys;

import java.io.Serializable;
import javax.persistence.*;


/**
 *
 * @author René
 */
@Entity
public class Handyvertrag extends Vertrag implements Serializable {
    
    private String tarifname;
    private String netztyp;
    private String rufnummer;

    public Handyvertrag() {
    }

    public String getTarifname() {
        return tarifname;
    }

    public void setTarifname(String tarifname) {
        this.tarifname = tarifname;
    }

    public String getNetztyp() {
        return netztyp;
    }

    public void setNetztyp(String netztyp) {
        this.netztyp = netztyp;
    }

    public String getRufnummer() {
        return rufnummer;
    }

    public void setRufnummer(String rufnummer) {
        this.rufnummer = rufnummer;
    }
}
