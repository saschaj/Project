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
    private String empfangstyp;

    public Festnetzvertrag() {
    }

    public String getTarifname() {
        return tarifname;
    }

    public void setTarifname(String tarifname) {
        this.tarifname = tarifname;
    }

    public String getEmpfangstyp() {
        return empfangstyp;
    }

    public void setEmpfangstyp(String empfangstyp) {
        this.empfangstyp = empfangstyp;
    }
    
}
