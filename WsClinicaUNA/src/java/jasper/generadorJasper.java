/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasper;

import cr.ac.una.wsclinicauna.util.CodigoRespuesta;
import cr.ac.una.wsclinicauna.util.Respuesta;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;   
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Carlos Olivares
 */
public class generadorJasper {

    public JasperPrint print;

    public generadorJasper() {
    }

    public Respuesta generaReporte(String fechaIni, String fechaFin, String folio, Connection connection) {
        try {
            Map parametro = new HashMap();
            parametro.put("fechaIni", fechaIni);
            parametro.put("fechaFin", fechaFin);
            parametro.put("folio", folio);
            parametro.put("ID", "");
            JasperReport reporte = null;
            System.out.println("Reporte " + generadorJasper.class.getResource("/jasper/fechas.jasper"));
            reporte = (JasperReport) JRLoader.loadObject(generadorJasper.class.getResource("/jasper/fechas.jasper"));

            JasperPrint j = JasperFillManager.fillReport(reporte, parametro, connection);
            print = j;
            // JasperViewer viewer;

            connection.close();
            Respuesta respuesta = CrearPdf(print, "agenda");
            return respuesta;
        } catch (SQLException | JRException e) {
            Logger.getLogger(generadorJasper.class.getName()).log(Level.SEVERE, null, e);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Error generando Reporte", e.getMessage());
        }
    }

    public Respuesta CrearPdf(JasperPrint j, String reporte) {
        try {
            /*
            Creo el pdf
             */
            //Guardo el pdf en el archivo
            File archivo;
            if (reporte.equals("agenda")) {
                archivo = new File("ReporteAgenda.pdf");
            } else {
                archivo = new File("ReportePaciente.pdf");
            }

            JasperExportManager.exportReportToPdfFile(j, archivo.getAbsolutePath());
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "Reporte Generado exitosamente", "");
        } catch (JRException ex) {
            Logger.getLogger(generadorJasper.class.getName()).log(Level.SEVERE, null, ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Error generando reporte", ex.getMessage());
        }
    }

    public Respuesta generaReporte(String cedula, Connection connection) {

        try {
            Map parametro = new HashMap();
            parametro.put("cedula", cedula);
            File file = new File("graficoIMC.jasper");
            
            parametro.put("SUBREPORT_DIR",generadorJasper.class.getResource("/jasper/").toString());
            
            JasperReport reporte = null;
            //System.out.println(generadorJasper.class.getResource("/jasper/controlMedico.jasper"));
            reporte = (JasperReport) JRLoader.loadObject(generadorJasper.class.getResource("/jasper/controlMedico.jasper"));
            JasperPrint j = JasperFillManager.fillReport(reporte, parametro, connection);
            print = j;
            // JasperViewer viewer;

            connection.close();
            Respuesta respuesta = CrearPdf(print, "control");
            return respuesta;
        } catch (SQLException | JRException e) {
            Logger.getLogger(generadorJasper.class.getName()).log(Level.SEVERE, null, e);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Error generando Reporte", e.getMessage());
        }
    }

}
