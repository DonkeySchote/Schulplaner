
import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nicole.wagner
 */
public class Halbjahr implements Serializable {

    private ArrayList<Fach> faecher;

    public Halbjahr() {
        faecher = new ArrayList<Fach>();
    }

    public Fach getFach(int fachid) {
        return faecher.get(fachid);
    }

    public Fach getFach() {
        Fach tempFach = new Fach();
        return tempFach;
    }

    public Fach getFachByName(String name) {
        int j = -1;
        for (int i = 0; i < faecher.size(); i++) {
            if (faecher.get(i).getName() == name) {
                j = i;
            }
        }
        if (j == -1) {
            throw new ArrayIndexOutOfBoundsException("Dieses Fach exisitert nicht");
        }
        return faecher.get(j);
    }

    public void addFach(Fach fach) {
        faecher.add(fach);
    }

    public void deleteFach(int fachid) {
        faecher.remove(fachid);
    }

    public int rechneHalbjahrPunkte() {
        int summe = 0;

        for (int i = 0; i < faecher.size(); i++) {
            summe = +faecher.get(i).getZeugnisnote();
        }
        return summe;
    }
}
