
import java.io.Serializable;
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
public class Fach implements Serializable{
    private int zeugnisnote;
    private String lehrer;
    private ArrayList <Integer> stunden;
    private boolean klammerbar;
    private String name;
    private ArrayList <Klausur> klausur;
    private String raum;
    
    public Fach()
    {
        stunden = new ArrayList <Integer> ();
        klausur = new ArrayList <Klausur> ();

        this.name ="";

        zeugnisnote = -1;

    }
    
    public Fach(String name)
    {
        stunden = new ArrayList <Integer> ();
        klausur = new ArrayList <Klausur> ();
        this.name = name;
        zeugnisnote = -1;
    }
    
    public void setZeugnisnote(int zeugnisnote)
    {
        this.zeugnisnote = zeugnisnote;
    }
    
    public void setRaum(String raum){
        this.raum = raum;
    }
    
    public String getRaum(){
        return raum;
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
        
            return stunden;
        
    }
    
    public String getUnterrichtszeiten(){
        String montag = "";
        String dienstag = "";
        String mittwoch = "";
        String donnerstag = "";
        String freitag = "";
        String woche ="";
        for (Integer stunde : stunden) {
            if((int)stunde/12==0){
                if(!montag.isEmpty())
                    montag += ", ";
                montag += String.valueOf(stunde%12+1);
            }
            else if((int)stunde/12==1){
                if(!dienstag.isEmpty())
                    dienstag += ", ";
                dienstag += String.valueOf(stunde%12+1);
            }
            else if((int)stunde/12==0){
                if(!mittwoch.isEmpty())
                    mittwoch += ", ";
                mittwoch += String.valueOf(stunde%12+1);
            }
            else if((int)stunde/12==0){
                if(!donnerstag.isEmpty())
                    donnerstag += ", ";
                donnerstag += String.valueOf(stunde%12+1);
            }
            else if((int)stunde/12==0){
                if(!freitag.isEmpty())
                    freitag += ", ";
                freitag += String.valueOf(stunde%12+1);
            }
        }
        if(!montag.isEmpty()){
            woche += "Mo: " + montag;
        }
        if(!dienstag.isEmpty()){
            if(!woche.isEmpty())
                woche += ";";
            woche += "Di: " + dienstag;
        }
        if(!mittwoch.isEmpty()){
            if(!woche.isEmpty())
                woche += ";";
            woche += "Mi: " + mittwoch;
        }
        if(!donnerstag.isEmpty()){
            if(!woche.isEmpty())
                woche += ";";
            woche += "Do: " + donnerstag;
        }
        if(!freitag.isEmpty()){
            if(!woche.isEmpty())
                woche += ";";
            woche += "Fr: " + freitag;
        }
        return woche;
    }
    
    public String getKlausurnoten(){
        String noteKA = "NA";
        if(klausur.size()>0){
            noteKA = String.valueOf(klausur.get(0).getNote());
            for (int i = 0; i < klausur.size(); i++) {
                noteKA = noteKA + String.valueOf(klausur.get(i).getNote());
            }
        }
        return noteKA;
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
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
