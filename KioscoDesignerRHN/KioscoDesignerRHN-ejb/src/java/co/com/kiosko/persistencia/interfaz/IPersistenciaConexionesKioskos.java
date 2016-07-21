package co.com.kiosko.persistencia.interfaz;

import javax.persistence.EntityManager;

/**
 *
 * @author Felipe Triviño
 */
public interface IPersistenciaConexionesKioskos {

    public boolean registrarConexion(javax.persistence.EntityManager eManager, co.com.kiosko.entidades.ConexionesKioskos cnk);

    public co.com.kiosko.entidades.ConexionesKioskos consultarConexionEmpleado(javax.persistence.EntityManager eManager, java.lang.String codigoEmpleado, long nitEmpresa);

    public boolean validarRespuestas(EntityManager eManager, String respuesta1, String respuesta2, String codigoEmpleado, String nitEmpresa);
    
    public boolean validarClave(EntityManager eManager, String pwd, String codigoEmpleado, String nitEmpresa);

}
