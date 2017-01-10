
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author donkeyschote
 */
public class GUI extends javax.swing.JFrame {

    Planer planer;
    Speicher speicher;
    ArrayList<Klausur> alleKlausuren;
    ArrayList<Klausur> anstKlausuren;
    
    /**
     * Creates new form Main
     */
    public GUI() {
        initComponents();
        try {
            planer = Speicher.ladePlaner();
        } catch (FileNotFoundException e) {
            planer = new Planer();

            for (int i = 0; i < 4; i++) {

                Halbjahr temphalb = new Halbjahr();
                planer.setHalbjahr(temphalb, i);
            }
   
        }
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                speichern();
                System.exit(0);
            }

        });
        terminFaecherFuellen();
        refreshNoten();
        refreshFaecher();
        refreshAll();
    }
    
    public void speichern(){
        //Speicher.speicherePlaner(planer);
    }
    
    public void refreshAll(){
         terminFaecherFuellen();
         refreshNoten();
         refreshFaecher();
    }

    public void terminFaecherFuellen() {
        cbFach.removeAllItems();
        if (cbHalbjahre.getSelectedIndex() < 4) {
            Halbjahr halbjahr = planer.getHalbjahr(cbHalbjahre.getSelectedIndex());
            for (int i = 0; i < halbjahr.getAnzahlFach(); i++) {
                cbFach.addItem(halbjahr.getFach(i));
            }
        }
    }
    
    public void refreshFaecher() {
        drawFaecher(panelJ1_1, scrollPaneJ1_1, planer.getHalbjahr(0));
        drawNoten(panelJ1_2, scrollPaneJ1_2, planer.getHalbjahr(1));
        drawNoten(panelJ2_1, scrollPaneJ2_1, planer.getHalbjahr(2));
        drawNoten(panelJ2_2, scrollPaneJ2_2, planer.getHalbjahr(3));
    }
    
    public void refreshNoten() {
        drawNoten(notenj11Panel, notenj11ScrollPane, planer.getHalbjahr(0));
        drawFaecher(notenj12Panel, notenj12ScrollPane, planer.getHalbjahr(1));
        drawFaecher(notenj21Panel, notenj21ScrollPane, planer.getHalbjahr(2));
        drawFaecher(notenj22Panel, notenj22ScrollPane, planer.getHalbjahr(3));
    }

    public void drawAlleTermine() {
        alleKlausuren = new ArrayList();
        ArrayList<TerminPanel> panels = new ArrayList<>();

        for (int i = 0; i < 4; i++) { //alle halbjahre
            Halbjahr semester = planer.getHalbjahr(i);
            if (semester == null) {
                break;
            } else {
                for (int j = 0; j < semester.getAnzahlFach(); j++) {
                    Fach fach = semester.getFach(j);
                    for (int k = 0; k < fach.getAnzahlKlausur(); k++) {
                        //System.out.println(fach.getKlausur(k));
                        TerminPanel panel = new TerminPanel();
                        panel.setFach(fach.getName());
                        panel.setDatum(fach.getKlausur(k).getTermin());
                        panel.getTxaNotiz().setText(fach.getKlausur(k).getNotiz());
                        panel.setNote(fach.getKlausur(k).getNote());
                        panels.add(panel);
                        alleKlausuren.add(fach.getKlausur(k));
                    }
                }
            }
        }

        Collections.sort(alleKlausuren);
        Collections.sort(panels);
        //System.out.println(panels.size());
        panelAllTermin.removeAll();
        for (int i = 0; i < panels.size(); i++) {
            TerminPanel panel = panels.get(i);
            panel.setBounds(0, i * 160, panelAnstTermin.getWidth(), 159);
            panelAllTermin.add(panel);
            //System.out.println(panel.datum.toString());
            panelAllTermin.setPreferredSize(new Dimension(panelAllTermin.getWidth(), (i + 1) * 160));
        }
        scrollPaneAllTermin.revalidate();
        panelAllTermin.revalidate();
        panelAllTermin.repaint();
    }

    public void drawAnstTermine() {
        alleKlausuren = new ArrayList();
        ArrayList<TerminPanel> panels = new ArrayList<>();

        for (int i = 0; i < 4; i++) { //alle halbjahre
            Halbjahr semester = planer.getHalbjahr(i);
            if (semester == null) {
                break;
            } else {
                for (int j = 0; j < semester.getAnzahlFach(); j++) {
                    Fach fach = semester.getFach(j);
                    for (int k = 0; k < fach.getAnzahlKlausur(); k++) {
                        if (fach.getKlausur(k).getTermin().compareTo(new Date()) > 0) {
                            //System.out.println(fach.getKlausur(k));
                            TerminPanel panel = new TerminPanel();
                            panel.setFach(fach.getName());
                            panel.setDatum(fach.getKlausur(k).getTermin());
                            panel.getTxaNotiz().setText(fach.getKlausur(k).getNotiz());
                            panel.setNote(fach.getKlausur(k).getNote());
                            panels.add(panel);
                            alleKlausuren.add(fach.getKlausur(k));
                        }
                    }
                }
            }
        }

        Collections.sort(alleKlausuren);
        Collections.sort(panels);
        //System.out.println(panels.size());
        panelAnstTermin.removeAll();
        for (int i = 0; i < panels.size(); i++) {
            TerminPanel panel = panels.get(i);
            panel.setBounds(0, i * 160, panelAnstTermin.getWidth(), 159);
            panelAnstTermin.add(panel);
            //System.out.println(panel.datum.toString());
            panelAnstTermin.setPreferredSize(new Dimension(panelAnstTermin.getWidth(), i * 160));
        }
        scrollPaneAnstTermin.revalidate();
        panelAnstTermin.revalidate();
        panelAnstTermin.repaint();
    }
    
    public void drawNoten(javax.swing.JPanel contentpanel, javax.swing.JScrollPane scrollpane, Halbjahr semester){
        int height = 100;
        ArrayList<NotenPanel> panels = new ArrayList();
        if (semester != null) {
            for (int j = 0; j < semester.getAnzahlFach(); j++) {
                Fach fach = semester.getFach(j);
                NotenPanel panel = new NotenPanel();
                panel.setFach(fach.getName());
                String noteKA = "";
                for (int k = 0; k < fach.getAnzahlKlausur(); k++) {
                    noteKA = noteKA + ", " + String.valueOf(fach.getKlausur(k).getNote());
                }
                //TODO: Berechnung stimmt nicht wenn noch nicht alle noten feststehen
                panel.setNoteKA(noteKA);
                panels.add(panel);
            }

            Collections.sort(panels);
            contentpanel.removeAll();
            for (int i = 0; i < panels.size(); i++) {
                NotenPanel panel = panels.get(i);
                panel.setBounds(0, i * (height+1), panelAnstTermin.getWidth(), height);
                contentpanel.add(panel);
                contentpanel.setPreferredSize(new Dimension(contentpanel.getWidth(), i * (height+1)));
            }
            scrollpane.revalidate();
            contentpanel.revalidate();
            contentpanel.repaint();
        }
    }
    
    
    public void drawFaecher(javax.swing.JPanel contentpanel, javax.swing.JScrollPane scrollpane, Halbjahr semester){
        int height = 120;
        ArrayList<FachPanel> panels = new ArrayList();
        if (semester != null) {
            for (int j = 0; j < semester.getAnzahlFach(); j++) {
                Fach fach = semester.getFach(j);
                System.out.println(fach.getName());
                FachPanel panel = new FachPanel();
                panel.setFach(fach.getName());
                String noteKA ="";
                panel.setLehrer(fach.getLehrer());
                panel.setUnterrichtszeiten(fach.getUnterrichtszeiten());
                panel.setNoteKA(fach.getKlausurnoten());
                panel.setKlammerbar(fach.getKlammerbar());
                panels.add(panel);
            }

            Collections.sort(panels);
            contentpanel.removeAll();
            for (int i = 0; i < panels.size(); i++) {
                FachPanel panel = panels.get(i);
                panel.setBounds(0, i * (height+1), panelAnstTermin.getWidth(), height);
                contentpanel.add(panel);
                contentpanel.setPreferredSize(new Dimension(contentpanel.getWidth(), i * (height+1)));
            }
            scrollpane.revalidate();
            contentpanel.revalidate();
            contentpanel.repaint();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngrpJahrgang = new javax.swing.ButtonGroup();
        btngrpHalbjahr = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        paneStundenplan = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableStundenplan = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cBoxFachSp = new javax.swing.JComboBox();
        cBoxTag = new javax.swing.JComboBox();
        spinnerStunde = new javax.swing.JSpinner();
        checkBoxA = new javax.swing.JCheckBox();
        checkBoxB = new javax.swing.JCheckBox();
        btnStundeErstellen = new javax.swing.JButton();
        btnStundeLoeschen = new javax.swing.JButton();
        btnWocheWechseln = new javax.swing.JButton();
        lblWoche = new javax.swing.JLabel();
        paneFach = new javax.swing.JPanel();
        mitteFachSeparator = new javax.swing.JSeparator();
        linkerFachPane = new javax.swing.JTabbedPane();
        scrollPaneJ1_1 = new javax.swing.JScrollPane();
        panelJ1_1 = new javax.swing.JPanel();
        scrollPaneJ1_2 = new javax.swing.JScrollPane();
        panelJ1_2 = new javax.swing.JPanel();
        j1_2Separator1 = new javax.swing.JSeparator();
        paneFachJ1_2_1 = new javax.swing.JPanel();
        lblJ1_2Fach1 = new javax.swing.JLabel();
        lblJ1_2NotenKA1 = new javax.swing.JLabel();
        lblJ1_2Lehrer1 = new javax.swing.JLabel();
        lblJ1_2Wann1 = new javax.swing.JLabel();
        lblJ1_2NoteZeugnis1 = new javax.swing.JLabel();
        txfZeugnisJ1_2_1 = new javax.swing.JTextField();
        txfKlausurenJ1_2_1 = new javax.swing.JTextField();
        scrollPaneJ2_1 = new javax.swing.JScrollPane();
        panelJ2_1 = new javax.swing.JPanel();
        j2_1Separator1 = new javax.swing.JSeparator();
        paneFachJ2_1_1 = new javax.swing.JPanel();
        lblJ2_1Fach1 = new javax.swing.JLabel();
        lblJ2_1NotenKA1 = new javax.swing.JLabel();
        lblJ2_1Lehrer1 = new javax.swing.JLabel();
        lblJ2_1Wann1 = new javax.swing.JLabel();
        lblJ2_1NoteZeugnis1 = new javax.swing.JLabel();
        txfZeugnisJ2_1_1 = new javax.swing.JTextField();
        txfKlausurenJ2_1_1 = new javax.swing.JTextField();
        scrollPaneJ2_2 = new javax.swing.JScrollPane();
        panelJ2_2 = new javax.swing.JPanel();
        j2_2Separator1 = new javax.swing.JSeparator();
        paneFachJ2_2_1 = new javax.swing.JPanel();
        lblJ2_2Fach1 = new javax.swing.JLabel();
        lblJ2_2NotenKA1 = new javax.swing.JLabel();
        lblJ2_2Lehrer1 = new javax.swing.JLabel();
        lblJ2_2Wann1 = new javax.swing.JLabel();
        lblJ2_2NoteZeugnis1 = new javax.swing.JLabel();
        txfZeugnisJ2_2_1 = new javax.swing.JTextField();
        txfKlausurenJ2_2_1 = new javax.swing.JTextField();
        lblFachSet = new javax.swing.JLabel();
        txfFach = new javax.swing.JTextField();
        lblLehrerSet = new javax.swing.JLabel();
        txfLehrer = new javax.swing.JTextField();
        lblZeugnisSet = new javax.swing.JLabel();
        txfZeugnisSet = new javax.swing.JTextField();
        lblJahrgangstufe = new javax.swing.JLabel();
        rdbtnJ1 = new javax.swing.JRadioButton();
        rdbtnJ2 = new javax.swing.JRadioButton();
        rdbtnHalbjahr1 = new javax.swing.JRadioButton();
        rdbtnHalbjahr2 = new javax.swing.JRadioButton();
        lblHalbjahrSet = new javax.swing.JLabel();
        btnBearbeitenFach = new javax.swing.JButton();
        btnHinzufuegenFach = new javax.swing.JButton();
        btnLoeschenFach = new javax.swing.JButton();
        lblTitelFach = new javax.swing.JLabel();
        lblKlammerbar = new javax.swing.JLabel();
        cbKlamemrbar = new javax.swing.JCheckBox();
        paneTermine = new javax.swing.JPanel();
        mitteTerminSeparator = new javax.swing.JSeparator();
        linkerPane = new javax.swing.JTabbedPane();
        scrollPaneAnstTermin = new javax.swing.JScrollPane();
        panelAnstTermin = new javax.swing.JPanel();
        scrollPaneAllTermin = new javax.swing.JScrollPane();
        panelAllTermin = new javax.swing.JPanel();
        txfName = new javax.swing.JTextField();
        lblTerminName = new javax.swing.JLabel();
        lblTitelTermin = new javax.swing.JLabel();
        lblFach = new javax.swing.JLabel();
        lblDatum = new javax.swing.JLabel();
        spinnerDatum = new javax.swing.JSpinner();
        lblRaum = new javax.swing.JLabel();
        txfRaum = new javax.swing.JTextField();
        lblNotiz = new javax.swing.JLabel();
        notizScrollPane = new javax.swing.JScrollPane();
        txaNotiz = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        txfNote = new javax.swing.JTextField();
        btnBearbeitenTermin = new javax.swing.JButton();
        btnHinzufuegenTermin = new javax.swing.JButton();
        cbFach = new javax.swing.JComboBox();
        btnLoeschenTermin = new javax.swing.JButton();
        lblHalbjahrTerminDingens = new javax.swing.JLabel();
        cbHalbjahre = new javax.swing.JComboBox<>();
        paneNotenuebersicht = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        notenj11ScrollPane = new javax.swing.JScrollPane();
        notenj11Panel = new javax.swing.JPanel();
        notenj12ScrollPane = new javax.swing.JScrollPane();
        notenj12Panel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblFachNoteJ12 = new javax.swing.JLabel();
        lblFachKlausurJ12 = new javax.swing.JLabel();
        lblZeugnisNoteJ12 = new javax.swing.JLabel();
        notenj21ScrollPane = new javax.swing.JScrollPane();
        notenj21Panel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lblFachNoteJ21 = new javax.swing.JLabel();
        lblKlausurNoteJ21 = new javax.swing.JLabel();
        lblZeugnisNoteJ21 = new javax.swing.JLabel();
        notenj22ScrollPane = new javax.swing.JScrollPane();
        notenj22Panel = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        lblFachNoteJ22 = new javax.swing.JLabel();
        lblKlausurNoteJ22 = new javax.swing.JLabel();
        lblZeugnisNoteJ22 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cBoxFachNote = new javax.swing.JComboBox();
        spinnerHalbjahr = new javax.swing.JSpinner();
        txfKlausur = new javax.swing.JTextField();
        txfZeugnis = new javax.swing.JTextField();
        btnSucheFach = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txfDurchschnitt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txfErziehlt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txfMoeglich = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tableStundenplan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {" 1", null, null, null, null, null},
                {" 2", null, null, null, null, null},
                {" 3", null, null, null, null, null},
                {" 4", null, null, null, null, null},
                {" 5", null, null, null, null, null},
                {" 6", null, null, null, null, null},
                {" 7", null, null, null, null, null},
                {" 8", null, null, null, null, null},
                {" 9", null, null, null, null, null},
                {" 10", null, null, null, null, null},
                {" 11", null, null, null, null, null},
                {" 12", null, null, null, null, null}
            },
            new String [] {
                "Stunde", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableStundenplan.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableStundenplan);

        jLabel2.setText("Fach:");

        jLabel3.setText("Tag:");

        jLabel4.setText("Stunde:");

        jLabel5.setText("Woche:");

        cBoxFachSp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cBoxTag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        checkBoxA.setText("A");

        checkBoxB.setText("B");

        btnStundeErstellen.setText("Stunde erstellen");

        btnStundeLoeschen.setText("Stunde löschen");

        btnWocheWechseln.setText("Woche wechseln");

        lblWoche.setText("Woche A:");

        javax.swing.GroupLayout paneStundenplanLayout = new javax.swing.GroupLayout(paneStundenplan);
        paneStundenplan.setLayout(paneStundenplanLayout);
        paneStundenplanLayout.setHorizontalGroup(
            paneStundenplanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
            .addGroup(paneStundenplanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneStundenplanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneStundenplanLayout.createSequentialGroup()
                        .addGroup(paneStundenplanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(paneStundenplanLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(67, 67, 67)
                                .addGroup(paneStundenplanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cBoxTag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(paneStundenplanLayout.createSequentialGroup()
                                        .addGroup(paneStundenplanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cBoxFachSp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spinnerStunde, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(30, 30, 30)
                                        .addGroup(paneStundenplanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnStundeErstellen, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnStundeLoeschen, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnWocheWechseln)))
                                    .addGroup(paneStundenplanLayout.createSequentialGroup()
                                        .addComponent(checkBoxA)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(checkBoxB))))
                            .addComponent(lblWoche))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(paneStundenplanLayout.createSequentialGroup()
                        .addGroup(paneStundenplanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        paneStundenplanLayout.setVerticalGroup(
            paneStundenplanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneStundenplanLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblWoche)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(paneStundenplanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cBoxFachSp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStundeErstellen))
                .addGap(18, 18, 18)
                .addGroup(paneStundenplanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cBoxTag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStundeLoeschen))
                .addGap(18, 18, 18)
                .addGroup(paneStundenplanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnWocheWechseln)
                    .addComponent(jLabel3)
                    .addComponent(spinnerStunde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(paneStundenplanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(checkBoxA)
                    .addComponent(checkBoxB))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Stundenplan", paneStundenplan);

        mitteFachSeparator.setForeground(new java.awt.Color(0, 0, 0));
        mitteFachSeparator.setOrientation(javax.swing.SwingConstants.VERTICAL);

        scrollPaneJ1_1.setBorder(null);
        scrollPaneJ1_1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneJ1_1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panelJ1_1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout panelJ1_1Layout = new javax.swing.GroupLayout(panelJ1_1);
        panelJ1_1.setLayout(panelJ1_1Layout);
        panelJ1_1Layout.setHorizontalGroup(
            panelJ1_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );
        panelJ1_1Layout.setVerticalGroup(
            panelJ1_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 715, Short.MAX_VALUE)
        );

        scrollPaneJ1_1.setViewportView(panelJ1_1);

        linkerFachPane.addTab("J1.1", scrollPaneJ1_1);

        scrollPaneJ1_2.setBorder(null);
        scrollPaneJ1_2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneJ1_2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panelJ1_2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        j1_2Separator1.setForeground(new java.awt.Color(0, 0, 0));
        j1_2Separator1.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        paneFachJ1_2_1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        paneFachJ1_2_1.setToolTipText("Klicken um zu bearbeiten");
        paneFachJ1_2_1.setPreferredSize(new java.awt.Dimension(285, 135));

        lblJ1_2Fach1.setText("Fach");

        lblJ1_2NotenKA1.setText("Klausuren Noten :");

        lblJ1_2Lehrer1.setText("Lehrer");

        lblJ1_2Wann1.setText("Unterrichtzeiten");

        lblJ1_2NoteZeugnis1.setText("Zeugnis Note:");

        txfZeugnisJ1_2_1.setEditable(false);

        txfKlausurenJ1_2_1.setEditable(false);

        javax.swing.GroupLayout paneFachJ1_2_1Layout = new javax.swing.GroupLayout(paneFachJ1_2_1);
        paneFachJ1_2_1.setLayout(paneFachJ1_2_1Layout);
        paneFachJ1_2_1Layout.setHorizontalGroup(
            paneFachJ1_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneFachJ1_2_1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneFachJ1_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneFachJ1_2_1Layout.createSequentialGroup()
                        .addComponent(lblJ1_2Fach1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblJ1_2Lehrer1))
                    .addGroup(paneFachJ1_2_1Layout.createSequentialGroup()
                        .addGroup(paneFachJ1_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblJ1_2Wann1)
                            .addGroup(paneFachJ1_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, paneFachJ1_2_1Layout.createSequentialGroup()
                                    .addComponent(lblJ1_2NotenKA1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txfKlausurenJ1_2_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, paneFachJ1_2_1Layout.createSequentialGroup()
                                    .addComponent(lblJ1_2NoteZeugnis1)
                                    .addGap(33, 33, 33)
                                    .addComponent(txfZeugnisJ1_2_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        paneFachJ1_2_1Layout.setVerticalGroup(
            paneFachJ1_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneFachJ1_2_1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneFachJ1_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJ1_2Fach1)
                    .addComponent(lblJ1_2Lehrer1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJ1_2Wann1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(paneFachJ1_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJ1_2NoteZeugnis1)
                    .addComponent(txfZeugnisJ1_2_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneFachJ1_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJ1_2NotenKA1)
                    .addComponent(txfKlausurenJ1_2_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );

        javax.swing.GroupLayout panelJ1_2Layout = new javax.swing.GroupLayout(panelJ1_2);
        panelJ1_2.setLayout(panelJ1_2Layout);
        panelJ1_2Layout.setHorizontalGroup(
            panelJ1_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(j1_2Separator1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
            .addComponent(paneFachJ1_2_1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
        );
        panelJ1_2Layout.setVerticalGroup(
            panelJ1_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJ1_2Layout.createSequentialGroup()
                .addComponent(paneFachJ1_2_1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(j1_2Separator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(468, Short.MAX_VALUE))
        );

        scrollPaneJ1_2.setViewportView(panelJ1_2);

        linkerFachPane.addTab("J1.2", scrollPaneJ1_2);

        scrollPaneJ2_1.setBorder(null);
        scrollPaneJ2_1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneJ2_1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panelJ2_1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        j2_1Separator1.setForeground(new java.awt.Color(0, 0, 0));
        j2_1Separator1.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        paneFachJ2_1_1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        paneFachJ2_1_1.setToolTipText("Klicken um zu bearbeiten");
        paneFachJ2_1_1.setPreferredSize(new java.awt.Dimension(285, 135));

        lblJ2_1Fach1.setText("Fach");

        lblJ2_1NotenKA1.setText("Klausuren Noten :");

        lblJ2_1Lehrer1.setText("Lehrer");

        lblJ2_1Wann1.setText("Unterrichtzeiten");

        lblJ2_1NoteZeugnis1.setText("Zeugnis Note:");

        txfZeugnisJ2_1_1.setEditable(false);

        txfKlausurenJ2_1_1.setEditable(false);

        javax.swing.GroupLayout paneFachJ2_1_1Layout = new javax.swing.GroupLayout(paneFachJ2_1_1);
        paneFachJ2_1_1.setLayout(paneFachJ2_1_1Layout);
        paneFachJ2_1_1Layout.setHorizontalGroup(
            paneFachJ2_1_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneFachJ2_1_1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneFachJ2_1_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneFachJ2_1_1Layout.createSequentialGroup()
                        .addComponent(lblJ2_1Fach1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblJ2_1Lehrer1))
                    .addGroup(paneFachJ2_1_1Layout.createSequentialGroup()
                        .addGroup(paneFachJ2_1_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblJ2_1Wann1)
                            .addGroup(paneFachJ2_1_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, paneFachJ2_1_1Layout.createSequentialGroup()
                                    .addComponent(lblJ2_1NotenKA1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txfKlausurenJ2_1_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, paneFachJ2_1_1Layout.createSequentialGroup()
                                    .addComponent(lblJ2_1NoteZeugnis1)
                                    .addGap(33, 33, 33)
                                    .addComponent(txfZeugnisJ2_1_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        paneFachJ2_1_1Layout.setVerticalGroup(
            paneFachJ2_1_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneFachJ2_1_1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneFachJ2_1_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJ2_1Fach1)
                    .addComponent(lblJ2_1Lehrer1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJ2_1Wann1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(paneFachJ2_1_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJ2_1NoteZeugnis1)
                    .addComponent(txfZeugnisJ2_1_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneFachJ2_1_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJ2_1NotenKA1)
                    .addComponent(txfKlausurenJ2_1_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );

        javax.swing.GroupLayout panelJ2_1Layout = new javax.swing.GroupLayout(panelJ2_1);
        panelJ2_1.setLayout(panelJ2_1Layout);
        panelJ2_1Layout.setHorizontalGroup(
            panelJ2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(j2_1Separator1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
            .addComponent(paneFachJ2_1_1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
        );
        panelJ2_1Layout.setVerticalGroup(
            panelJ2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJ2_1Layout.createSequentialGroup()
                .addComponent(paneFachJ2_1_1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(j2_1Separator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(468, Short.MAX_VALUE))
        );

        scrollPaneJ2_1.setViewportView(panelJ2_1);

        linkerFachPane.addTab("J2.1", scrollPaneJ2_1);

        scrollPaneJ2_2.setBorder(null);
        scrollPaneJ2_2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneJ2_2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panelJ2_2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        j2_2Separator1.setForeground(new java.awt.Color(0, 0, 0));
        j2_2Separator1.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        paneFachJ2_2_1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        paneFachJ2_2_1.setToolTipText("Klicken um zu bearbeiten");
        paneFachJ2_2_1.setPreferredSize(new java.awt.Dimension(285, 135));

        lblJ2_2Fach1.setText("Fach");

        lblJ2_2NotenKA1.setText("Klausuren Noten :");

        lblJ2_2Lehrer1.setText("Lehrer");

        lblJ2_2Wann1.setText("Unterrichtzeiten");

        lblJ2_2NoteZeugnis1.setText("Zeugnis Note:");

        txfZeugnisJ2_2_1.setEditable(false);

        txfKlausurenJ2_2_1.setEditable(false);

        javax.swing.GroupLayout paneFachJ2_2_1Layout = new javax.swing.GroupLayout(paneFachJ2_2_1);
        paneFachJ2_2_1.setLayout(paneFachJ2_2_1Layout);
        paneFachJ2_2_1Layout.setHorizontalGroup(
            paneFachJ2_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneFachJ2_2_1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneFachJ2_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneFachJ2_2_1Layout.createSequentialGroup()
                        .addComponent(lblJ2_2Fach1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblJ2_2Lehrer1))
                    .addGroup(paneFachJ2_2_1Layout.createSequentialGroup()
                        .addGroup(paneFachJ2_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblJ2_2Wann1)
                            .addGroup(paneFachJ2_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, paneFachJ2_2_1Layout.createSequentialGroup()
                                    .addComponent(lblJ2_2NotenKA1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txfKlausurenJ2_2_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, paneFachJ2_2_1Layout.createSequentialGroup()
                                    .addComponent(lblJ2_2NoteZeugnis1)
                                    .addGap(33, 33, 33)
                                    .addComponent(txfZeugnisJ2_2_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        paneFachJ2_2_1Layout.setVerticalGroup(
            paneFachJ2_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneFachJ2_2_1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneFachJ2_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJ2_2Fach1)
                    .addComponent(lblJ2_2Lehrer1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJ2_2Wann1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(paneFachJ2_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJ2_2NoteZeugnis1)
                    .addComponent(txfZeugnisJ2_2_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneFachJ2_2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJ2_2NotenKA1)
                    .addComponent(txfKlausurenJ2_2_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );

        javax.swing.GroupLayout panelJ2_2Layout = new javax.swing.GroupLayout(panelJ2_2);
        panelJ2_2.setLayout(panelJ2_2Layout);
        panelJ2_2Layout.setHorizontalGroup(
            panelJ2_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(j2_2Separator1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
            .addComponent(paneFachJ2_2_1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
        );
        panelJ2_2Layout.setVerticalGroup(
            panelJ2_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJ2_2Layout.createSequentialGroup()
                .addComponent(paneFachJ2_2_1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(j2_2Separator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(468, Short.MAX_VALUE))
        );

        scrollPaneJ2_2.setViewportView(panelJ2_2);

        linkerFachPane.addTab("J2.2", scrollPaneJ2_2);

        lblFachSet.setText("Fach");

        lblLehrerSet.setText("Lehrer");

        lblZeugnisSet.setText("Zeugnis Note");

        lblJahrgangstufe.setText("Jahrgangstufe");

        btngrpJahrgang.add(rdbtnJ1);
        rdbtnJ1.setText("J1");

        btngrpJahrgang.add(rdbtnJ2);
        rdbtnJ2.setText("J2");

        btngrpHalbjahr.add(rdbtnHalbjahr1);
        rdbtnHalbjahr1.setText("erstes");

        btngrpHalbjahr.add(rdbtnHalbjahr2);
        rdbtnHalbjahr2.setText("zweites");

        lblHalbjahrSet.setText("Halbjahr");

        btnBearbeitenFach.setText("Bearbeitung Speichern");
        btnBearbeitenFach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBearbeitenFachActionPerformed(evt);
            }
        });

        btnHinzufuegenFach.setText("Fach Hinzufügen");
        btnHinzufuegenFach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHinzufuegenFachActionPerformed(evt);
            }
        });

        btnLoeschenFach.setText("Fach Löschen");
        btnLoeschenFach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoeschenFachActionPerformed(evt);
            }
        });

        lblTitelFach.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTitelFach.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitelFach.setText("Fächer hinzufügen und bearbeiten");

        lblKlammerbar.setText("Klammerbar");

        cbKlamemrbar.setText("ja ? ");

        javax.swing.GroupLayout paneFachLayout = new javax.swing.GroupLayout(paneFach);
        paneFach.setLayout(paneFachLayout);
        paneFachLayout.setHorizontalGroup(
            paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneFachLayout.createSequentialGroup()
                .addComponent(linkerFachPane, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mitteFachSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLoeschenFach)
                    .addGroup(paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblTitelFach)
                        .addGroup(paneFachLayout.createSequentialGroup()
                            .addGroup(paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnBearbeitenFach)
                                .addGroup(paneFachLayout.createSequentialGroup()
                                    .addGroup(paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblLehrerSet)
                                        .addComponent(lblZeugnisSet)
                                        .addComponent(lblJahrgangstufe)
                                        .addComponent(lblHalbjahrSet)
                                        .addComponent(lblFachSet)
                                        .addComponent(lblKlammerbar))
                                    .addGap(18, 18, 18)
                                    .addGroup(paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(paneFachLayout.createSequentialGroup()
                                            .addGroup(paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txfFach, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txfZeugnisSet, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txfLehrer, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(88, 88, 88))
                                        .addGroup(paneFachLayout.createSequentialGroup()
                                            .addGroup(paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(paneFachLayout.createSequentialGroup()
                                                    .addComponent(rdbtnHalbjahr1)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneFachLayout.createSequentialGroup()
                                                    .addComponent(rdbtnJ1)
                                                    .addGap(60, 60, 60)))
                                            .addGroup(paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(rdbtnJ2)
                                                .addComponent(rdbtnHalbjahr2)))
                                        .addComponent(cbKlamemrbar, javax.swing.GroupLayout.Alignment.LEADING)))
                                .addComponent(btnHinzufuegenFach))
                            .addGap(24, 24, 24))))
                .addGap(0, 172, Short.MAX_VALUE))
        );
        paneFachLayout.setVerticalGroup(
            paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(linkerFachPane, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
            .addGroup(paneFachLayout.createSequentialGroup()
                .addComponent(lblTitelFach)
                .addGap(8, 8, 8)
                .addGroup(paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFachSet)
                    .addComponent(txfFach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLehrerSet)
                    .addComponent(txfLehrer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblZeugnisSet)
                    .addComponent(txfZeugnisSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJahrgangstufe)
                    .addComponent(rdbtnJ1)
                    .addComponent(rdbtnJ2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbtnHalbjahr1)
                    .addComponent(lblHalbjahrSet)
                    .addComponent(rdbtnHalbjahr2))
                .addGap(1, 1, 1)
                .addGroup(paneFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKlammerbar)
                    .addComponent(cbKlamemrbar))
                .addGap(18, 18, 18)
                .addComponent(btnBearbeitenFach)
                .addGap(18, 18, 18)
                .addComponent(btnHinzufuegenFach)
                .addGap(18, 18, 18)
                .addComponent(btnLoeschenFach)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(mitteFachSeparator)
        );

        jTabbedPane1.addTab("Fächer", paneFach);

        mitteTerminSeparator.setForeground(new java.awt.Color(0, 0, 0));
        mitteTerminSeparator.setOrientation(javax.swing.SwingConstants.VERTICAL);

        scrollPaneAnstTermin.setBorder(null);
        scrollPaneAnstTermin.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panelAnstTermin.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout panelAnstTerminLayout = new javax.swing.GroupLayout(panelAnstTermin);
        panelAnstTermin.setLayout(panelAnstTerminLayout);
        panelAnstTerminLayout.setHorizontalGroup(
            panelAnstTerminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );
        panelAnstTerminLayout.setVerticalGroup(
            panelAnstTerminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
        );

        scrollPaneAnstTermin.setViewportView(panelAnstTermin);

        linkerPane.addTab("Anstehende Termine", scrollPaneAnstTermin);

        scrollPaneAllTermin.setBorder(null);
        scrollPaneAllTermin.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panelAllTermin.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout panelAllTerminLayout = new javax.swing.GroupLayout(panelAllTermin);
        panelAllTermin.setLayout(panelAllTerminLayout);
        panelAllTerminLayout.setHorizontalGroup(
            panelAllTerminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );
        panelAllTerminLayout.setVerticalGroup(
            panelAllTerminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
        );

        scrollPaneAllTermin.setViewportView(panelAllTermin);

        linkerPane.addTab("Alle Termine", scrollPaneAllTermin);

        txfName.setToolTipText("Termin Name");
        txfName.setEnabled(false);

        lblTerminName.setText("Termin Name");

        lblTitelTermin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTitelTermin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitelTermin.setText("Termine hinzufügen und bearbeiten");

        lblFach.setText("Fach");

        lblDatum.setText("Datum");

        spinnerDatum.setModel(new javax.swing.SpinnerDateModel());

        lblRaum.setText("Raum");

        txfRaum.setEnabled(false);

        lblNotiz.setText("Notiz");

        notizScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txaNotiz.setColumns(20);
        txaNotiz.setRows(5);
        txaNotiz.setText("Notiiiz");
        notizScrollPane.setViewportView(txaNotiz);

        jLabel1.setText("Note");

        btnBearbeitenTermin.setText("Bearbeitung Speichern");
        btnBearbeitenTermin.setEnabled(false);

        btnHinzufuegenTermin.setText("Termin hinzufügen");
        btnHinzufuegenTermin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHinzufuegenTerminActionPerformed(evt);
            }
        });

        cbFach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFachActionPerformed(evt);
            }
        });

        btnLoeschenTermin.setText("Termin Löschen");

        lblHalbjahrTerminDingens.setText("Halbjahr");

        cbHalbjahre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "J1.1", "J1.2", "J2.1", "J2.2", "Abitur" }));
        cbHalbjahre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbHalbjahreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneTermineLayout = new javax.swing.GroupLayout(paneTermine);
        paneTermine.setLayout(paneTermineLayout);
        paneTermineLayout.setHorizontalGroup(
            paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneTermineLayout.createSequentialGroup()
                .addComponent(linkerPane, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mitteTerminSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneTermineLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTerminName)
                            .addComponent(lblFach)
                            .addComponent(lblDatum)
                            .addComponent(lblRaum)
                            .addComponent(lblNotiz)
                            .addComponent(jLabel1)
                            .addComponent(lblHalbjahrTerminDingens))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(paneTermineLayout.createSequentialGroup()
                                .addGroup(paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(notizScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                                    .addGroup(paneTermineLayout.createSequentialGroup()
                                        .addGroup(paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txfName)
                                            .addComponent(txfRaum)
                                            .addComponent(txfNote)
                                            .addComponent(cbFach, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(spinnerDatum))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(123, 123, 123))
                            .addGroup(paneTermineLayout.createSequentialGroup()
                                .addComponent(cbHalbjahre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(paneTermineLayout.createSequentialGroup()
                        .addGroup(paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(paneTermineLayout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(lblTitelTermin))
                            .addGroup(paneTermineLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnHinzufuegenTermin)
                                    .addComponent(btnLoeschenTermin)
                                    .addComponent(btnBearbeitenTermin))))
                        .addContainerGap())))
        );
        paneTermineLayout.setVerticalGroup(
            paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mitteTerminSeparator)
            .addGroup(paneTermineLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(lblTitelTermin)
                .addGap(4, 4, 4)
                .addGroup(paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTerminName)
                    .addComponent(txfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFach)
                    .addComponent(cbFach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatum)
                    .addComponent(spinnerDatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRaum)
                    .addComponent(txfRaum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNotiz)
                    .addComponent(notizScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txfNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneTermineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHalbjahrTerminDingens)
                    .addComponent(cbHalbjahre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(btnBearbeitenTermin)
                .addGap(18, 18, 18)
                .addComponent(btnHinzufuegenTermin)
                .addGap(18, 18, 18)
                .addComponent(btnLoeschenTermin)
                .addContainerGap(105, Short.MAX_VALUE))
            .addComponent(linkerPane, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Termine", paneTermine);

        paneNotenuebersicht.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jTabbedPane3.setMinimumSize(new java.awt.Dimension(115, 66));
        jTabbedPane3.setPreferredSize(new java.awt.Dimension(350, 533));

        notenj11ScrollPane.setBorder(null);
        notenj11ScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        notenj11ScrollPane.setPreferredSize(new java.awt.Dimension(345, 505));

        notenj11Panel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout notenj11PanelLayout = new javax.swing.GroupLayout(notenj11Panel);
        notenj11Panel.setLayout(notenj11PanelLayout);
        notenj11PanelLayout.setHorizontalGroup(
            notenj11PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );
        notenj11PanelLayout.setVerticalGroup(
            notenj11PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
        );

        notenj11ScrollPane.setViewportView(notenj11Panel);

        jTabbedPane3.addTab("J1.1", notenj11ScrollPane);

        notenj12ScrollPane.setBorder(null);
        notenj12ScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        notenj12ScrollPane.setPreferredSize(new java.awt.Dimension(345, 505));

        notenj12Panel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setPreferredSize(new java.awt.Dimension(285, 135));

        lblFachNoteJ12.setText("Fach:");

        lblFachKlausurJ12.setText("Klausurnoten:");

        lblZeugnisNoteJ12.setText("Zeugnisnote:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFachNoteJ12)
                    .addComponent(lblFachKlausurJ12)
                    .addComponent(lblZeugnisNoteJ12))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFachNoteJ12)
                .addGap(18, 18, 18)
                .addComponent(lblFachKlausurJ12)
                .addGap(18, 18, 18)
                .addComponent(lblZeugnisNoteJ12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout notenj12PanelLayout = new javax.swing.GroupLayout(notenj12Panel);
        notenj12Panel.setLayout(notenj12PanelLayout);
        notenj12PanelLayout.setHorizontalGroup(
            notenj12PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
        );
        notenj12PanelLayout.setVerticalGroup(
            notenj12PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notenj12PanelLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 462, Short.MAX_VALUE))
        );

        notenj12ScrollPane.setViewportView(notenj12Panel);

        jTabbedPane3.addTab("J1.2", notenj12ScrollPane);

        notenj21ScrollPane.setBorder(null);
        notenj21ScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        notenj21ScrollPane.setPreferredSize(new java.awt.Dimension(345, 505));

        notenj21Panel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        notenj21Panel.setPreferredSize(new java.awt.Dimension(328, 461));

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setPreferredSize(new java.awt.Dimension(285, 135));

        lblFachNoteJ21.setText("Fach:");

        lblKlausurNoteJ21.setText("Klausurnoten:");

        lblZeugnisNoteJ21.setText("Zeugnisnote:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFachNoteJ21)
                    .addComponent(lblKlausurNoteJ21)
                    .addComponent(lblZeugnisNoteJ21))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFachNoteJ21)
                .addGap(18, 18, 18)
                .addComponent(lblKlausurNoteJ21)
                .addGap(18, 18, 18)
                .addComponent(lblZeugnisNoteJ21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout notenj21PanelLayout = new javax.swing.GroupLayout(notenj21Panel);
        notenj21Panel.setLayout(notenj21PanelLayout);
        notenj21PanelLayout.setHorizontalGroup(
            notenj21PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
        );
        notenj21PanelLayout.setVerticalGroup(
            notenj21PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notenj21PanelLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 452, Short.MAX_VALUE))
        );

        notenj21ScrollPane.setViewportView(notenj21Panel);

        jTabbedPane3.addTab("J2.1", notenj21ScrollPane);

        notenj22ScrollPane.setBorder(null);
        notenj22ScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        notenj22ScrollPane.setPreferredSize(new java.awt.Dimension(345, 505));

        notenj22Panel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.setPreferredSize(new java.awt.Dimension(285, 135));

        lblFachNoteJ22.setText("Fach:");

        lblKlausurNoteJ22.setText("Klausurnoten:");

        lblZeugnisNoteJ22.setText("Zeugnisnote:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFachNoteJ22)
                    .addComponent(lblKlausurNoteJ22)
                    .addComponent(lblZeugnisNoteJ22))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFachNoteJ22)
                .addGap(18, 18, 18)
                .addComponent(lblKlausurNoteJ22)
                .addGap(18, 18, 18)
                .addComponent(lblZeugnisNoteJ22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout notenj22PanelLayout = new javax.swing.GroupLayout(notenj22Panel);
        notenj22Panel.setLayout(notenj22PanelLayout);
        notenj22PanelLayout.setHorizontalGroup(
            notenj22PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
        );
        notenj22PanelLayout.setVerticalGroup(
            notenj22PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notenj22PanelLayout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 462, Short.MAX_VALUE))
        );

        notenj22ScrollPane.setViewportView(notenj22Panel);

        jTabbedPane3.addTab("J2.2", notenj22ScrollPane);

        jLabel8.setText("Fach:");

        jLabel9.setText("Halbjahr:");

        jLabel6.setText("Klausurnoten:");

        jLabel7.setText("Zeugnisnote:");

        spinnerHalbjahr.setModel(new javax.swing.SpinnerNumberModel(1, 1, 4, 1));

        txfKlausur.setEditable(false);

        txfZeugnis.setEditable(false);

        btnSucheFach.setText("Suche Fach");
        btnSucheFach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSucheFachActionPerformed(evt);
            }
        });

        jLabel10.setText("Durchschnitt:");

        txfDurchschnitt.setEditable(false);
        txfDurchschnitt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfDurchschnittActionPerformed(evt);
            }
        });

        jLabel11.setText("bis jetzt erziehlte Punkte:");

        txfErziehlt.setEditable(false);

        jLabel12.setText("möglich gewesene Punktzahl:");

        txfMoeglich.setEditable(false);

        jLabel13.setText("Unterpunktet:");

        jLabel14.setText("Punkte bis zum bestandenen Abi:");

        jTextField1.setEditable(false);

        jScrollPane3.setEnabled(false);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        javax.swing.GroupLayout paneNotenuebersichtLayout = new javax.swing.GroupLayout(paneNotenuebersicht);
        paneNotenuebersicht.setLayout(paneNotenuebersichtLayout);
        paneNotenuebersichtLayout.setHorizontalGroup(
            paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneNotenuebersichtLayout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneNotenuebersichtLayout.createSequentialGroup()
                        .addGroup(paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10)
                            .addComponent(jLabel14))
                        .addGap(2, 77, Short.MAX_VALUE)
                        .addGroup(paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField1)
                            .addComponent(txfZeugnis, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfMoeglich, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfErziehlt)
                            .addComponent(txfDurchschnitt)
                            .addComponent(txfKlausur, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spinnerHalbjahr, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cBoxFachNote, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSucheFach, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(78, 78, 78))
                    .addGroup(paneNotenuebersichtLayout.createSequentialGroup()
                        .addGroup(paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(paneNotenuebersichtLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        paneNotenuebersichtLayout.setVerticalGroup(
            paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(paneNotenuebersichtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cBoxFachNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(spinnerHalbjahr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txfKlausur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txfZeugnis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSucheFach)
                .addGap(18, 18, 18)
                .addGroup(paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txfDurchschnitt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txfErziehlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txfMoeglich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(paneNotenuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
            .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Notenübersicht", paneNotenuebersicht);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoeschenFachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoeschenFachActionPerformed
        String tempName, tempLehrer;
        int tempHalbjahrNummer, tempfachid;
        Halbjahr tempHalbjahr = new Halbjahr();
        Fach tempFach = new Fach();

        tempName = txfFach.getText();

        if (rdbtnJ1.isSelected()) {
            if (rdbtnHalbjahr1.isSelected()) {
                tempHalbjahrNummer = 0;
                tempHalbjahr = planer.getHalbjahr(tempHalbjahrNummer);
            } else if (rdbtnHalbjahr2.isSelected()) {
                tempHalbjahrNummer = 1;
                tempHalbjahr = planer.getHalbjahr(tempHalbjahrNummer);
            }
        } else if (rdbtnJ2.isSelected()) {
            if (rdbtnHalbjahr1.isSelected()) {
                tempHalbjahrNummer = 2;
                tempHalbjahr = planer.getHalbjahr(tempHalbjahrNummer);
            } else if (rdbtnHalbjahr2.isSelected()) {
                tempHalbjahrNummer = 3;
                tempHalbjahr = planer.getHalbjahr(tempHalbjahrNummer);
            }
        } else {
            JOptionPane.showMessageDialog(this, "HalbJahr und Kurstufe auswählen");
        }

        if (tempHalbjahr.getAnzahlFach() > 0) {
            if (tempHalbjahr.existFach(tempName) == true) {
                tempfachid = tempHalbjahr.getFachid(tempName);
                tempHalbjahr.deleteFach(tempfachid);

            } else {
                JOptionPane.showMessageDialog(this, "Dieses Fach gibt es nicht");
            }
        }
        refreshAll();
    }//GEN-LAST:event_btnLoeschenFachActionPerformed

    private void cbFachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFachActionPerformed

    private void txfDurchschnittActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfDurchschnittActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfDurchschnittActionPerformed

    private void btnHinzufuegenFachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHinzufuegenFachActionPerformed
        String tempName, tempLehrer;
        int tempHalbjahrNummer;
        Halbjahr tempHalbjahr = new Halbjahr();
        Fach tempFach = new Fach();

        tempName = txfFach.getText();
        tempLehrer = txfLehrer.getText();
        if (rdbtnJ1.isSelected()) {
            if (rdbtnHalbjahr1.isSelected()) {
                tempHalbjahrNummer = 0;
                tempHalbjahr = planer.getHalbjahr(tempHalbjahrNummer);
            } else if (rdbtnHalbjahr2.isSelected()) {
                tempHalbjahrNummer = 1;
                tempHalbjahr = planer.getHalbjahr(tempHalbjahrNummer);
            }
        } else if (rdbtnJ2.isSelected()) {
            if (rdbtnHalbjahr1.isSelected()) {
                tempHalbjahrNummer = 2;
                tempHalbjahr = planer.getHalbjahr(tempHalbjahrNummer);
            } else if (rdbtnHalbjahr2.isSelected()) {
                tempHalbjahrNummer = 3;
                tempHalbjahr = planer.getHalbjahr(tempHalbjahrNummer);
            }
        } else {
            JOptionPane.showMessageDialog(this, "HalbJahr und Kurstufe auswählen");
        }

        if (tempHalbjahr.getAnzahlFach() > 0) {
            if (tempHalbjahr.existFach(tempName) == true) {
                JOptionPane.showMessageDialog(this, "Dieses Fach gibt es schon");
            } else {
                tempFach.setLehrer(tempLehrer);
                tempFach.setName(tempName);
                tempFach.setKlammerbar(cbKlamemrbar.isSelected());

                if (txfZeugnis.getText().equals("") == false) {
                    tempFach.setZeugnisnote(Integer.valueOf(txfZeugnis.getText()));
                }
                tempHalbjahr.addFach(tempFach);
            }
        } else {
            tempFach.setLehrer(tempLehrer);
            tempFach.setName(tempName);
            tempFach.setKlammerbar(cbKlamemrbar.isSelected());

            if (txfZeugnis.getText().equals("") == false) {
                tempFach.setZeugnisnote(Integer.valueOf(txfZeugnis.getText()));
            }
            tempHalbjahr.addFach(tempFach);
        }
        refreshAll();
    }//GEN-LAST:event_btnHinzufuegenFachActionPerformed


    private void btnHinzufuegenTerminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHinzufuegenTerminActionPerformed
        Klausur klausur = new Klausur((Date) spinnerDatum.getValue());
        klausur.setNotiz(txaNotiz.getText());
        if(!txfNote.getText().isEmpty())
            klausur.setNote(Integer.parseInt(txfNote.getText()));
        //TODO Error handling
        Fach fach = (Fach) cbFach.getSelectedItem();
        fach.addKlausur(klausur);
        
        drawAlleTermine();
        drawAnstTermine();
        refreshNoten();
    }//GEN-LAST:event_btnHinzufuegenTerminActionPerformed

    private void btnBearbeitenFachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBearbeitenFachActionPerformed
        String tempName, tempLehrer;
        int tempHalbjahrNummer;
        Halbjahr tempHalbjahr = new Halbjahr();
        Fach tempFach = new Fach();

        tempName = txfFach.getText();
        tempLehrer = txfLehrer.getText();

        if (rdbtnJ1.isSelected()) {
            if (rdbtnHalbjahr1.isSelected()) {
                tempHalbjahrNummer = 0;
                tempHalbjahr = planer.getHalbjahr(tempHalbjahrNummer);
            } else if (rdbtnHalbjahr2.isSelected()) {
                tempHalbjahrNummer = 1;
                tempHalbjahr = planer.getHalbjahr(tempHalbjahrNummer);
            }
        } else if (rdbtnJ2.isSelected()) {
            if (rdbtnHalbjahr1.isSelected()) {
                tempHalbjahrNummer = 2;
                tempHalbjahr = planer.getHalbjahr(tempHalbjahrNummer);
            } else if (rdbtnHalbjahr2.isSelected()) {
                tempHalbjahrNummer = 3;
                tempHalbjahr = planer.getHalbjahr(tempHalbjahrNummer);
            }
        } else {
            JOptionPane.showMessageDialog(this, "HalbJahr und Kurstufe auswählen");
        }

        if (tempHalbjahr.getAnzahlFach() > 0) {
            if (tempHalbjahr.existFach(tempName) == true) {
                tempFach = tempHalbjahr.getFachByName(tempName);
                tempFach.setLehrer(tempLehrer);
                tempFach.setKlammerbar(cbKlamemrbar.isSelected());

                if (txfZeugnis.getText().equals("") == false) {
                    tempFach.setZeugnisnote(Integer.valueOf(txfZeugnis.getText()));
                }
                //tempHalbjahr.addFach(tempFach);
            } else {
                JOptionPane.showMessageDialog(this, "Dieses Fach gibt es nicht");
            }
        } else {
            tempFach = tempHalbjahr.getFachByName(tempName);
            tempFach.setLehrer(tempLehrer);
            tempFach.setKlammerbar(cbKlamemrbar.isSelected());

            if (txfZeugnis.getText().equals("") == false) {
                tempFach.setZeugnisnote(Integer.valueOf(txfZeugnis.getText()));
            }
            //tempHalbjahr.addFach(tempFach);
        }

        refreshAll();
    }//GEN-LAST:event_btnBearbeitenFachActionPerformed


    private void btnSucheFachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSucheFachActionPerformed
        String fachname = "";
        if(planer.getHalbjahr((Integer)spinnerHalbjahr.getValue()).getFachByName(fachname).getZeugnisnote() != -1)
        {
            txfZeugnis.setText(Integer.toString(planer.getHalbjahr((Integer)spinnerHalbjahr.getValue()).getFachByName(fachname).getZeugnisnote()));
        }
        
        String klausuren = "";
        {
            for (int i = 0; i < planer.getHalbjahr((Integer)spinnerHalbjahr.getValue()).getFachByName(fachname).getAnzahlKlausur(); i++) {
                klausuren = klausuren + "; " + Integer.toString(planer.getHalbjahr((Integer)spinnerHalbjahr.getValue()).getFachByName(fachname).getKlausur(i).getNote());
            }
        }
        txfKlausur.setText(klausuren);
    }//GEN-LAST:event_btnSucheFachActionPerformed

    private void cbHalbjahreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbHalbjahreActionPerformed
        terminFaecherFuellen();
    }//GEN-LAST:event_cbHalbjahreActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("GTK+".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBearbeitenFach;
    private javax.swing.JButton btnBearbeitenTermin;
    private javax.swing.JButton btnHinzufuegenFach;
    private javax.swing.JButton btnHinzufuegenTermin;
    private javax.swing.JButton btnLoeschenFach;
    private javax.swing.JButton btnLoeschenTermin;
    private javax.swing.JButton btnStundeErstellen;
    private javax.swing.JButton btnStundeLoeschen;
    private javax.swing.JButton btnSucheFach;
    private javax.swing.JButton btnWocheWechseln;
    private javax.swing.ButtonGroup btngrpHalbjahr;
    private javax.swing.ButtonGroup btngrpJahrgang;
    private javax.swing.JComboBox cBoxFachNote;
    private javax.swing.JComboBox cBoxFachSp;
    private javax.swing.JComboBox cBoxTag;
    private javax.swing.JComboBox cbFach;
    private javax.swing.JComboBox<String> cbHalbjahre;
    private javax.swing.JCheckBox cbKlamemrbar;
    private javax.swing.JCheckBox checkBoxA;
    private javax.swing.JCheckBox checkBoxB;
    private javax.swing.JSeparator j1_2Separator1;
    private javax.swing.JSeparator j2_1Separator1;
    private javax.swing.JSeparator j2_2Separator1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblDatum;
    private javax.swing.JLabel lblFach;
    private javax.swing.JLabel lblFachKlausurJ12;
    private javax.swing.JLabel lblFachNoteJ12;
    private javax.swing.JLabel lblFachNoteJ21;
    private javax.swing.JLabel lblFachNoteJ22;
    private javax.swing.JLabel lblFachSet;
    private javax.swing.JLabel lblHalbjahrSet;
    private javax.swing.JLabel lblHalbjahrTerminDingens;
    private javax.swing.JLabel lblJ1_2Fach1;
    private javax.swing.JLabel lblJ1_2Lehrer1;
    private javax.swing.JLabel lblJ1_2NoteZeugnis1;
    private javax.swing.JLabel lblJ1_2NotenKA1;
    private javax.swing.JLabel lblJ1_2Wann1;
    private javax.swing.JLabel lblJ2_1Fach1;
    private javax.swing.JLabel lblJ2_1Lehrer1;
    private javax.swing.JLabel lblJ2_1NoteZeugnis1;
    private javax.swing.JLabel lblJ2_1NotenKA1;
    private javax.swing.JLabel lblJ2_1Wann1;
    private javax.swing.JLabel lblJ2_2Fach1;
    private javax.swing.JLabel lblJ2_2Lehrer1;
    private javax.swing.JLabel lblJ2_2NoteZeugnis1;
    private javax.swing.JLabel lblJ2_2NotenKA1;
    private javax.swing.JLabel lblJ2_2Wann1;
    private javax.swing.JLabel lblJahrgangstufe;
    private javax.swing.JLabel lblKlammerbar;
    private javax.swing.JLabel lblKlausurNoteJ21;
    private javax.swing.JLabel lblKlausurNoteJ22;
    private javax.swing.JLabel lblLehrerSet;
    private javax.swing.JLabel lblNotiz;
    private javax.swing.JLabel lblRaum;
    private javax.swing.JLabel lblTerminName;
    private javax.swing.JLabel lblTitelFach;
    private javax.swing.JLabel lblTitelTermin;
    private javax.swing.JLabel lblWoche;
    private javax.swing.JLabel lblZeugnisNoteJ12;
    private javax.swing.JLabel lblZeugnisNoteJ21;
    private javax.swing.JLabel lblZeugnisNoteJ22;
    private javax.swing.JLabel lblZeugnisSet;
    private javax.swing.JTabbedPane linkerFachPane;
    private javax.swing.JTabbedPane linkerPane;
    private javax.swing.JSeparator mitteFachSeparator;
    private javax.swing.JSeparator mitteTerminSeparator;
    private javax.swing.JPanel notenj11Panel;
    private javax.swing.JScrollPane notenj11ScrollPane;
    private javax.swing.JPanel notenj12Panel;
    private javax.swing.JScrollPane notenj12ScrollPane;
    private javax.swing.JPanel notenj21Panel;
    private javax.swing.JScrollPane notenj21ScrollPane;
    private javax.swing.JPanel notenj22Panel;
    private javax.swing.JScrollPane notenj22ScrollPane;
    private javax.swing.JScrollPane notizScrollPane;
    private javax.swing.JPanel paneFach;
    private javax.swing.JPanel paneFachJ1_2_1;
    private javax.swing.JPanel paneFachJ2_1_1;
    private javax.swing.JPanel paneFachJ2_2_1;
    private javax.swing.JPanel paneNotenuebersicht;
    private javax.swing.JPanel paneStundenplan;
    private javax.swing.JPanel paneTermine;
    private javax.swing.JPanel panelAllTermin;
    private javax.swing.JPanel panelAnstTermin;
    private javax.swing.JPanel panelJ1_1;
    private javax.swing.JPanel panelJ1_2;
    private javax.swing.JPanel panelJ2_1;
    private javax.swing.JPanel panelJ2_2;
    private javax.swing.JRadioButton rdbtnHalbjahr1;
    private javax.swing.JRadioButton rdbtnHalbjahr2;
    private javax.swing.JRadioButton rdbtnJ1;
    private javax.swing.JRadioButton rdbtnJ2;
    private javax.swing.JScrollPane scrollPaneAllTermin;
    private javax.swing.JScrollPane scrollPaneAnstTermin;
    private javax.swing.JScrollPane scrollPaneJ1_1;
    private javax.swing.JScrollPane scrollPaneJ1_2;
    private javax.swing.JScrollPane scrollPaneJ2_1;
    private javax.swing.JScrollPane scrollPaneJ2_2;
    private javax.swing.JSpinner spinnerDatum;
    private javax.swing.JSpinner spinnerHalbjahr;
    private javax.swing.JSpinner spinnerStunde;
    private javax.swing.JTable tableStundenplan;
    private javax.swing.JTextArea txaNotiz;
    private javax.swing.JTextField txfDurchschnitt;
    private javax.swing.JTextField txfErziehlt;
    private javax.swing.JTextField txfFach;
    private javax.swing.JTextField txfKlausur;
    private javax.swing.JTextField txfKlausurenJ1_2_1;
    private javax.swing.JTextField txfKlausurenJ2_1_1;
    private javax.swing.JTextField txfKlausurenJ2_2_1;
    private javax.swing.JTextField txfLehrer;
    private javax.swing.JTextField txfMoeglich;
    private javax.swing.JTextField txfName;
    private javax.swing.JTextField txfNote;
    private javax.swing.JTextField txfRaum;
    private javax.swing.JTextField txfZeugnis;
    private javax.swing.JTextField txfZeugnisJ1_2_1;
    private javax.swing.JTextField txfZeugnisJ2_1_1;
    private javax.swing.JTextField txfZeugnisJ2_2_1;
    private javax.swing.JTextField txfZeugnisSet;
    // End of variables declaration//GEN-END:variables
}
