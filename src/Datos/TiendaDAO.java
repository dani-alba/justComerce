/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Modelo.Tienda;
import Modelo.Trabajador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class TiendaDAO {

    private Connection conexion;

    public TiendaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List cargarDatos() throws SQLException {

        PreparedStatement psTiendas;
        ResultSet rsTiendas;
        Tienda tienda;
        List<Tienda> listaTiendas = new ArrayList<>();
        psTiendas = conexion.prepareStatement("SELECT * FROM tiendas;");
        rsTiendas = psTiendas.executeQuery();
        while (rsTiendas.next()) {
            tienda = new Tienda(rsTiendas.getInt("idTienda"),
                    rsTiendas.getString("nombre"),
                    rsTiendas.getString("direccion"),
                    rsTiendas.getString("ciudad"));
            listaTiendas.add(tienda);
        }
        return listaTiendas;
    }

    public int idTienda(String nombreTienda) throws SQLException {

        PreparedStatement psTiendas;
        ResultSet rsTiendas;
        psTiendas = conexion.prepareStatement("SELECT idTienda FROM tiendas WHERE nombre = ?;");
        psTiendas.setString(1, nombreTienda);
        rsTiendas = psTiendas.executeQuery();
        rsTiendas.next();

        return rsTiendas.getInt("idTienda");
    }

    public String nombreTienda(int idTienda) throws SQLException {

        PreparedStatement psTiendas;
        ResultSet rsTiendas;
        psTiendas = conexion.prepareStatement("SELECT nombre FROM tiendas WHERE idTienda = ?;");
        psTiendas.setInt(1, idTienda);
        rsTiendas = psTiendas.executeQuery();
        rsTiendas.next();

        return rsTiendas.getString("nombre");
    }

    public Tienda cargarTienda(Trabajador trabajador) throws SQLException {
        PreparedStatement psTienda;
        ResultSet rsTienda = null;
        Tienda tienda = null;
        String nombreTrabajador = trabajador.getNombre();

        psTienda = conexion.prepareStatement("SELECT * FROM tiendas WHERE idTienda = (SELECT idTienda FROM trabajadores WHERE nombre = ?);");
        psTienda.setString(1, nombreTrabajador);
        rsTienda = psTienda.executeQuery();

        rsTienda.next();

        tienda = new Tienda(rsTienda.getInt("idTienda"),
                rsTienda.getString("nombre"),
                rsTienda.getString("direccion"),
                rsTienda.getString("ciudad"));

        return tienda;
    }

}
