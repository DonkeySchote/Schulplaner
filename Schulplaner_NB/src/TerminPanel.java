
import java.util.Date;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TerminPanel extends JPanel implements Comparable<TerminPanel>{
    
    javax.swing.GroupLayout layout;
    JLabel lblTermin;
    JLabel lblDatum;
    Date datum;
    JLabel lblFach;
    //JLabel lblRaum;
    JLabel lblNote;
    JScrollPane scrolldingens;
    JTextArea txaNotiz;
    
    public JTextArea getTxaNotiz(){
        return txaNotiz;
    }
    
    public void setNote(int note){
        if(!(note==-1))
            this.lblNote.setText("Note: " + String.valueOf(note));
    }
    
    /*public void setRaum(String raum){
        lblRaum.setText(raum);
    }*/
    
    public void setFach(String fach){
        lblFach.setText(fach);
    }
    
    public void setDatum(Date datum){
        this.datum = datum;
        lblDatum.setText(datum.toString());
    }
    
    public Date getDatum(){
        return datum;
    }
    
    @Override
    public int compareTo(TerminPanel panel) {
        return this.datum.compareTo(panel.getDatum());
    }
    
    public TerminPanel(){
        layout = new javax.swing.GroupLayout(this);
        super.setLayout(layout);
        super.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        
        lblTermin = new JLabel("Datum");
        lblDatum = new JLabel("Datum");
        lblFach = new JLabel("Fach");
        //lblRaum = new JLabel("Raum");
        lblNote = new JLabel("Note: ");
        txaNotiz = new JTextArea();
        scrolldingens = new JScrollPane();
        
        txaNotiz.setColumns(20);
        txaNotiz.setRows(5);
        scrolldingens.setViewportView(txaNotiz);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTermin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDatum))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFach)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        /*.addComponent(lblRaum)*/)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNote)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(scrolldingens, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTermin)
                    .addComponent(lblDatum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFach)
                    /*.addComponent(lblRaum)*/)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNote)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrolldingens)
                .addContainerGap())
        );
        
    }
}
