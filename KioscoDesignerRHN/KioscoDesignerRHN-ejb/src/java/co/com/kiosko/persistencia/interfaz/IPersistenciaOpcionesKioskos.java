package co.com.kiosko.persistencia.interfaz;

/**
 *
 * @author Felipe Trivi�o
 */
public interface IPersistenciaOpcionesKioskos {

    public java.util.List<co.com.kiosko.entidades.OpcionesKioskos> consultarOpcionesPorPadre(javax.persistence.EntityManager eManager, java.math.BigDecimal secuenciaPadre, java.math.BigDecimal secuenciaEmpresa);
    
}
