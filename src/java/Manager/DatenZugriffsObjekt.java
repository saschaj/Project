package Manager;

import javax.persistence.*;
import Entitys.*;
import java.util.Date;

/**
 *
 * @author René
 */
public class DatenZugriffsObjekt {
    
    private final EntityManager entityManager;
    
    /**
     * Konstruktor
     */
    public DatenZugriffsObjekt() {
        this.entityManager = Persistence
                .createEntityManagerFactory("VertragsverwaltungPU")
                .createEntityManager();
    }
    
    /**
     * Legt Testweise einen neuen Kunden und einen Vertrag zum Kunden an.
     */
    public void beispiel() {
        Kunde neuerKunde = new Kunde();
        Stromvertrag stromVertrag = new Stromvertrag();
        
        neuerKunde.setVorname("Saul Slash");
        neuerKunde.setNachname("Hudson");
        neuerKunde.setGeburtsdatum(new Date(1965, 7, 23));
        neuerKunde.setStrasse("Highway To Hell");
        neuerKunde.setHausnummer("666");
        neuerKunde.setOrt("Paradise City"); 
        neuerKunde.setEmail("gunsNroses@web.de");
        
        stromVertrag.setLaufzeit(30);
        stromVertrag.setStromzaehlerNr("1337#YOLO");
        stromVertrag.setKunde(neuerKunde);
        
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(neuerKunde);
            this.entityManager.persist(stromVertrag);
            this.entityManager.getTransaction().commit();
        }catch (RollbackException re) { 
            
        }catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();
        }catch (Throwable th) {
            this.entityManager.getTransaction().rollback();
        }
            
    }
    
    /**
     * Methode zum schließen des EntityManagers.
     */
    public void close() {
        this.entityManager.close();
    }
}
