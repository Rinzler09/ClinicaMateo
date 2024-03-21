package Reportes;

import proyectofinal2023.*;
import java.awt.Color;
import ConexionDB.ConexionDB;
import java.net.URL;
import java.sql.*;
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

/**
 *
 * @author Milton Paz
 */
public class BotonesReportes extends javax.swing.JInternalFrame {

    String especialidad;
    Connection con = null;
    ConexionDB conecta;

    public BotonesReportes() {
        initComponents();

    }

    public void conectarBD() {
        conecta = new ConexionDB("clinica_mateo");
        con = conecta.getConexion();
    }

    public void reportes(String nombre) {
        try {
            this.conectarBD();//conectamos la BD
            JasperReport reporte = null;
//RUTA DEL ARCHIVO
            URL urlMaestro = getClass().getResource(nombre);
//LLAMADO DEL ARCHIVO
            reporte = (JasperReport) JRLoader.loadObject(urlMaestro);
//LLENADO DEL REPORTE
            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, con);
//VISTA DEL REPORTE
            JasperViewer view = new JasperViewer(jprint, false);
//TITULO
            view.setTitle("REPORTE DE DOCTORES");
//CIERRE DEL REPORTE 
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            con.close();
        } catch (JRException ex) {
            System.out.print(ex);
        } catch (SQLException ex) {
            System.out.print(ex);
        }

    }

    public void reportePorEspecialidad() {
        especialidad = JOptionPane.showInputDialog("INGRESE LA ESPECIALIDAD A BUSCAR:");
        try {
            this.conectarBD();//conectamos la BD
            JasperReport reporte = null;
            Map parametro = new HashMap();

            parametro.put("especialidad", especialidad);
//RUTA DEL ARCHIVO
            URL urlMaestro = getClass().getResource("ReporteDocPorEsp.jasper");
//LLAMADO DEL ARCHIVO
            reporte = (JasperReport) JRLoader.loadObject(urlMaestro);
//LLENADO DEL REPORTE
            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, con);
//VISTA DEL REPORTE
            JasperViewer view = new JasperViewer(jprint, false);
//TITULO
            view.setTitle("REPORTE DE DOCTORES POR ESPECIALIDAD");
//CIERRE DEL REPORTE 
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            con.close();
        } catch (JRException ex) {
            System.out.print(ex);
        } catch (SQLException ex) {
            System.out.print(ex);
        }

    }

    public void ImprimirFactura() {
        int ID_FACTURA = 15;
        try {
            conectarBD();
            Map parametro = new HashMap();
            parametro.put("paramIdFactura", ID_FACTURA);
            JasperReport reporte = null;
            URL urlMaestro = getClass().getResource("ReporteFacturacion.jasper");
            reporte = (JasperReport) JRLoader.loadObject(urlMaestro);
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
        btnR_DOC = new javax.swing.JButton();
        btnR_DOC1 = new javax.swing.JButton();
        btnR_DOC2 = new javax.swing.JButton();
        btnReporteFacturas = new javax.swing.JButton();
        btnReportePacientes = new javax.swing.JButton();
        btnReportePermisos1 = new javax.swing.JButton();
        btnReporteUsuarios = new javax.swing.JButton();
        Fondo = new javax.swing.JLabel();
        btnR_DOC3 = new javax.swing.JButton();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Script MT Bold", 1, 40)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Reportes de Sistema");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 460, 60));

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

        btnR_DOC.setBackground(new java.awt.Color(0, 0, 0));
        btnR_DOC.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnR_DOC.setForeground(new java.awt.Color(153, 255, 204));
        btnR_DOC.setText("REPORTE DOCTORES");
        btnR_DOC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR_DOCActionPerformed(evt);
            }
        });
        jPanel1.add(btnR_DOC, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, 360, 40));

        btnR_DOC1.setBackground(new java.awt.Color(0, 0, 0));
        btnR_DOC1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnR_DOC1.setForeground(new java.awt.Color(153, 255, 204));
        btnR_DOC1.setText("REPORTE DOCTOR POR ESPECIALIDAD");
        btnR_DOC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR_DOC1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnR_DOC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 320, 360, 40));

        btnR_DOC2.setBackground(new java.awt.Color(0, 0, 0));
        btnR_DOC2.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnR_DOC2.setForeground(new java.awt.Color(153, 255, 204));
        btnR_DOC2.setText("REPORTE TRATAMIENTOS");
        btnR_DOC2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR_DOC2ActionPerformed(evt);
            }
        });
        jPanel1.add(btnR_DOC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 200, 360, 40));

        btnReporteFacturas.setBackground(new java.awt.Color(0, 0, 0));
        btnReporteFacturas.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnReporteFacturas.setForeground(new java.awt.Color(153, 255, 204));
        btnReporteFacturas.setText("REPORTE FACTURAS");
        btnReporteFacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteFacturasActionPerformed(evt);
            }
        });
        jPanel1.add(btnReporteFacturas, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, 360, 40));

        btnReportePacientes.setBackground(new java.awt.Color(0, 0, 0));
        btnReportePacientes.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnReportePacientes.setForeground(new java.awt.Color(153, 255, 204));
        btnReportePacientes.setText("REPORTE DE PACIENTES");
        btnReportePacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportePacientesActionPerformed(evt);
            }
        });
        jPanel1.add(btnReportePacientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 500, 360, 40));

        btnReportePermisos1.setBackground(new java.awt.Color(0, 0, 0));
        btnReportePermisos1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnReportePermisos1.setForeground(new java.awt.Color(153, 255, 204));
        btnReportePermisos1.setText("REPORTE DE PERMISOS");
        btnReportePermisos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportePermisos1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnReportePermisos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 440, 360, 40));

        btnReporteUsuarios.setBackground(new java.awt.Color(0, 0, 0));
        btnReporteUsuarios.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnReporteUsuarios.setForeground(new java.awt.Color(153, 255, 204));
        btnReporteUsuarios.setText("REPORTE DE USUARIOS");
        btnReporteUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteUsuariosActionPerformed(evt);
            }
        });
        jPanel1.add(btnReporteUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 380, 360, 40));

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo1.png"))); // NOI18N
        jPanel1.add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 1110, 670));

        btnR_DOC3.setBackground(new java.awt.Color(0, 0, 0));
        btnR_DOC3.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnR_DOC3.setForeground(new java.awt.Color(153, 255, 204));
        btnR_DOC3.setText("REPORTE DOCTORES");
        btnR_DOC3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR_DOC3ActionPerformed(evt);
            }
        });
        jPanel1.add(btnR_DOC3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, 360, 40));

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

    private void btnR_DOCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR_DOCActionPerformed
        reportes("ReporteDoctor.jasper");
    }//GEN-LAST:event_btnR_DOCActionPerformed

    private void btnR_DOC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR_DOC1ActionPerformed
        this.reportePorEspecialidad();
    }//GEN-LAST:event_btnR_DOC1ActionPerformed

    private void btnR_DOC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR_DOC2ActionPerformed
        reportes("ReporteTratamientos.jasper");
    }//GEN-LAST:event_btnR_DOC2ActionPerformed

    private void btnReporteFacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteFacturasActionPerformed
        reportes("ReporteFacturas.jasper");
    }//GEN-LAST:event_btnReporteFacturasActionPerformed

    private void btnR_DOC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR_DOC3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnR_DOC3ActionPerformed

    private void btnReportePacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportePacientesActionPerformed
        reportes("ReportePermisos.jasper");
    }//GEN-LAST:event_btnReportePacientesActionPerformed

    private void btnReporteUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteUsuariosActionPerformed
        reportes("ReporteUsuarios.jasper");
    }//GEN-LAST:event_btnReporteUsuariosActionPerformed

    private void btnReportePermisos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportePermisos1ActionPerformed
        reportes("ReportePacientes.jasper");
    }//GEN-LAST:event_btnReportePermisos1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton btnHOME;
    private javax.swing.JButton btnR_DOC;
    private javax.swing.JButton btnR_DOC1;
    private javax.swing.JButton btnR_DOC2;
    private javax.swing.JButton btnR_DOC3;
    private javax.swing.JButton btnReporteFacturas;
    private javax.swing.JButton btnReportePacientes;
    private javax.swing.JButton btnReportePermisos1;
    private javax.swing.JButton btnReporteUsuarios;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
