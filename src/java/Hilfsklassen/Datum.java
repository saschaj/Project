package Hilfsklassen;

/**
 * Ersteller:       Julie Kenfack
 * Erstelldatum:    20.08.2015
 * Version:         1.0
 * Diese Klasse prüft ob eines eingegebene Datum in der Zukunft liegt.
 */


public class Datum implements Comparable {
    private int jahr;    // Jahr vierstellig
    private int  monat;  // Monat von 1 bis 12
    private int  tag;    // Tag von 1 bis 31
    
    public Datum() {
     // eigentlich soll hier das aktuelle Datum genommen werden
        tag = 02;
        monat = 9;
        jahr = 2015;
    }
    
    public Datum(int tag, int monat, int jahr) {
        setzeDatum(tag, monat, jahr);
    }
    
    public boolean equals(Object einObject) {
        Datum einDatum = (Datum)einObject;
        return this.jahr == einDatum.jahr
                & this.monat == einDatum.monat
                & this.tag == einDatum.tag;
    }
    
    public void setzeDatum(int tag, int monat, int jahr) {
        checkDatum(tag,monat,jahr);
        this.tag = tag;
        this.monat = monat;
        this.jahr = jahr;
    }
    
    private void checkDatum(int t, int m, int j) {
        if (j<1000)
            throw new IllegalArgumentException
                    ("Jahreszahlen unter 1000 sind nicht zugelassen.");
        if (m>12)
            throw new IllegalArgumentException
                    ("Monat darf nicht grˆﬂer als 12 sein.");
        if (t>31)
            throw new IllegalArgumentException
                    ("Tag darf nicht grˆﬂer als 31 sein.");
        if (t<29)
            return;
        
        if (t==29) {
            if (m!=2)
                return;
            if (j%4==0 &(j%100!=0 | j%1000==0))
                return;
            else
                throw new IllegalArgumentException
                        ("29.02." + j + " ist kein reales Datum.");
        }
        if (t==30) {
            if (m!=2)
                return;
            else
                throw new IllegalArgumentException
                        ("30.02. ist kein reales Datum.");
        }
        if (m==2 | m==4 | m==6 | m==9 | m==11)
            throw new IllegalArgumentException
                    ("31." + m + ". ist kein reales Datum.");
    }
    
    public boolean fueherAls(Datum vergleichDatum) {
        if (jahr < vergleichDatum.jahr)
            return true;
        if (jahr == vergleichDatum.jahr) {
            if (monat < vergleichDatum.monat)
                return true;
            if (monat == vergleichDatum.monat)
                if (tag < vergleichDatum.tag)
                    return true;
        }
        return false;
    }
    
    public String toString() {
        return (tag + "." + monat + "." + jahr);
    }
    
    String gibDatum() {
        return toString();
    }
    
    public int gibTag() {
        return tag;
    }
    
    public int gibMonat() {
        return monat;
    }
    
    public int gibJahr() {
        return jahr;
    }
    
    public int compareTo(Object einObject) {
        Datum einDatum = (Datum)einObject;
        if (fueherAls(einDatum))
            return -1;
        else if (equals(einDatum))
            return 0;
        else
            return 1;
    }
}
