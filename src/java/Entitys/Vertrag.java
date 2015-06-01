package Entitys;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 *
 * @author René
 */
@Entity
@Table (name="Vertrag")
@Inheritance(strategy = javax.persistence.InheritanceType.JOINED)
public class Vertrag implements Serializable{
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name="VERTRAG_ID")
    private int vertragId;
    
    @ManyToOne
    private Kunde kunde;
    
    @ManyToOne
    private Vertrag_Status status;
    
    @ManyToOne
    private Vertrag_Art art;
    
    @Column (name="VERTRAG_ENDE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vertragEnde;
    
    @Column (name="VERTRAG_BEGINN")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vertragBeginn;
    
    @Column (name="VERTRAG_NR")
    private String vertragNr;
    private int kuendigungsfrist;
    @Column (name="KUENDIGUNGSFRIST_EINHEIT")
    private String kuendigungsfristEinheit;
    private int laufzeit;
    @Column (name="LAUFZEIT_EINHEIT")
    private String laufzeitEinheit;

    public Vertrag() {
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public Vertrag_Status getStatus() {
        return status;
    }

    public void setStatus(Vertrag_Status status) {
        this.status = status;
    }

    public Vertrag_Art getArt() {
        return art;
    }

    public void setArt(Vertrag_Art art) {
        this.art = art;
    }

    public Date getVertragEnde() {
        return vertragEnde;
    }

    public void setVertragEnde(Date vertragEnde) {
        this.vertragEnde = vertragEnde;
    }

    public Date getVertragBeginn() {
        return vertragBeginn;
    }

    public void setVertragBeginn(Date vertragBeginn) {
        this.vertragBeginn = vertragBeginn;
    }

    public String getVertragNr() {
        return vertragNr;
    }

    public void setVertragNr(String vertragNr) {
        this.vertragNr = vertragNr;
    }

    public int getKuendigungsfrist() {
        return kuendigungsfrist;
    }

    public void setKuendigungsfrist(int kuendigungsfrist) {
        this.kuendigungsfrist = kuendigungsfrist;
    }

    public String getKuendigungsfristEinheit() {
        return kuendigungsfristEinheit;
    }

    public void setKuendigungsfristEinheit(String kuendigungsfristEinheit) {
        this.kuendigungsfristEinheit = kuendigungsfristEinheit;
    }

    public int getLaufzeit() {
        return laufzeit;
    }

    public void setLaufzeit(int laufzeit) {
        this.laufzeit = laufzeit;
    }

    public String getLaufzeitEinheit() {
        return laufzeitEinheit;
    }

    public void setLaufzeitEinheit(String laufzeitEinheit) {
        this.laufzeitEinheit = laufzeitEinheit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.vertragId;
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
        final Vertrag other = (Vertrag) obj;
        if (this.vertragId != other.vertragId) {
            return false;
        }
        return true;
    }
    
}
