package co.com.kiosko.reportes;

import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author Felipe Trivi�o
 */
public interface IniciarReporteInterface {

    public String ejecutarReporte(String nombreReporte, String rutaReporte, String rutaGenerado, String nombreArchivo, String tipoReporte, Map parametros, EntityManager em);

    public void cerrarConexion();

    public void inicarC(EntityManager em);
}
