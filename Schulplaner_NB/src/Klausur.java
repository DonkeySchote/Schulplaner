
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
public class Klausur implements Serializable, Comparable<Klausur> {

    protected Date termin;
    protected String notiz;
    protected int note;
    protected String name;
  
    public Klausur(Date termin) {
        this.termin = termin;
        notiz = "";
        note = -1;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public Date getTermin() {
        return termin;
    }

    public void setTermin(Date termin) {
        this.termin = termin;
    }

    public void setNotiz(String notiz) {
        this.notiz = notiz;
    }

    public String getNotiz() {
        return notiz;
    }

    public void setNote(int note) {
        if (note > 15 || note < 0) {
            throw new NumberFormatException("Notenbereich nur zwischen 0 bis 15");
        }
        this.note = note;
    }

    public int getNote() {
        return note;
    }

    public String toString() {
        return termin.toString() + notiz;
    }
    @Override
    public int compareTo(Klausur klausur) {
        return this.termin.compareTo(klausur.getTermin());
    }
}
