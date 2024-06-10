/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package progettorubricaturingmod;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author simdr
 */
public final class FinestraPrincipale extends javax.swing.JFrame {

    private DefaultTableModel modelloTabella;

    private final ArrayList<Persona> persone = new ArrayList<>();

    private final String nomeFile = "informazioni.txt";

    public FinestraPrincipale() {
        initComponents();

        leggiCartellaInformazioni();

        popolaTabella();

        impostaImmaginiBottoni();
    }

    public void impostaImmaginiBottoni() {
        try {

            int width = 20;
            int heigh = 20;

            BufferedImage bufferedImage1 = ImageIO.read(new File("src/progettorubricaturingmod/plus.png"));
            Image image1 = bufferedImage1.getScaledInstance(width, heigh, Image.SCALE_DEFAULT);
            Icon icon1 = new ImageIcon(image1);
            bottoneNuovo.setIcon(icon1);

            BufferedImage bufferedImage2 = ImageIO.read(new File("src/progettorubricaturingmod/delete.png"));
            Image image2 = bufferedImage2.getScaledInstance(width, heigh, Image.SCALE_DEFAULT);
            Icon icon2 = new ImageIcon(image2);
            bottoneElimina.setIcon(icon2);

            BufferedImage bufferedImage3 = ImageIO.read(new File("src/progettorubricaturingmod/pen.png"));
            Image image3 = bufferedImage3.getScaledInstance(width, heigh, Image.SCALE_DEFAULT);
            Icon icon3 = new ImageIcon(image3);
            bottoneModifica.setIcon(icon3);

        } catch (IOException ex) {
            System.out.println("IOException");
        }
    }

    public void stampaArrayListPersone() {
        System.out.print("Arraylist contiene le seguenti persone: ");
        for (Persona persona : persone) {
            System.out.print(persona.getNome() + " ");
        }
        System.out.println();
    }

    public String ottieniRigaFilePersona(Persona p) {
        return p.getNome() + ";" + p.getCognome() + ";" + p.getIndirizzo() + ";" + p.getTelefono() + ";" + p.getEta();
    }

    public void aggiungiPersonaArrayList(Persona personaDaAggiungere) {

        boolean personaEsisteGia = false;

        for (Persona persona : persone) {
            if (persona.equals(personaDaAggiungere)) {
                personaEsisteGia = true;
                break;
            }

        }

        if (personaEsisteGia) {
            System.out.println("La persona " + personaDaAggiungere.getNome() + " " + personaDaAggiungere.getCognome() + " esiste già nell'ArrayList persone.");
        } else {
            System.out.println("La persona " + personaDaAggiungere.getNome() + " " + personaDaAggiungere.getCognome() + " è stata aggiunta nell'ArrayList persone.");

            persone.add(personaDaAggiungere);
        }

    }

    public void rimuoviPersonaArrayList(String nomeSelezionatoDaEliminare, String cognomeSelezionatoDaEliminare, String telefonoSelezionatoDaEliminare) {
        String nomeCorrente;
        String cognomeCorrente;
        String telefonoCorrente;
        Persona personaCorrente;
        for (Iterator<Persona> iterator = persone.iterator(); iterator.hasNext();) {
            personaCorrente = iterator.next();
            nomeCorrente = personaCorrente.getNome();
            cognomeCorrente = personaCorrente.getCognome();
            telefonoCorrente = personaCorrente.getTelefono();

            if (nomeCorrente.equals(nomeSelezionatoDaEliminare) && cognomeCorrente.equals(cognomeSelezionatoDaEliminare) && telefonoCorrente.equals(telefonoSelezionatoDaEliminare)) {
                iterator.remove();
                System.out.println("Persona eliminata da ArrayList.");
                break;
            }
        }

        stampaArrayListPersone();
    }

    public void modificaPersonaArrayList(Persona personaDaModificare, Persona nuovaPersona) {
        for (Persona persona : persone) {
            if (persona == personaDaModificare) {
                persone.remove(persona);
                persone.add(nuovaPersona);
                break;
            }

        }

        stampaArrayListPersone();

    }

    private void leggiFilePersonaCartellaInformazioni(File fileDaLeggere) {

        String riga;
        String splitBy = ";";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileDaLeggere));
            riga = reader.readLine();

            String[] valoriPersona = riga.split(splitBy);

            //Debug
            //System.out.println("Persona: [Nome=" + valoriPersona[0] + ", Cognome=" + valoriPersona[1] + ", Indirizzo=" + valoriPersona[2] + ", Telefono=" + valoriPersona[3] + ", Eta= " + valoriPersona[4] + "]");
            Persona p = new Persona(valoriPersona[0], valoriPersona[1], valoriPersona[2], valoriPersona[3], Integer.parseInt(valoriPersona[4]));

            aggiungiPersonaArrayList(p);

            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Il file " + nomeFile + " non esiste.");
        } catch (IOException e) {
            System.out.println("Qualcosa è andato storto.");
        }

    }

    private void leggiCartellaInformazioni() {

        try {
            final File folder = new File("informazioni/");

            for (final File fileEntry : folder.listFiles()) {
                //Debug
                //System.out.println("Leggo il file: "+ fileEntry.getName());

                leggiFilePersonaCartellaInformazioni(fileEntry);

            }
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: la cartella non esiste.");

            File cartellaInformazioni = new File("informazioni");
            if (!cartellaInformazioni.exists()) {
                cartellaInformazioni.mkdirs();
                System.out.println("Cartella "+ cartellaInformazioni.getName()+" è stata creata.");
            }

        }

    }

    void popolaTabella() {
        modelloTabella = new DefaultTableModel();
        tabellaRubrica.setModel(modelloTabella);
        modelloTabella.addColumn("Nome");
        modelloTabella.addColumn("Cognome");
        modelloTabella.addColumn("Telefono");

        for (Persona persona : persone) {
            modelloTabella.addRow(persona.getArrayValori());
        }

        stampaArrayListPersone();
    }

    public void aggiungiFilePersonaCartellaInformazioni(Persona p) {
        BufferedWriter writer;

        String nomeFilePersona = "informazioni/" + p.getNome() + "-" + p.getCognome() + "-" + p.getTelefono() + ".txt";
        File filePersonaDaAggiungere = new File(nomeFilePersona);

        try {
            if (filePersonaDaAggiungere.createNewFile()) {
                System.out.println("File creato: " + filePersonaDaAggiungere.getName());

                try {

                    writer = new BufferedWriter(new FileWriter(filePersonaDaAggiungere, true));

                    String nuovaLinea = ottieniRigaFilePersona(p);
                    writer.write(nuovaLinea + System.getProperty("line.separator"));

                    writer.close();

                    System.out.println("Persona aggiunta nel file " + nomeFilePersona);

                } catch (FileNotFoundException ex) {
                    System.out.println("Il file " + nomeFilePersona + " non esiste.");
                } catch (IOException ex) {
                    System.out.println("IOException.");
                }

            } else {
                System.out.println("Il File" + filePersonaDaAggiungere.getName() + " già esiste.");
            }
        } catch (IOException ex) {
            System.out.println("IOException nella creazione del file " + filePersonaDaAggiungere.getName());
        }

    }

    public void rimuoviModificaPersonaCartellaInformazioni(Persona personaDaModificare, Persona nuovaPersona, String nomeSelezionato, String cognomeSelezionato, String telefonoSelezionato) {

        if (personaDaModificare != null && nuovaPersona != null) {
            aggiungiFilePersonaCartellaInformazioni(nuovaPersona);

            String nomeFileVecchio = "informazioni/" + personaDaModificare.getNome() + "-" + personaDaModificare.getCognome() + "-" + personaDaModificare.getTelefono() + ".txt";

            File fileVecchio = new File(nomeFileVecchio);

            fileVecchio.delete();
        } else {
            String nomeFileDaEliminare = "informazioni/" + nomeSelezionato + "-" + cognomeSelezionato + "-" + telefonoSelezionato + ".txt";
            File fileDaEliminare = new File(nomeFileDaEliminare);
            fileDaEliminare.delete();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tabellaRubrica = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        bottoneNuovo = new javax.swing.JButton();
        bottoneModifica = new javax.swing.JButton();
        bottoneElimina = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Finestra Principale");
        setLocation(new java.awt.Point(0, 0));
        setMinimumSize(new java.awt.Dimension(200, 200));
        setName("finestraPrincipale"); // NOI18N

        jScrollPane1.setBorder(null);

        tabellaRubrica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabellaRubrica.setPreferredSize(new java.awt.Dimension(800, 800));
        jScrollPane1.setViewportView(tabellaRubrica);

        jToolBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToolBar1.setRollover(true);

        bottoneNuovo.setText("Nuovo");
        bottoneNuovo.setBorder(null);
        bottoneNuovo.setMaximumSize(new java.awt.Dimension(57, 24));
        bottoneNuovo.setMinimumSize(new java.awt.Dimension(57, 24));
        bottoneNuovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bottoneNuovoActionPerformed(evt);
            }
        });
        jToolBar1.add(bottoneNuovo);

        bottoneModifica.setText("Modifica");
        bottoneModifica.setBorder(null);
        bottoneModifica.setMaximumSize(new java.awt.Dimension(57, 24));
        bottoneModifica.setMinimumSize(new java.awt.Dimension(57, 24));
        bottoneModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bottoneModificaActionPerformed(evt);
            }
        });
        jToolBar1.add(bottoneModifica);

        bottoneElimina.setText("Elimina");
        bottoneElimina.setBorder(null);
        bottoneElimina.setMaximumSize(new java.awt.Dimension(57, 24));
        bottoneElimina.setMinimumSize(new java.awt.Dimension(57, 24));
        bottoneElimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bottoneEliminaActionPerformed(evt);
            }
        });
        jToolBar1.add(bottoneElimina);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bottoneNuovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bottoneNuovoActionPerformed

        FinestraEditorPersona finestraEditorPersona = new FinestraEditorPersona(this, null);
        finestraEditorPersona.setVisible(true);
    }//GEN-LAST:event_bottoneNuovoActionPerformed

    private void bottoneModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bottoneModificaActionPerformed

        Persona personaDaModificare = null;

        if (tabellaRubrica.getSelectionModel().isSelectionEmpty()) {
            System.out.println("Nessuna riga selezionata.");
            JOptionPane.showMessageDialog(this, "Seleziona prima una persona nella tabella.", "Errore", HEIGHT);
        } else {
            for (Persona persona : persone) {
                if (persona.getNome().equals(tabellaRubrica.getModel().getValueAt(tabellaRubrica.getSelectedRow(), 0).toString()) && persona.getCognome().equals(tabellaRubrica.getModel().getValueAt(tabellaRubrica.getSelectedRow(), 1).toString()) && persona.getTelefono().equals(tabellaRubrica.getModel().getValueAt(tabellaRubrica.getSelectedRow(), 2).toString())) {
                    personaDaModificare = persona;
                }
            }
            FinestraEditorPersona finestraEditorPersona = new FinestraEditorPersona(this, personaDaModificare);

            finestraEditorPersona.setVisible(true);

        }
    }//GEN-LAST:event_bottoneModificaActionPerformed

    private void bottoneEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bottoneEliminaActionPerformed

        if (tabellaRubrica.getSelectionModel().isSelectionEmpty()) {
            System.out.println("Nessuna riga selezionata.");
            JOptionPane.showMessageDialog(this, "Seleziona prima una persona nella tabella.", "Errore", HEIGHT);
        } else {
            String nomeSelezionato = tabellaRubrica.getModel().getValueAt(tabellaRubrica.getSelectedRow(), 0).toString();
            String cognomeSelezionato = tabellaRubrica.getModel().getValueAt(tabellaRubrica.getSelectedRow(), 1).toString();
            String telefonoSelezionato = tabellaRubrica.getModel().getValueAt(tabellaRubrica.getSelectedRow(), 2).toString();

            int ritornoFinestraDialog = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler eliminare la persona " + nomeSelezionato + " " + cognomeSelezionato + "?", "Conferma", HEIGHT);

            //Se l'utente conferma l'eliminazione
            if (ritornoFinestraDialog == JOptionPane.YES_NO_OPTION) {

                //Si rimuove la riga dalla tabella JTable
                ((DefaultTableModel) tabellaRubrica.getModel()).removeRow(tabellaRubrica.getSelectedRow());

                //Si rimuovere la persona dall'ArrayList
                rimuoviPersonaArrayList(nomeSelezionato, cognomeSelezionato, telefonoSelezionato);

                //Si rimuove la persona dal file informazioni.txt
                //rimuoviModificaPersonaFileInformazioni(null, null, nomeSelezionato, cognomeSelezionato, telefonoSelezionato);
                rimuoviModificaPersonaCartellaInformazioni(null, null, nomeSelezionato, cognomeSelezionato, telefonoSelezionato);
            }

        }

    }//GEN-LAST:event_bottoneEliminaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bottoneElimina;
    private javax.swing.JButton bottoneModifica;
    private javax.swing.JButton bottoneNuovo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tabellaRubrica;
    // End of variables declaration//GEN-END:variables

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FinestraLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FinestraLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FinestraLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FinestraLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FinestraPrincipale().setVisible(true);
            }
        });
    }

}
