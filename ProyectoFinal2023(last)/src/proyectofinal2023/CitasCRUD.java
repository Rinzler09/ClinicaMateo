package proyectofinal2023;

import java.awt.Color;
import ConexionDB.ConexionDB;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Milton Paz
 */
public class CitasCRUD extends javax.swing.JInternalFrame {
java.util.Date fechaA;
    String Fecha = "";
    String usuario, sentenciaSQL;
    Connection con = null;
    ConexionDB conecta;
    PreparedStatement ps = null;
    ImageIcon icono;
    ResultSet rs = null;
    DefaultTableModel modelo;
    Object datosCitas[] = new Object[5];
    String textSeleccionDoctor = "SELECCIONE EL DOCTOR";
    String textSeleccionPaciente = "SELECCIONE EL PACIENTE";

    public CitasCRUD() {
        initComponents();
        txtCodigo.setText("0");
        ListaDoctores();
        ListaPacientes();
    }

    public void conectarBD() {
        conecta = new ConexionDB("clinica_mateo");
        con = conecta.getConexion();
    }

 public void takedate()
    {
        
        fechaA= this.txtFecha.getDate();
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        
        Fecha = fecha.format(fechaA);
    } 

    public void ListaDoctores() {

        try {
            conectarBD();
            sentenciaSQL = "SELECT Nombre FROM doctores WHERE Estado LIKE 'Activo'";
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            cbxDoctor.addItem(textSeleccionDoctor);
            while (rs.next()) {
                String tmpStrObtenido = rs.getString("Nombre");
                cbxDoctor.addItem(tmpStrObtenido);
            }
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO CARGA LISTA DE DOCTORES " + ex.getMessage());
        }
    }

    public void ListaPacientes() {

        try {
            conectarBD();
            sentenciaSQL = "SELECT Nombre FROM pacientes WHERE Estado LIKE 'Activo'";
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            cbxPaciente.addItem(textSeleccionPaciente);
            while (rs.next()) {
                String tmpStrObtenido = rs.getString("Nombre");
                cbxPaciente.addItem(tmpStrObtenido);
            }
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO CARGA LISTA DE PACIENTES " + ex.getMessage());
        }
    }

    public void Limpiar() {
        txtCodigo.setText("0");
        cbxDoctor.setSelectedItem(textSeleccionDoctor);
        cbxPaciente.setSelectedItem(textSeleccionPaciente);
        txtMotivo.setText("");
        LimpiarTable();
    }

    private void LimpiarTable() {
        int fila = tblCitas.getRowCount();
        for (int i = fila - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }

    public void CrearCitas() {
takedate();
        try {
            conectarBD();
            sentenciaSQL = "INSERT INTO citas (CitaID, Paciente, Medico, Motivo_Cita, Fecha, Estado) VALUES(?,?,?,?,?,?)";
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, 0);
            ps.setString(2, cbxPaciente.getSelectedItem().toString());
            ps.setString(3, cbxDoctor.getSelectedItem().toString());
            ps.setString(4, txtMotivo.getText().toString());
            ps.setString(5, Fecha);
            ps.setString(6, "Activo");
            ps.execute();
            JOptionPane.showMessageDialog(null, "DATOS INGRESADOS CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR NO SE PUDO INGRESAR DATOS " + ex.getMessage());
        }

    }

    public boolean Validacion() {

        if (cbxDoctor.getSelectedItem().equals(textSeleccionDoctor)) {
            JOptionPane.showMessageDialog(null, "CAMPO VACIO",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            cbxDoctor.requestFocus();
            return false;
        }
        if (cbxPaciente.getSelectedItem().equals(textSeleccionPaciente)) {
            JOptionPane.showMessageDialog(null, "CAMPO VACIO",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            cbxPaciente.requestFocus();
            return false;
        }


        if (txtMotivo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "CAMPO VACIO",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtMotivo.requestFocus();
            return false;
        }

        return true;
    }

    public void LeerCitas() {
        conectarBD();
        sentenciaSQL = "SELECT CitaID, Paciente,Medico,Motivo_Cita,Fecha FROM citas WHERE Estado LIKE 'Activo'";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            modelo = (DefaultTableModel) tblCitas.getModel();
            while (rs.next()) {
                datosCitas[0] = (rs.getInt(1));
                datosCitas[1] = (rs.getString(2));
                datosCitas[2] = (rs.getString(3));
                datosCitas[3] = (rs.getString(4));
                datosCitas[4] = (rs.getString(5));
                modelo.addRow(datosCitas);
            }
            tblCitas.setModel(modelo);
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR NO SE PUDIERON LEER LOS DATOS " + ex.getMessage());
        }
    }

    public void ActualizarCitas() {
takedate();
        try {
            conectarBD();
            sentenciaSQL = "UPDATE citas SET Paciente=?, Medico=?, Motivo_Cita=?, Fecha=? WHERE CitaID=?";
            ps = con.prepareStatement(sentenciaSQL);

            ps.setString(1, cbxPaciente.getSelectedItem().toString());
            ps.setString(2, cbxDoctor.getSelectedItem().toString());
            ps.setString(3, txtMotivo.getText().toString());
            ps.setString(4, Fecha);
            ps.setInt(5, Integer.parseInt(txtCodigo.getText()));

            ps.execute();
            JOptionPane.showMessageDialog(null, "DATOS ACTUALIZADOS CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR NO SE PUDIERON ACTUALIZAR LOS DATOS " + ex.getMessage());
        }
    }

    public void EliminarCitas() {
        try {
            conectarBD();
            sentenciaSQL = "UPDATE citas SET Estado='Inactivo' WHERE CitaID=" + txtCodigo.getText().trim();
            ps = con.prepareStatement(sentenciaSQL);
            ps.execute();
            JOptionPane.showMessageDialog(null, "DATOS ELIMINADOS CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO ELIMINAR LOS DATOS " + ex.getMessage());
        }
    }

    public void BuscarCitas(String search) {
        conectarBD();
        sentenciaSQL = "SELECT CitaID,Paciente,Medico,Motivo_Cita,Fecha FROM tratamiento WHERE CitaID = '" + search + "' AND estado = 'activo'";
        System.out.print(sentenciaSQL);
        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            modelo = (DefaultTableModel) tblCitas.getModel();
            while (rs.next()) {
                datosCitas[0] = (rs.getInt(1));
                datosCitas[1] = (rs.getString(2));
                datosCitas[2] = (rs.getString(3));
                datosCitas[3] = (rs.getString(4));
                datosCitas[4] = (rs.getString(5));
                modelo.addRow(datosCitas);

                txtCodigo.setText("" + rs.getInt(1));
            }

            tblCitas.setModel(modelo);
            con.close();
            int rowCount = tblCitas.getRowCount();
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
        btnCrear = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnLeer = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCitas = new javax.swing.JTable();
        btnBuscarCodigo = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbxPaciente = new javax.swing.JComboBox<>();
        cbxDoctor = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMotivo = new javax.swing.JTextArea();
        txtFecha = new com.toedter.calendar.JDateChooser();
        Fondo = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Script MT Bold", 1, 40)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Control de Citas");
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

        tblCitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Paciente", "Medico", "Motivo", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCitasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCitas);

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
        jPanel1.add(btnBuscarCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 40, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Código:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 60, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Pacientes:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, 100, -1));

        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 100, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Motivo:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 170, 60, -1));

        jPanel1.add(cbxPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, 350, -1));

        jPanel1.add(cbxDoctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 260, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Medico:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 60, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Fecha Cita:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, -1));

        txtMotivo.setColumns(20);
        txtMotivo.setRows(5);
        jScrollPane1.setViewportView(txtMotivo);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 470, 70));
        jPanel1.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, 220, -1));

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
            CrearCitas();
            Limpiar();
            LeerCitas();
        }
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (Validacion()) {
            ActualizarCitas();
            Limpiar();
            LeerCitas();
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnLeerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeerActionPerformed
        this.Limpiar();
        this.LeerCitas();
    }//GEN-LAST:event_btnLeerActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (txtCodigo.getText().isEmpty() || txtCodigo.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "FAVOR SELECCIONE REGISTRO A ELIMINAR",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int resultDialog = JOptionPane.showConfirmDialog(null, "¿SEGURO QUE DESEAS ELIMINAR ESTE REGISTRO?", "CONFIRMACION", JOptionPane.YES_NO_OPTION);
        if (resultDialog == JOptionPane.YES_OPTION) {
            EliminarCitas();
            Limpiar();
            LeerCitas();
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
        BuscarCitas(codigo);
    }//GEN-LAST:event_btnBuscarCodigoActionPerformed

    private void tblCitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCitasMouseClicked
        int fila = tblCitas.getSelectedRow();
        txtCodigo.setText(tblCitas.getValueAt(fila, 0).toString());
        cbxPaciente.setSelectedItem(tblCitas.getValueAt(fila, 1).toString());
        cbxDoctor.setSelectedItem(tblCitas.getValueAt(fila, 2).toString());
        txtMotivo.setText(tblCitas.getValueAt(fila, 3).toString());

    }//GEN-LAST:event_tblCitasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscarCodigo;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnHOME;
    private javax.swing.JButton btnLeer;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cbxDoctor;
    private javax.swing.JComboBox<String> cbxPaciente;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblCitas;
    private javax.swing.JTextField txtCodigo;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextArea txtMotivo;
    // End of variables declaration//GEN-END:variables
}
