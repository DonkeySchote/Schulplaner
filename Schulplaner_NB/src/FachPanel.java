
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
public class FachPanel extends JPanel{
    
    javax.swing.GroupLayout layout;
    JLabel lblFach;
    JLabel lblLehrer;
    JLabel lblNoteKA;
    JLabel lblNoteZeugnis;
    JLabel lblUnterrichtszeiten;
    JTextField txfNoteZeugnis;
    JTextField txfNoteKA;
    
    
    public FachPanel(){
        layout = new javax.swing.GroupLayout(this);
        super.setLayout(layout);
        super.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        
        lblFach = new JLabel("Fach");//<-ersetzt
        lblLehrer = new JLabel("Lehrer");//<-ersetzt
        lblNoteKA = new JLabel("Klausuren Noten: ");
        lblNoteZeugnis = new JLabel("Zeugnis Note: ");
        lblUnterrichtszeiten = new JLabel("Unterrichtszeiten: ");
        txfNoteZeugnis = new JTextField();
        txfNoteKA = new JTextField();
        
        
        /*GroupLayout.ParallelGroup hortParaGroup = fachPanelFach.createParallelGroup();
        hortParaGroup.addComponent(lblFach);
        fachPanelFach.setHorizontalGroup(hortParaGroup);
        
        GroupLayout.ParallelGroup vertParaGroup = fachPanelFach.createParallelGroup();
        vertParaGroup.addComponent(lblFach);
        fachPanelFach.setVerticalGroup(vertParaGroup);*/
        /*
        fachPanelFach.setHorizontalGroup(
            fachPanelFach.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fachPanelFach.createSequentialGroup()
                .addContainerGap()
                .addGroup(fachPanelFach.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFach)
                    .addComponent(lblLehrer))
                .addContainerGap(326, Short.MAX_VALUE))
        );
        fachPanelFach.setVerticalGroup(
            fachPanelFach.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fachPanelFach.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFach)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLehrer)
                .addContainerGap(246, Short.MAX_VALUE))
        );
        */
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFach)
                    .addComponent(lblLehrer)
                    .addComponent(lblUnterrichtszeiten)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNoteKA)
                            .addComponent(lblNoteZeugnis))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfNoteZeugnis, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                            .addComponent(txfNoteKA))))
                .addContainerGap(217, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFach)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLehrer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUnterrichtszeiten)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoteZeugnis)
                    .addComponent(txfNoteZeugnis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoteKA)
                    .addComponent(txfNoteKA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(146, Short.MAX_VALUE))
        );
    }
}
