package co.com.kiosko.persistencia.interfaz;

/**
 *
 * @author Felipe Trivi�o
 */
public interface IPersistenciaEmpleados {

    public co.com.kiosko.administrar.entidades.Empleados consultarEmpleado(javax.persistence.EntityManager eManager, java.math.BigInteger codigoEmpleado);
    
}
