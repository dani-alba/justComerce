/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Modelo.Producto;
import Modelo.Trabajador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author daw
 */
public class TiendasProductosDAO {

    private Connection conexion;

    public TiendasProductosDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void anadirProducto(int idTienda, Producto producto, int cantidad) throws SQLException {

        ResultSet rsProducto;
        PreparedStatement psProducto;
        PreparedStatement psTienda_producto;

        psProducto = conexion.prepareStatement("INSERT INTO productos "
                + "(referencia, nombre, categoria, descripcion, precioCompra, precioVenta, iva) "
                + "VALUES(?,?,?,?,?,?,?);");

        psProducto.setInt(1, producto.getReferencia());
        psProducto.setString(2, producto.getNombre());
        psProducto.setString(3, producto.getCategoria());
        psProducto.setString(4, producto.getDescripcion());
        psProducto.setDouble(5, producto.getPrecioCompra());
        psProducto.setDouble(6, producto.getPrecioVenta());
        psProducto.setDouble(7, producto.getIva());
        psProducto.executeUpdate();

        psTienda_producto = conexion.prepareStatement("INSERT INTO tiendas_productos"
                + "(idTienda, idProducto, stock)"
                + "VALUES (?,?,?);");
        psTienda_producto.setInt(1, idTienda);
        psTienda_producto.setInt(2, producto.getReferencia());
        psTienda_producto.setInt(3, cantidad);
        psTienda_producto.executeUpdate();

    }

}
