package proyectofinal2023;

import java.awt.Color;
import ConexionDB.ConexionDB;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Milton Paz
 */
public class CobrosFacturas extends javax.swing.JInternalFrame {

    String usuario, sentenciaSQL;
    Connection con = null;
    ConexionDB conecta;
    PreparedStatement ps = null;
    ImageIcon icono;
    ResultSet rs = null;
    DefaultTableModel modelo;
    Object[] datosCobros = new Object[4];
    String textSeleccionIsv = "SELECCIONE ISV";

    public CobrosFacturas() {
        initComponents();
        txtDni.setText("0");
        txtIdFactura.setText("0");
        txtPaciente.requestFocus();
    }

    public void conectarBD() {
        conecta = new ConexionDB("clinica_mateo");
        con = conecta.getConexion();
    }

    public void Limpiar() {
        txtDni.setText("0");
        txtPaciente.setText("");
        txtIdFactura.setText("0");
        txtPaciente.requestFocus();
        LimpiarTable();
    }

    private void LimpiarTable() {
        int fila = tbCobros.getRowCount();
        for (int i = fila - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }


    public void LeerCobros() {
        conectarBD();
        sentenciaSQL = "SELECT factura.IdFactura, pacientes.DNI, pacientes.Nombre, factura.Total  FROM factura "
                +"INNER JOIN pacientes on factura.Paciente = pacientes.Nombre "
                +"WHERE factura.Pagado = 0 AND factura.Estado = 'activo'";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            modelo = (DefaultTableModel) tbCobros.getModel();
            while (rs.next()) {
                datosCobros[0] = (rs.getInt(1));
                datosCobros[1] = (rs.getString(2));
                datosCobros[2] = (rs.getString(3));
                datosCobros[3] = (rs.getDouble(4));
                modelo.addRow(datosCobros);
            }
            tbCobros.setModel(modelo);
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR NO SE PUDIERON LEER LOS DATOS " + ex.getMessage());
        }
    }


    public void EliminarCobros() {
        try {
            conectarBD();
            sentenciaSQL = "UPDATE factura SET Estado='Inactivo' WHERE idFactura=" + txtIdFactura.getText().trim();
            ps = con.prepareStatement(sentenciaSQL);
            ps.execute();
            JOptionPane.showMessageDialog(null, "DATOS ELIMINADOS CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO ELIMINAR LOS DATOS " + ex.getMessage());
        }
    }
    
    public void RealizarCobros() {
        try {
            conectarBD();
            sentenciaSQL = "UPDATE factura SET Pagado="+true+" WHERE idFactura=" + txtIdFactura.getText().trim();
            ps = con.prepareStatement(sentenciaSQL);
            ps.execute();
            JOptionPane.showMessageDialog(null, "COBRO REALIZADO CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO REALIZAR EL COBRO " + ex.getMessage());
        }
    }

    public void BuscarCobro(String search) {
        conectarBD();
        sentenciaSQL = "SELECT factura.IdFactura, pacientes.DNI, pacientes.Nombre, factura.Total  FROM factura "
                +"INNER JOIN pacientes on factura.Paciente = pacientes.Nombre "
                +"WHERE (factura.idFactura = '" + search + "' OR pacientes.Nombre LIKE '%" + search + "%' OR pacientes.DNI LIKE '%" + search + "%') "
                +"AND factura.Pagado = 0 AND factura.Estado = 'activo'";
        
        System.out.print(sentenciaSQL);
        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            modelo = (DefaultTableModel) tbCobros.getModel();
            while (rs.next()) {
                datosCobros[0] = (rs.getInt(1));
                datosCobros[1] = (rs.getString(2));
                datosCobros[2] = (rs.getString(3));
                datosCobros[3] = (rs.getDouble(4));
                modelo.addRow(datosCobros);
            }

            tbCobros.setModel(modelo);
            con.close();
            int rowCount = tbCobros.getRowCount();
            if (rowCount <= 0) {
                JOptionPane.showMessageDialog(null, "NO SE ENCONTRARON DATOS",
                        "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO LEER LOS DATOS " + ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnHOME = new javax.swing.JButton();
        txtIdFactura = new javax.swing.JTextField();
        txtPaciente = new javax.swing.JTextField();
        btnRealizarCobro = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbCobros = new javax.swing.JTable();
        btnBuscarIdFactura = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnBuscarPaciente = new javax.swing.JButton();
        btnBuscarDni = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnLeer = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        Fondo = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Script MT Bold", 1, 40)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Control de Cobros Facturas");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 600, 60));

        btnHOME.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/home.png"))); // NOI18N
        btnHOME.setBorder(null);
        btnHOME.setBorderPainted(false);
        btnHOME.setContentAreaFilled(false);
        btnHOME.setFocusPainted(false);
        btnHOME.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/home60.png"))); // NOI18N
        btnHOME.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHOMEMouseClicked(evt);
            }
        });
        jPanel1.add(btnHOME, new org.netbeans.lib.awtextra.AbsoluteConstraints(1023, 30, 60, 60));

        txtIdFactura.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel1.add(txtIdFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 160, -1));

        txtPaciente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel1.add(txtPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 160, 450, -1));

        btnRealizarCobro.setBackground(new java.awt.Color(255, 255, 204));
        btnRealizarCobro.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btnRealizarCobro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/factura.png"))); // NOI18N
        btnRealizarCobro.setText("REALIZAR COBRO");
        btnRealizarCobro.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, null, new java.awt.Color(0, 0, 0)));
        btnRealizarCobro.setBorderPainted(false);
        btnRealizarCobro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRealizarCobroMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRealizarCobroMouseExited(evt);
            }
        });
        btnRealizarCobro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRealizarCobroActionPerformed(evt);
            }
        });
        jPanel1.add(btnRealizarCobro, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 150, 170, 70));

        tbCobros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "# Factura", "Dni", "Paciente", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbCobros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCobrosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbCobros);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 850, 350));

        btnBuscarIdFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-14-24.png"))); // NOI18N
        btnBuscarIdFactura.setBorderPainted(false);
        btnBuscarIdFactura.setContentAreaFilled(false);
        btnBuscarIdFactura.setFocusPainted(false);
        btnBuscarIdFactura.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-15-24.png"))); // NOI18N
        btnBuscarIdFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarIdFacturaActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarIdFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 200, 40, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Dni:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 40, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Paciente:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 160, 70, -1));

        txtDni.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel1.add(txtDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 170, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Seleccione la factura que desea realizar el cobro");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 250, 390, -1));

        btnBuscarPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-14-24.png"))); // NOI18N
        btnBuscarPaciente.setBorderPainted(false);
        btnBuscarPaciente.setContentAreaFilled(false);
        btnBuscarPaciente.setFocusPainted(false);
        btnBuscarPaciente.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-15-24.png"))); // NOI18N
        btnBuscarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPacienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 150, 40, 30));

        btnBuscarDni.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-14-24.png"))); // NOI18N
        btnBuscarDni.setBorderPainted(false);
        btnBuscarDni.setContentAreaFilled(false);
        btnBuscarDni.setFocusPainted(false);
        btnBuscarDni.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-15-24.png"))); // NOI18N
        btnBuscarDni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarDniActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 40, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("#Factura:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 70, -1));

        btnLeer.setBackground(new java.awt.Color(255, 255, 204));
        btnLeer.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btnLeer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/leer.png"))); // NOI18N
        btnLeer.setText("         LEER");
        btnLeer.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, null, new java.awt.Color(0, 0, 0)));
        btnLeer.setBorderPainted(false);
        btnLeer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLeerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLeerMouseExited(evt);
            }
        });
        btnLeer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeerActionPerformed(evt);
            }
        });
        jPanel1.add(btnLeer, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 250, 150, 70));

        btnEliminar.setBackground(new java.awt.Color(255, 255, 204));
        btnEliminar.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, null, new java.awt.Color(0, 0, 0)));
        btnEliminar.setBorderPainted(false);
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarMouseExited(evt);
            }
        });
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 350, 150, 70));

        btnLimpiar.setBackground(new java.awt.Color(255, 255, 204));
        btnLimpiar.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/limpiar.png"))); // NOI18N
        btnLimpiar.setText("   LIMPIAR");
        btnLimpiar.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, null, new java.awt.Color(0, 0, 0)));
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseExited(evt);
            }
        });
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 450, 150, 70));

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo1.png"))); // NOI18N
        jPanel1.add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 5, 1110, 670));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHOMEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHOMEMouseClicked
        dispose();
    }//GEN-LAST:event_btnHOMEMouseClicked

    private void btnRealizarCobroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRealizarCobroMouseEntered
        this.btnRealizarCobro.setBackground(new Color(153, 255, 255));
    }//GEN-LAST:event_btnRealizarCobroMouseEntered

    private void btnRealizarCobroMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRealizarCobroMouseExited
        this.btnRealizarCobro.setBackground(new Color(255, 255, 204));
    }//GEN-LAST:event_btnRealizarCobroMouseExited

    private void btnRealizarCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRealizarCobroActionPerformed
        if (txtIdFactura.getText().isEmpty() || txtIdFactura.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "FAVOR SELECCIONE REGISTRO A REALIZAR COBRO",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int resultDialog = JOptionPane.showConfirmDialog(null, "¿SEGURO QUE DESEAS REALIZAR EL COBRO?", "CONFIRMACION", JOptionPane.YES_NO_OPTION);
        if (resultDialog == JOptionPane.YES_OPTION) {
            RealizarCobros();
            Limpiar();
            LeerCobros();
        }
    }//GEN-LAST:event_btnRealizarCobroActionPerformed

    private void btnBuscarIdFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarIdFacturaActionPerformed
        String id = this.txtIdFactura.getText();
        if (id.equals("0") || id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "FAVOR DIGITE EL DATO A BUSCAR",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtIdFactura.requestFocus();
            return;
        }
        LimpiarTable();
        BuscarCobro(id);
    }//GEN-LAST:event_btnBuscarIdFacturaActionPerformed

    private void tbCobrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCobrosMouseClicked
        int fila = tbCobros.getSelectedRow();
        txtDni.setText(tbCobros.getValueAt(fila, 1).toString());
        txtPaciente.setText(tbCobros.getValueAt(fila, 2).toString());
        txtIdFactura.setText(tbCobros.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_tbCobrosMouseClicked

    private void btnBuscarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPacienteActionPerformed
        String paciente = this.txtPaciente.getText();
        if (paciente.isEmpty()) {
            JOptionPane.showMessageDialog(null, "FAVOR DIGITE EL DATO A BUSCAR",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtPaciente.requestFocus();
            return;
        }
        LimpiarTable();
        BuscarCobro(paciente);
    }//GEN-LAST:event_btnBuscarPacienteActionPerformed

    private void btnBuscarDniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarDniActionPerformed
        String dni = this.txtDni.getText();
        if (dni.equals("0") || dni.isEmpty()) {
            JOptionPane.showMessageDialog(null, "FAVOR DIGITE EL DATO A BUSCAR",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtDni.requestFocus();
            return;
        }
        LimpiarTable();
        BuscarCobro(dni);
    }//GEN-LAST:event_btnBuscarDniActionPerformed

    private void btnLeerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLeerMouseEntered
        this.btnLeer.setBackground(new Color(153, 255, 255));
    }//GEN-LAST:event_btnLeerMouseEntered

    private void btnLeerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLeerMouseExited
        this.btnLeer.setBackground(new Color(255, 255, 204));
    }//GEN-LAST:event_btnLeerMouseExited

    private void btnLeerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeerActionPerformed
        this.Limpiar();
        this.LeerCobros();
    }//GEN-LAST:event_btnLeerActionPerformed

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        this.btnRealizarCobro.setBackground(new Color(153, 255, 255));
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        this.btnRealizarCobro.setBackground(new Color(255, 255, 204));
    }//GEN-LAST:event_btnEliminarMouseExited

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (txtIdFactura.getText().isEmpty() || txtIdFactura.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "FAVOR SELECCIONE REGISTRO A ELIMINAR",
                "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int resultDialog = JOptionPane.showConfirmDialog(null, "¿SEGURO QUE DESEAS ELIMINAR ESTE REGISTRO?", "CONFIRMACION", JOptionPane.YES_NO_OPTION);
        if (resultDialog == JOptionPane.YES_OPTION) {
            EliminarCobros();
            Limpiar();
            LeerCobros();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseEntered
        this.btnLimpiar.setBackground(new Color(153, 255, 255));
    }//GEN-LAST:event_btnLimpiarMouseEntered

    private void btnLimpiarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseExited
        this.btnLimpiar.setBackground(new Color(255, 255, 204));
    }//GEN-LAST:event_btnLimpiarMouseExited

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        this.Limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton btnBuscarDni;
    private javax.swing.JButton btnBuscarIdFactura;
    private javax.swing.JButton btnBuscarPaciente;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnHOME;
    private javax.swing.JButton btnLeer;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRealizarCobro;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbCobros;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtIdFactura;
    private javax.swing.JTextField txtPaciente;
    // End of variables declaration//GEN-END:variables
}
