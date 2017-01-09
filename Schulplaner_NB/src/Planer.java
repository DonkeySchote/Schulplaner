
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
public class Planer implements Serializable {

    private Abitur abi;
    private Halbjahr[] halbjahren;

    public Planer() {
        abi = new Abitur();

        halbjahren = new Halbjahr[4];

    }

    public void setHalbjahr(Halbjahr halbjahr, int nummer) {
        halbjahren[nummer] = halbjahr;
    }


    public Halbjahr getHalbjahr(int halbjahr) {
        return halbjahren[halbjahr];
    }

    public Fach findFach(String fachname, int halbjahr) {
        return halbjahren[halbjahr].getFachByName(fachname);
    }

    public double getNotenStandFach(int fachid, int halbjahr) {
        double summe = 0;

        for (int i = 0; i < halbjahren[halbjahr].getFach(fachid).getAnzahlKlausur(); i++) {
            summe =+ halbjahren[halbjahr].getFach(fachid).getKlausur(i).getNote();
        }
        summe = summe / halbjahren[halbjahr].getFach(fachid).getAnzahlKlausur();
        return summe;
    }
    
    public int rechneGesamtPunkte(){
        int summe = 0;
        
        for (int i = 0; i < halbjahren.length; i++) {
            summe =+ halbjahren[i].rechneHalbjahrPunkte();
        }
        
        summe =+ abi.rechnePuknt();
        
        return summe;
    }

}
