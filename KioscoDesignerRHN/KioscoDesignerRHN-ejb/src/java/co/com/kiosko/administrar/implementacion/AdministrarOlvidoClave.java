package co.com.kiosko.administrar.implementacion;

import co.com.kiosko.entidades.ConexionesKioskos;
import co.com.kiosko.entidades.ParametrizaClave;
import co.com.kiosko.administrar.interfaz.IAdministrarOlvidoClave;
import co.com.kiosko.administrar.interfaz.IAdministrarSesiones;
import co.com.kiosko.persistencia.interfaz.IPersistenciaConexionesKioskos;
import co.com.kiosko.persistencia.interfaz.IPersistenciaParametrizaClave;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 *
 * @author Felipe Triviño
 */
@Stateful
public class AdministrarOlvidoClave implements IAdministrarOlvidoClave {

    @EJB
    private IAdministrarSesiones administrarSesiones;
    @EJB
    private IPersistenciaConexionesKioskos persistenciaConexionesKioskos;
    @EJB
    private IPersistenciaParametrizaClave persistenciaParametrizaClave;
    private EntityManager em;

    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }

    @Override
    public ConexionesKioskos obtenerConexionEmpleado(String codigoEmpleado, String nitEmpresa) {
        return persistenciaConexionesKioskos.consultarConexionEmpleado(em, codigoEmpleado, Long.parseLong(nitEmpresa));
    }

    @Override
    public boolean validarRespuestas(String respuesta1, String respuesta2, String codigoEmpleado, String nitEmpresa) {
        return persistenciaConexionesKioskos.validarRespuestas(em, respuesta1, respuesta2, codigoEmpleado, nitEmpresa);
    }

    @Override
    public boolean cambiarClave(ConexionesKioskos ck) {
        return persistenciaConexionesKioskos.registrarConexion(em, ck);
    }

    @Override
    public ParametrizaClave obtenerFormatoClave(long nitEmpresa) {
        return persistenciaParametrizaClave.obtenerFormatoClave(em, nitEmpresa);
    }
    
    @Override
    public boolean validarClave(String pwd, String codigoEmpleado, String nitEmpresa) {
        return persistenciaConexionesKioskos.validarClave(em, pwd, codigoEmpleado, nitEmpresa);
    }
}
