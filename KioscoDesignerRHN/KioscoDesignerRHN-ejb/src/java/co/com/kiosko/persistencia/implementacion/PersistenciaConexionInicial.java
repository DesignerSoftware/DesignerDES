package co.com.kiosko.persistencia.implementacion;

import co.com.kiosko.persistencia.interfaz.IPersistenciaConexionInicial;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

@Stateless
public class PersistenciaConexionInicial implements IPersistenciaConexionInicial {

    @Override
    public void setearKiosko(EntityManager eManager) {
        eManager.getTransaction().begin();
        //String sqlQuery = "SET ROLE ROLKIOSKO IDENTIFIED BY RLKSK";
        String sqlQuery = "SET ROLE 'ROLKIOSKO'";
        Query query = eManager.createNativeQuery(sqlQuery);
        query.executeUpdate();
        eManager.getTransaction().commit();
    }

    @Override
    public boolean validarUsuarioyEmpresa(EntityManager eManager, String usuario, String nitEmpresa) {
        try {
            eManager.getTransaction().begin();
            String sqlQuery = "SELECT COUNT(*) FROM EMPLEADOS e, Empresas em WHERE e.empresa = em.secuencia AND e.codigoempleado = ? AND em.nit = ?";
            Query query = eManager.createNativeQuery(sqlQuery);
            query.setParameter(1, new BigInteger(usuario));
            query.setParameter(2, new Long(nitEmpresa));
            Long retorno = (Long) query.getSingleResult();
            eManager.getTransaction().commit();
            if (retorno > 0) {
                //System.out.println("El usuario existe y corresponde a la empresa seleccionada.");
                return true;
            } else {
                //System.out.println("El usuario no existe ó no corresponde a la empresa seleccionada.");
                eManager.getEntityManagerFactory().close();
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error PersistenciaConexionInicial.validarUsuarioyEmpresa: " + e);
            return false;
        }
    }

    @Override
    public boolean validarUsuarioRegistrado(EntityManager eManager, String usuario) {
        try {
            eManager.getTransaction().begin();
            String sqlQuery = "SELECT COUNT(*) FROM CONEXIONESKIOSKOS ck, EMPLEADOS e WHERE ck.EMPLEADO = e.SECUENCIA AND e.codigoempleado = ?";
            Query query = eManager.createNativeQuery(sqlQuery);
            query.setParameter(1, new BigInteger(usuario));
            Long retorno = (Long) query.getSingleResult();
            eManager.getTransaction().commit();
            return retorno > 0; 
            //System.out.println("El usuario está registrado.");
            //System.out.println("El usuario no esta registrado");
        } catch (Exception e) {
            System.out.println("Error PersistenciaConexionInicial.validarUsuarioRegistrado: " + e);
            return false;
        }
    }

    @Override
    public boolean validarEstadoUsuario(EntityManager eManager, String usuario) {
        try {
            eManager.getTransaction().begin();
            String sqlQuery = "SELECT COUNT(*) FROM CONEXIONESKIOSKOS ck, EMPLEADOS e WHERE ck.EMPLEADO = e.SECUENCIA AND e.codigoempleado = ? AND ck.activo = 'N'";
            Query query = eManager.createNativeQuery(sqlQuery);
            query.setParameter(1, new BigInteger(usuario));
            Long retorno = (Long) query.getSingleResult();
            eManager.getTransaction().commit();
            return retorno <= 0;
            //System.out.println("El usuario esta bloqueado.");
            //System.out.println("El usuario esta activo");
        } catch (Exception e) {
            System.out.println("Error PersistenciaConexionInicial.validarEstadoUsuario: " + e);
            return false;
        }
    }

    @Override
    public boolean validarIngresoUsuarioRegistrado(EntityManager eManager, String usuario, String clave) {
        try {
            eManager.getTransaction().begin();
            //String sqlQuery = "SELECT COUNT(*) FROM CONEXIONESKIOSKOS ck, EMPLEADOS e WHERE ck.EMPLEADO = e.SECUENCIA AND e.codigoempleado = ? AND ck.PWD = GENERALES_PKG.ENCRYPT(?)";
            String sqlQuery = "SELECT COUNT(*) FROM CONEXIONESKIOSKOS ck, EMPLEADOS e WHERE ck.EMPLEADO = e.SECUENCIA AND e.codigoempleado = ? AND ck.PWD = crypt(?, ck.PWD)";
            //SELECT 'x' AS pswmatch FROM CIUDADES WHERE psw = crypt('holanda', psw);
            Query query = eManager.createNativeQuery(sqlQuery);
            query.setParameter(1, new BigInteger(usuario));
            query.setParameter(2, clave);
            Long retorno = (Long) query.getSingleResult();
            eManager.getTransaction().commit();
            return retorno > 0; 
            //System.out.println("El usuario y clave son correctos.");
            //System.out.println("El usuario o clave son incorrectos");
        } catch (Exception e) {
            System.out.println("Error PersistenciaConexionInicial.validarIngresoUsuarioRegistrado: " + e);
            return false;
        }
    }

    @Override
    public EntityManager validarConexionUsuario(EntityManagerFactory emf) {
        try {
            EntityManager eManager = emf.createEntityManager();
            if (eManager.isOpen()) {
                return eManager;
            }
        } catch (Exception e) {
            System.out.println("Error PersistenciaConexionInicial.validarConexionUsuario : " + e);
            emf.close();
        }
        return null;
    }
}
