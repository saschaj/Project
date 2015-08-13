package Entitys;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashSet;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int benutzerId;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "BENUTZER_STATUS_ID")
    private Benutzer_Status status;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "BENUTZER_RECHT_ZUORDNUNG")
    private Collection<Benutzer_Recht> rechte;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORT")
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

    /**
     * Ersteller:	René Kanzenbach
     * Erstelldatum:    11.06.2015
     * Methode:         addRecht
     * Version:         1.0
     * Änderungen:      -
     * 
     * Fügt dem Benutzer das übergebene Recht hinzu. 
     * 
     * Prüft ob schon eine Collection mit Rechten existiert, falls nicht wird 
     * eine neues HashSet angelegt und das Recht diesem hinzugefügt.
     * 
     * @param recht Wird den Rechten des Benutzers hinzugefügt.
     */
    public void addRecht(Benutzer_Recht recht) {

        if (this.rechte != null) {
            this.rechte.add(recht);
        } else {
            this.rechte = new HashSet();
            this.rechte.add(recht);
        }
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

    /**
     * Ersteller:	René Kanzenbach Erstelldatum: 02.06.2015 Methode:
     * pruefePasswort Version: 1.0 Änderungen: -
     *
     * Gleicht das übergebene Klartext Passwort mit dem Passwort des Benutzers
     * ab und gibt TRUE zurück falls diese identisch sind.
     *
     * Diese Methode nutzt die Benutzer.createHash() Methode.
     *
     * @param passwort Passwort in Klartext.
     * @return TRUE falls die Passwörter identisch sind und FALSE falls nicht.
     */
    public boolean pruefePasswort(String passwort) {
        return this.passwort.equals(Benutzer.createHash(passwort));
    }

    public void setPasswort(String passwort) {
        this.passwort = createHash(passwort);
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

    public static String createHash(String text) {
        MessageDigest md5;
        StringBuffer sb = new StringBuffer();
        try {

            md5 = MessageDigest.getInstance("MD5");
            byte[] hash = md5.digest(text.getBytes());

            for (int i = 0; i < hash.length; i++) {
                sb.append(Integer.toHexString(
                        (hash[i] & 0xFF) | 0x100
                ).toLowerCase().substring(1, 3)
                );
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return sb.toString();
        }
    }

}
