package Entitys;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author René
 */
@Entity
public class Vertrag_Art implements Serializable{
    
    @Id
    @GeneratedValue
    @Column (name="VERTRAG_ART_ID")
    private int vertragArtId;
    
    private String name;

    public Vertrag_Art() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVertragArtId() {
        return vertragArtId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.vertragArtId;
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
        final Vertrag_Art other = (Vertrag_Art) obj;
        if (this.vertragArtId != other.vertragArtId) {
            return false;
        }
        return true;
    }
    
}
