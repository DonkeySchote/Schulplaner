
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

public class NotenPanel extends JPanel{
    
    javax.swing.GroupLayout layout;
    JLabel lblFach;
    JLabel lblNoteKA;
    JLabel lblNoteZeugnis;
    
    public void setNoteZeugnis(String note){
        lblNoteZeugnis.setText("Zeugnisnote: " + note);
    }
    
    public void setNoteKA(String note){
        lblNoteKA.setText("Klausurennote: " + note);
    }
    
    public void setFach(String fach){
        lblFach.setText(fach);
    }
    
    public NotenPanel(){
        layout = new javax.swing.GroupLayout(this);
        super.setLayout(layout);
        super.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        
        lblFach = new JLabel("Fach");//<-ersetzt
        lblNoteKA = new JLabel("Klausuren Noten: ");
        lblNoteZeugnis = new JLabel("Zeugnis Note: ");
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFach)
                    .addComponent(lblNoteKA)
                    .addComponent(lblNoteZeugnis))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFach)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNoteKA)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNoteZeugnis)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
}
