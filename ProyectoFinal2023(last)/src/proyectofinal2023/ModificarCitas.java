/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal2023;

import ConexionDB.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class ModificarCitas extends javax.swing.JInternalFrame {
    String sentenciaSQL;
    Connection con = null;
    //ConexionDB conecta;
    PreparedStatement ps = null; //hace un plan de ejecucion 
    ImageIcon icono;
    ResultSet rs = null;
    DefaultTableModel modelo;
    Object datosCitas[] = new Object[8];
    String txtidpaci, txtidmed, jtxtservcod, jtxtFecha,txtcodcitas,txtcoding;
    private ConexionDB conecta;
    
    
     public void conectarBD() {
        conecta = new ConexionDB("clinica_mateo");
        con = conecta.getConexion();
        
      
         
    }


    public void CrearCitas() {

        try {
            //conectarBD();

            sentenciaSQL = "INSERT INTO citas (idpaciente,idmedico,codservicio,fecha,codcitas) VALUES (?, ?, ?, ?, ?, ?, ?, ? ,?)";
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, 0);
            ps.setString(2, jtidpaci.getText());
            ps.setString(3, jtidmed.getText());
            ps.setString(4, jtcodser.getText());
            ps.setString(5, jtfecha.getText());
            ps.setString(6, txtcodcita.getText());
            ps.setString(7, jtincod.getText());
            ps.setString(8, "Activo");
            ps.execute();
            JOptionPane.showConfirmDialog(null, "Los datos se han guardado correctamente");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, "ERROR! Los datos no se pueden ingresar!!!" + ex.getMessage());
        }
    }

 public void actualizarCitas() {

        try {

            //conectarBD();

            sentenciaSQL = "UPDATE pacientes SET txtidpaciente=?, txtidmedico=?, txtcodservicio=?, fecha=?  WHERE codcitas=?";

            ps = con.prepareStatement(sentenciaSQL);

            ps.setString(1, jtidpaci.getText());
            ps.setString(2, jtidmed.getText());
            ps.setString(3, jtcodser.getText());
            ps.setString(4, jtfecha.getText());
            ps.setString(5, txtcodcita.getText());
          
            ps.setInt(5, Integer.parseInt(jtidpaci.getText()));

            ps.execute();

            JOptionPane.showMessageDialog(null, "Los datos se actualizaron corectamente!");

            con.close();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "ERROR!!, los datos no se actualizaron" + ex.getMessage());

        }

    }

    private void BuscarPaciente(String busqueda) {
        // conectarBD();
        int fila = tablaDatos.getRowCount();
        for (int i = fila - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }

        //conectarBD();
       
        sentenciaSQL = "SELECT * FROM citas WHERE idpaciente = '" + busqueda + "' OR Nombre LIKE '%" + busqueda + "%'";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            modelo = (DefaultTableModel) tablaDatos.getModel();

            while (rs.next()) {
            

                datosCitas[0] = (rs.getInt(1));
                datosCitas[1] = (rs.getString(2));
                datosCitas[2] = (rs.getString(3));
                datosCitas[3] = (rs.getString(4));
                datosCitas[4] = (rs.getString(5));
                datosCitas[5] = (rs.getString(6));
               

                modelo.addRow(datosCitas);
            }
            JOptionPane.showMessageDialog(null, "Los datos se encontraron correctamente!");
            tablaDatos.setModel(modelo);
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, no se pueden leer los datos!" + ex.getMessage() + sentenciaSQL);
        }

        jtidpaci.setText("0");
        jtidmed.setText(null);
        jtcodser.setText(null);
        jtfecha.setText(null);
        txtcodcita.setText(null);
        jtincod.setText(null);
        
    }

    private void limpiar() {
        jtidpaci.setText("0");
        jtidmed.setText(null);
        jtcodser.setText(null);
        jtfecha.setText(null);
        txtcodcita.setText(null);
        jtincod.setText(null);
        

        jtidpaci.requestFocus();
        int fila = tablaDatos.getRowCount();
        for (int i = fila - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }

    private void eliminar() {
       // conectarBD();
        try {

            sentenciaSQL = "DELETE FROM citas WHERE PacientesID=" + jtidmed.getText().trim();
            
            ps = con.prepareStatement(sentenciaSQL);
            ps.execute();
            JOptionPane.showMessageDialog(null, "DATOS ELIMINADOS CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO ELIMINAR LOS DATOS " + ex.getMessage());

        }

    }

   public void HabilitarBNTCrear() {

      if (jtidpaci.getText().length() == 0 || jtidmed.getText().length() == 0 || jtidmed.getText().length() == 0 || jtfecha.getText().length() == 0 || txtcodcita.getText().length() ==0) {
            this.btnmodificar.setEnabled(false);

       } else if (jtidpaci.getText().length() != 0 || jtidmed.getText().length() == 0 || jtcodser.getText().length() == 0 || jtfecha.getText().length() == 0 || txtcodcita.getText().length() ==0) {
            this.btnmodificar.setEnabled(true);
        }
    }

    private void desactivar() {
        this.jtidpaci.enable(false);
        this.jtidmed.enable(false);
        this.jtcodser.enable(false);
        this.jtfecha.enable(false);
        this.txtcodcita.enable(false);
        this.jtincod.enable(false);
        

        this.btnBuscarCod.setEnabled(false);
        this.btnmodificar.setEnabled(false);
        this.btneliminar.setEnabled(false);
        this.btnregresar.setEnabled(false);

    }

    private void activar() {
        this.jtidpaci.enable(true);
        this.jtidmed.enable(true);
        this.jtcodser.enable(true);
        this.jtfecha.enable(true);
        this.txtcodcita.enable(true);
        this.jtincod.enable(true);
        

        this.btnBuscarCod.setEnabled(true);
        this.btnmodificar.setEnabled(true);
        this.btneliminar.setEnabled(true);
        this.btnregresar.setEnabled(true);

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtcodcita = new javax.swing.JTextField();
        jtfecha = new javax.swing.JTextField();
        jtcodser = new javax.swing.JTextField();
        jtidmed = new javax.swing.JTextField();
        jtidpaci = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnBuscarCod = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        btnmodificar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        btnregresar = new javax.swing.JButton();
        btnlimpiar = new javax.swing.JButton();
        jtincod = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondomenuprincipal.png"))); // NOI18N

        jFormattedTextField2.setText("jFormattedTextField2");

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(7, 7, 7, 7, new java.awt.Color(102, 102, 102)));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(7, 7, 7, 7, new java.awt.Color(102, 102, 102)));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logologo.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Script MT Bold", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Citas");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(222, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel2))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 750, 160);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Citas", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Symbol", 1, 14))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI Symbol", 1, 12)); // NOI18N
        jLabel4.setText("ID PACIENTE");

        jLabel5.setFont(new java.awt.Font("Segoe UI Symbol", 1, 12)); // NOI18N
        jLabel5.setText("ID MEDICO");

        jLabel6.setFont(new java.awt.Font("Segoe UI Symbol", 1, 12)); // NOI18N
        jLabel6.setText("COD SERVICIO");

        jLabel7.setFont(new java.awt.Font("Segoe UI Symbol", 1, 12)); // NOI18N
        jLabel7.setText("FECHA");

        jLabel8.setFont(new java.awt.Font("Segoe UI Symbol", 1, 12)); // NOI18N
        jLabel8.setText("COD CITA");

        jtidmed.setText("jTextField1");

        jtidpaci.setText("jTextField2");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtidpaci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtidmed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                        .addComponent(jLabel8)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcodcita, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtcodser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jtcodser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtidmed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtidpaci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(txtcodcita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4);
        jPanel4.setBounds(20, 170, 710, 150);

        jLabel9.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        jLabel9.setText("Ingresar Codigo");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(30, 370, 130, 30);

        btnBuscarCod.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        btnBuscarCod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.png"))); // NOI18N
        btnBuscarCod.setText("Buscar\n");
        btnBuscarCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCodActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarCod);
        btnBuscarCod.setBounds(40, 410, 120, 28);

        tablaDatos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tablaDatos.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "CodCitas", "IdPacientes", "IdDoctor", "CodServicio", "Fecha"
            }
        ));
        jScrollPane1.setViewportView(tablaDatos);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(260, 340, 470, 402);

        btnmodificar.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        btnmodificar.setText("Modificar");
        btnmodificar.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(51, 51, 51)));
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnmodificar);
        btnmodificar.setBounds(50, 510, 100, 30);

        btneliminar.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        btneliminar.setText("Eliminar");
        btneliminar.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(51, 51, 51)));
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btneliminar);
        btneliminar.setBounds(50, 570, 100, 30);

        btnregresar.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        btnregresar.setText("Regresar");
        btnregresar.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(51, 51, 51)));
        btnregresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregresarActionPerformed(evt);
            }
        });
        jPanel1.add(btnregresar);
        btnregresar.setBounds(50, 630, 100, 30);

        btnlimpiar.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        btnlimpiar.setText("Limpiar");
        btnlimpiar.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        btnlimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiarActionPerformed(evt);
            }
        });
        jPanel1.add(btnlimpiar);
        btnlimpiar.setBounds(50, 690, 100, 30);
        jPanel1.add(jtincod);
        jtincod.setBounds(150, 370, 80, 29);

        jButton1.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        jButton1.setText("Crear");
        jButton1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(50, 460, 100, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCodActionPerformed
       btnBuscarCod(jtidpaci.getText()); 
    }//GEN-LAST:event_btnBuscarCodActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
         eliminar();
         limpiar(); 
    }//GEN-LAST:event_btnmodificarActionPerformed

    private void btnregresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregresarActionPerformed
       actualizarcitas();
        limpiar(); // TODO add your handling code here:
    }//GEN-LAST:event_btnregresarActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        eliminar();
         limpiar();// TODO add your handling code here:
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btnlimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiarActionPerformed
         
         limpiar(); // TODO add your handling code here:
    }//GEN-LAST:event_btnlimpiarActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       CrearCitas();
        limpiar(); // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarCod;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnlimpiar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JButton btnregresar;
    private javax.swing.JButton jButton1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtcodser;
    private javax.swing.JTextField jtfecha;
    private javax.swing.JTextField jtidmed;
    private javax.swing.JTextField jtidpaci;
    private javax.swing.JTextField jtincod;
    private javax.swing.JTable tablaDatos;
    private javax.swing.JTextField txtcodcita;
    // End of variables declaration//GEN-END:variables

    private void btnBuscarCod(String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void actualizarpacientes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void actualizarcitas() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
