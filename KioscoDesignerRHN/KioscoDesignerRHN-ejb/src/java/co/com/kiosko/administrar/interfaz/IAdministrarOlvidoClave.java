package co.com.kiosko.administrar.interfaz;

/**
 *
 * @author Felipe Triviño
 */
public interface IAdministrarOlvidoClave {

    public void obtenerConexion(java.lang.String idSesion);

    public co.com.kiosko.entidades.ConexionesKioskos obtenerConexionEmpleado(java.lang.String codigoEmpleado, String nitEmpresa);

    public boolean validarRespuestas(String respuesta1, String respuesta2, String codigoEmpleado, String nitEmpresa);

    public boolean cambiarClave(co.com.kiosko.entidades.ConexionesKioskos ck);

    public co.com.kiosko.entidades.ParametrizaClave obtenerFormatoClave(long nitEmpresa);

    public boolean validarClave(String pwd, String codigoEmpleado, String nitEmpresa);

}
