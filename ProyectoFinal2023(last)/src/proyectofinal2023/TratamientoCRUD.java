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
public class TratamientoCRUD extends javax.swing.JInternalFrame {

    String usuario, sentenciaSQL;
    Connection con = null;
    ConexionDB conecta;
    PreparedStatement ps = null;
    ImageIcon icono;
    ResultSet rs = null;
    DefaultTableModel modelo;
    Object datosTratamiento[] = new Object[4];
    String textSeleccionIsv = "SELECCIONE ISV";

    public TratamientoCRUD() {
        initComponents();
        txtCodigo.setText("0");
        txtPrecio.setText("0");
        txtDescripcion.requestFocus();
    }

    public void conectarBD() {
        conecta = new ConexionDB("clinica_mateo");
        con = conecta.getConexion();
    }

    public void Limpiar() {
        txtCodigo.setText("0");
        txtDescripcion.setText("");
        txtPrecio.setText("0");
        cbxIsv.setSelectedItem(textSeleccionIsv);
        txtDescripcion.requestFocus();
        LimpiarTable();
    }

    private void LimpiarTable() {
        int fila = tblTratamiento.getRowCount();
        for (int i = fila - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }

    public void CrearTratamiento() {
        try {
            conectarBD();
            sentenciaSQL = "INSERT INTO tratamiento (idTratamiento, descripcion, precio, porcentaje_isv, estado) VALUES(?,?,?,?,?)";
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, 0);
            ps.setString(2, txtDescripcion.getText());
            ps.setDouble(3, Double.parseDouble(txtPrecio.getText()));
            ps.setInt(4, Integer.parseInt(cbxIsv.getSelectedItem().toString()));
            ps.setString(5, "Activo");
            ps.execute();
            JOptionPane.showMessageDialog(null, "DATOS INGRESADOS CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR NO SE PUDO INGRESAR DATOS " + ex.getMessage());
        }

    }

    public boolean Validacion() {
        if (txtDescripcion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "CAMPOS VACIOS",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtDescripcion.requestFocus();
            return false;
        }

        Double precio = Double.parseDouble(txtPrecio.getText());
        if (precio <= 0) {
            JOptionPane.showMessageDialog(null, "VALOR NUMERICO INVALIDO EN PRECIO",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtPrecio.requestFocus();
            return false;
        }

        if (cbxIsv.getSelectedItem().equals(textSeleccionIsv)) {
            JOptionPane.showMessageDialog(null, "CAMPO VACIO",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            cbxIsv.requestFocus();
            return false;
        }

        return true;
    }

    public void LeerTratamiento() {
        conectarBD();
        sentenciaSQL = "SELECT * FROM tratamiento WHERE estado LIKE 'Activo'";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            modelo = (DefaultTableModel) tblTratamiento.getModel();
            while (rs.next()) {
                datosTratamiento[0] = (rs.getInt(1));
                datosTratamiento[1] = (rs.getString(2));
                datosTratamiento[2] = (rs.getDouble(3));
                datosTratamiento[3] = (rs.getInt(4));
                modelo.addRow(datosTratamiento);
            }
            tblTratamiento.setModel(modelo);
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR NO SE PUDIERON LEER LOS DATOS " + ex.getMessage());
        }
    }

    public void ActualizarTratamiento() {
        try {
            conectarBD();
            sentenciaSQL = "UPDATE tratamiento SET descripcion=?, precio=?, porcentaje_isv=? WHERE idTratamiento=?";
            ps = con.prepareStatement(sentenciaSQL);

            ps.setString(1, txtDescripcion.getText());
            ps.setDouble(2, Double.parseDouble(txtPrecio.getText()));
            ps.setInt(3, Integer.parseInt(cbxIsv.getSelectedItem().toString()));
            ps.setInt(4, Integer.parseInt(txtCodigo.getText()));
            ps.execute();
            JOptionPane.showMessageDialog(null, "DATOS ACTUALIZADOS CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR NO SE PUDIERON ACTUALIZAR LOS DATOS " + ex.getMessage());
        }
    }

    public void EliminarTratamiento() {
        try {
            conectarBD();
            sentenciaSQL = "UPDATE tratamiento SET estado='Inactivo' WHERE idTratamiento=" + txtCodigo.getText().trim();
            ps = con.prepareStatement(sentenciaSQL);
            ps.execute();
            JOptionPane.showMessageDialog(null, "DATOS ELIMINADOS CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO ELIMINAR LOS DATOS " + ex.getMessage());
        }
    }

    public void BuscarTratamiento(String search) {
        conectarBD();
        sentenciaSQL = "SELECT * FROM tratamiento WHERE (idTratamiento = '" + search + "' OR descripcion LIKE '%" + search + "%') AND estado = 'activo'";
        System.out.print(sentenciaSQL);
        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            modelo = (DefaultTableModel) tblTratamiento.getModel();
            while (rs.next()) {
                datosTratamiento[0] = (rs.getInt(1));
                datosTratamiento[1] = (rs.getString(2));
                datosTratamiento[2] = (rs.getDouble(3));
                datosTratamiento[3] = (rs.getInt(4));
                
                modelo.addRow(datosTratamiento);

                txtCodigo.setText("" + rs.getInt(1));
                txtDescripcion.setText("" + rs.getString(2));
                txtPrecio.setText("" + rs.getString(3));
                cbxIsv.setSelectedItem("" + rs.getString(4));
            }

            tblTratamiento.setModel(modelo);
            con.close();
            int rowCount = tblTratamiento.getRowCount();
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
        txtPrecio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        btnCrear = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnLeer = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTratamiento = new javax.swing.JTable();
        btnBuscarCodigo = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbxIsv = new javax.swing.JComboBox<>();
        btnBuscarDescripcion = new javax.swing.JButton();
        Fondo = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Script MT Bold", 1, 40)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Control de Tratamientos");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 480, 60));

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

        txtPrecio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel1.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 100, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Isv %:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 210, 50, -1));

        txtDescripcion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel1.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 160, 450, -1));

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

        tblTratamiento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tratamiento", "Precio", "Isv %"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTratamiento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTratamientoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblTratamiento);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 850, 330));

        btnBuscarCodigo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-14-24.png"))); // NOI18N
        btnBuscarCodigo.setBorderPainted(false);
        btnBuscarCodigo.setContentAreaFilled(false);
        btnBuscarCodigo.setFocusPainted(false);
        btnBuscarCodigo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-15-24.png"))); // NOI18N
        btnBuscarCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCodigoActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, 40, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Código:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 60, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Tratamiento:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, 100, -1));

        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 100, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Precio:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 50, -1));

        cbxIsv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONE ISV", "0", "15" }));
        cbxIsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxIsvActionPerformed(evt);
            }
        });
        jPanel1.add(cbxIsv, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 210, 170, -1));

        btnBuscarDescripcion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-14-24.png"))); // NOI18N
        btnBuscarDescripcion.setBorderPainted(false);
        btnBuscarDescripcion.setContentAreaFilled(false);
        btnBuscarDescripcion.setFocusPainted(false);
        btnBuscarDescripcion.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-15-24.png"))); // NOI18N
        btnBuscarDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarDescripcionActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 150, 40, 30));

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
        if (Validacion()) {
            CrearTratamiento();
            Limpiar();
            LeerTratamiento();
        }
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (Validacion()) {
            ActualizarTratamiento();
            Limpiar();
            LeerTratamiento();
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnLeerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeerActionPerformed
        this.Limpiar();
        this.LeerTratamiento();
    }//GEN-LAST:event_btnLeerActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (txtCodigo.getText().isEmpty() || txtCodigo.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "FAVOR SELECCIONE REGISTRO A ELIMINAR",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int resultDialog = JOptionPane.showConfirmDialog(null, "¿SEGURO QUE DESEAS ELIMINAR ESTE REGISTRO?", "CONFIRMACION", JOptionPane.YES_NO_OPTION);
        if (resultDialog == JOptionPane.YES_OPTION) {
            EliminarTratamiento();
            Limpiar();
            LeerTratamiento();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        this.Limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnBuscarCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCodigoActionPerformed
        String codigo = this.txtCodigo.getText();
        if (codigo.equals("0") || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "FAVOR DIGITE EL DATO A BUSCAR",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtCodigo.requestFocus();
            return;
        }
        LimpiarTable();
        BuscarTratamiento(codigo);
    }//GEN-LAST:event_btnBuscarCodigoActionPerformed

    private void tblTratamientoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTratamientoMouseClicked
        int fila = tblTratamiento.getSelectedRow();
        txtCodigo.setText(tblTratamiento.getValueAt(fila, 0).toString());
        txtDescripcion.setText(tblTratamiento.getValueAt(fila, 1).toString());
        txtPrecio.setText(tblTratamiento.getValueAt(fila, 2).toString());
        cbxIsv.setSelectedItem(tblTratamiento.getValueAt(fila, 3).toString());
    }//GEN-LAST:event_tblTratamientoMouseClicked

    private void cbxIsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxIsvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxIsvActionPerformed

    private void btnBuscarDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarDescripcionActionPerformed
        String descripcion = this.txtDescripcion.getText();
        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "FAVOR DIGITE EL DATO A BUSCAR",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtCodigo.requestFocus();
            return;
        }
        LimpiarTable();
        BuscarTratamiento(descripcion);
    }//GEN-LAST:event_btnBuscarDescripcionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscarCodigo;
    private javax.swing.JButton btnBuscarDescripcion;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnHOME;
    private javax.swing.JButton btnLeer;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cbxIsv;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblTratamiento;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
