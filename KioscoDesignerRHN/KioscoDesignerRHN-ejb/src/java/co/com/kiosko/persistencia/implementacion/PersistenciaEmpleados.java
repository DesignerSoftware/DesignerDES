package co.com.kiosko.persistencia.implementacion;

import co.com.kiosko.entidades.Empleados;
import co.com.kiosko.persistencia.interfaz.IPersistenciaEmpleados;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Felipe Trivi�o
 */
@Stateless
public class PersistenciaEmpleados implements IPersistenciaEmpleados {

    @Override
    public Empleados consultarEmpleado(EntityManager eManager, BigInteger codigoEmpleado) {
        try {
            eManager.getTransaction().begin();
            String sqlQuery = "SELECT e FROM Empleados e WHERE e.codigoempleado = :codigoEmpleado";
            Query query = eManager.createQuery(sqlQuery);
            query.setParameter("codigoEmpleado", codigoEmpleado);
            Empleados emp = (Empleados) query.getSingleResult();
            eManager.getTransaction().commit();
            return emp;
        } catch (Exception e) {
            System.out.println("Error PersistenciaEmpleados.consultarEmpleado: " + e);
            eManager.getTransaction().rollback();
            return null;
        }
    }
}
