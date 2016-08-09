package co.com.kiosko.administrar.implementacion;

import co.com.kiosko.entidades.ConexionesKioskos;
import co.com.kiosko.entidades.ConfiguracionCorreo;
import co.com.kiosko.entidades.Generales;
import co.com.kiosko.administrar.interfaz.IAdministrarGenerarReporte;
import co.com.kiosko.administrar.interfaz.IAdministrarSesiones;
import co.com.kiosko.clasesAyuda.ReporteGenerado;
import co.com.kiosko.persistencia.interfaz.IPersistenciaConexionesKioskos;
import co.com.kiosko.persistencia.interfaz.IPersistenciaConfiguracionCorreo;
import co.com.kiosko.persistencia.interfaz.IPersistenciaGenerales;
import co.com.kiosko.correo.EnvioCorreo;
import co.com.kiosko.reportes.IniciarReporteInterface;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 *
 * @author Felipe Triviño
 */
@Stateful
public class AdministrarGenerarReporte implements IAdministrarGenerarReporte {

    @EJB
    private IAdministrarSesiones administrarSesiones;
    @EJB
    private IPersistenciaGenerales persistenciaGenerales;
    @EJB
    private IPersistenciaConexionesKioskos persistenciaConexionesKioskos;
    @EJB
    private IPersistenciaConfiguracionCorreo persistenciaConfiguracionCorreo;
    @EJB
    private IniciarReporteInterface reporte;
    private EntityManager em;

    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }

    @Override
    public List<ReporteGenerado> consultarReporte(String nombreDirectorio, String codigoEmpleado, Date fechaDesde, Date fechaHasta) {
        try {
            Generales general = persistenciaGenerales.consultarRutasGenerales(em);
            String rutaReporte = general.getPathreportes();
            List<ReporteGenerado> reportes = new ArrayList<ReporteGenerado>();

            File folder = new File(rutaReporte + nombreDirectorio);
            File[] listOfFiles = folder.listFiles();
            SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    String archivo = listOfFile.getName();
                    String archivoSinExt = archivo.substring(0, archivo.lastIndexOf("."));
                    String fecha = archivoSinExt.substring(archivoSinExt.length() - 8);
                    if (archivo.startsWith(codigoEmpleado)
                            && (fechaDesde.compareTo(formato.parse(fecha)) <= 0 && fechaHasta.compareTo(formato.parse(fecha)) >= 0)) {
                        ReporteGenerado infoReporte = new ReporteGenerado();
                        infoReporte.setNombre(archivo);
                        infoReporte.setRuta(rutaReporte + nombreDirectorio + File.separator + listOfFile.getName());
                        reportes.add(infoReporte);
                    }
                }
            }
            return reportes;
        } catch (Exception ex) {
            System.out.println("Error AdministrarGenerarReporte.consultarReporte: " + ex);
            return null;
        }
    }

    @Override
    public boolean modificarConexionKisko(ConexionesKioskos cnx) {
        return persistenciaConexionesKioskos.registrarConexion(em, cnx);
    }

    @Override
    public boolean enviarCorreo(BigInteger secuenciaEmpresa, String destinatario, String asunto, String mensaje, String pathAdjunto) {
        ConfiguracionCorreo cc = persistenciaConfiguracionCorreo.consultarConfiguracionServidorCorreo(em, secuenciaEmpresa);
        EnvioCorreo enviarCorreo = new EnvioCorreo();
        return enviarCorreo.enviarCorreo(cc, destinatario, asunto, mensaje, pathAdjunto);
    }

    @Override
    public boolean comprobarConfigCorreo(BigInteger secuenciaEmpresa) {
        boolean retorno = false;
        try {
            ConfiguracionCorreo cc = persistenciaConfiguracionCorreo.consultarConfiguracionServidorCorreo(em, secuenciaEmpresa);
            if (cc.getServidorSmtp().length() != 0) {
                retorno = true;
            } else {
                retorno = false;
            }
        } catch (NullPointerException npe) {
            retorno = false;
        } catch (Exception e) {
            System.out.println("AdministrarGenerarReporte.comprobarConfigCorreo");
            System.out.println("Error validando configuracion");
            System.out.println("ex: " + e);
        }
        return retorno;
    }
}
