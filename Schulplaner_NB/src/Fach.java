
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nicole
 */
public class Fach {
    private int zeugnisnote;
    private String lehrer;
    private ArrayList <Integer> stunden;
    private boolean klammerbar;
    private String name;
    private ArrayList <Klausur> klausur;
    
    public Fach()
    {
        stunden = new ArrayList <Integer> ();
        klausur = new ArrayList <Klausur> ();
    }
    
    public Fach(String name)
    {
        stunden = new ArrayList <Integer> ();
        klausur = new ArrayList <Klausur> ();
    }
    
    public void setZeugnisnote(int zeugnisnote)
    {
        this.zeugnisnote = zeugnisnote;
    }
    
    public int getZeugnisnote()
    {
        return zeugnisnote;
    }
    
    public void setStunde(int stunde)
    {
        stunden.add(stunde);
    }
    
    public ArrayList<Integer> getStunden()
    {
        if (stunden.isEmpty())
        {
            throw new ArrayIndexOutOfBoundsException("Dieses Fach hat noch keine Stunden");
        }
        else
        {
            return stunden;
        }
    }
    
    public void deleteStunde(int stunde)
    {
        stunden.remove(stunde);
    }
    
    public void setLehrer(String lehrer)
    {
        this.lehrer = lehrer;
    }
    
    public String getLehrer()
    {
        return lehrer;
    }
    
    public Klausur getKlausur(int nr)
    {
        return klausur.get(nr);
    }
    
    public void addKlausur(Klausur klausur)
    {
        this.klausur.add(klausur);
    }
    
    public void deleteKlausur(int nr)
    {
        klausur.remove(nr);
    }
    
    public void setKlammerbar(boolean klammerbar)
    {
        this.klammerbar = klammerbar;
    }
    
    public boolean getKlammerbar()
    {
        return klammerbar;
    }
    
    public int getAnzahlKlausur()
    {
        return klausur.size();
    }
}
