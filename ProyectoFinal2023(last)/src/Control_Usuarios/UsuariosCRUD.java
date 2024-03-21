package Control_Usuarios;

import proyectofinal2023.*;
import java.awt.Color;
import ConexionDB.ConexionDB;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Milton Paz
 */
public class UsuariosCRUD extends javax.swing.JInternalFrame {

    String usuario, sentenciaSQL;
    Connection con = null;
    ConexionDB conecta;
    PreparedStatement ps = null;
    ImageIcon icono;
    ResultSet rs = null;
    DefaultTableModel modelo;
    Object datosDoctor[] = new Object[5];

    public UsuariosCRUD() {
        initComponents();
        txtID.setText("0");
        txtNombre.requestFocus();
    }

    public void conectarBD() {
        conecta = new ConexionDB("clinica_mateo");
        con = conecta.getConexion();
    }

    public void limpiar() {
        txtID.setText("0");
        txtNombre.setText(null);
        txtCorreo.setText(null);
        txtNombre.requestFocus();
        int fila = tblUsuarios.getRowCount();

        for (int i = fila - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }

    public void crear() {
        boolean isSelected = chkAdmin.isSelected();
        String admin = "0";

        if (isSelected) {
            admin = "1";
        } else {
            admin = "0";
        }

        try {
            conectarBD();
            sentenciaSQL = "INSERT INTO usuarios(id_usuario,nombre_usuario,password,correo_electronico,admin,estado) VALUES (?,?,?,?,?,?)";
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, 0);
            ps.setString(2, txtNombre.getText());
            ps.setString(3, txtPassword.getText());
            ps.setString(4, txtCorreo.getText());
            ps.setString(5, admin);
            ps.setString(6, "Activo");
            ps.execute();
            JOptionPane.showMessageDialog(null, "DATOS INGRESADOS CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR NO SE PUDO INGRESAR DATOS " + ex.getMessage());
        }

    }

    public void leer() {
        conectarBD();
        sentenciaSQL = "SELECT * FROM usuarios WHERE estado LIKE 'Activo'";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            modelo = (DefaultTableModel) tblUsuarios.getModel();
            while (rs.next()) {
                datosDoctor[0] = (rs.getInt(1));
                datosDoctor[1] = (rs.getString(2));
                datosDoctor[2] = (rs.getString(3));
                datosDoctor[3] = (rs.getString(4));
                modelo.addRow(datosDoctor);
            }
            tblUsuarios.setModel(modelo);
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR NO SE PUDIERON LEER LOS DATOS " + ex.getMessage());
        }
    }

    public void actualizar() {

        boolean isSelected = chkAdmin.isSelected();
        String admin = "0";

        if (isSelected) {
            admin = "1";
        } else {
            admin = "0";
        }

        try {
            conectarBD();
            sentenciaSQL = "UPDATE usuarios SET nombre_usuario=?,password=?,correo_electronico=?, admin = ? WHERE id_usuario=?";
            ps = con.prepareStatement(sentenciaSQL);

            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtPassword.getText());
            ps.setString(3, txtCorreo.getText());
            ps.setString(4, admin);
            ps.setInt(5, Integer.parseInt(txtID.getText()));
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
            sentenciaSQL = "UPDATE usuarios SET estado='Inactivo' WHERE id_usuario=" + txtID.getText().trim();
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
        String nombre, especialidad, correo, estado;

        try {

            cod = Integer.parseInt(txtID.getText());
            sentenciaSQL = "SELECT * FROM usuarios WHERE id_usuario  = '" + cod + "'";
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();

            if (rs.next()) {
                cod = rs.getInt("id_usuario");
                nombre = rs.getString("nombre_usuario");
                correo = rs.getString("correo_electronico");
                estado = rs.getString("estado");

                txtID.setText("" + cod);
                txtNombre.setText(nombre);
                txtEstado.setText(estado);
                txtCorreo.setText(correo);

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
        txtNombre = new javax.swing.JTextField();
        btnCrear = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnLeer = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        chkAdmin = new javax.swing.JCheckBox();
        Fondo = new javax.swing.JLabel();
        btnBuscarDNI = new javax.swing.JButton();
        txtNombre2 = new javax.swing.JTextField();
        txtCorreo1 = new javax.swing.JTextField();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Script MT Bold", 1, 40)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Control de Usuarios");
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

        txtID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIDKeyTyped(evt);
            }
        });
        jPanel1.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 180, -1));

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 180, -1));

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

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id.Usuario", "Nombre Usuario", "Correo Electronico", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblUsuarios);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 860, 300));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Id. de usuario:");
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
        jLabel7.setText("Nombre usuario:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 140, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Password:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 100, -1));

        txtEstado.setText("Activo");
        txtEstado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstadoKeyTyped(evt);
            }
        });
        jPanel1.add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 210, 200, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Correo:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 170, 60, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Administrador:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 260, 110, 20));

        txtCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCorreoKeyTyped(evt);
            }
        });
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 170, 200, -1));

        txtPassword.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPasswordKeyTyped(evt);
            }
        });
        jPanel1.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, 180, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Estado:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 210, 60, 20));
        jPanel1.add(chkAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 260, -1, -1));

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo1.png"))); // NOI18N
        jPanel1.add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 5, 1110, 670));

        btnBuscarDNI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-14-24.png"))); // NOI18N
        btnBuscarDNI.setBorderPainted(false);
        btnBuscarDNI.setContentAreaFilled(false);
        btnBuscarDNI.setFocusPainted(false);
        btnBuscarDNI.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-15-24.png"))); // NOI18N
        btnBuscarDNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarDNIActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 150, 40, 30));

        txtNombre2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNombre2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre2ActionPerformed(evt);
            }
        });
        txtNombre2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombre2KeyTyped(evt);
            }
        });
        jPanel1.add(txtNombre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 180, -1));

        txtCorreo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCorreo1KeyTyped(evt);
            }
        });
        jPanel1.add(txtCorreo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 250, 200, -1));

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

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        int fila = tblUsuarios.getSelectedRow();

        txtID.setText(tblUsuarios.getValueAt(fila, 0).toString());
        txtNombre.setText(tblUsuarios.getValueAt(fila, 1).toString());
        txtCorreo.setText(tblUsuarios.getValueAt(fila, 2).toString());
        txtEstado.setText(tblUsuarios.getValueAt(fila, 3).toString());
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void btnBuscarDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarDNIActionPerformed
        this.Buscar();
    }//GEN-LAST:event_btnBuscarDNIActionPerformed

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

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        int limite = 30;
        {
            if (txtNombre.getText().length() == limite) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtEstadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstadoKeyTyped
        char x = evt.getKeyChar();
        if (x < '0' || x > '9') {
            evt.consume();
        }
        int limite = 13;
        {
            if (txtEstado.getText().length() == limite) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtEstadoKeyTyped

    private void txtCorreoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoKeyTyped
        int limite = 30;
        {
            if (txtNombre.getText().length() == limite) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtCorreoKeyTyped

    private void txtPasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordKeyTyped

    private void txtCorreo1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreo1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo1KeyTyped

    private void txtNombre2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombre2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre2KeyTyped

    private void txtNombre2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarDNI;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnHOME;
    private javax.swing.JButton btnLeer;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JCheckBox chkAdmin;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtCorreo1;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombre2;
    private javax.swing.JTextField txtPassword;
    // End of variables declaration//GEN-END:variables
}
