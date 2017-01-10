
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

    public int getAnzahlFach() {
        return faecher.size();
    }
    
    public int getFachid(String name){
        int temp = -1;
        for (int i = 0; i < faecher.size(); i++) {
            if (faecher.get(i) != null) {
                if (faecher.get(i).getName().equals(name)) {
                    temp = i;
                }
            }
        }
        return temp;
    }

    public boolean existFach(String name) {
        boolean temp = false;
        for (int i = 0; i < faecher.size(); i++) {
            if (faecher.get(i) != null) {
                if (faecher.get(i).getName().equals(name)) {
                    temp = true;
                }
            }
        }
        return temp;
    }

    public Fach getFachByName(String name) {
        Fach temp = new Fach();
        for (int i = 0; i < faecher.size(); i++) {
            if (faecher.get(i) != null) {
                if (faecher.get(i).getName().equals(name)) {
                    temp = faecher.get(i);
                }
            }
        }
        return temp;
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
            if(faecher.get(i).getZeugnisnote() != -1)
            {
                summe += faecher.get(i).getZeugnisnote();
            }
        }
        return summe;
    }
}
