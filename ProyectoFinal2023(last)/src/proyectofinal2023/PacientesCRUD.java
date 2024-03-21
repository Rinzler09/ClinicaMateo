package proyectofinal2023;

import ConexionDB.ConexionDB;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  Angely Rosa Paz
 */
public class PacientesCRUD extends javax.swing.JInternalFrame {

    String sentenciaSQL;
    Connection con = null;
    ConexionDB conecta;
    PreparedStatement ps = null; //hace un plan de ejecucion 
    ImageIcon icono;
    ResultSet rs = null;
    DefaultTableModel modelo;
    Object datosClientes[] = new Object[8];
    String nombre, codigo, ciudad, telefono;
    
    /**
     * Creates new form ClientesCRUD
     */
    public PacientesCRUD() {
        initComponents();

        tid.setText("0");
        tnombre.requestFocus();
        
       
         
    }

    public void conectarBD() {
        conecta = new ConexionDB("clinica_mateo");
        con = conecta.getConexion();
    }

    public void CrearPacientes() {

        try {
            conectarBD();

            sentenciaSQL = "INSERT INTO pacientes (PacientesID, Nombre, DNI, Direccion, Procedimiento_Realizado, Pago_Consulta,Telefono, Correo, Estado) VALUES (?, ?, ?, ?, ?, ?, ?, ? ,?)";
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, 0);
            ps.setString(2, tnombre.getText());
            ps.setString(3, tdni.getText());
            ps.setString(4, tdireccion.getText());
            ps.setString(5, tprocedimiento.getText());
            ps.setString(6, tpagoconsulta.getText());
            ps.setString(7, ttelefono.getText());
            ps.setString(8, tcorreo.getText());
            ps.setString(9, "Activo");
            ps.execute();
            JOptionPane.showConfirmDialog(null, "Los datos se han guardado correctamente");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, "ERROR! Los datos no se pueden ingresar!!!" + ex.getMessage());
        }
    }

    public void leerPacientes() {

        sentenciaSQL = "SELECT * FROM pacientes WHERE Estado LIKE 'Activo'";
        try {
            conectarBD();
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            modelo = (DefaultTableModel) tabladatos.getModel();

            while (rs.next()) {
                datosClientes[0] = (rs.getInt(1));
                datosClientes[1] = (rs.getString(2));
                datosClientes[2] = (rs.getString(3));
                datosClientes[3] = (rs.getString(4));
                datosClientes[4] = (rs.getString(5));
                datosClientes[5] = (rs.getString(6));
                datosClientes[6] = (rs.getString(7));
                datosClientes[7] = (rs.getString(8));

                modelo.addRow(datosClientes);

            }
            tabladatos.setModel(modelo);
            con.close();

        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, "ERROR! los datos no se pueden mostra!!" + ex.getMessage());
        }
    }

    public void actualizarClientes() {

        try {

            conectarBD();

            sentenciaSQL = "UPDATE pacientes SET Nombre=?, DNI=?, Direccion=?, Procedimiento_Realizado=?, Pago_Consulta=?, Telefono=?, Correo=?  WHERE PacientesID=?";

            ps = con.prepareStatement(sentenciaSQL);

            ps.setString(1, tnombre.getText());
            ps.setString(2, tdni.getText());
            ps.setString(3, tdireccion.getText());
            ps.setString(4, tprocedimiento.getText());
            ps.setString(5, tpagoconsulta.getText());
            ps.setString(6, ttelefono.getText());
            ps.setString(7, tcorreo.getText());

            ps.setInt(8, Integer.parseInt(tid.getText()));

            ps.execute();

            JOptionPane.showMessageDialog(null, "Los datos se actualizaron corectamente!");

            con.close();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "ERROR!!, los datos no se actualizaron" + ex.getMessage());

        }

    }

    private void BuscarPaciente(String busqueda) {
         conectarBD();
        int fila = tabladatos.getRowCount();
        for (int i = fila - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }

        conectarBD();
       
        sentenciaSQL = "SELECT * FROM pacientes WHERE PacientesID = '" + busqueda + "' OR Nombre LIKE '%" + busqueda + "%'";
        try {
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            modelo = (DefaultTableModel) tabladatos.getModel();

            while (rs.next()) {

                datosClientes[0] = (rs.getInt(1));
                datosClientes[1] = (rs.getString(2));
                datosClientes[2] = (rs.getString(3));
                datosClientes[3] = (rs.getString(4));
                datosClientes[4] = (rs.getString(5));
                datosClientes[5] = (rs.getString(6));
                datosClientes[6] = (rs.getString(7));
                datosClientes[7] = (rs.getString(8));

                modelo.addRow(datosClientes);
            }
            JOptionPane.showMessageDialog(null, "Los datos se encontraron correctamente!");
            tabladatos.setModel(modelo);
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, no se pueden leer los datos!" + ex.getMessage() + sentenciaSQL);
        }

        tid.setText("0");
        tnombre.setText(null);
        tdni.setText(null);
        tprocedimiento.setText(null);
        tpagoconsulta.setText(null);
        tdireccion.setText(null);
        ttelefono.setText(null);
        tcorreo.setText(null);
    }

    public void limpiar() {
        tid.setText("0");
        tnombre.setText(null);
        tdni.setText(null);
        tprocedimiento.setText(null);
        tpagoconsulta.setText(null);
        tdireccion.setText(null);
        ttelefono.setText(null);
        tcorreo.setText(null);

        tnombre.requestFocus();
        int fila = tabladatos.getRowCount();
        for (int i = fila - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }

    public void eliminar() {
        conectarBD();
        try {

            sentenciaSQL = "DELETE FROM pacientes WHERE PacientesID=" + tid.getText().trim();
            
            ps = con.prepareStatement(sentenciaSQL);
            ps.execute();
            JOptionPane.showMessageDialog(null, "DATOS ELIMINADOS CORRECTAMENTE!");
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO ELIMINAR LOS DATOS " + ex.getMessage());

        }

    }

   public void HabilitarBNTCrear() {

      if (tnombre.getText().length() == 0 || tdni.getText().length() == 0 || tprocedimiento.getText().length() == 0 || tpagoconsulta.getText().length() == 0 || tdireccion.getText().length() == 0 || ttelefono.getText().length() == 0 || tcorreo.getText().length() == 0) {
            this.bcrear.setEnabled(false);

       } else if (tnombre.getText().length() != 0 || tdni.getText().length() != 0 || tprocedimiento.getText().length() != 0 || tpagoconsulta.getText().length() != 0 || tdireccion.getText().length() != 0 || ttelefono.getText().length() != 0 || tcorreo.getText().length() != 0) {
            this.bcrear.setEnabled(true);
        }
    }

    public void desactivar() {
        this.tnombre.enable(false);
        this.tdni.enable(false);
        this.tprocedimiento.enable(false);
        this.tpagoconsulta.enable(false);
        this.tdireccion.enable(false);
        this.ttelefono.enable(false);
        this.tcorreo.enable(false);

        this.bcrear.setEnabled(false);
        this.bleer.setEnabled(false);
        this.bactualizar.setEnabled(false);
        this.blimpiar.setEnabled(false);

    }

    public void activar() {
        this.tnombre.enable(true);
        this.tdni.enable(true);
        this.tprocedimiento.enable(true);
        this.tpagoconsulta.enable(true);
        this.tdireccion.enable(true);
        this.ttelefono.enable(true);
        this.tcorreo.enable(true);

        this.bcrear.setEnabled(true);
        this.bleer.setEnabled(true);
        this.bactualizar.setEnabled(true);
        this.blimpiar.setEnabled(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnHOME = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        btnHOME1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabladatos = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tid = new javax.swing.JTextField();
        tnombre = new javax.swing.JTextField();
        tdni = new javax.swing.JTextField();
        tprocedimiento = new javax.swing.JTextField();
        tpagoconsulta = new javax.swing.JTextField();
        tdireccion = new javax.swing.JTextField();
        tcorreo = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        ttelefono = new javax.swing.JFormattedTextField();
        jButton2 = new javax.swing.JButton();
        beliminar = new javax.swing.JButton();
        blimpiar = new javax.swing.JButton();
        bcrear = new javax.swing.JButton();
        bleer = new javax.swing.JButton();
        bactualizar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

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

        jPanel1.setLayout(null);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 50)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mateologo.png"))); // NOI18N
        jPanel1.add(jLabel12);
        jLabel12.setBounds(-10, 0, 270, 140);

        btnHOME1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/home.png"))); // NOI18N
        btnHOME1.setBorder(null);
        btnHOME1.setBorderPainted(false);
        btnHOME1.setContentAreaFilled(false);
        btnHOME1.setFocusPainted(false);
        btnHOME1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/home60.png"))); // NOI18N
        btnHOME1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHOME1MouseClicked(evt);
            }
        });
        btnHOME1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHOME1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnHOME1);
        btnHOME1.setBounds(1110, 40, 50, 50);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tabladatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PacientesID", "Nombre", "DNI", "Direccion", "Procedimiento_Realizado", "Pago_Consuta", "Telefono", "Correo"
            }
        ));
        tabladatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabladatosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabladatos);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Id del paciente:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Nombre completo:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Direccion:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("DNI:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Correo:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Telefono:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Pago de la consulta:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Tratamiento realizado:");

        tid.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tidKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tidKeyTyped(evt);
            }
        });

        tnombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tnombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tnombreKeyTyped(evt);
            }
        });

        tdni.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tdni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tdniKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tdniKeyTyped(evt);
            }
        });

        tprocedimiento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tprocedimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tprocedimientoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tprocedimientoKeyTyped(evt);
            }
        });

        tpagoconsulta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tpagoconsulta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tpagoconsultaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tpagoconsultaKeyTyped(evt);
            }
        });

        tdireccion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tdireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tdireccionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tdireccionKeyTyped(evt);
            }
        });

        tcorreo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tcorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tcorreoKeyTyped(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-15-24.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        try {
            ttelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(504) ####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search-15-24.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        beliminar.setBackground(new java.awt.Color(255, 255, 255));
        beliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminarlogo.png"))); // NOI18N
        beliminar.setText(" Eliminar");
        beliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beliminarActionPerformed(evt);
            }
        });

        blimpiar.setBackground(new java.awt.Color(255, 255, 255));
        blimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/limpiarlogo.png"))); // NOI18N
        blimpiar.setText("  Limpiar  ");
        blimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blimpiarActionPerformed(evt);
            }
        });

        bcrear.setBackground(new java.awt.Color(255, 255, 255));
        bcrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/crearlogo.png"))); // NOI18N
        bcrear.setText("  Crear    ");
        bcrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcrearActionPerformed(evt);
            }
        });

        bleer.setBackground(new java.awt.Color(255, 255, 255));
        bleer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/leerlogo.png"))); // NOI18N
        bleer.setText("    Leer");
        bleer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bleerActionPerformed(evt);
            }
        });

        bactualizar.setBackground(new java.awt.Color(255, 255, 255));
        bactualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizarlogo.png"))); // NOI18N
        bactualizar.setText("Actualizar");
        bactualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bactualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tprocedimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tdni, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tid, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(128, 128, 128)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tcorreo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                .addComponent(tdireccion, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(tpagoconsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(131, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(bcrear, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(bleer, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(bactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addComponent(beliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(blimpiar)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(tid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(tpagoconsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(tdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(ttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(tcorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(tdni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(tprocedimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(beliminar)
                    .addComponent(blimpiar)
                    .addComponent(bcrear)
                    .addComponent(bleer)
                    .addComponent(bactualizar))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 170, 1220, 580);

        jLabel2.setFont(new java.awt.Font("Script MT Bold", 1, 65)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Control de Pacientes");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(240, 40, 680, 100);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fon2.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(-60, -40, 1300, 1030);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1239, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 803, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabladatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabladatosMouseClicked
        int fila = tabladatos.getSelectedRow();
        tid.setText(tabladatos.getValueAt(fila, 0).toString());
        tnombre.setText(tabladatos.getValueAt(fila, 1).toString());
        tdni.setText(tabladatos.getValueAt(fila, 2).toString());
        tdireccion.setText(tabladatos.getValueAt(fila, 3).toString());
        tprocedimiento.setText(tabladatos.getValueAt(fila, 4).toString());
        tpagoconsulta.setText(tabladatos.getValueAt(fila, 5).toString());
        ttelefono.setText(tabladatos.getValueAt(fila, 6).toString());
        tcorreo.setText(tabladatos.getValueAt(fila, 7).toString());

// TODO add your handling code here:
    }//GEN-LAST:event_tabladatosMouseClicked

    private void bcrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcrearActionPerformed
        CrearPacientes();
        limpiar();
        // TODO add your handling code here:
    }//GEN-LAST:event_bcrearActionPerformed

    private void bleerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bleerActionPerformed
        leerPacientes();        // TODO add your handling code here:
    }//GEN-LAST:event_bleerActionPerformed

    private void bactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bactualizarActionPerformed
        actualizarClientes();
        limpiar();// TODO add your handling code here:
    }//GEN-LAST:event_bactualizarActionPerformed

    private void blimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blimpiarActionPerformed
        limpiar();    // TODO add your handling code here:
    }//GEN-LAST:event_blimpiarActionPerformed

    private void beliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beliminarActionPerformed
        eliminar();
        limpiar(); 
        // TODO add your handling code here:
    }//GEN-LAST:event_beliminarActionPerformed

    private void tidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tidKeyReleased
      // HabilitarBNTCrear();            // TODO add your handling code here:
    }//GEN-LAST:event_tidKeyReleased

    private void tidKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tidKeyTyped

    }//GEN-LAST:event_tidKeyTyped

    private void tnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tnombreKeyReleased
      //  HabilitarBNTCrear();          // TODO add your handling code here:
    }//GEN-LAST:event_tnombreKeyReleased

    private void tdniKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tdniKeyReleased
     // HabilitarBNTCrear();          // TODO add your handling code here:
    }//GEN-LAST:event_tdniKeyReleased

    private void tprocedimientoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tprocedimientoKeyReleased
      // HabilitarBNTCrear();          // TODO add your handling code here:
    }//GEN-LAST:event_tprocedimientoKeyReleased

    private void tpagoconsultaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpagoconsultaKeyReleased
      //  HabilitarBNTCrear();          // TODO add your handling code here:
    }//GEN-LAST:event_tpagoconsultaKeyReleased

    private void tdireccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tdireccionKeyReleased
      // HabilitarBNTCrear();          // TODO add your handling code here:
    }//GEN-LAST:event_tdireccionKeyReleased

    private void btnHOMEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHOMEMouseClicked
        dispose();
    }//GEN-LAST:event_btnHOMEMouseClicked

    private void btnHOME1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHOME1MouseClicked
        dispose();
    }//GEN-LAST:event_btnHOME1MouseClicked

    private void btnHOME1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHOME1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHOME1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     BuscarPaciente(tid.getText());           // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     BuscarPaciente(tnombre.getText());       // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tdniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tdniKeyTyped
        char x = evt.getKeyChar();
        if (x < '0' || x > '9') {
            evt.consume();
        }
        int limite = 13;
        {
            if (tdni.getText().length() == limite) {
                evt.consume();
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tdniKeyTyped

    private void tnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tnombreKeyTyped
      
       
        int limite = 30;
        {
            if (tnombre.getText().length()== limite)
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tnombreKeyTyped

    private void tprocedimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tprocedimientoKeyTyped
         int limite = 50;
        {
            if (tprocedimiento.getText().length()== limite)
            evt.consume();   
        }  // TODO add your handling code here:
    }//GEN-LAST:event_tprocedimientoKeyTyped

    private void tpagoconsultaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpagoconsultaKeyTyped
    char x = evt.getKeyChar();
        if(x<'0' || x>'9') evt.consume();
        int limite = 20;
        {
            if (tpagoconsulta.getText().length()== limite)
            evt.consume();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tpagoconsultaKeyTyped

    private void tdireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tdireccionKeyTyped
      int limite = 50;
        {
            if (tdireccion.getText().length()== limite)
            evt.consume();   
        }       // TODO add your handling code here:
    }//GEN-LAST:event_tdireccionKeyTyped

    private void tcorreoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tcorreoKeyTyped
     int limite = 30;
        {
            if (tcorreo.getText().length()== limite)
            evt.consume();   
        }       // TODO add your handling code here:
                // TODO add your handling code here:
    }//GEN-LAST:event_tcorreoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bactualizar;
    private javax.swing.JButton bcrear;
    private javax.swing.JButton beliminar;
    private javax.swing.JButton bleer;
    private javax.swing.JButton blimpiar;
    private javax.swing.JButton btnHOME;
    private javax.swing.JButton btnHOME1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabladatos;
    private javax.swing.JTextField tcorreo;
    private javax.swing.JTextField tdireccion;
    private javax.swing.JTextField tdni;
    private javax.swing.JTextField tid;
    private javax.swing.JTextField tnombre;
    private javax.swing.JTextField tpagoconsulta;
    private javax.swing.JTextField tprocedimiento;
    private javax.swing.JFormattedTextField ttelefono;
    // End of variables declaration//GEN-END:variables
}
