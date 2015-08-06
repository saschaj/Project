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
    private Vertrag_Status vertragStatus;

    @ManyToOne
    private Vertrag_Art vertragArt;

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
    private Zeit_Einheit kuendigungsfristEinheit;

    @Column(name = "LAUFZEIT")
    private int laufzeit;

    @ManyToOne
    @JoinColumn(name = "LAUFZEIT_EINHEIT")
    private Zeit_Einheit laufzeitEinheit;

    @Column(name = "BENACHRICHTIGUNGSFRIST")
    private int benachrichtigungsfrist;

    @ManyToOne
    @JoinColumn(name = "BENACHRICHTIGUNGSFRIST_EINHEIT")
    private Zeit_Einheit benachrichtigungsfristEinheit;
    
    @Column(name = "VERTRAGSBEZEICHNUNG")
    private String vertragsBezeichnung;
    
    @ManyToOne
    @JoinColumn (name = "LIEFERANSCHRIFT")
    private Adresse lieferAnschrift;

    @ManyToOne
    @JoinColumn (name = "RECHNUNGSANSCHRIFT")
    private Adresse rechnungsAnschrift;
    
    @Column (name = "VERLAENGERUNG_MONATE")
    private int verlaengerungMonate;
    
    @Column (name = "VERTRAGS_PARTNER")
    private String vertragsPartner;
    
    @Column (name = "KUNDEN_NR")
    private String kundenNr;

    public String getVertragsBezeichnung() {
        return vertragsBezeichnung;
    }

    public void setVertragsBezeichnung(String vertragsBezeichnung) {
        this.vertragsBezeichnung = vertragsBezeichnung;
    }

    public String getVertragsPartner() {
        return vertragsPartner;
    }

    public void setVertragsPartner(String vertragsPartner) {
        this.vertragsPartner = vertragsPartner;
    }

    public String getKundenNr() {
        return kundenNr;
    }

    public void setKundenNr(String kundenNr) {
        this.kundenNr = kundenNr;
    }
    
    public Vertrag() {
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public Vertrag_Status getVertragStatus() {
        return vertragStatus;
    }

    public void setVertragStatus(Vertrag_Status vertragStatus) {
        this.vertragStatus = vertragStatus;
    }

    public Vertrag_Art getVertragArt() {
        return vertragArt;
    }

    public void setVertragArt(Vertrag_Art vertragArt) {
        this.vertragArt = vertragArt;
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

    public Zeit_Einheit getKuendigungsfristEinheit() {
        return kuendigungsfristEinheit;
    }

    public void setKuendigungsfristEinheit(Zeit_Einheit kuendigungsfristEinheit) {
        this.kuendigungsfristEinheit = kuendigungsfristEinheit;
    }

    public Zeit_Einheit getLaufzeitEinheit() {
        return laufzeitEinheit;
    }

    public void setLaufzeitEinheit(Zeit_Einheit laufzeitEinheit) {
        this.laufzeitEinheit = laufzeitEinheit;
    }

    public int getBenachrichtigungsfrist() {
        return benachrichtigungsfrist;
    }

    public void setBenachrichtigungsfrist(int benachrichtigungsfrist) {
        this.benachrichtigungsfrist = benachrichtigungsfrist;
    }

    public Zeit_Einheit getBenachrichtigungsfristEinheit() {
        return benachrichtigungsfristEinheit;
    }

    public void setBenachrichtigungsfristEinheit(Zeit_Einheit benachrichtigungsfristEinheit) {
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
