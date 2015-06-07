package Hilfsklassen;

/**
 * Ersteller:       René Kanzenbach
 * Erstelldatum:    02.06.2015
 * Version:         1.0
 * Änderungen:      1.1 (René Kanzenbach) 07.06.2015
 *                  -SESSION_ATTR_KUNDE entfernt. (Unnötig, da Benutzer zu Kunde 
 *                  gecastet werden kann.)
 * 
 * Diese Klasse stellt eine Sammlung verschiedener Konstanten dar. Unter
 * anderem die Namen der Attribute der HttpSession Objekte.
 */
public class Konstanten {
    
    /**
     * Attributname für eine Benutzer_Entity in einem HttpSession Objekt.
     */
    public static final String SESSION_ATTR_BENUTZER = "BENUTZER";
    
    /**
     * Attributname für einen Fehlertext in einem HttpRequest Objekt. 
     */
    public static final String REQUEST_ATTR_FEHLER = "fehler";
}
