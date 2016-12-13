
import java.io.Serializable;

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

    public Pruefung(String termin, int wertung, String fach) {
        this.wertung = wertung;
        this.fach = fach;
        this.termin = termin;
    }

}
