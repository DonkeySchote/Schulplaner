
import java.io.Serializable;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author william.kilburg
 */
public class Pruefung extends Klausur implements Serializable {

    private int wertung;
    private String fach;

    public Pruefung(int wertung, String fach, Date termin) {
        super(termin);
        this.wertung = wertung;
        this.fach = fach;
    }

    public Pruefung() {
        super(null);
    }

    public int getWertung() {
        return wertung;
    }

    public String getFach() {
        return fach;
    }

    public void setWertung(int wertung) {
        this.wertung = wertung;
    }

    public void setFach(String fach) {
        this.fach = fach;
    }

}
