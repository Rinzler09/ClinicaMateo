package proyectofinal2023;

import java.awt.Color;
import ConexionDB.ConexionDB;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import Reportes.BotonesReportes;
        
/**
 *
 * @author Milton Paz
 */
public class FacturaCRUD extends javax.swing.JInternalFrame {

    String usuario, sentenciaSQL;
    Connection con = null;
    ConexionDB conecta;
    PreparedStatement ps = null;
    ImageIcon icono;
    ResultSet rs = null;
    DefaultTableModel modelo;
    Object datosDetalleFactura[] = new Object[5];
    String textSeleccionDoctor = "SELECCIONE EL DOCTOR";
    String textSeleccionPaciente = "SELECCIONE EL PACIENTE";
    String textSeleccionTratamiento = "SELECCIONE TRATAMIENTO";
    double subTotal = 0, valorIsv = 0, Total = 0;
    int ID_FACTURA=0;
    boolean _pagado = false;
    
    public FacturaCRUD() {
        initComponents();
        txtId.setText("0");
        txtPrecio.setText("0");
        txtCantidad.setText("0");
        txtIsvPorci.setText("0");
        txtSubTotalDetalle.setText("0");
        txtSubTotal.setText("0");
        txtIsvValor.setText("0");
        txtTotal.setText("0");
        ListaDoctores();
        ListaPacientes();
        ListaTratamientos();
        Date fechaActual = Date.valueOf(LocalDate.now());
        txtFecha.setText(fechaActual.toString());
    }

    public void conectarBD() {
        conecta = new ConexionDB("clinica_mateo");
        con = conecta.getConexion();
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

    public void ListaTratamientos() {

        try {
            conectarBD();
            sentenciaSQL = "SELECT descripcion FROM tratamiento WHERE Estado LIKE 'Activo'";
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            cbxTratamiento.addItem(textSeleccionTratamiento);
            while (rs.next()) {
                String tmpStrObtenido = rs.getString("descripcion");
                cbxTratamiento.addItem(tmpStrObtenido);
            }
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO CARGA LISTA DE TRATAMIENTO " + ex.getMessage());
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

        int fila = tblFacturaDetalle.getRowCount();
        if (fila <= 0) {
            JOptionPane.showMessageDialog(null, "DETALLE VACIO",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean ValidacionAgregar() {
        if (cbxTratamiento.getSelectedItem().equals(textSeleccionTratamiento)) {
            JOptionPane.showMessageDialog(null, "CAMPO VACIO",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            cbxTratamiento.requestFocus();
            return false;
        }

        int cantidad = Integer.parseInt(txtCantidad.getText());
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(null, "VALOR NUMERICO INVALIDO EN CANTIDAD",
                    "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtCantidad.requestFocus();
            return false;
        }
        return true;
    }

    public void AgregarDetalle() {
        if (ValidacionAgregar()) {
            modelo = (DefaultTableModel) tblFacturaDetalle.getModel();
            datosDetalleFactura[0] = (cbxTratamiento.getSelectedItem().toString());
            datosDetalleFactura[1] = (txtPrecio.getText());
            datosDetalleFactura[2] = (txtCantidad.getText());
            datosDetalleFactura[3] = (txtIsvPorci.getText());
            datosDetalleFactura[4] = (txtSubTotalDetalle.getText());
            modelo.addRow(datosDetalleFactura);
            tblFacturaDetalle.setModel(modelo);
            subTotal += Double.parseDouble(txtSubTotalDetalle.getText());
            valorIsv += (Double.parseDouble(txtIsvPorci.getText()) * Double.parseDouble(txtSubTotalDetalle.getText())) / 100;
            Total = subTotal + valorIsv;
            txtSubTotal.setText("" + subTotal);
            txtIsvValor.setText("" + valorIsv);
            txtTotal.setText("" + Total);

            cbxTratamiento.setSelectedItem(textSeleccionTratamiento);
            txtPrecio.setText("0");
            txtCantidad.setText("0");
            txtIsvPorci.setText("0");
            txtSubTotalDetalle.setText("0");
        }
    }

    public void CrearFactura() {
        try {
            conectarBD();
            sentenciaSQL = "INSERT INTO factura (idFactura, Doctor, Paciente, Total, Valor_isv, Pagado, Estado, Fecha) VALUES (?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sentenciaSQL);
            ps.setInt(1, 0);
            ps.setString(2, cbxDoctor.getSelectedItem().toString());
            ps.setString(3, cbxPaciente.getSelectedItem().toString());
            ps.setDouble(4, Double.parseDouble(txtTotal.getText()));
            ps.setDouble(5, Double.parseDouble(txtIsvValor.getText()));
            ps.setBoolean(6, _pagado);
            ps.setString(7, "activo");
            ps.setString(8, txtFecha.getText());
            ps.execute();
            con.close();
            IdFactura();
            CrearFacturaDetalle();
            JOptionPane.showMessageDialog(null, "DATOS INGRESADOS CORRECTAMENTE");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO INGRESAR FACTURA " + ex.getMessage());
        }
    }

    public void CrearFacturaDetalle() {
        try {
            int count = tblFacturaDetalle.getRowCount(), isvDetalle = 0, cantidadDetalle = 0;
            double precioDetalle = 0, subTotalDetalle = 0;
            String tratamientoDetalle = "";
            if (count > 0) {
                conectarBD();
                sentenciaSQL = "INSERT INTO facturadetalle (idfacturaDetalle, idFactura, Tratamiento, Cantidad, Precio, Porcentaje_isv, Total, Estado) VALUES (?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement(sentenciaSQL);
                for (int i = 0; i < count; i++) {
                    tratamientoDetalle = modelo.getValueAt(i, 0).toString();
                    precioDetalle = Double.parseDouble(modelo.getValueAt(i, 1).toString());
                    cantidadDetalle = Integer.parseInt(modelo.getValueAt(i, 2).toString());
                    isvDetalle = Integer.parseInt(modelo.getValueAt(i, 3).toString());
                    subTotalDetalle = Double.parseDouble(modelo.getValueAt(i, 4).toString());
                    ps.setInt(1, 0);
                    ps.setInt(2, ID_FACTURA);
                    ps.setString(3, tratamientoDetalle);
                    ps.setInt(4, cantidadDetalle);
                    ps.setDouble(5, precioDetalle);
                    ps.setInt(6, isvDetalle);
                    ps.setDouble(7, subTotalDetalle);
                    ps.setString(8, "Activo");
                    ps.execute();
                }
                con.close();
            } else {
                JOptionPane.showMessageDialog(null, "NO HAY DATOS EN EL DETALLE DE LA FACTURA");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO INGRESAR DETALLE FACTURA " + ex.getMessage());
        }
    }

    public void IdFactura() {
        try {
            conectarBD();
            sentenciaSQL = "SELECT MAX(idFactura) FROM factura";
            ps = con.prepareStatement(sentenciaSQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                ID_FACTURA = rs.getInt(1);
            }
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO OBTENER EL ID FACTURA " + ex.getMessage());
        }
    }

    public void Limpiar() {
        txtId.setText("0");
        cbxDoctor.setSelectedItem(textSeleccionDoctor);
        cbxPaciente.setSelectedItem(textSeleccionPaciente);
    }

    public void LimpiarDetalle(){
        int fila = tblFacturaDetalle.getRowCount();
        for (int i = fila - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }
    
    public void ImprimirFactura() {
        try {
            conectarBD(); 
            Map parametro = new HashMap();
            parametro.put("paramIdFactura", ID_FACTURA);
            JasperReport reporte = null;
            URL urlMaestro = getClass().getResource("ReporteFacturacion.jasper");
            reporte = (JasperReport)JRLoader.loadObject(urlMaestro);
            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, con);
            JasperViewer view = new JasperViewer(jprint, true);
            view.setTitle("REPORTE DE FACTURA");
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            con.close();

        } catch (JRException ex) {
            System.out.print(ex);
        } catch (SQLException ex) {
            System.out.print(ex);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        cbxDoctor = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbxPaciente = new javax.swing.JComboBox<>();
        txtFecha = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFacturaDetalle = new javax.swing.JTable();
        cbxTratamiento = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSubTotalDetalle = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtIsvPorci = new javax.swing.JTextField();
        btnQuitar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtIsvValor = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        Fondo = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Script MT Bold", 1, 40)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Factura");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 160, 60));

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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Factura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel2.setLayout(null);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Doctor:");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(270, 30, 90, 17);

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
        jPanel2.add(btnBuscar);
        btnBuscar.setBounds(210, 20, 40, 30);

        jPanel2.add(cbxDoctor);
        cbxDoctor.setBounds(370, 22, 430, 30);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Id Factura:");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(20, 30, 90, 17);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Fecha:");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(510, 70, 90, 17);

        jPanel2.add(cbxPaciente);
        cbxPaciente.setBounds(120, 60, 370, 30);

        txtFecha.setEditable(false);
        txtFecha.setBackground(new java.awt.Color(255, 255, 255));
        txtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jPanel2.add(txtFecha);
        txtFecha.setBounds(610, 60, 190, 30);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Paciente:");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(20, 70, 90, 17);

        txtId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel2.add(txtId);
        txtId.setBounds(120, 21, 90, 30);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 840, 130));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Detalle", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N
        jPanel3.setLayout(null);

        tblFacturaDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tratamiento", "Precio", "Cantidad", "Isv%", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFacturaDetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFacturaDetalleMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblFacturaDetalle);

        jPanel3.add(jScrollPane2);
        jScrollPane2.setBounds(20, 80, 800, 190);

        cbxTratamiento.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTratamientoItemStateChanged(evt);
            }
        });
        cbxTratamiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbxTratamientoKeyReleased(evt);
            }
        });
        jPanel3.add(cbxTratamiento);
        cbxTratamiento.setBounds(20, 42, 250, 30);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Precio");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(310, 20, 50, 17);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Tratamiento");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(20, 20, 90, 17);

        txtPrecio.setEditable(false);
        txtPrecio.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(txtPrecio);
        txtPrecio.setBounds(280, 41, 80, 30);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Cantidad");
        jPanel3.add(jLabel11);
        jLabel11.setBounds(390, 20, 80, 17);

        txtCantidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadKeyReleased(evt);
            }
        });
        jPanel3.add(txtCantidad);
        txtCantidad.setBounds(370, 41, 100, 30);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Isv %");
        jPanel3.add(jLabel12);
        jLabel12.setBounds(500, 20, 80, 17);

        txtSubTotalDetalle.setEditable(false);
        txtSubTotalDetalle.setBackground(new java.awt.Color(255, 255, 255));
        txtSubTotalDetalle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(txtSubTotalDetalle);
        txtSubTotalDetalle.setBounds(590, 41, 100, 30);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Total");
        jPanel3.add(jLabel13);
        jLabel13.setBounds(610, 20, 80, 17);

        txtIsvPorci.setEditable(false);
        txtIsvPorci.setBackground(new java.awt.Color(255, 255, 255));
        txtIsvPorci.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(txtIsvPorci);
        txtIsvPorci.setBounds(480, 41, 100, 30);

        btnQuitar.setBackground(new java.awt.Color(255, 0, 51));
        btnQuitar.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        btnQuitar.setText("-");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });
        jPanel3.add(btnQuitar);
        btnQuitar.setBounds(770, 30, 50, 40);

        btnAgregar.setBackground(new java.awt.Color(0, 255, 0));
        btnAgregar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnAgregar.setText("+");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel3.add(btnAgregar);
        btnAgregar.setBounds(700, 30, 60, 40);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("SubTotal:");
        jPanel3.add(jLabel14);
        jLabel14.setBounds(640, 280, 80, 17);

        txtSubTotal.setEditable(false);
        txtSubTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtSubTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(txtSubTotal);
        txtSubTotal.setBounds(720, 270, 100, 30);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Isv 15%:");
        jPanel3.add(jLabel15);
        jLabel15.setBounds(640, 310, 80, 17);

        txtIsvValor.setEditable(false);
        txtIsvValor.setBackground(new java.awt.Color(255, 255, 255));
        txtIsvValor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(txtIsvValor);
        txtIsvValor.setBounds(720, 300, 100, 30);

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(txtTotal);
        txtTotal.setBounds(720, 331, 100, 30);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Total:");
        jPanel3.add(jLabel16);
        jLabel16.setBounds(640, 340, 80, 17);

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 840, 370));

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
            int resultDialog = JOptionPane.showConfirmDialog(null, "¿DESEA REALIZAR EL COBRO DE LA FACTURA?", "CONFIRMACION", JOptionPane.YES_NO_OPTION);
            if (resultDialog == JOptionPane.YES_OPTION) {
                _pagado = true;
            }else{
                _pagado = false;
            }
            CrearFactura();
            ImprimirFactura();
            Limpiar();
            LimpiarDetalle();
        }
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed

    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnLeerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeerActionPerformed

    }//GEN-LAST:event_btnLeerActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed

    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblFacturaDetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFacturaDetalleMouseClicked

    }//GEN-LAST:event_tblFacturaDetalleMouseClicked

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if (ValidacionAgregar()) {
            AgregarDetalle();
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyReleased
        CalcularSubTotalDetalle();
    }//GEN-LAST:event_txtCantidadKeyReleased

    private void cbxTratamientoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxTratamientoKeyReleased

    }//GEN-LAST:event_cbxTratamientoKeyReleased

    private void CalcularSubTotalDetalle() {
        double subTotalDet = Double.parseDouble(txtPrecio.getText()) * Double.parseDouble(txtCantidad.getText());
        txtSubTotalDetalle.setText("" + subTotalDet);
    }
    private void cbxTratamientoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTratamientoItemStateChanged
        try {
            conectarBD();
            String tratamientoSelect = cbxTratamiento.getSelectedItem().toString();

            if (!tratamientoSelect.equals(textSeleccionTratamiento)) {
                sentenciaSQL = "SELECT precio, porcentaje_isv FROM tratamiento WHERE estado = 'Activo' AND descripcion = '" + tratamientoSelect + "'";
                ps = con.prepareStatement(sentenciaSQL);
                rs = ps.executeQuery();
                while (rs.next()) {
                    txtPrecio.setText("" + (rs.getDouble(1)));
                    txtIsvPorci.setText("" + (rs.getInt(2)));
                    CalcularSubTotalDetalle();
                }

                /*txtPrecio.setText(""+(rs.getDouble(1)));
                txtIsvPorci.setText(""+(rs.getInt(2)));*/
                //double subtotadet = rs.getDouble("precio") * Double.parseDouble(txtCantidad.getText());
                //txtSubTotalDetalle.setText(""+subtotadet);
            } else {
                txtPrecio.setText("0");
                txtCantidad.setText("0");
                txtIsvPorci.setText("0");
                txtSubTotalDetalle.setText("0");
            }

            /*cbxTratamiento.addItem(textSeleccionTratamiento);
            while (rs.next()) {
                String tmpStrObtenido = rs.getString("descripcion");
                cbxTratamiento.addItem(tmpStrObtenido);
            }*/
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR, NO SE PUDO CARGA LISTA DE TRATAMIENTOS " + ex.getMessage());
        }

    }//GEN-LAST:event_cbxTratamientoItemStateChanged

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        double subTotalDeta = Double.parseDouble(modelo.getValueAt(tblFacturaDetalle.getSelectedRow(), 4).toString());
        double isvPorcetDetalle = Double.parseDouble(modelo.getValueAt(tblFacturaDetalle.getSelectedRow(), 3).toString());
        subTotal -= subTotalDeta;
        valorIsv -= (isvPorcetDetalle * subTotalDeta) / 100;
        Total = subTotal + valorIsv;
        txtSubTotal.setText("" + subTotal);
        txtIsvValor.setText("" + valorIsv);
        txtTotal.setText("" + Total);
        modelo.removeRow(tblFacturaDetalle.getSelectedRow());
    }//GEN-LAST:event_btnQuitarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnHOME;
    private javax.swing.JButton btnLeer;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JComboBox<String> cbxDoctor;
    private javax.swing.JComboBox<String> cbxPaciente;
    private javax.swing.JComboBox<String> cbxTratamiento;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblFacturaDetalle;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JFormattedTextField txtFecha;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIsvPorci;
    private javax.swing.JTextField txtIsvValor;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtSubTotalDetalle;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
