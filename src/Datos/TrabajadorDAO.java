/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Modelo.Trabajador;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class TrabajadorDAO {

    Connection conexion;

    public TrabajadorDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Trabajador trabajador) throws SQLException {
        String apellido1 = trabajador.getApellido1(), apellido2 = trabajador.getApellido2();
        LocalDate fecha = trabajador.getFechaAlta();
//        insertarTrabajador(p_id INT(5),p_dni VARCHAR(9), p_nombre VARCHAR(45),p_apellido1 VARCHAR(45),
// p_apellido2 VARCHAR(45), p_puesto VARCHAR(30), p_salario DECIMAL(8,2), p_fecha DATE, p_nick VARCHAR(45),
// p_pass VARCHAR(45), p_horaEntrada TIME, p_horaSalida TIME, p_idTienda INT(5))
        PreparedStatement psInsertar;
        psInsertar = conexion.prepareStatement("INSERT INTO trabajadores VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);");
        psInsertar.setInt(1, trabajador.getId());
        psInsertar.setString(2, trabajador.getDni());
        psInsertar.setString(3, trabajador.getNombre());
        if (apellido1 == null || apellido1.isEmpty()) {
            psInsertar.setString(4, null);
        } else {
            psInsertar.setString(4, apellido1);
        }
        if (apellido2 == null || apellido2.isEmpty()) {
            psInsertar.setString(5, null);
        } else {
            psInsertar.setString(5, apellido2);
        }
        psInsertar.setString(6, trabajador.getPuesto());
        psInsertar.setDouble(7, trabajador.getSalarioBrutoAnual());
        if (fecha == null) {
            psInsertar.setDate(8, null);
        } else {
            psInsertar.setDate(8, Date.valueOf(fecha));
        }
        psInsertar.setString(9, trabajador.getNick());
        psInsertar.setString(10, trabajador.getPass());
        psInsertar.setTime(11, Time.valueOf(trabajador.getHoraEntrada()));
        psInsertar.setTime(12, Time.valueOf(trabajador.getHoraSalida()));
        psInsertar.setInt(13, trabajador.getIdTienda());
        psInsertar.executeUpdate();
    }

    public int mostrarSiguienteID() throws SQLException {
        PreparedStatement psMostrar;
        psMostrar = conexion.prepareStatement("SELECT MAX(idTrabajador) AS 'id' FROM trabajadores;");
        ResultSet resultado = psMostrar.executeQuery();
        resultado.next();
        return (resultado.getInt("id") + 1);
    }

    public List<Trabajador> cargarDatos() throws SQLException {
        PreparedStatement psTrabajadores;
        ResultSet rsTrabajadores;
        Trabajador trabajador;
        List<Trabajador> listaTrabajadores = new ArrayList<>();
        psTrabajadores = conexion.prepareStatement("SELECT * FROM trabajadores;");
        rsTrabajadores = psTrabajadores.executeQuery();
        while (rsTrabajadores.next()) {

            trabajador = new Trabajador(rsTrabajadores.getInt("idTrabajador"),
                    rsTrabajadores.getString("dni"),
                    rsTrabajadores.getString("nombre"),
                    rsTrabajadores.getString("apellido1"),
                    rsTrabajadores.getString("apellido2"),
                    rsTrabajadores.getString("puesto"),
                    rsTrabajadores.getDouble("salarioBrutoAnual"),
                    rsTrabajadores.getDate("fechaAlta").toLocalDate(),
                    rsTrabajadores.getString("nick"),
                    rsTrabajadores.getString("password"),
                    rsTrabajadores.getTime("horaEntrada").toLocalTime(),
                    rsTrabajadores.getTime("horaSalida").toLocalTime(),
                    rsTrabajadores.getInt("idTienda"));
            listaTrabajadores.add(trabajador);
        }
        return listaTrabajadores;
    }
    
    public List<Trabajador> cargarTrabajadoresTienda(int tienda) throws SQLException {
        PreparedStatement psTrabajadores;
        ResultSet rsTrabajadores;
        Trabajador trabajador;
        List<Trabajador> listaTrabajadores = new ArrayList<>();
        psTrabajadores = conexion.prepareStatement("SELECT * FROM trabajadores WHERE idTienda=?;");
        psTrabajadores.setInt(1, tienda);
        rsTrabajadores = psTrabajadores.executeQuery();
        while (rsTrabajadores.next()) {

            trabajador = new Trabajador(rsTrabajadores.getInt("idTrabajador"),
                    rsTrabajadores.getString("dni"),
                    rsTrabajadores.getString("nombre"),
                    rsTrabajadores.getString("apellido1"),
                    rsTrabajadores.getString("apellido2"),
                    rsTrabajadores.getString("puesto"),
                    rsTrabajadores.getDouble("salarioBrutoAnual"),
                    rsTrabajadores.getDate("fechaAlta").toLocalDate(),
                    rsTrabajadores.getString("nick"),
                    rsTrabajadores.getString("password"),
                    rsTrabajadores.getTime("horaEntrada").toLocalTime(),
                    rsTrabajadores.getTime("horaSalida").toLocalTime(),
                    rsTrabajadores.getInt("idTienda"));
            listaTrabajadores.add(trabajador);
        }
        return listaTrabajadores;
    }
    
    public Trabajador cargarTrabajador(String buscando, int tipo, int idTienda) throws SQLException {
        PreparedStatement psTrabajadores;
        ResultSet rsTrabajadores = null;
        Trabajador trabajador = null;

        switch (tipo) {
            case 0:// BUSQUEDA POR USER
                psTrabajadores = conexion.prepareStatement("SELECT * FROM trabajadores WHERE nick=?;");
                psTrabajadores.setString(1, buscando);
                rsTrabajadores = psTrabajadores.executeQuery();
                break;

            case 1:// BUSQUEDA POR ID
                psTrabajadores = conexion.prepareStatement("SELECT * FROM trabajadores WHERE idTrabajador=? AND idTienda=?;");
                psTrabajadores.setString(1, buscando);
                psTrabajadores.setInt(2, idTienda);
                rsTrabajadores = psTrabajadores.executeQuery();
                break;

            case 2: //BUSQUEDA POR DNI
                psTrabajadores = conexion.prepareStatement("SELECT * FROM trabajadores WHERE dni=?  AND idTienda=?;");
                psTrabajadores.setString(1, buscando);
                psTrabajadores.setInt(2, idTienda);
                rsTrabajadores = psTrabajadores.executeQuery();
                break;
            default:
                break;
        }

        rsTrabajadores.next();

        trabajador = new Trabajador(rsTrabajadores.getInt("idTrabajador"),
                rsTrabajadores.getString("dni"),
                rsTrabajadores.getString("nombre"),
                rsTrabajadores.getString("apellido1"),
                rsTrabajadores.getString("apellido2"),
                rsTrabajadores.getString("puesto"),
                rsTrabajadores.getDouble("salarioBrutoAnual"),
                rsTrabajadores.getDate("fechaAlta").toLocalDate(),
                rsTrabajadores.getString("nick"),
                rsTrabajadores.getString("password"),
                rsTrabajadores.getTime("horaEntrada").toLocalTime(),
                rsTrabajadores.getTime("horaSalida").toLocalTime(),
                rsTrabajadores.getInt("idTienda"));

        return trabajador;
    }

    public void cambiarContraseña(String usuario, String pass) {

        try {
            PreparedStatement psTrabajadores;
            psTrabajadores = conexion.prepareStatement("CALL cambiarContraseña(?,?);");
            psTrabajadores.setString(1, usuario);
            psTrabajadores.setString(2, pass);
            psTrabajadores.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(TrabajadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int idTrabajador(String nombreTrabajador) throws SQLException {
        int resultado = 0;

        PreparedStatement psTrabajador;
        ResultSet rsTrabajador;
        psTrabajador = conexion.prepareStatement("SELECT idTrabajador FROM trabajadores WHERE nombre = ?;");
        psTrabajador.setString(1, nombreTrabajador);
        rsTrabajador = psTrabajador.executeQuery();
        while (rsTrabajador.next()) {
            resultado = rsTrabajador.getInt("idTrabajador");
        }

        return resultado;
    }

    public void despedir(String trabajador, int modo) throws SQLException {
        PreparedStatement psTrabajadores;
        
        switch (modo) {
            case 1:// BORRAR POR ID
                psTrabajadores = conexion.prepareStatement("DELETE FROM trabajadores WHERE idTrabajador=?;");
                psTrabajadores.setString(1, trabajador);
                psTrabajadores.executeUpdate();
                break;

            case 2: //BORRAR POR DNI
                psTrabajadores = conexion.prepareStatement("DELETE FROM trabajadores WHERE dni=?;");
                psTrabajadores.setString(1, trabajador);
                psTrabajadores.executeUpdate();
                break;
            default:
                break;
        }
    }

}
