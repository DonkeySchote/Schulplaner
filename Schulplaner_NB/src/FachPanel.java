
import java.util.ArrayList;
import javax.swing.JPanel;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author donkeyschote
 */
import javax.swing.JLabel;
import javax.swing.JTextField;
public class FachPanel extends JPanel implements Comparable<FachPanel>{
    
    javax.swing.GroupLayout layout;
    JLabel lblFach;
    JLabel lblLehrer;
    JLabel lblNoteKA;
    JLabel lblNoteZeugnis;
    JLabel lblUnterrichtszeiten;
    JLabel lblRaum;
    /*JTextField txfNoteZeugnis;
    JTextField txfNoteKA;*/
    
    
    public void setUnterrichtszeiten(String stunden){
        lblUnterrichtszeiten.setText("Unterrichtszeiten: " + stunden);
    }
    
    public void setRaum(String raum){
        lblRaum.setText(raum);
    }
    
    public void setFach(String fach){
        lblFach.setText(fach);
    }
    
    public String getFach(){
        return lblFach.getText();
    }
    
    public void setLehrer(String lehrer){
        lblLehrer.setText(lehrer);
        if(lblLehrer.getText().isEmpty()){
            lblLehrer.setText(lehrer);
        }
    }
    
    public void setKlammerbar(boolean klammer){
        if(klammer)
            lblNoteKA.setText("Klammerbar");
        else
            lblNoteKA.setText("Nicht klammerbar");
    }
    
    public void setNoteKA(String noteka){
        lblNoteKA.setText("");
    }
    
    public void setNoteZeugnis(String note){
        lblNoteZeugnis.setText("Zeugnisnote: " + note);
    }
    
    @Override
    public int compareTo(FachPanel panel) {
        return this.lblFach.getText().compareTo(panel.getFach());
    }
    
    public FachPanel(){
        layout = new javax.swing.GroupLayout(this);
        super.setLayout(layout);
        super.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        
        lblFach = new JLabel("Fach");//<-ersetzt
        lblLehrer = new JLabel("Lehrer");//<-ersetzt
        lblNoteKA = new JLabel("Klausurennote: ");
        lblNoteZeugnis = new JLabel("Zeugnisnote: ");
        lblUnterrichtszeiten = new JLabel("Unterrichtszeiten: ");
        lblRaum = new JLabel("Raum");
        /*txfNoteZeugnis = new JTextField();
        txfNoteKA = new JTextField();*/
        
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFach)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblRaum))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLehrer)
                            .addComponent(lblUnterrichtszeiten)
                            .addComponent(lblNoteZeugnis)
                            .addComponent(lblNoteKA))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFach)
                    .addComponent(lblRaum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLehrer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUnterrichtszeiten)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNoteZeugnis)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNoteKA)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
}
