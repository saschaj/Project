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
    @JoinColumn(name = "LIEFERANSCHRIFT")
    private Adresse lieferAnschrift;

    @ManyToOne
    @JoinColumn(name = "RECHNUNGSANSCHRIFT")
    private Adresse rechnungsAnschrift;

    @Column(name = "VERTRAGS_PARTNER")
    private String vertragsPartner;

    @Column(name = "KUNDEN_NR")
    private String kundenNr;

    @Column(name = "BENACHRICHTIGUNG_VERSAND", nullable = false, columnDefinition = "boolean default false")
    private boolean benachrichtigungVersand;

    public int getVertragId() {
        return vertragId;
    }

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
        if (vertragEnde.compareTo(vertragEnde) != 0) {
            this.vertragEnde = vertragEnde;
            this.benachrichtigungVersand = false;
        }

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
        if (this.kuendigungsfrist != kuendigungsfrist) {
            this.kuendigungsfrist = kuendigungsfrist;
            this.benachrichtigungVersand = false;
        }
    }

    public int getLaufzeit() {
        return laufzeit;
    }

    public void setLaufzeit(int laufzeit) {
        if (this.laufzeit != laufzeit) {
            this.laufzeit = laufzeit;
            this.benachrichtigungVersand = false;
        }
    }

    public Zeit_Einheit getKuendigungsfristEinheit() {
        return kuendigungsfristEinheit;
    }

    public void setKuendigungsfristEinheit(Zeit_Einheit kuendigungsfristEinheit) {
        if (this.kuendigungsfristEinheit != kuendigungsfristEinheit) {
            this.kuendigungsfristEinheit = kuendigungsfristEinheit;
            this.benachrichtigungVersand = false;
        }
    }

    public Zeit_Einheit getLaufzeitEinheit() {
        return laufzeitEinheit;
    }

    public void setLaufzeitEinheit(Zeit_Einheit laufzeitEinheit) {
        if (this.laufzeitEinheit != laufzeitEinheit) {
            this.laufzeitEinheit = laufzeitEinheit;
            this.benachrichtigungVersand = false;
        }
    }

    public int getBenachrichtigungsfrist() {
        return benachrichtigungsfrist;
    }

    public void setBenachrichtigungsfrist(int benachrichtigungsfrist) {
        if (this.benachrichtigungsfrist != benachrichtigungsfrist) {
            this.benachrichtigungsfrist = benachrichtigungsfrist;
            this.benachrichtigungVersand = false;
        }
    }

    public Zeit_Einheit getBenachrichtigungsfristEinheit() {
        return benachrichtigungsfristEinheit;
    }

    public void setBenachrichtigungsfristEinheit(Zeit_Einheit benachrichtigungsfristEinheit) {
        if (this.benachrichtigungVersand != false) {
            this.benachrichtigungsfristEinheit = benachrichtigungsfristEinheit;
            this.benachrichtigungVersand = false;
        }
    }

    public Adresse getLieferAnschrift() {
        return lieferAnschrift;
    }

    public void setLieferAnschrift(Adresse lieferAnschrift) {
        this.lieferAnschrift = lieferAnschrift;
    }

    public Adresse getRechnungsAnschrift() {
        return rechnungsAnschrift;
    }

    public void setRechnungsAnschrift(Adresse rechnungsAnschrift) {
        this.rechnungsAnschrift = rechnungsAnschrift;
    }

    public boolean isBenachrichtigungVersand() {
        return benachrichtigungVersand;
    }

    public void setBenachrichtigungVersand(boolean benachrichtigungVersand) {
        this.benachrichtigungVersand = benachrichtigungVersand;
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
