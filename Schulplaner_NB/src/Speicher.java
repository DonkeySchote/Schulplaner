
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author donkeyschote
 */
public class Speicher {
    private static String dateiname = "data";
    private static String ordnername = ".schulplaner";
    
    private Speicher(){}
    
    public static Planer ladePlaner() throws FileNotFoundException{
        InputStream fis = null;
        Planer planer = null;
        String path = getSystemApplicationDirectory() + File.separator + ordnername + File.separator + dateiname;
        if(new File(path).exists())
            throw new FileNotFoundException("Konnte keine Daten unter: " + path + " finden");
        try {
            fis = new FileInputStream("/home/donkeyschote/fos.ser");
            ObjectInputStream o = new ObjectInputStream(fis);
            planer = (Planer) o.readObject();
            System.out.println("loaded");
        }
        catch (IOException e){
            System.err.println("e");
        }
        catch (ClassNotFoundException e) {
            System.err.println("e");
        }
        finally{
            try {
                fis.close();
            }
            catch (Exception e){
                
            }
        }
        return planer;
   }
    
    public static void speicherePlaner(Planer planer) {
        OutputStream fos = null;
        
        try{
            fos = new FileOutputStream("/home/donkeyschote/fos.ser");
            ObjectOutputStream o = new ObjectOutputStream(fos);
            o.writeObject(planer);
            System.out.println("saved");
        }
        catch (IOException e) {
            System.err.println(e);
        }
        finally {
            try {
                fos.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    
    private static String getSystemApplicationDirectory(){
        String directory;
        String os = (System.getProperty("os.name")).toLowerCase();
        if (os.contains("win")){
            directory = System.getenv("AppData");
        }
        else {
            directory = System.getProperty("user.home");
        }
        //System.out.println(directory);
        return directory;
    }
}
