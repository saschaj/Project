package Entitys;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 *
 * @author René
 */
@Entity
@Table(name = "Vertrag")
@Inheritance(strategy = javax.persistence.InheritanceType.JOINED)
public class Vertrag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VERTRAG_ID")
    private int vertragId;

    @ManyToOne
    private Kunde kunde;

    @ManyToOne
    private Vertrag_Status status;

    @ManyToOne(targetEntity = Vertrag_Art.class)
    private String art;

    @Column(name = "VERTRAG_ENDE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vertragEnde;

    @Column(name = "VERTRAG_BEGINN")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vertragBeginn;

    @Column(name = "VERTRAG_NR")
    private String vertragNr;

    @Column(name = "KUENDIGUNGSFRIST")
    private int kuendigungsfrist;

    @ManyToOne
    @JoinColumn(name = "KUENDIGUNGSFRIST_EINHEIT")
    private Datum_Einheit kuendigungsfristEinheit;

    @Column(name = "LAUFZEIT")
    private int laufzeit;

    @ManyToOne
    @JoinColumn(name = "LAUFZEIT_EINHEIT")
    private Datum_Einheit laufzeitEinheit;

    @Column(name = "BENACHRICHTIGUNGSFRIST")
    private int benachrichtigungsfrist;

    @ManyToOne
    @JoinColumn(name = "BENACHRICHTIGUNGSFRIST_EINHEIT")
    private Datum_Einheit benachrichtigungsfristEinheit;

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

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
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

    public int getLaufzeit() {
        return laufzeit;
    }

    public void setLaufzeit(int laufzeit) {
        this.laufzeit = laufzeit;
    }

    public Datum_Einheit getKuendigungsfristEinheit() {
        return kuendigungsfristEinheit;
    }

    public void setKuendigungsfristEinheit(Datum_Einheit kuendigungsfristEinheit) {
        this.kuendigungsfristEinheit = kuendigungsfristEinheit;
    }

    public Datum_Einheit getLaufzeitEinheit() {
        return laufzeitEinheit;
    }

    public void setLaufzeitEinheit(Datum_Einheit laufzeitEinheit) {
        this.laufzeitEinheit = laufzeitEinheit;
    }

    public int getBenachrichtigungsfrist() {
        return benachrichtigungsfrist;
    }

    public void setBenachrichtigungsfrist(int benachrichtigungsfrist) {
        this.benachrichtigungsfrist = benachrichtigungsfrist;
    }

    public Datum_Einheit getBenachrichtigungsfristEinheit() {
        return benachrichtigungsfristEinheit;
    }

    public void setBenachrichtigungsfristEinheit(Datum_Einheit benachrichtigungsfristEinheit) {
        this.benachrichtigungsfristEinheit = benachrichtigungsfristEinheit;
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
