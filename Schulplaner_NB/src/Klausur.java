
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
public class Klausur implements Serializable {

    protected String termin;
    protected String notiz;
    protected int note;

    public Klausur(String termin) {
        this.termin = termin;
        notiz = "";
    }

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
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
}