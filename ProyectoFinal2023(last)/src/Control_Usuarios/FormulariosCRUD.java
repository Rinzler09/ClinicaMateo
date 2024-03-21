package Control_Usuarios;

import proyectofinal2023.*;
import java.awt.Color;
import ConexionDB.ConexionDB;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ana Jimenez
 */
public class FormulariosCRUD extends javax.swing.JInternalFrame {

    String usuario, sentenciaSQL;
    Connection con = null;
    ConexionDB conecta;
    PreparedStatement ps = null;
    ImageIcon icono;
    ResultSet rs = null;
    DefaultTableModel modelo;
    Object datosDoctor[] = new Object[4];

    public FormulariosCRUD() {
        initComponents();
        txtID.setText("0");
        txtEstado.requestFocus();
    }

    public void conectarBD() {
        conecta = new ConexionDB("clinica_mateo");
        con = conecta.getConexion();
    }

    public void limpiar() {
        txtID.setText("0");
        txtEstado.setText(null);
        txtNombre.setText(null);
        txtNombre.requestFocus();
        int fila = tbFormularios.getRowCount();

        for (int i = fila - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }

    public void crear() {
        try {
            conectarBD();
            sentenciaSQL = "INSERT INTO formularios (id_formulario, nombre_formulario,estado) VALUES(?,?,?)";
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, 0);
            ps.setString(2, txtNombre.getText());
            ps.setString(3, "Activo");
            ps.execute();
            JOptionPane.showMessageDialog(null, "DATOS INGRESADOS CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR NO SE PUDO INGRESAR DATOS " + ex.getMessage());
        }

    }

    public void leer() {
        conectarBD();
        sentenciaSQL = "SELECT * FROM formularios WHERE estado LIKE 'Activo'";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            modelo = (DefaultTableModel) tbFormularios.getModel();
            while (rs.next()) {
                datosDoctor[0] = (rs.getInt(1));
                datosDoctor[1] = (rs.getString(2));
                datosDoctor[2] = (rs.getString(3));
                modelo.addRow(datosDoctor);
            }
            tbFormularios.setModel(modelo);
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR NO SE PUDIERON LEER LOS DATOS " + ex.getMessage());
        }
    }

    public void actualizar() {

        try {
            conectarBD();
            sentenciaSQL = "UPDATE formularios SET nombre_formulario=? WHERE id_formulario=?";
            ps = con.prepareStatement(sentenciaSQL);

            ps.setString(1, txtNombre.getText());
            ps.setInt(2, Integer.parseInt(txtID.getText()));
            ps.execute();
            JOptionPane.showMessageDialog(null, "DATOS ACTUALIZADOS CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR NO SE PUDIERON ACTUALIZAR LOS DATOS " + ex.getMessage());
        }

    }

    public void eliminar() {
        try {
            conectarBD();
            sentenciaSQL = "UPDATE formularios SET estado='Inactivo' WHERE id_formulario=" + txtID.getText().trim();
            ps = con.prepareStatement(sentenciaSQL);
            ps.execute();
            JOptionPane.showMessageDialog(null, "DATOS ELIMINADOS CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO ELIMINAR LOS DATOS " + ex.getMessage());
        }
    }

    public void Buscar() {
        conectarBD();
        int cod;
        String nombre, estado;

        try {

            cod = Integer.parseInt(txtID.getText());
            sentenciaSQL = "SELECT * FROM formularios WHERE id_formulario = '" + cod + "'";
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();

            if (rs.next()) {
                cod = rs.getInt("id_formulario");
                nombre = rs.getString("nombre_formulario");
                estado = rs.getString("estado");

                txtID.setText("" + cod);
                txtNombre.setText(nombre);
                txtEstado.setText(estado);

            } else {
                JOptionPane.showMessageDialog(null, "REGISTROS NO ENCONTRADOS", "ERROR", JOptionPane.ERROR_MESSAGE);
                this.txtID.setText("");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnHOME = new javax.swing.JButton();
        txtID = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        btnCrear = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnLeer = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbFormularios = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        Fondo = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Script MT Bold", 1, 40)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Control de Formularios");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 450, 60));

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

        txtID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIDKeyTyped(evt);
            }
        });
        jPanel1.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 180, -1));

        txtEstado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEstado.setText("Activo");
        txtEstado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstadoKeyTyped(evt);
            }
        });
        jPanel1.add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 160, 180, -1));

        btnCrear.setBackground(new java.awt.Color(255, 255, 204));
        btnCrear.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/crear.png"))); // NOI18N
        btnCrear.setText("       CREAR");
        btnCrear.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, null, new java.awt.Color(0, 0, 0)));
        btnCrear.setBorderPainted(false);
        btnCrear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCrearMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCrearMouseExited(evt);
            }
        });
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        jPanel1.add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 140, 150, 70));

        btnActualizar.setBackground(new java.awt.Color(255, 255, 204));
        btnActualizar.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, null, new java.awt.Color(0, 0, 0)));
        btnActualizar.setBorderPainted(false);
        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnActualizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnActualizarMouseExited(evt);
            }
        });
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 250, 150, 70));

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
        jPanel1.add(btnLeer, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 360, 150, 70));

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
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 470, 150, 70));

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
        jPanel1.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 570, 150, 70));

        tbFormularios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id. Formulario", "Nombre Formulario", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbFormularios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFormulariosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbFormularios);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 860, 300));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Id. Formulario:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 110, -1));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-14-24.png"))); // NOI18N
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-15-24.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, 40, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Nombre formulario:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 140, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Estado:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 60, 20));

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 180, -1));

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

    private void btnCrearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearMouseEntered
        this.btnCrear.setBackground(new Color(153, 255, 255));
    }//GEN-LAST:event_btnCrearMouseEntered

    private void btnCrearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearMouseExited
        this.btnCrear.setBackground(new Color(255, 255, 204));
    }//GEN-LAST:event_btnCrearMouseExited

    private void btnActualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseEntered
        this.btnActualizar.setBackground(new Color(153, 255, 255));
    }//GEN-LAST:event_btnActualizarMouseEntered

    private void btnActualizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseExited
        this.btnActualizar.setBackground(new Color(255, 255, 204));
    }//GEN-LAST:event_btnActualizarMouseExited

    private void btnLeerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLeerMouseEntered
        this.btnLeer.setBackground(new Color(153, 255, 255));
    }//GEN-LAST:event_btnLeerMouseEntered

    private void btnLeerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLeerMouseExited
        this.btnLeer.setBackground(new Color(255, 255, 204));
    }//GEN-LAST:event_btnLeerMouseExited

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        this.btnEliminar.setBackground(new Color(153, 255, 255));
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        this.btnEliminar.setBackground(new Color(255, 255, 204));
    }//GEN-LAST:event_btnEliminarMouseExited

    private void btnLimpiarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseEntered
        this.btnLimpiar.setBackground(new Color(153, 255, 255));
    }//GEN-LAST:event_btnLimpiarMouseEntered

    private void btnLimpiarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseExited
        this.btnLimpiar.setBackground(new Color(255, 255, 204));
    }//GEN-LAST:event_btnLimpiarMouseExited

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        this.crear();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        this.actualizar();
        this.limpiar();
        this.leer();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnLeerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeerActionPerformed
        this.leer();
    }//GEN-LAST:event_btnLeerActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        this.eliminar();
        this.limpiar();
        this.leer();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        this.limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        this.Buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tbFormulariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFormulariosMouseClicked
        int fila = tbFormularios.getSelectedRow();

        txtID.setText(tbFormularios.getValueAt(fila, 0).toString());
        txtNombre.setText(tbFormularios.getValueAt(fila, 1).toString());
        txtEstado.setText(tbFormularios.getValueAt(fila, 2).toString());
 
    }//GEN-LAST:event_tbFormulariosMouseClicked

    private void txtIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyTyped
        char x = evt.getKeyChar();
        if (x < '0' || x > '9') {
            evt.consume();
        }
        int limite = 5;
        {
            if (txtID.getText().length() == limite) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtIDKeyTyped

    private void txtEstadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoKeyTyped
        int limite = 30;
        {
            if (txtEstado.getText().length() == limite) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtEstadoKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnHOME;
    private javax.swing.JButton btnLeer;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbFormularios;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
