package co.com.kiosko.administrar.interfaz;

import co.com.kiosko.clasesAyuda.ReporteGenerado;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Felipe Triviño
 */
public interface IAdministrarGenerarReporte {

    public void obtenerConexion(java.lang.String idSesion);

    public List<ReporteGenerado> consultarReporte(String nombreDirectorio, String codigoEmpleado, Date fechaDesde, Date fechaHasta);

    public boolean modificarConexionKisko(co.com.kiosko.entidades.ConexionesKioskos cnx);

    public boolean enviarCorreo(java.math.BigInteger secuenciaEmpresa, java.lang.String destinatario, java.lang.String asunto, java.lang.String mensaje, java.lang.String pathAdjunto);
    
    public boolean comprobarConfigCorreo(BigInteger secuenciaEmpresa);
    
}
