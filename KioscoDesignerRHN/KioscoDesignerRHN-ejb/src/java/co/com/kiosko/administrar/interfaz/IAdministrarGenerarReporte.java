package co.com.kiosko.administrar.interfaz;

import co.com.kiosko.clasesAyuda.ReporteGenerado;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Felipe Trivi�o
 */
public interface IAdministrarGenerarReporte {

    public void obtenerConexion(java.lang.String idSesion);

    public List<ReporteGenerado> consultarReporte(String nombreDirectorio, String codigoEmpleado, Date fechaDesde, Date fechaHasta);

    public boolean modificarConexionKisko(co.com.kiosko.entidades.ConexionesKioskos cnx);

    public boolean enviarCorreo(java.math.BigInteger secuenciaEmpresa, java.lang.String destinatario, java.lang.String asunto, java.lang.String mensaje, List<ReporteGenerado> pathArchivos);
    
    public boolean comprobarConfigCorreo(BigInteger secuenciaEmpresa);
    
}
