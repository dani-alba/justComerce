package Datos;

import Modelo.Trabajador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class ConexionBD {

    public static Connection conexion;
    public static Trabajador actualUser;
    private String mensajeErrorConexion;
    private TrabajadorDAO trabajadorDAO;
    private TiendaDAO tiendaDAO;

    public ConexionBD() {
    }

    public ConexionBD(Connection conn) {
        conexion = conn;
    }

    public boolean conectar(String bd, String user, String pwd, String AppUser) {
        boolean conectado = false;
        String mensaje = "";
        try {
            conexion = DriverManager.getConnection(bd, user, pwd);
            if (conexion != null) {
                conectado = true;
            }
            trabajadorDAO = new TrabajadorDAO(conexion);
            tiendaDAO = new TiendaDAO(conexion);
            this.actualUser = trabajadorDAO.cargarTrabajador(AppUser, 0, 0);

            mensaje = "Conexión establecida con la Base de Datos " + bd;
            this.mensajeErrorConexion = mensaje;

        } catch (SQLException ex) {

        }
        return conectado;
    }

    public static boolean desconectar() {
        boolean desconectado = false;

        try {
            conexion.close();
            desconectado = true;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return desconectado;

    }

    public boolean existe(String user, String pass) { // BORRAR

        PreparedStatement psExiste;
        ResultSet resultado;
        boolean existe = false;

        try {
            psExiste = conexion.prepareStatement("SELECT existeTrabajador(?,?) AS 'existe';");
            psExiste.setString(1, user);
            psExiste.setString(2, pass);
            resultado = psExiste.executeQuery();
            resultado.next();
            existe = resultado.getBoolean("existe");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return existe;
    }

    public String puesto(String user) {
        PreparedStatement psPuesto;
        ResultSet resultado;
        String puesto = null;

        try {
            psPuesto = conexion.prepareStatement("SELECT puesto FROM trabajadores WHERE nick = ?;");
            psPuesto.setString(1, user);

            resultado = psPuesto.executeQuery();
            resultado.next();
            puesto = resultado.getString("puesto");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return puesto;
    }

    public boolean cambiarContraseña(String user) {

        PreparedStatement psExiste;
        ResultSet resultado;
        boolean existe = false;
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String dni;

        try {
            psExiste = conexion.prepareStatement("SELECT dni FROM trabajadores WHERE nick = ?;");
            psExiste.setString(1, user);
            resultado = psExiste.executeQuery();
            resultado.next();
            dni = resultado.getString("dni");
            if (passwordEncryptor.checkPassword(dni, contraseña(user))) {
                existe = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return existe;
    }

    public String contraseña(String user) {
        PreparedStatement psContraseña;
        ResultSet resultado;
        String contraseña = null;

        try {
            psContraseña = conexion.prepareStatement("SELECT password FROM trabajadores WHERE nick = ?;");
            psContraseña.setString(1, user);

            resultado = psContraseña.executeQuery();
            resultado.next();
            contraseña = resultado.getString("password");
        } catch (SQLException ex) {
            
        }
        return contraseña;

    }

    // * * * * * * * * * * GET AND SET * * * * * * * * * * 
    public String getMsgErrorConexion() {
        return mensajeErrorConexion;
    }

    public Connection getConexion() {
        return conexion;
    }

    public Trabajador getConectado() {
        return actualUser;
    }

    public void setConectado(Trabajador conectado) {
        this.actualUser = conectado;
    }

}
