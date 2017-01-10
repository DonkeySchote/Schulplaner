
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

public class NotenPanel extends JPanel implements Comparable<NotenPanel>{
    
    javax.swing.GroupLayout layout;
    JLabel lblFach;
    String fach;
    JLabel lblNoteKA;
    JLabel lblNoteZeugnis;
    
    public String getFach(){
        return fach;
    }
    public void setNoteZeugnis(int note){
        lblNoteZeugnis.setText("Zeugnisnote: " + note);
        if(note==-1)
            lblNoteKA.setText("Klausurennote: N/A");
    }
    
    public void setNoteKA(String note){
        lblNoteKA.setText("Klausurennote: " + note);
        if(note.equals(""))
            lblNoteKA.setText("Klausurennote: N/A");
    }
    
    public void setFach(String fach){
        this.fach = fach;
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
    
    @Override
    public int compareTo(NotenPanel panel) {
        return this.fach.compareTo(panel.getFach());
    }
}
