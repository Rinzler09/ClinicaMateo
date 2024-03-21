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
public class PermisosCRUD extends javax.swing.JInternalFrame {

    String usuario, sentenciaSQL;
    Connection con = null;
    ConexionDB conecta;
    PreparedStatement ps = null;
    ImageIcon icono;
    ResultSet rs = null;
    DefaultTableModel modelo;
    Object datosDoctor[] = new Object[5];
    String textSeleccionUsuario = "SELECCIONE EL USUARIO";
    String textSeleccionFormulario = "SELECCIONE EL FORMULARIO";

    public PermisosCRUD() {
        initComponents();
        txtId.setText("0");
        txtEstado.requestFocus();
        this.CargarUsuario();
        this.CargarFormulario();
    }

    public void conectarBD() {
        conecta = new ConexionDB("clinica_mateo");
        con = conecta.getConexion();
    }

    public void CargarUsuario() {
        try {
            conectarBD();
            sentenciaSQL = "SELECT nombre_usuario FROM usuarios WHERE Estado LIKE 'Activo'";
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            cboUsuario.addItem(textSeleccionUsuario);
            while (rs.next()) {
                String tmpStrObtenido = rs.getString("nombre_usuario");
                cboUsuario.addItem(tmpStrObtenido);
            }
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO CARGA LISTA DE PACIENTES " + ex.getMessage());
        }
    }

    public void CargarFormulario() {
        try {
            conectarBD();
            sentenciaSQL = "SELECT nombre_formulario FROM formularios WHERE Estado LIKE 'Activo'";
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            cboFormulario.addItem(textSeleccionFormulario);
            while (rs.next()) {
                String tmpStrObtenido = rs.getString("nombre_formulario");
                cboFormulario.addItem(tmpStrObtenido);
            }
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO CARGA LISTA DE FORMULARIOS " + ex.getMessage());
        }
    }

    public void limpiar() {
        txtId.setText("0");
        txtEstado.setText(null);
        txtId.requestFocus();
        int fila = tblPermisos.getRowCount();

        for (int i = fila - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }

    public void crear() {
        String usuario, formulario;
        usuario = (String) cboUsuario.getSelectedItem();
        formulario = (String) cboFormulario.getSelectedItem();
        try {
            conectarBD();
            sentenciaSQL = "INSERT INTO permisos (id_permiso, usuario, formulario, estado) VALUES(?,?,?,?)";
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, 0);
            ps.setString(2, usuario);
            ps.setString(3, formulario);
            ps.setString(4, "Activo");
            ps.execute();
            JOptionPane.showMessageDialog(null, "DATOS INGRESADOS CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR NO SE PUDO INGRESAR DATOS " + ex.getMessage());
        }

    }

    public void leer() {
        conectarBD();
        sentenciaSQL = "SELECT id_permiso,usuario,formulario,estado FROM permisos WHERE estado LIKE 'Activo'";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            modelo = (DefaultTableModel) tblPermisos.getModel();
            while (rs.next()) {
                datosDoctor[0] = (rs.getInt(1));
                datosDoctor[1] = (rs.getString(2));
                datosDoctor[2] = (rs.getString(3));
                datosDoctor[3] = (rs.getString(4));
                modelo.addRow(datosDoctor);
            }
            tblPermisos.setModel(modelo);
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR NO SE PUDIERON LEER LOS DATOS " + ex.getMessage());
        }
    }

    public void actualizar() {
        String usuario, formulario;
        usuario = (String) cboUsuario.getSelectedItem();
        formulario = (String) cboFormulario.getSelectedItem();

        try {
            conectarBD();
            sentenciaSQL = "UPDATE permisos SET usuario=?, formulario=? WHERE id_permiso=?";
            ps = con.prepareStatement(sentenciaSQL);

            ps.setString(1, usuario);
            ps.setString(2, formulario);
            ps.setInt(3, Integer.parseInt(txtId.getText()));
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
            sentenciaSQL = "UPDATE permisos SET estado='Inactivo' WHERE id_permiso=" + txtId.getText().trim();
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
        String nombre, usuario, formulario, estado;

        try {

            cod = Integer.parseInt(txtId.getText());
            sentenciaSQL = "SELECT * FROM permisos WHERE id_permiso = '" + cod + "'";
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();

            if (rs.next()) {
                cod = rs.getInt("id_permiso");
                usuario = rs.getString("usuario");
                formulario = rs.getString("formulario");
                estado = rs.getString("estado");

                txtId.setText("" + cod);
                txtEstado.setText(estado);
                cboFormulario.setSelectedItem(formulario);
                cboUsuario.setSelectedItem(usuario);

            } else {
                JOptionPane.showMessageDialog(null, "REGISTROS NO ENCONTRADOS", "ERROR", JOptionPane.ERROR_MESSAGE);
                this.txtId.setText("");
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
        txtId = new javax.swing.JTextField();
        txtEstado = new javax.swing.JTextField();
        btnCrear = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnLeer = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPermisos = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cboFormulario = new javax.swing.JComboBox<>();
        cboUsuario = new javax.swing.JComboBox<>();
        Fondo = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Script MT Bold", 1, 40)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Control de Permisos");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 400, 60));

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

        txtId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdKeyTyped(evt);
            }
        });
        jPanel1.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 200, -1));

        txtEstado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEstado.setText("Activo");
        txtEstado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstadoKeyTyped(evt);
            }
        });
        jPanel1.add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 230, 180, -1));

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

        tblPermisos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id. Permiso", "Usuario", "Formulario", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPermisos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPermisosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPermisos);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 860, 300));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Id. permiso:");
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
        jLabel7.setText("Estado:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, 140, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Usuario:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 100, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Formulario:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 160, 90, -1));

        jPanel1.add(cboFormulario, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 160, 200, -1));

        jPanel1.add(cboUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 200, -1));

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
        this.limpiar();
        this.leer();
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

    private void tblPermisosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPermisosMouseClicked
        int fila = tblPermisos.getSelectedRow();

        txtId.setText(tblPermisos.getValueAt(fila, 0).toString());
        cboUsuario.setSelectedItem(tblPermisos.getValueAt(fila, 1).toString());
        cboFormulario.setSelectedItem(tblPermisos.getValueAt(fila, 2).toString());
        txtEstado.setText(tblPermisos.getValueAt(fila, 3).toString());

    }//GEN-LAST:event_tblPermisosMouseClicked

    private void txtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyTyped
        char x = evt.getKeyChar();
        if (x < '0' || x > '9') {
            evt.consume();
        }
        int limite = 5;
        {
            if (txtId.getText().length() == limite) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtIdKeyTyped

    private void txtEstadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoKeyTyped
        int limite = 30;
        {
            if (txtEstado.getText().length() == limite) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtEstadoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnHOME;
    private javax.swing.JButton btnLeer;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cboFormulario;
    private javax.swing.JComboBox<String> cboUsuario;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblPermisos;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtId;
    // End of variables declaration//GEN-END:variables
}
