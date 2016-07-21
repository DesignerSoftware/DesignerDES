package co.com.kiosko.persistencia.implementacion;

import co.com.kiosko.entidades.ConexionesKioskos;
import co.com.kiosko.persistencia.interfaz.IPersistenciaConexionesKioskos;
import java.math.BigInteger;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Felipe Triviño
 */
@Stateless
public class PersistenciaConexionesKioskos implements IPersistenciaConexionesKioskos {

    @Override
    public boolean registrarConexion(EntityManager eManager, ConexionesKioskos cnk) {
        cnk.setUltimaconexion(new Date());
        eManager.clear();
        EntityTransaction tx = eManager.getTransaction();
        try {
            tx.begin();
            eManager.merge(cnk);
            tx.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error PersistenciaConexionesKioskos.registrarConexion: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
            return false;
        }
    }

    @Override
    public ConexionesKioskos consultarConexionEmpleado(EntityManager eManager, String codigoEmpleado, long nitEmpresa) {
        try {
            eManager.getTransaction().begin();
            String sqlQuery = "SELECT ck FROM ConexionesKioskos ck WHERE ck.empleado.codigoempleado = :codigoEmpleado and ck.empleado.empresa.nit = :nitEmpresa";
            Query query = eManager.createQuery(sqlQuery);
            query.setParameter("codigoEmpleado", new BigInteger(codigoEmpleado));
            query.setParameter("nitEmpresa", nitEmpresa);
            eManager.getTransaction().commit();
            return (ConexionesKioskos) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error PersistenciaConexionesKioskos.consultarConexionEmpleado: " + e);
            eManager.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public boolean validarRespuestas(EntityManager eManager, String respuesta1, String respuesta2, String codigoEmpleado, String nitEmpresa) {
        try {
            eManager.getTransaction().begin();
            String sqlQuery = "SELECT COUNT(ck.*) FROM ConexionesKioskos ck, Empleados e, Empresas em WHERE ck.empleado = e.secuencia AND e.empresa = em.secuencia AND e.codigoempleado = ? AND em.nit = ? and ck.respuesta1 = crypt(?, ck.respuesta1) and ck.respuesta2 = crypt(?, ck.respuesta2) ";
            Query query = eManager.createNativeQuery(sqlQuery);
            query.setParameter(1, new BigInteger(codigoEmpleado));
            query.setParameter(2, new Long(nitEmpresa));
            query.setParameter(3, respuesta1.toUpperCase());
            query.setParameter(4, respuesta2.toUpperCase());
            eManager.getTransaction().commit();
            return (Long) query.getSingleResult() > 0;
        } catch (Exception e) {
            System.out.println("Error PersistenciaConexionesKioskos.validarRespuestas: " + e);
            eManager.getTransaction().rollback();
            return false;
        }
    }
    
    @Override
    public boolean validarClave(EntityManager eManager, String pwd, String codigoEmpleado, String nitEmpresa) {
        try {
            eManager.getTransaction().begin();
            String sqlQuery = "SELECT COUNT(ck.*) FROM ConexionesKioskos ck, Empleados e, Empresas em WHERE ck.empleado = e.secuencia AND e.empresa = em.secuencia AND e.codigoempleado = ? AND em.nit = ? AND ck.pwd = crypt(?, ck.pwd) ";
            Query query = eManager.createNativeQuery(sqlQuery);
            query.setParameter(1, new BigInteger(codigoEmpleado));
            query.setParameter(2, new Long(nitEmpresa));
            query.setParameter(3, pwd);
            eManager.getTransaction().commit();
            return (Long) query.getSingleResult() > 0;
        } catch (Exception e) {
            System.out.println("Error PersistenciaConexionesKioskos.validarClave: " + e);
            eManager.getTransaction().rollback();
            return false;
        }
    }
}
