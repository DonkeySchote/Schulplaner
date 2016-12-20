
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

    public void setHalbjahr(Halbjahr halbjahr,int nummer) {
        halbjahren[nummer] = halbjahr;
    }
    
    

}
