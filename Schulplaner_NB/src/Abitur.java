
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
public class Abitur implements Serializable {

    private Pruefung[] pruefungen;

    public Abitur() {
        pruefungen = new Pruefung[5];

        for (int i = 0; i < 5; i++) {
            pruefungen[i] = new Pruefung();
        }
    }

    public Pruefung getPruefung() {
        Pruefung tempPruef = new Pruefung();
        return tempPruef;
    }

    public void setPruefung(Pruefung pruefung, int zahl) {
        pruefungen[zahl] = pruefung;
    }

    public int rechnePuknt() {
        int gPunkt = 0;

        for (int i = 0; i < 5; i++) {
            gPunkt += pruefungen[i].getWertung() * pruefungen[i].getNote();
        }
        return gPunkt;
    }

}
