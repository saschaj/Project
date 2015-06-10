package Entitys;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
public class Festnetzvertrag extends Vertrag implements Serializable{
    
    private String tarifname;
    
    @ManyToOne
    @JoinColumn (name="EMPFANGSTYP")
    private Netztyp empfangstyp;

    public Festnetzvertrag() {
    }

    public String getTarifname() {
        return tarifname;
    }

    public void setTarifname(String tarifname) {
        this.tarifname = tarifname;
    }

    public Netztyp getEmpfangstyp() {
        return empfangstyp;
    }

    public void setEmpfangstyp(Netztyp empfangstyp) {
        this.empfangstyp = empfangstyp;
    }
    
}
